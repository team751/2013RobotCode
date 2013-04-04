package org.team751.commands.cow;

import org.team751.commands.CommandBase;
import org.team751.util.cow.CowPosition;

/**
 * If the cow is in loading mode, this command moves the cow to the next forward
 * loading position (or shoot position) and finishes when the cow is in position.
 * If the cow is not in loading mode, this command finishes immediately.
 * @author samcrow
 */
public class NextLoadingPosition extends CommandBase {
    
    public NextLoadingPosition() {
        requires(cow);
		setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        if(!cow.getTriggers().isLoadMode()) {
            //Don't do anything
            cancel();
            return;
        }
        
        CowPosition forwardPosition = cow.getTargetPosition().nextForward();
        
        if(forwardPosition == null) {
            System.err.println("Next forward position is null. Bug!");
        }
        
        cow.setTargetPosition(forwardPosition);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//        cow.updateMotion();
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
