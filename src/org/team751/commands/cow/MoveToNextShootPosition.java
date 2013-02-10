package org.team751.commands.cow;

import org.team751.commands.CommandBase;
import org.team751.subsystems.Cow2;
import org.team751.subsystems.Cow2.Position;
import org.team751.util.cow.CowShootPositionFinder;
import org.team751.util.cow.NoCowPositionException;

/**
 * Moves the cow to the next (closest) open position for shooting.
 * This command finishes when the cow is in position.
 * If no disk slot has a disk in it, this command finishes immediately.
 * @author Sam Crow
 */
public class MoveToNextShootPosition extends CommandBase {

    public MoveToNextShootPosition() {
        requires(cow);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        //Choose the best position to move to
		Position targetPosition = null;
		try {
			targetPosition = new CowShootPositionFinder().getClosestPosition(cow.getOccupationStatus(), cow.getTargetPosition());
		} catch (NoCowPositionException ex) {
			ex.printStackTrace();
		}

		cow.setTargetPosition(targetPosition);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return cow.isInPosition();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
