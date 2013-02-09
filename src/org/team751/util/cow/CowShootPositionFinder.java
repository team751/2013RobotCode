package org.team751.util.cow;

import org.team751.subsystems.Cow2.Position;

/**
 * Finds the best position to move the cow to to shoot a disk.
 * @author Sam Crow
 */
public class CowShootPositionFinder implements CowPositionFinder {

    private Position closestPosition;

    /**
     * Constructor
     * @param status The current occupation status of the cow
     * @param currentPosition The current target position of the cow
     */
    public CowShootPositionFinder(CowOccupationStatus status, Position currentPosition) {

        //Find the best position to move to

        //If currently in a loading position, the nearest position is the shooting
        //position that is rotated the least far forward.
        //kShoot3 is the best in this situation, followed by the lower numbers.
        if(currentPosition == Position.kLoad0 || currentPosition == Position.kLoad1
                || currentPosition == Position.kLoad2 || currentPosition == Position.kLoad3) {
            //Best case: kShoot3
            if(status.slot3) {
                closestPosition = Position.kShoot3;
                return;
            }
            if(status.slot2) {
                closestPosition = Position.kShoot2;
                return;
            }
            if(status.slot1) {
                closestPosition = Position.kShoot1;
                return;
            }
            if(status.slot0) {
                closestPosition = Position.kShoot0;
                return;
            }
        }

        //If currently in shoot3 position, closest shoot positions are 2, then 1, then 0
        if(currentPosition == Position.kShoot3) {
            if(status.slot2) {
                closestPosition = Position.kShoot2;
                return;
            }
            if(status.slot1) {
                closestPosition = Position.kShoot1;
                return;
            }
            if(status.slot0) {
                closestPosition = Position.kShoot0;
            }
        }

        //If currently in shoot2 position, closest shoot positions are {1 and 3}, then 0
		if(currentPosition == Position.kShoot2) {
			//Arbitrarily prioritize slot 1 over 3, because it is more central
			if(status.slot1) {
				closestPosition = Position.kShoot1;
				return;
			}
			if(status.slot3) {
				closestPosition = Position.kShoot3;
				return;
			}
			if(status.slot0) {
				closestPosition = Position.kShoot0;
				return;
			}
		}

		//If currently in shoot1 position, closest shoot positions are {0 and 2}, then 3
		if(currentPosition == Position.kShoot1) {
			//Prefer position 2, because it is more central
			if(status.slot2) {
				closestPosition = Position.kShoot2;
				return;
			}
			if(status.slot0) {
				closestPosition = Position.kShoot0;
				return;
			}

			if(status.slot3) {
				closestPosition = Position.kShoot3;
				return;
			}
		}

		//If currently in shoot0 position, preference is 1, then 2, and so on
		if(currentPosition == Position.kShoot0) {
			if(status.slot1) {
				closestPosition = Position.kShoot1;
				return;
			}
			if(status.slot2) {
				closestPosition = Position.kShoot2;
				return;
			}
			if(status.slot3) {
				closestPosition = Position.kShoot3;
				return;
			}
		}

		//The return statements shouldn't let code execution get here.
		System.err.println("CowShootPositionFinder: Closest position not found!");
    }



    public Position getClosestPosition() {

        return closestPosition;
    }

}
