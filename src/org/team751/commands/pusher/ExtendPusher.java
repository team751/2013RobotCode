package org.team751.commands.pusher;

import org.team751.commands.CommandBase;
import org.team751.util.cow.CowPosition;

/**
 * Extends the pusher. Returns when the pusher has fully extended.
 *
 * @author Sam Crow
 */
public class ExtendPusher extends CommandBase {
       
    /**
     * If the command should cancel itself
     */
    private boolean isCanceled = false;

    public ExtendPusher() {
        requires(pusher);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

        //Verify that the cow is in a shoot position with a disk in the set stomach
        isCanceled = true;
        CowPosition currentPosition = cow.getTargetPosition();
        if (currentPosition == CowPosition.kShoot3 && cow.getStomachs().stomach3Full()) {
            isCanceled = false;
        }
        if (currentPosition == CowPosition.kShoot2 && cow.getStomachs().stomach2Full()) {
            isCanceled = false;
        }
        if (currentPosition == CowPosition.kShoot1 && cow.getStomachs().stomach1Full()) {
            isCanceled = false;
        }
        if (currentPosition == CowPosition.kShoot0 && cow.getStomachs().stomach0Full()) {
            isCanceled = false;
        }
        if(isCanceled) {
            return;
        }

        pusher.push();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        pusher.push();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return pusher.isExtended() || isCanceled;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
