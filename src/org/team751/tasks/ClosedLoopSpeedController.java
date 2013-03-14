package org.team751.tasks;

/**
 * An interface for a general closed-loop speed controller
 * @author Sam Crow
 */
public interface ClosedLoopSpeedController {

    /**
     * Disable the controller and stop the motor.
     */
    void disable();

    /**
     * Enable the controller. This will start the motor
     * if the target RPM has been set above zero.
     */
    void enable();

    /**
     * Get the actual speed of the system that is being controlled
     * @return the actual speed of the system that is being controlled, in RPM
     */
    double getActualRpm();

    /**
     * Determine if the actual speed and the target speed match to within
     * 100 RPM.
     * @return true if on target, otherwise false
     */
    boolean isOnTarget();

    /**
     * Set the RPM to target
     * @param rpm the target RPM
     */
    void setTargetRpm(double rpm);
    
}
