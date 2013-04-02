package org.team751.commands.drivetrain;

import org.team751.commands.CommandBase;

/**
 *
 * @author samcrow
 */
public class CheesyJoystickDrive extends CommandBase {
    
    public CheesyJoystickDrive() {
        requires(driveTrain);
    }
    
    protected void initialize() {
        
    }

    protected void execute() {
        double x = oi.driveStick.getX();
        double y = oi.operatorStick.getY();
        
        boolean quickTurn = oi.driveStick.getRawButton(3);
        
        boolean brake = oi.driveStick.getRawButton(2);
        
        //Set the drivetrain to brake or coast mode, as requested
        if(brake) {
            driveTrain.setBrakeMode();
        }
        else {
            driveTrain.setCoastMode();
        }

        //For the joystick Y axis, forward is negative.
        //This changes it back so that forward is positive.
        y = -y;
        
        driveTrain.cheesyDrive(y, x, quickTurn);//TODO: Add a button for quickTurn
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        //Stop the drivetrain
        driveTrain.arcadeDrive(0, 0);
    }

    protected void interrupted() {
        end();
    }
    
}
