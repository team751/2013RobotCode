package org.team751.util;

import edu.wpi.first.wpilibj.Encoder;
import org.team751.tasks.PeriodicTask;

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
    
    
    
    protected void run() {
        if(enabled) {
            
        }
    }
    
}
