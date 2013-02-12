package org.team751.commands.cow;

import org.team751.commands.CommandBase;

/**
 * Ensures that the cow control is enabled, then waits until the cow is in position,
 * then disables the cow.
 * @author Sam Crow
 */
public class WaitForCowInPosition extends CommandBase {
	
	public WaitForCowInPosition() {
		requires(cow);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		cow.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return cow.isInPosition();
	}

	// Called once after isFinished returns true
	protected void end() {
		cow.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
