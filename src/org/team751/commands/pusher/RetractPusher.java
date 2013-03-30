package org.team751.commands.pusher;

import edu.wpi.first.wpilibj.DriverStationLCD;
import org.team751.commands.CommandBase;

/**
 * Retracts the pusher. Returns when the pusher has retracted.
 *
 * @author samcrow
 */
public class RetractPusher extends CommandBase {

    public RetractPusher() {
        requires(pusher);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser1, 1, "RetractPusher starting");
        DriverStationLCD.getInstance().updateLCD();
        pusher.retract();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        pusher.retract();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return pusher.isRetracted();
    }

    // Called once after isFinished returns true
    protected void end() {
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser1, 1, "RetractPusher finished");
        DriverStationLCD.getInstance().updateLCD();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
