package org.team751.vision;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.LinearAverages;
import edu.wpi.first.wpilibj.image.NIVision;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

/**
 * Does basic vision with the Axis camera
 *
 * @author Sam Crow
 */
public class AxisVision {

    //<editor-fold defaultstate="collapsed" desc="Many constants">
    final int XMAXSIZE = 24;
    final int XMINSIZE = 24;
    final int YMAXSIZE = 24;
    final int YMINSIZE = 48;
    final double xMax[] = {1, 1, 1, 1, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, 1, 1, 1, 1};
    final double xMin[] = {.4, .6, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, 0.6, 0};
    final double yMax[] = {1, 1, 1, 1, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, 1, 1, 1, 1};
    final double yMin[] = {.4, .6, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05,
        .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05,
        .05, .05, .6, 0};
    final int RECTANGULARITY_LIMIT = 60;
    final int ASPECT_RATIO_LIMIT = 75;
    final int X_EDGE_LIMIT = 40;
    final int Y_EDGE_LIMIT = 60;
    final int X_IMAGE_RES = 320;          //X Image resolution in pixels, should be 160, 320 or 640
    final double VIEW_ANGLE = 67;       //Axis M1013 camera

    public class Scores {

        double rectangularity;
        double aspectRatioInner;
        double aspectRatioOuter;
        double xEdge;
        double yEdge;
    }
    //</editor-fold>
    AxisCamera camera;          // the axis camera object (connected to the switch)
    CriteriaCollection cc;      // the criteria for doing the particle filter operation

    public AxisVision() {
        camera = AxisCamera.getInstance();
        cc = new CriteriaCollection();      // create the criteria for the particle filter
        cc.addCriteria(NIVision.MeasurementType.IMAQ_MT_AREA, 500, 65535, false);
    }

    public void run() {

        if (camera.freshImage()) {
            try {
                ColorImage color = camera.getImage();

                //Blue and red are all ranges, green is >=100
                BinaryImage thresholdImage = color.thresholdRGB(0, 255, 100, 255, 0, 255);
                color.free();

                BinaryImage convexHullImage = thresholdImage.convexHull(false);          // fill in occluded rectangles

                BinaryImage filteredImage = convexHullImage.particleFilter(cc);           // filter out small particles
                convexHullImage.free();

                //iterate through each particle and score to see if it is a target
                Scores scores[] = new Scores[filteredImage.getNumberParticles()];
                for (int i = 0; i < scores.length; i++) {
                    ParticleAnalysisReport report = filteredImage.getParticleAnalysisReport(i);
                    scores[i] = new Scores();

                    scores[i].rectangularity = scoreRectangularity(report);
                    scores[i].aspectRatioOuter = scoreAspectRatio(filteredImage, report, i, true);
                    scores[i].aspectRatioInner = scoreAspectRatio(filteredImage, report, i, false);
                    scores[i].xEdge = scoreXEdge(thresholdImage, report);
                    scores[i].yEdge = scoreYEdge(thresholdImage, report);

                    if (scoreCompare(scores[i], false)) {
                        System.out.println("particle: " + i + "is a High Goal  centerX: " + report.center_mass_x_normalized + "centerY: " + report.center_mass_y_normalized);
                        System.out.println("Distance: " + computeDistance(thresholdImage, report, i, false));
                    } else if (scoreCompare(scores[i], true)) {
                        System.out.println("particle: " + i + "is a Middle Goal  centerX: " + report.center_mass_x_normalized + "centerY: " + report.center_mass_y_normalized);
                        System.out.println("Distance: " + computeDistance(thresholdImage, report, i, true));
                    } else {
                        System.out.println("particle: " + i + "is not a goal  centerX: " + report.center_mass_x_normalized + "centerY: " + report.center_mass_y_normalized);
                    }
                    System.out.println("rect: " + scores[i].rectangularity + "ARinner: " + scores[i].aspectRatioInner);
                    System.out.println("ARouter: " + scores[i].aspectRatioOuter + "xEdge: " + scores[i].xEdge + "yEdge: " + scores[i].yEdge);
                }

                filteredImage.free();
                thresholdImage.free();

            } catch (AxisCameraException ex) {
                ex.printStackTrace();
            } catch (NIVisionException ex) {
                ex.printStackTrace();
            }


        }
    }

    /**
     * Computes the estimated distance to a target using the height of the
     * particle in the image. For more information and graphics showing the math
     * behind this approach see the Vision Processing section of the
     * ScreenStepsLive documentation.
     *
     * @param image The image to use for measuring the particle estimated
     * rectangle
     * @param report The Particle Analysis Report for the particle
     * @param outer True if the particle should be treated as an outer target,
     * false to treat it as a center target
     * @return The estimated distance to the target in Inches.
     */
    double computeDistance(BinaryImage image, ParticleAnalysisReport report, int particleNumber, boolean outer) throws NIVisionException {
        double rectShort, height;
        int targetHeight;

        rectShort = NIVision.MeasureParticle(image.image, particleNumber, false, NIVision.MeasurementType.IMAQ_MT_EQUIVALENT_RECT_SHORT_SIDE);
        //using the smaller of the estimated rectangle short side and the bounding rectangle height results in better performance
        //on skewed rectangles
        height = Math.min(report.boundingRectHeight, rectShort);
        targetHeight = outer ? 29 : 21;

        return X_IMAGE_RES * targetHeight / (height * 12 * 2 * Math.tan(VIEW_ANGLE * Math.PI / (180 * 2)));
    }

    /**
     * Computes a score (0-100) comparing the aspect ratio to the ideal aspect
     * ratio for the target. This method uses the equivalent rectangle sides to
     * determine aspect ratio as it performs better as the target gets skewed by
     * moving to the left or right. The equivalent rectangle is the rectangle
     * with sides x and y where particle area= x*y and particle perimeter= 2x+2y
     *
     * @param image The image containing the particle to score, needed to
     * perform additional measurements
     * @param report The Particle Analysis Report for the particle, used for the
     * width, height, and particle number
     * @param outer	Indicates whether the particle aspect ratio should be
     * compared to the ratio for the inner target or the outer
     * @return The aspect ratio score (0-100)
     */
    public double scoreAspectRatio(BinaryImage image, ParticleAnalysisReport report, int particleNumber, boolean outer) throws NIVisionException {
        double rectLong, rectShort, aspectRatio, idealAspectRatio;

        rectLong = NIVision.MeasureParticle(image.image, particleNumber, false, NIVision.MeasurementType.IMAQ_MT_EQUIVALENT_RECT_LONG_SIDE);
        rectShort = NIVision.MeasureParticle(image.image, particleNumber, false, NIVision.MeasurementType.IMAQ_MT_EQUIVALENT_RECT_SHORT_SIDE);
        idealAspectRatio = outer ? (62 / 29) : (62 / 20);	//Dimensions of goal opening + 4 inches on all 4 sides for reflective tape

        //Divide width by height to measure aspect ratio
        if (report.boundingRectWidth > report.boundingRectHeight) {
            //particle is wider than it is tall, divide long by short
            aspectRatio = 100 * (1 - Math.abs((1 - ((rectLong / rectShort) / idealAspectRatio))));
        } else {
            //particle is taller than it is wide, divide short by long
            aspectRatio = 100 * (1 - Math.abs((1 - ((rectShort / rectLong) / idealAspectRatio))));
        }
        return (Math.max(0, Math.min(aspectRatio, 100.0)));		//force to be in range 0-100
    }

    /**
     * Compares scores to defined limits and returns true if the particle
     * appears to be a target
     *
     * @param scores The structure containing the scores to compare
     * @param outer True if the particle should be treated as an outer target,
     * false to treat it as a center target
     *
     * @return True if the particle meets all limits, false otherwise
     */
    boolean scoreCompare(Scores scores, boolean outer) {
        boolean isTarget = true;

        isTarget &= scores.rectangularity > RECTANGULARITY_LIMIT;
        if (outer) {
            isTarget &= scores.aspectRatioOuter > ASPECT_RATIO_LIMIT;
        } else {
            isTarget &= scores.aspectRatioInner > ASPECT_RATIO_LIMIT;
        }
        isTarget &= scores.xEdge > X_EDGE_LIMIT;
        isTarget &= scores.yEdge > Y_EDGE_LIMIT;

        return isTarget;
    }

    /**
     * Computes a score (0-100) estimating how rectangular the particle is by
     * comparing the area of the particle to the area of the bounding box
     * surrounding it. A perfect rectangle would cover the entire bounding box.
     *
     * @param report The Particle Analysis Report for the particle to score
     * @return The rectangularity score (0-100)
     */
    double scoreRectangularity(ParticleAnalysisReport report) {
        if (report.boundingRectWidth * report.boundingRectHeight != 0) {
            return 100 * report.particleArea / (report.boundingRectWidth * report.boundingRectHeight);
        } else {
            return 0;
        }
    }

    /**
     * Computes a score based on the match between a template profile and the
     * particle profile in the X direction. This method uses the the column
     * averages and the profile defined at the top of the sample to look for the
     * solid vertical edges with a hollow center.
     *
     * @param image The image to use, should be the image before the convex hull
     * is performed
     * @param report The Particle Analysis Report for the particle
     *
     * @return The X Edge Score (0-100)
     */
    public double scoreXEdge(BinaryImage image, ParticleAnalysisReport report) throws NIVisionException {
        double total = 0;
        LinearAverages averages;

        NIVision.Rect rect = new NIVision.Rect(report.boundingRectTop, report.boundingRectLeft, report.boundingRectHeight, report.boundingRectWidth);
        averages = NIVision.getLinearAverages(image.image, LinearAverages.LinearAveragesMode.IMAQ_COLUMN_AVERAGES, rect);
        float columnAverages[] = averages.getColumnAverages();
        for (int i = 0; i < (columnAverages.length); i++) {
            if (xMin[(i * (XMINSIZE - 1) / columnAverages.length)] < columnAverages[i]
                    && columnAverages[i] < xMax[i * (XMAXSIZE - 1) / columnAverages.length]) {
                total++;
            }
        }
        total = 100 * total / (columnAverages.length);
        return total;
    }

    /**
     * Computes a score based on the match between a template profile and the
     * particle profile in the Y direction. This method uses the the row
     * averages and the profile defined at the top of the sample to look for the
     * solid horizontal edges with a hollow center
     *
     * @param image The image to use, should be the image before the convex hull
     * is performed
     * @param report The Particle Analysis Report for the particle
     *
     * @return The Y Edge score (0-100)
     *
     */
    public double scoreYEdge(BinaryImage image, ParticleAnalysisReport report) throws NIVisionException {
        double total = 0;
        LinearAverages averages;

        NIVision.Rect rect = new NIVision.Rect(report.boundingRectTop, report.boundingRectLeft, report.boundingRectHeight, report.boundingRectWidth);
        averages = NIVision.getLinearAverages(image.image, LinearAverages.LinearAveragesMode.IMAQ_ROW_AVERAGES, rect);
        float rowAverages[] = averages.getRowAverages();
        for (int i = 0; i < (rowAverages.length); i++) {
            if (yMin[(i * (YMINSIZE - 1) / rowAverages.length)] < rowAverages[i]
                    && rowAverages[i] < yMax[i * (YMAXSIZE - 1) / rowAverages.length]) {
                total++;
            }
        }
        total = 100 * total / (rowAverages.length);
        return total;
    }
}
