package org.team751.commands.shooter;

import org.team751.commands.CommandBase;

/**
 * Turns on the shooter and exits immediately
 * @author Sam Crow
 */
public class ShooterOn extends CommandBase {
	
	public ShooterOn() {
		// Use requires() here to declare subsystem dependencies
		requires(shooterWheels);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		shooterWheels.enable();
		System.out.print("Turning shooter on... ");
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
		System.out.println("done.");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
