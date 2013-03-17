package org.team751.speedcontrol;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team751.util.SpeedSource;

/**
 * Implements a controller that controls the speed of a motor based on feedback
 * from an encoder.
 *
 * @author Sam Crow
 */
public class BangBangSpeedController extends ThreadedSpeedController {

    /**
     * If the controller is enabled and should control the motor
     */
    private volatile boolean enabled = false;
    /**
     * The source that provides speed information
     */
    private SpeedSource source;
    /**
     * The controller that is used to set the speed
     */
    private SpeedController controller;
    /**
     * The target speed, in RPM
     */
    private volatile double targetRpm = 0;
    /**
     * The time in microseconds when this loop last ran
     */
    private long lastLoopTime = Utility.getFPGATime();

    /**
     * Constructor
     *
     * @param source The speed source that provides the speed
     * @param controller The speed controller used to set the speed
     */
    public BangBangSpeedController(SpeedSource source, SpeedController controller) {
        this.source = source;
        this.controller = controller;
    }

    /**
     * Set the RPM to target
     *
     * @param rpm the target RPM
     */
    public synchronized void setTargetRpm(double rpm) {
        targetRpm = rpm;
    }

    /**
     * Determine if the actual speed and the target speed match to within 100
     * RPM.
     *
     * @return true if on target, otherwise false
     */
    public synchronized boolean isOnTarget() {
        double actualRpm = source.getRpm();

        double difference = Math.abs(actualRpm - targetRpm);

        return difference < 100;
    }

    protected void runSpeedControl() {

//        System.out.println("Loop time " + (Utility.getFPGATime() - lastLoopTime) + " microseconds");
//        lastLoopTime = Utility.getFPGATime();

        double currentRpm = source.getRpm();
        double power = controller.get();
        
        SmartDashboard.putNumber(toString(), currentRpm);

        if (currentRpm > targetRpm) {
            //slightly decrease the power
            power = 0;

        }

        if (currentRpm < targetRpm) {
            //slightly increase the power
            power += 1;
        }

        //Prevent the motor from going into reverse
        if (power < 0) {
            power = 0;
        }
        //Limit to <= 1
        if (power > 1) {
            power = 1;
        }

        controller.set(power);
    }
    
    protected void stopMotor() {
        controller.set(0);
    }

    public synchronized double getActualRpm() {
        return source.getRpm();
    }
}
