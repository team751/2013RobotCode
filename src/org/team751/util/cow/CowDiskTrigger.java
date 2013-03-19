package org.team751.util.cow;

import edu.wpi.first.wpilibj.buttons.Trigger;
import org.team751.commands.CommandBase;

/**
 * A trigger that activates when a disk enters a stomach, and deactivates
 * when it leaves
 * @author Sam Crow
 */
public class CowDiskTrigger extends Trigger {
    
    /**
     * The stomach number for this trigger
     */
    private int stomachNumber;

    public CowDiskTrigger(int stomachNumber) {
        this.stomachNumber = stomachNumber;
    }

    public boolean get() {
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
    
}
