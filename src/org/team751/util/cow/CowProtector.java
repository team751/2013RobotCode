package org.team751.util.cow;

import org.team751.commands.CommandBase;
import org.team751.subsystems.Cow;

/**
 * Keeps track of the state of the cow and determines if it is safe to move.
 * @author Sam Crow
 */
public class CowProtector {
    
    
    /**
     * The cow that is being monitored and protected
     */
    private Cow cow;
    
    /**
     * Constructor
     * @param parent the cow to monitor
     */
    public CowProtector(Cow parent) {
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
    public boolean canMoveTo(CowPosition position) {
        
		//Return false if the cow can't move at all
		if(!canMoveAtAll()) return false;
		
        if(position == CowPosition.kLoad0) {
			//Load 0: Loading into furthest back position
			
			return true;
		}
		else if(position == CowPosition.kLoad1) {
			//Load 1: Loading into furthest back +1 (this rotates back 1 slot
			//further than load0)
			
			return true;
		}
		else if(position == CowPosition.kLoad2) {
			//Load 2: Back 1 slot further than load1
			return true;
		}
		else if(position == CowPosition.kLoad3) {
			//Load 3: Back 1 slot further than load2
		}
		else if(position == CowPosition.kShoot0) {
			//Shoot 0: Shoot from the furthest back (topmost) stomach
			//Stomachs 1-3 must be empty so that no disk crashes into the
			//shooter as it descends.
			if(cow.isStomach1Full() || cow.isStomach2Full() || cow.isStomach3Full()) {
				return false;
			}
			else {
				return true;
			}
		}
		else if(position == CowPosition.kShoot1) {
			//Shoot 1: One slot back from shoot0
			//Stomachs 2-3 must be empty
			if(cow.isStomach2Full() || cow.isStomach3Full()) {
				return false;
			}
			else {
				return true;
			}
		}
		else if(position == CowPosition.kShoot2) {
			//Shoot 2: One slot back from shoot1
			//Stomach 3 must be empty
			if(cow.isStomach3Full()) {
				return false;
			}
			else {
				return true;
			}
		}
		else if(position == CowPosition.kShoot3) {
			//Shooting from the furthest-forward stomach
			//always safe
			return true;
		}
        
        return false;
        
    }
}
