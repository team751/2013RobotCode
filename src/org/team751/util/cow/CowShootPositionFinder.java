package org.team751.util.cow;

import org.team751.subsystems.Cow2.Position;

/**
 * Finds the best position to move the cow to to shoot a disk.
 * @author Sam Crow
 */
public class CowShootPositionFinder implements CowPositionFinder {

	public Position getClosestPosition(final CowStomachStatus status,
									   final Position currentPosition) throws NoCowPositionException {
		
		//Throw an exception if no slot has a disk in it
		if(!status.isAnyFull()) {
			throw new NoCowPositionException(
					NoCowPositionException.Cause.kStomachsEmpty);
		}
		
		//Find the best position to move to

        //If currently in a loading position, the nearest position is the shooting
        //position that is rotated the least far forward.
        //kShoot3 is the best in this situation, followed by the lower numbers.
        if(currentPosition == Position.kLoad0 || currentPosition == Position.kLoad1
                || currentPosition == Position.kLoad2 || currentPosition == Position.kLoad3) {
            //Best case: kShoot3
            if(status.stomach3) {
                return Position.kShoot3;
            }
            if(status.stomach2) {
                return Position.kShoot2;
            }
            if(status.stomach1) {
                return Position.kShoot1;
            }
            if(status.stomach0) {
                return Position.kShoot0;
            }
        }

        //If currently in shoot3 position, closest shoot positions are 2, then 1, then 0
        if(currentPosition == Position.kShoot3) {
            if(status.stomach2) {
				return Position.kShoot2;
            }
            if(status.stomach1) {
                return Position.kShoot1;
            }
            if(status.stomach0) {
                return Position.kShoot0;
            }
        }

        //If currently in shoot2 position, closest shoot positions are {1 and 3}, then 0
		if(currentPosition == Position.kShoot2) {
			//Arbitrarily prioritize slot 1 over 3, because it is more central
			if(status.stomach1) {
				return Position.kShoot1;
			}
			if(status.stomach3) {
				return Position.kShoot3;
			}
			if(status.stomach0) {
				return Position.kShoot0;
			}
		}

		//If currently in shoot1 position, closest shoot positions are {0 and 2}, then 3
		if(currentPosition == Position.kShoot1) {
			//Prefer position 2, because it is more central
			if(status.stomach2) {
				return Position.kShoot2;
			}
			if(status.stomach0) {
				return Position.kShoot0;
			}

			if(status.stomach3) {
				return Position.kShoot3;
			}
		}

		//If currently in shoot0 position, preference is 1, then 2, and so on
		if(currentPosition == Position.kShoot0) {
			if(status.stomach1) {
				return Position.kShoot1;
			}
			if(status.stomach2) {
				return Position.kShoot2;
			}
			if(status.stomach3) {
				return Position.kShoot3;
			}
		}
		
		//Throw a new exception indicating an algorithm error
		throw new NoCowPositionException(NoCowPositionException.Cause.kAlgorithmError);
	}

}
