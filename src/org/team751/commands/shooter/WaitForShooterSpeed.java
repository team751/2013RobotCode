package org.team751.commands.shooter;

import org.team751.commands.CommandBase;

/**
 * Waits for the shooter wheels to spin up or down to their set speed.
 * Returns when this has been achieved.
 * @author Sam Crow
 */
public class WaitForShooterSpeed extends CommandBase {
	
	public WaitForShooterSpeed() {
		requires(shooterWheels);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return shooterWheels.isOnTarget();
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
