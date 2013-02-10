package org.team751.util.cow;

import org.team751.subsystems.Cow2.Position;

/**
 * Finds the best position to move the cow to to load a disk
 * @author Sam Crow
 */
public class CowLoadPositionFinder implements CowPositionFinder {

	public Position getClosestPosition(final CowStomachStatus status,
									   final Position currentPosition) throws NoCowPositionException {
		
		//If all the stomachs are full, throw an exception saying so.
		if(status.areAllFull()) {
			throw new NoCowPositionException(
					NoCowPositionException.Cause.kStomachsFull);
		}
		
		//If currently in a shooting position, the closest loading position is
		//the one that is rotate the least far backwards. This is load0,
		//then load1, load2, and load3.
		if(currentPosition == Position.kShoot0 || currentPosition == Position.kShoot1
				|| currentPosition == Position.kShoot2 || currentPosition == Position.kShoot3) {
			//Choose the most preferable stomach that is not full
			if(!status.stomach0) {
				return Position.kLoad0;
			}
			if(!status.stomach1) {
				return Position.kLoad1;
			}
			if(!status.stomach2) {
				return Position.kLoad2;
			}
			if(!status.stomach3) {
				return Position.kLoad3;
			}
		}
		
		//If currently at load0, closest are load1, load2, load3
		if(currentPosition == Position.kLoad0) {
			if(!status.stomach1) {
				return Position.kLoad1;
			}
			if(!status.stomach2) {
				return Position.kLoad2;
			}
			if(!status.stomach3) {
				return Position.kLoad3;
			}
		}
		
		//If currently at load1, closest are {load0, load2} and load3
		if(currentPosition == Position.kLoad1) {
			//Prefer load2 because it is more central
			if(!status.stomach2) {
				return Position.kLoad2;
			}
			if(!status.stomach0) {
				return Position.kLoad0;
			}
			if(!status.stomach3) {
				return Position.kLoad3;
			}
		}
		
		//If currently at load2, closest are {load1, load3} and load0
		if(currentPosition == Position.kLoad2) {
			//Prefer load1 because it is more central
			if(!status.stomach1) {
				return Position.kLoad1;
			}
			if(!status.stomach3) {
				return Position.kLoad3;
			}
			if(!status.stomach0) {
				return Position.kLoad0;
			}
		}
		
		//If currently at load3, closest are load2, load1, and load0
		if(currentPosition == Position.kLoad3) {
			if(!status.stomach2) {
				return Position.kLoad2;
			}
			if(!status.stomach1) {
				return Position.kLoad1;
			}
			if(!status.stomach0) {
				return Position.kLoad0;
			}
		}
		
		//Throw an exception indicating an algorithm error
		throw new NoCowPositionException(NoCowPositionException.Cause.kAlgorithmError);
	}

}
