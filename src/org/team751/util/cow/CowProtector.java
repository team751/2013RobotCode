package org.team751.util.cow;

import org.team751.commands.CommandBase;
import org.team751.subsystems.Cow2;

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
        
        return false;
        
    }
}
