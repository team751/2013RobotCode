package org.team751.util.cow;

import edu.wpi.first.wpilibj.buttons.Trigger;
import org.team751.commands.cow3.NextLoadingPosition;

/**
 * Manages the triggers used to move the cow based on disk presence
 * @author Sam Crow
 */
public class CowTriggers {
    
    private Trigger stomach0Trigger = new CowDiskTrigger(0);
    
    private Trigger stomach1Trigger = new CowDiskTrigger(1);
    
    private Trigger stomach2Trigger = new CowDiskTrigger(2);
    
    private Trigger stomach3Trigger = new CowDiskTrigger(3);
    
    /**
     * If the cow is set to a target position that is a feeding position
     */
    private boolean loadMode = false;
    
    public CowTriggers() {
        
        stomach0Trigger.whenActive(new NextLoadingPosition());
        stomach1Trigger.whenActive(new NextLoadingPosition());
        stomach2Trigger.whenActive(new NextLoadingPosition());
        stomach3Trigger.whenActive(new NextLoadingPosition());
        
    }

    /**
     * Determine if the cow is targeting a feeding position
     * @return 
     */
    public boolean isLoadMode() {
        return loadMode;
    }

    /**
     * Set if the cow is targeting a feeding position
     * @param loadMode 
     */
    public void setLoadMode(boolean loadMode) {
        this.loadMode = loadMode;
    }
    
    
    
    
}
