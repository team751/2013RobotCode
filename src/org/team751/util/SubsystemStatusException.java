package org.team751.util;

/**
 * An exception that a subsystem can throw when it is not working.
 * @author Sam Crow
 */
public class SubsystemStatusException extends Exception {

    public SubsystemStatusException(Exception e) {
        super(e);
    }
    
    
    
}
