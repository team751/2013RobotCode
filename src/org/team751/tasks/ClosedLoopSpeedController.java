package org.team751.tasks;

import org.team751.util.SpeedSource;

/**
 * Implements a controller that controls the speed of a motor based on feedback
 * from an encoder.
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
    
    protected void run() {
        if(enabled) {
            
        }
    }
    
}
