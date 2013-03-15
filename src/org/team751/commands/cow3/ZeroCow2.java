package org.team751.commands.cow3;

import org.team751.commands.CommandBase;

/**
 * Moves the cow back for 1 second, in preparation for zero step 3
 * @author Sam Crow
 */
public class ZeroCow2 extends CommandBase {
    
    public ZeroCow2() {
        requires(cow3);
        setTimeout(1);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        cow3.moveSlowBack();
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
        cow3.manualStop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        cow3.manualStop();
    }
}
