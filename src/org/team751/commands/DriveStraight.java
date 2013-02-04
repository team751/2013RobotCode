package org.team751.commands;

/**
 * Drives the robot, using PID and the drivetrain encoders, forward or back
 * a specified distance. This command also uses PID with the gyroscope sensor
 * and/or encoders to ensure that the robot drives straight.
 * @author Sam Crow
 */
public class DriveStraight extends CommandBase {
    
    public DriveStraight(double meters) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
