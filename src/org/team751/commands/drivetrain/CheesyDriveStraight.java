package org.team751.commands.drivetrain;

import org.team751.cheesy.util.AccelFilterBase;
import org.team751.commands.CommandBase;

/**
 * Drives the robot, using motion profiling and the drivetrain encoders, forward or back a
 * specified distance.
 *
 * @author Sam Crow
 */
public class CheesyDriveStraight extends CommandBase {

    
    /**
     * The distance, in meters, that the robot should move in the course of this
     * command
     */
    private double distance = 0;
    
    private AccelFilterBase accel;

    /**
     * Constructor
     *
     * @param meters The distance in meters (forward is positive) that the robot
     * should move
     */
    public CheesyDriveStraight(double meters) {
        requires(driveTrain);

        this.distance = meters;

        
        //For testing: set to timeout after 10 seconds
        setTimeout(10);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

        //Ensure that the Navigator currently returns an encoder position of 0
        navigator.resetEncoderDistance();

        driveTrain.setBrakeMode();

        
    }

    // Called repeatedly when this Command is scheduled to run
    protected synchronized void execute() {
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //Finished if the move has been completed or if the command has timed out
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
        

        //Ensure that the drivetrain is commanded to stop
        driveTrain.arcadeDrive(0, 0);
        
        driveTrain.setDefaultNeutralMode();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
