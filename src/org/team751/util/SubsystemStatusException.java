package org.team751.util;

/**
 * An exception that a subsystem can throw when it is not working.
 * @author Sam Crow
 */
public class SubsystemStatusException extends Exception {

    /**
     * The exception that caused this one
     */
    private Exception cause;
    
    public SubsystemStatusException(Exception e) {
        cause = e;
    }
    
    
    
}
