package org.team751.commands.cow;

import org.team751.commands.CommandBase;
import org.team751.util.cow.CowPosition;

/**
 * Moves the robot to the first and furthest back loading position, load 3
 * @author Sam Crow
 */
public class MoveToFirstLoadPosition extends CommandBase {
    
    public MoveToFirstLoadPosition() {
        requires(cow);
		setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        cow.setTargetPosition(CowPosition.kLoad3);
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
