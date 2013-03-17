package org.team751.speedcontrol;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team751.util.SpeedSource;

/**
 * Implements a take-back-half speed control system.
 *
 * For information, see:
 * http://www.chiefdelphi.com/forums/showthread.php?threadid=105965
 * http://www.chiefdelphi.com/media/papers/2674?
 *
 * @author Sam Crow
 */
public class TakeBackHalfSpeedController extends ThreadedSpeedController {

    /**
     * The source used to get the current speed
     */
    private SpeedSource source;
    /**
     * The speed controller used to send output
     */
    private SpeedController controller;
    /**
     * The target speed, in RPM
     */
    private volatile double targetRpm = 0;
    //Control data
    
    /**
     * The ratio (0-1) of motor power that is being applied
     */
    private double motorPower = 0;
    
    /**
     * Gain (G)
     */
    private static final double gain = 1E-5;
    /**
     * The error encountered during the last loop
     */
    private double lastError = 10;
    /**
     * The take-back-half variable (b)
     */
    private double tbh = 0;
    
    /**
     * The approximate speed of the shooter wheels, in RPM,
     * at full motor power. This is used for spinup optimization.
     */
    private static final double kMaxRpm = 2800;

    /**
     * Constructor
     *
     * @param source The source to use to get the speed
     * @param controller The speed controller to control
     */
    public TakeBackHalfSpeedController(SpeedSource source, SpeedController controller) {
        this.source = source;
        this.controller = controller;
    }

    protected void runSpeedControl() {
//
//        System.out.println("Loop time " + (Utility.getFPGATime() - lastLoopTime) + " microseconds");
//        lastLoopTime = Utility.getFPGATime();

        double error = targetRpm - getActualRpm();
        
        SmartDashboard.putNumber("RPM", getActualRpm());

        motorPower += gain * error;
//        System.out.println("Increasing power by "+(gain * error));
        motorPower = clamp(motorPower);

        //If the error has changed in sign since the last processing
        if (isPositive(lastError) != isPositive(error)) {
            motorPower = 0.5 * (motorPower + tbh);
            tbh = motorPower;

            lastError = error;
        }

        controller.set(motorPower);

    }

    public double getActualRpm() {
        return source.getRpm();
    }

    public boolean isOnTarget() {
        double difference = getActualRpm() - targetRpm;

        return Math.abs(difference) < 100;
    }

    public void setTargetRpm(double newRpm) {
        
        //Set up values for optimized spinup to the target
        if(targetRpm < newRpm) {
//            motorPower = 1;
            lastError = 1;
        }
        else if(targetRpm > newRpm) {
//            motorPower = 0.7;
            lastError = -1;
        }
        tbh = (2 * (newRpm / kMaxRpm)) - 1;
        
        targetRpm = newRpm;
        
        
        System.out.println("Target RPM set to "+newRpm);
    }

    /**
     * Clamp a value to within +/- 1, for use with speed controllers
     *
     * @param input The value to clamp
     * @return the clamped value
     */
    private static double clamp(double input) {
        if (input > 1) {
            return 1;
        }
        if (input < -1) {
            return -1;
        }
        return input;
    }

    /**
     * Determine if a value is positive
     *
     * @param input
     * @return
     */
    private static boolean isPositive(double input) {
        return input > 0;
    }

    protected void stopMotor() {
        controller.set(0);
    }
}
