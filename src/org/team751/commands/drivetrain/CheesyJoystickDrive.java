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
        double y = oi.driveStick.getY();
        
		//Use simple, non-cheesy drive code if button 3 is pressed
        boolean simpleDrive = oi.driveStick.getRawButton(3);
        
		//If the robot should only be allowed to drive straight
        boolean straight = oi.driveStick.getRawButton(2);
		if(straight) {
			//disable turning
			x = 0;
		}
        
//        //Set the drivetrain to brake or coast mode, as requested
//        if(brake) {
//            driveTrain.setBrakeMode();
//        }
//        else {
//            driveTrain.setCoastMode();
//        }

        //For the joystick Y axis, forward is negative.
        //This changes it back so that forward is positive.
        y = -y;
        
		if(simpleDrive) {
			driveTrain.arcadeDrive(y, x);
		}
		else {
			//quickTurn seems to rotate back once the joystick is released,
			//so don't use it
			driveTrain.cheesyDrive(y, x, false);
		}
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
