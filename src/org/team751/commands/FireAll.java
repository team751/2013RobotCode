
package org.team751.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team751.commands.Fire;
import org.team751.commands.shooter.ShooterOff;
import org.team751.commands.shooter.ShooterOn;

/**
 * Fires all the disks that the cow currently contains
 * @author Sam Crow
 */
public class FireAll extends CommandGroup {
    public FireAll() {
        addSequential(new ShooterOn());
        addSequential(new WaitCommand(3));
        addSequential(new Fire());
        addSequential(new Fire());
        addSequential(new Fire());
        addSequential(new Fire());
        addSequential(new ShooterOff());
    }
}
