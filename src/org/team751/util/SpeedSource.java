package org.team751.util;

/**
 * An interface for something that can provide a rate of rotation
 * @author Sam Crow
 */
public interface SpeedSource {
    
    /**
     * Get the rotational velocity of the object, in revolutions per minute
     * @return the velocity
     */
    public double getRpm();
    
}
