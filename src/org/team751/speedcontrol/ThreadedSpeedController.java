package org.team751.speedcontrol;

/**
 * An interface for a general closed-loop speed controller
 * @author Sam Crow
 */
public abstract class ThreadedSpeedController extends Thread {
    
    /**
     * If the controller should be enabled
     */
    private volatile boolean enabled = false;
    
    /**
     * Start doing speed control work. If this controller has already
     * been started, this method will have no effect.
     */
    public void start() {
        if(!super.isAlive()) {
            super.start();
        }
    }

    /**
     * Disable the controller and stop the motor.
     */
    public synchronized void disable() {
        enabled = false;
        stopMotor();
    }

    /**
     * Enable the controller. This will start the motor
     * if the target RPM has been set above zero.
     */
    public synchronized void enable() {
        enabled = true;
    }

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
     * This method is called often when the controller is enabled.
     * Subclasses should override this method and do their speed control
     * calculations here.
     */
    protected abstract void runSpeedControl();
    
    /**
     * Called after {@link #disable() } is called. Subclasses should override
     * this to stop the motors that they are controlling.
     */
    protected abstract void stopMotor();

    /**
     * Set the RPM to target
     * @param rpm the target RPM
     */
    public abstract void setTargetRpm(double rpm);
    
    
    public void run() {
        while(true) {
            if(enabled) {
                runSpeedControl();
            }
            try {
                sleep(1);
            } catch (InterruptedException ex) {}
        }
    }
    
}
