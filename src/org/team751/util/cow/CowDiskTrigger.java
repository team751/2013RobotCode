package org.team751.util.cow;

import edu.wpi.first.wpilibj.buttons.Trigger;
import org.team751.commands.CommandBase;

/**
 * A trigger that activates when a disk enters a stomach, and deactivates
 * when it leaves.
 * This trigger also debounces input to reduce spurious triggering caused
 * by disks bouncing around.
 * @author Sam Crow
 */
public class CowDiskTrigger extends Trigger {
    
    /**
     * 
     */
    private long lastDebounceTime;
    /**
     * Time, in milliseconds, to prevent bounces over
     */
    private static final long kDebounceDelay = 1000;
    /**
     * The reading that was observed during the previous call to get()
     */
    private boolean lastReading = false;
    
    private boolean status;
    
    /**
     * The stomach number for this trigger
     */
    private int stomachNumber;

    public CowDiskTrigger(int stomachNumber) {
        this.stomachNumber = stomachNumber;
    }

    /**
     * Determine if a disk is in the stomach assigned to this trigger
     * @return 
     */
    public boolean isDiskPresent() {
        switch(stomachNumber) {
            case 0:
                return CommandBase.cow.getStomachs().stomach0Full();
            case 1:
                return CommandBase.cow.getStomachs().stomach1Full();
            case 2:
                return CommandBase.cow.getStomachs().stomach2Full();
            case 3:
                return CommandBase.cow.getStomachs().stomach3Full();
            default:
                System.err.println("CowDiskTrigger constructed with a stomach number not in range 0-3!");
                return false;
        }
    }
    
    public boolean get() {
        
        final boolean reading = isDiskPresent();
        
        if(reading != lastReading) {
            lastDebounceTime = System.currentTimeMillis();
        }
        
        if(System.currentTimeMillis() - lastDebounceTime > kDebounceDelay) {
            status = reading;
        }
        
        lastReading = reading;
        
        return status;
    }
}
