package org.team751.commands.cow;

import org.team751.commands.CommandBase;
import org.team751.subsystems.Cow;

/**
 * Moves the cow forward, slowly, to find the precise zero position
 * @author Sam Crow
 */
public class ZeroCowStep3 extends CommandBase {
	
	public ZeroCowStep3() {
		requires(cow);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		cow.manualMoveForwardSlow();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
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
