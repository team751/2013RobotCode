package org.team751.commands.gates;

import org.team751.commands.CommandBase;

/**
 * Retracts the front left gate. Finishes immediately.
 * @author Sam Crow
 */
public class FrontLeftRetract extends CommandBase {
    
    public FrontLeftRetract() {
        requires(gatesFront);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        gatesFront.retractLeft();
        gatesFront.setExtended(false);
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
