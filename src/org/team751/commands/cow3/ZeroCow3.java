package org.team751.commands.cow3;

import org.team751.commands.CommandBase;
import org.team751.util.cow.CowPosition;

/**
 * Moves the cow to zero, then sets that position as zero and exits
 *
 * @author Sam Crow
 */
public class ZeroCow3 extends CommandBase {

    public ZeroCow3() {
        requires(cow3);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        cow3.moveExtraSlowForward();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return cow3.isAtZero();
    }

    // Called once after isFinished returns true
    protected void end() {
        cow3.manualStop();
        cow3.setThisAsZero();
        cow3.setTargetPosition(CowPosition.kShoot3);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        cow3.manualStop();
    }
}
