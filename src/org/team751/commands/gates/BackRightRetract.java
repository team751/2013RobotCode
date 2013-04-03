package org.team751.commands.gates;

import org.team751.commands.CommandBase;

/**
 * Retracts the back right gate. Finishes immediately.
 * @author Sam Crow
 */
public class BackRightRetract extends CommandBase {
    
    public BackRightRetract() {
        requires(gatesBack);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        gatesBack.retractRight();
        gatesBack.setExtended(false);
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
