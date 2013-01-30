package org.team751.util;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 * Keeps track of the electrical current that the drivetrain is consuming and
 * determines how much power limiting is required to prevent the 120A main
 * breaker from tripping
 *
 * @author Sam Crow
 */
public class DrivetrainCurrentLimiter {

    /**
     * The Jaguars that are being monitored
     */
    private CANJaguar[] jaguars;
    /**
     * If this limiter should do anything (true if all the provided
     * SpeedControllers are indeed CANJaguars)
     */
    private boolean enabled = false;
    /**
     * This stores 10 data points of the current consumed by all the motors in
     * total over the last 5 seconds
     */
    private FixedCapacityDoubleQueue currentValues = new FixedCapacityDoubleQueue(10);
    /**
     * The time (as returned by {@link java.lang.System#currentTimeMillis()})
     * that the process() function last did processing
     */
    private long lastRunTime = 0;

    /**
     * Determine the current consumption of each motor and decide if any
     * limiting is required
     */
    public void process() {

        if (enabled) {

            //Only do anything if 500 milliseconds have passed since the last run
            long now = System.currentTimeMillis();

            if (now - lastRunTime >= 500) {

                //Process data
                double totalCurrent = 0;
                for (int i = 0, max = jaguars.length; i < max; i++) {
                    try {
                        totalCurrent += jaguars[i].getOutputCurrent();
                    } catch (CANTimeoutException ex) {
                        ex.printStackTrace();
                    }
                }

                currentValues.enqueue(totalCurrent);


                //Integrate the current over time (time = 5 seconds)
                //to get the overall (something) in amp-seconds
                double ampSeconds = 0;
                for (int i = 0, max = currentValues.length; i < max; i++) {
                    ampSeconds += 0.5 * currentValues.at(i);
                }

                System.out.println("Current over last 5 seconds: " + ampSeconds + " amp-seconds");

                lastRunTime = now;
            }

        }
    }

    /**
     * Constructor
     *
     * @param controllers A set of CANJaguar controllers to monitor. If one or
     * more of these is not a CANJaguar, no monitoring nor limiting will be
     * performed.
     */
    public DrivetrainCurrentLimiter(SpeedController[] controllers) {
        //Initialize the jaguars array to be the same size as the controllers array
        jaguars = new CANJaguar[controllers.length];

        try {
            //No foreach in J2ME 1.5, so use a for loop
            for (int i = 0, max = controllers.length; i < max; i++) {
                SpeedController controller = controllers[i];

                //Try to cast the controller into a CANJaguar
                CANJaguar jaguar = (CANJaguar) controller;

                jaguars[i] = jaguar;
            }
            enabled = true;
        } catch (ClassCastException e) {
            //If any of the SpeedControllers couldn't be cast into a CANJaguar
            System.err.println("a SpeedController was not a CANJaguar! No current monitoring will be performed.");
            enabled = false;
        }
    }
}
