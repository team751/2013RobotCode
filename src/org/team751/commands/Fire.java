package org.team751.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team751.commands.cow3.MoveToShootPosition;
import org.team751.commands.pusher.PusherExtendRetract;

/**
 * Pushes a disk into the shooter, and then moves the shooter forward to the next shooting position
 * @author Sam Crow
 */
public class Fire extends CommandGroup {
    
    public Fire() {
        addSequential(new MoveToShootPosition());
        addSequential(new PusherExtendRetract());
        addSequential(new MoveToShootPosition());
    }
    
}
