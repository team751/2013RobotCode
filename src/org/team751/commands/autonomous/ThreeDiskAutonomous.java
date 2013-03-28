package org.team751.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team751.commands.cow.CowForward;
import org.team751.commands.cow.ZeroCow;
import org.team751.commands.pusher.PusherExtendRetract;
import org.team751.commands.shooter.ShooterOff;
import org.team751.commands.shooter.ShooterOn;

/**
 * An autonomous command that zeroes the cow, and then shoots three disks
 * @author Sam Crow
 */
public class ThreeDiskAutonomous extends CommandGroup {
    
    public ThreeDiskAutonomous() {
        addSequential(new ShooterOn());
        addSequential(new ZeroCow());
        addSequential(new PusherExtendRetract());
        addSequential(new CowForward());
        addSequential(new PusherExtendRetract());
        addSequential(new CowForward());
        addSequential(new PusherExtendRetract());
        addSequential(new ShooterOff());
    }
}
