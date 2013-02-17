package org.team751.commands.cow;

import org.team751.commands.CommandBase;

/**
 * Moves the cow until the zero photoswitch detects that it is in the zero
 * position, and then sets that position as zero.
 * @author Sam Crow
 */
public class ZeroCow extends CommandBase {
	
	public ZeroCow() {
		requires(cow);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		cow.manualMoveForward();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return cow.isAtZero();
	}

	// Called once after isFinished returns true
	protected void end() {
		cow.manualStop();
		cow.setToZero();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		cow.manualStop();
	}
}
