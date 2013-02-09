package org.team751.util.cow;

import org.team751.subsystems.Cow2;

/**
 * An interface for a class that, based on a {@link CowOccupationStatus}, can
 * choose the best position to move the cow to to load or shoot.
 * @author Sam Crow
 */
public interface CowPositionFinder {
    
    
    /**
     * Get the closest position to move to. If two positions are equally close,
     * one may be chosen arbitrarily.
     * @return the closest position
     */
    public Cow2.Position getClosestPosition();
}
