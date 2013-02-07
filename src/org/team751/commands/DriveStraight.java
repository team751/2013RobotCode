package org.team751.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

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
    
    //PID constants
    private static final double kP = 0.05;
    private static final double kI = 0;
    private static final double kD = 0;
    
    /**
     * Constructor
     * @param meters The distance in meters (forward is positive) that the robot
     * should move
     */
    public DriveStraight(double meters) {
        requires(driveTrain);
        
        moveController = new PIDController(kP, kI, kD, navigator., moveOutput)
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected synchronized void execute() {
        driveTrain.arcadeDrive(moveValue, rotateValue);
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
