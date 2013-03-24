package org.team751.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team751.commands.cow3.CowForward;
import org.team751.commands.cow3.ZeroCow;
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
        addSequential(new PusherExtendRetract());
        addSequential(new CowForward());
        addSequential(new PusherExtendRetract());
        addSequential(new ShooterOff());
    }
}
