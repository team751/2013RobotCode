package org.team751.commands.cow;

import org.team751.commands.CommandBase;

/**
 * Moves the cow backwards for a set time, in preparation for step 3
 * @author Sam Crow
 */
public class ZeroCowStep2 extends CommandBase {
	
	public ZeroCowStep2() {
		requires(cow);
		//run for a certain time
		setTimeout(0.5);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		cow.manualMoveBack();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		cow.manualStop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
