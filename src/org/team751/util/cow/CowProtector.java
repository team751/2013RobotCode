package org.team751.util.cow;

import org.team751.commands.CommandBase;
import org.team751.subsystems.Cow2;
import org.team751.subsystems.Cow2.Position;

/**
 * Keeps track of the state of the cow and determines if it is safe to move.
 * @author Sam Crow
 */
public class CowProtector {
    
    
    /**
     * The cow that is being monitored and protected
     */
    private Cow2 cow;
    
    /**
     * Constructor
     * @param parent the cow to monitor
     */
    public CowProtector(Cow2 parent) {
        cow = parent;
    }
    
    /**
     * Determine if the cow can safely move at all.
     * This will be true if the pusher is fully retracted
     * @return 
     */
    public boolean canMoveAtAll() {
        
        return CommandBase.pusher.isRetracted();
        
    }
    
    /**
     * Determine if the cow can safely move to the given position
     * @param position The position to check
     * @return 
     */
    public boolean canMoveTo(Cow2.Position position) {
        
        /*
         * If the cow is rotated forward so that a stomach that is full is
         * forward of the shooter, the disk will crash into the shooter.
         */
        if(cow.isStomach3Full()) {
            //Don't move forward of shoot3
            if(position == Position.kShoot3) {
                return true;
            }
            else {
                return false;
            }
        }
        
        return false;
        
    }
}
