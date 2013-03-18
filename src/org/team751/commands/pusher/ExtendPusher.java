package org.team751.commands.pusher;

import org.team751.commands.CommandBase;

/**
 * Extends the pusher. Returns when the pusher has fully extended.
 * @author Sam Crow
 */
public class ExtendPusher extends CommandBase {
	
	public ExtendPusher() {
		requires(pusher);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		pusher.push();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		pusher.push();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return pusher.isExtended();
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
