package org.team751.commands.cow3;

import org.team751.commands.CommandBase;
import org.team751.util.cow.CowPosition;

/**
 * Sets the cow to move one position forward, then exits immediately
 * @author Sam Crow
 */
public class CowForward extends CommandBase {
	
	public CowForward() {
		requires(cow3);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		CowPosition newPosition = cow3.getTargetPosition().nextForward();
		if(newPosition != null) {
			cow3.setTargetPosition(newPosition);
			System.out.println("Setting cow to position "+newPosition.toString());
		}
		else {
			System.out.println("No forward position to move cow to");
		}
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
