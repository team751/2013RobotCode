package org.team751.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.team751.commands.cow.CowForward;
import org.team751.commands.cow.MoveToFirstShootPosition;
import org.team751.commands.cow.ZeroCow;
import org.team751.commands.pusher.PusherExtendRetract;
import org.team751.commands.shooter.ShooterOff;
import org.team751.commands.shooter.ShooterOn;

/**
 * An autonomous command that zeroes the cow, and then shoots two disks
 * @author Sam Crow
 */
public class TwoDiskAutonomous extends CommandGroup {
    
    public TwoDiskAutonomous() {
        addSequential(new ShooterOn());
        addSequential(new ZeroCow());
        addSequential(new MoveToFirstShootPosition());
		addSequential(new WaitCommand(3));
        addSequential(new PusherExtendRetract());
        addSequential(new CowForward());
		addSequential(new WaitCommand(1));
        addSequential(new PusherExtendRetract());
        addSequential(new ShooterOff());
    }
}
