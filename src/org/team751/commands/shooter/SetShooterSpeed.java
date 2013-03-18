package org.team751.commands.shooter;

import org.team751.commands.CommandBase;

/**
 * Sets the shooter to a specified speed and returns immediately.
 * @author Sam Crow
 */
public class SetShooterSpeed extends CommandBase {
	
	/**
	 * The power level to set the shooter to
	 */
	private double speed;
	
	/**
	 * Constructor
	 * @param speed The speed ratio (0-1) to set the shooter to
	 */
	public SetShooterSpeed(double speed) {
		requires(shooterWheels);
		this.speed = speed;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		shooterWheels.setSpeed(speed);
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
