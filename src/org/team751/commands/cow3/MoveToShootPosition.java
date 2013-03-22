package org.team751.commands.cow3;

import org.team751.commands.CommandBase;
import org.team751.util.cow.CowPosition;

/**
 * Moves the cow to the nearest shooting position. Finishes when the cow is in
 * position.
 * @author Sam Crow
 */
public class MoveToShootPosition extends CommandBase {
    
    public MoveToShootPosition() {
        requires(cow);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        //Choose which position to move to
        if(cow.getStomachs().stomach3Full()) {
            cow.setTargetPosition(CowPosition.kShoot3);
            return;
        }
        if(cow.getStomachs().stomach2Full()) {
            cow.setTargetPosition(CowPosition.kShoot2);
            return;
        }
        if(cow.getStomachs().stomach1Full()) {
            cow.setTargetPosition(CowPosition.kShoot1);
            return;
        }
        if(cow.getStomachs().stomach0Full()) {
            cow.setTargetPosition(CowPosition.kShoot0);
            return;
        }
        //If no stomach full, just move forward
        CowPosition nextForward = cow.getTargetPosition().nextForward();
        if(nextForward != null) {
            cow.setTargetPosition(nextForward);
            return;
        }
        //Otherwise do nothing and cancel this command
        cancel();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        cow.debugPosition();
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
