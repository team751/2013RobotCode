package org.team751.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import org.team751.PIDConstants;

/**
 * Drives the robot, using PID and the drivetrain encoders, forward or back
 * a specified distance. This command also uses PID with the gyroscope sensor
 * and/or encoders to ensure that the robot drives straight.
 * @author Sam Crow
 */
public class DriveStraight extends CommandBase {
    
    //Movement values. These are set by the PID controllers and accessed
    //in execute().
    private volatile double moveValue = 0;
    private volatile double rotateValue = 0;
    
    private PIDController moveController;
    private PIDController rotateController;
    
    /**
     * Constructor
     * @param meters The distance in meters (forward is positive) that the robot
     * should move
     */
    public DriveStraight(double meters) {
        requires(driveTrain);
        
        moveController = new PIDController(PIDConstants.DRIVE_MOVE_P, PIDConstants.DRIVE_MOVE_I, PIDConstants.DRIVE_MOVE_D, navigator.movementPidSource, moveOutput);
        rotateController = new PIDController(PIDConstants.DRIVE_ROTATE_P, PIDConstants.DRIVE_ROTATE_I, PIDConstants.DRIVE_ROTATE_D, navigator.headingPidSource, rotateOutput);
    
        //For testing: set to timeout after 10 seconds
        setTimeout(10);
        
        //Configure on-target tolerance for move PID
        moveController.setPercentTolerance(10);
        
        //Set the controller setpoints
        moveController.setSetpoint(meters);
            //(no rotation)
        rotateController.setSetpoint(0);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        
        //Ensure that the Navigator currently returns an encoder position of 0
        navigator.resetEncoderDistance();
        
        //Enable the controllers
        moveController.enable();
        rotateController.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected synchronized void execute() {
        driveTrain.arcadeDrive(moveValue, rotateValue);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //Finished if the move has been completed or if the command has timed out
        return moveController.onTarget() || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
        //Disable and free the controllers
        moveController.disable();
        rotateController.disable();
        moveController.free();
        rotateController.free();
        
        //Ensure that the drivetrain is commanded to stop
        driveTrain.arcadeDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
    
    //These PIDSources are used to receive information on what driving
    //should be done.
    private final PIDOutput moveOutput = new PIDOutput() {
        public void pidWrite(double output) {
            synchronized(DriveStraight.this) {
                moveValue = output;
            }
        }
    };
    
    private final PIDOutput rotateOutput = new PIDOutput() {
        public void pidWrite(double output) {
            synchronized(DriveStraight.this) {
                rotateValue = output;
            }
        }
    };
}
