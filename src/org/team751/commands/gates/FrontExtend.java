package org.team751.commands.gates;

import org.team751.commands.CommandBase;

/**
 * Extends both front gates.
 * @author Sam Crow
 */
public class FrontExtend extends CommandBase {
    
    public FrontExtend() {
        requires(gatesFront);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        gatesFront.extendLeft();
        gatesFront.extendRight();
        gatesFront.setExtended(true);
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
