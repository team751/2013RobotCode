package org.team751.util;

import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 * An exception that a subsystem can throw when it is not working.
 * @author Sam Crow
 */
public class SubsystemStatusException extends Exception {

    public SubsystemStatusException(CANTimeoutException e) {
        super(e);
    }
    
    
    
}
