package org.team751.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team751.commands.cow.CowForward;
import org.team751.commands.cow.ZeroCow;
import org.team751.commands.drivetrain.DriveRotate;
import org.team751.commands.drivetrain.DriveStraight;
import org.team751.commands.pusher.PusherExtendRetract;
import org.team751.commands.shooter.ShooterOff;
import org.team751.commands.shooter.ShooterOn;

/**
 * Drives the robot from the rear right corner of the pyramid (from the robot's
 * perspective) towards the 3-point goal and shoots 3 disks
 * @author Sam Crow
 */
public class DriveFromRightShoot3Autonomous extends CommandGroup {
	
	public DriveFromRightShoot3Autonomous() {
		//Drive forward 2 meters
		addSequential(new DriveStraight(2));
		//Turn left 30 degrees
		addSequential(new DriveRotate(-30));
		
		//Fire
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
