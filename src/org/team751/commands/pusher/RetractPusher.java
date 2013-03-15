package org.team751.commands.pusher;

import org.team751.commands.CommandBase;

/**
 * Retracts the pusher. Returns when the pusher has retracted.
 * @author samcrow
 */
public class RetractPusher extends CommandBase {
	
	public RetractPusher() {
		requires(pusher);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
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
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
