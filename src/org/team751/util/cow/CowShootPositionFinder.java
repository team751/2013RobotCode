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
        
        //TODO: logic for current positions shoot2, shoot1, shoot0
    }

    
    
    public Position getClosestPosition() {
        
        return closestPosition;
    }
    
}
