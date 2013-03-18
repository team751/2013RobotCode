package org.team751.commands.cow3;

import org.team751.commands.CommandBase;

/**
 * Moves the cow to zero, then sets that position as zero and exits
 *
 * @author Sam Crow
 */
public class ZeroCow1 extends CommandBase {

    public ZeroCow1() {
        requires(cow3);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        cow3.moveSlowForward();
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        cow3.manualStop();
    }
}
