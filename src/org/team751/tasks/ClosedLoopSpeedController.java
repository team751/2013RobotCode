package org.team751.tasks;

import edu.wpi.first.wpilibj.SpeedController;
import org.team751.util.SpeedSource;

/**
 * Implements a controller that controls the speed of a motor based on feedback
 * from an encoder.
 *
 * @author Sam Crow
 */
public class ClosedLoopSpeedController extends PeriodicTask {

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
     * Enable the controller. This will start the motor
     * if the target RPM has been set above zero.
     */
    public synchronized void enable() {
        enabled = true;
    }
    
    /**
     * Disable the controller and stop the motor.
     */
    public synchronized void disable() {
        enabled = false;
        //Ensure that the motor is stopped
        controller.set(0);
    }
    
    /**
     * Set the RPM to target
     * @param rpm the target RPM
     */
    public synchronized void setTargetRpm(double rpm) {
        targetRpm = rpm;
    }

    protected synchronized void run() {
        if (enabled) {

            double currentRpm = source.getRpm();
            double power = controller.get();

            if (currentRpm > targetRpm) {
                //slightly decrease the power
                power -= 0.01;

            }
            
            if(currentRpm < targetRpm) {
                //slightly increase the power
                power += 0.1;
            }

            //Prevent the motor from going into reverse
            if (power < 0) {
                power = 0;
            }
            //Limit to <= 1
            if(power > 1) {
                power = 1;
            }

            controller.set(power);
        }
    }
}
