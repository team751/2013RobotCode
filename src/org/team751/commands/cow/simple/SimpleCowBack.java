package org.team751.commands.cow.simple;

import org.team751.commands.CommandBase;

/**
 *
 * @author samcrow
 */
public class SimpleCowBack extends CommandBase {
	
	public SimpleCowBack() {
		requires(simpleCow);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		simpleCow.moveBack();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
