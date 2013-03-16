package org.team751.tasks;

/**
 * An interface for a general closed-loop speed controller
 * @author Sam Crow
 */
public abstract class ClosedLoopSpeedController extends PeriodicTask {

    /**
     * Disable the controller and stop the motor.
     */
    public abstract void disable();

    /**
     * Enable the controller. This will start the motor
     * if the target RPM has been set above zero.
     */
    public abstract void enable();

    /**
     * Get the actual speed of the system that is being controlled
     * @return the actual speed of the system that is being controlled, in RPM
     */
    public abstract double getActualRpm();

    /**
     * Determine if the actual speed and the target speed match to within
     * 100 RPM.
     * @return true if on target, otherwise false
     */
    public abstract boolean isOnTarget();

    /**
     * Set the RPM to target
     * @param rpm the target RPM
     */
    public abstract void setTargetRpm(double rpm);
    
}
