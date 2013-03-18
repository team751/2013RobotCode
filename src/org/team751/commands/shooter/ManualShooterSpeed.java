package org.team751.commands.shooter;

import org.team751.commands.CommandBase;

/**
 * Manually sets the shooter speed with the joystick
 * @author samcrow
 */
public class ManualShooterSpeed extends CommandBase {
	
	public ManualShooterSpeed() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(shooterWheels);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		shooterWheels.setSpeedOpenLoop(oi.operatorStick.getY());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		shooterWheels.setSpeedOpenLoop(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
