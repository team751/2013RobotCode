package org.team751.commands.drivetrain;

import edu.wpi.first.wpilibj.PIDController;
import org.team751.commands.CommandBase;

/**
 * Turns the robot around its current location, with a closed loop based on
 * feedback from the gyroscope sensor.
 * @author Sam Crow
 */
public class DriveRotate extends CommandBase {

    private PIDController controller;
    
    /**
     * Constructor
     * @param degreesToTurn The number of degreesToTurn to turn. Right is positive.
     */
    public DriveRotate(double degreesToTurn) {
        requires(driveTrain);
        
        controller = new PIDController(0.01, 0, 0, navigator.headingPidSource, driveTrain.turningPidOutput);
        controller.setPercentTolerance(10);
        
        controller.setSetpoint(navigator.getHeading() + degreesToTurn);
        
        setTimeout(1);
    }
    
    protected void initialize() {
        
        controller.enable();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        
        //Finished in 2 cases: PID controller is on target, or timeout
        
        if(controller.onTarget()) return true;
        
        if(isTimedOut()) return true;
        
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
