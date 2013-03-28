package org.team751.commands.autonomous;

import org.team751.commands.CommandBase;

/**
 * Does nothing and finishes immediately.
 * @author Sam Crow
 */
public class DoNothingAutonomous extends CommandBase {
	
	public DoNothingAutonomous() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
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
