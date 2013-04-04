package org.team751.commands.shooter;

import org.team751.commands.CommandBase;

/**
 * Increases the shooter speed by 1/10 of its range. Returns immediately.
 * @author Sam Crow
 */
public class ShooterSpeedIncrease extends CommandBase {
    
    public ShooterSpeedIncrease() {
        requires(shooterWheels);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        shooterWheels.setSpeed(shooterWheels.getTargetSpeedRatio() + 0.05);
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
