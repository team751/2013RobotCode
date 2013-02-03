package org.team751.commands;

import edu.wpi.first.wpilibj.PIDController;

/**
 * Turns the robot around its current location, with a closed loop based on
 * feedback from the gyroscope sensor.
 * @author Sam Crow
 */
public class DriveRotate extends CommandBase {

    private PIDController controller;
    
    /**
     * The timestamp, as returned by {@link System#currentTimeMillis()}, when
     * the command has started
     */
    private long startTime = 0;
    
    /**
     * Constructor
     * @param degreesToTurn The number of degreesToTurn to turn. Right is positive.
     */
    public DriveRotate(double degreesToTurn) {
        requires(driveTrain);
        
        controller = new PIDController(0.01, 0, 0, navigator.headingPidSource, driveTrain.turningPidOutput);
        controller.setPercentTolerance(20);
        
        controller.setSetpoint(navigator.getHeading() + degreesToTurn);
    }
    
    protected void initialize() {
        //Set the started time to now
        startTime = System.currentTimeMillis();
        controller.enable();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        
        //Finished in 2 cases: PID controller is on target, or timeout
        
        if(controller.onTarget()) return true;
        
        //timeout after 1000 ms (1 second)
        if(System.currentTimeMillis() - startTime > 1000) return true;
        
        return false;
    }

    protected void end() {
        //Get rid of the PID controller
        controller.disable();
        controller.free();
        
        //Ensure that the drivetrain is stopped
        driveTrain.arcadeDrive(0, 0);
    }

    protected void interrupted() {
        end();
    }
    
}
