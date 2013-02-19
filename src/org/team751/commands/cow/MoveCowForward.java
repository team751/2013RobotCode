package org.team751.commands.cow;

import org.team751.commands.CommandBase;
import org.team751.subsystems.Cow.Position;

/**
 * Moves the cow forward one position. Finishes when the cow has reached
 * that position. If no forward position is available, this command
 * finishes immediately.
 * @author Sam Crow
 */
public class MoveCowForward extends CommandBase {
	
	/**
	 * If this command should finish immediately, because no position
	 * is available
	 */
	private boolean canceled = false;
	
	public MoveCowForward() {
		requires(cow);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Position newPosition = cow.getTargetPosition().nextForward();
		if(newPosition != null) {
			cow.setTargetPosition(newPosition);
		}
		else {
			canceled = true;
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return canceled || cow.isInPosition();
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
