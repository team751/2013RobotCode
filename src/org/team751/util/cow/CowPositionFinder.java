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
	 * 
	 * @param status The current cow slot occupancy status
	 * @param currentPosition The current cow target position
	 * @return the closest position
	 * @throws NoCowPositionException if no position could be found. This might
	 * happen when trying to find a shooting position when no slot contains
	 * a disk, or when trying to find a loading position when every slot
	 * contains a disk.
     */
    public Cow2.Position getClosestPosition
			(final CowOccupationStatus status, final Cow2.Position currentPosition)
			throws NoCowPositionException;
}
