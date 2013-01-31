package org.team751.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team751.commands.JoystickDrive;
import org.team751.resources.CANJaguarIDs;
import org.team751.util.PolyMotorRobotDrive;

/**
 *
 * @author sambaumgarten
 */
public class Drivetrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    //Left side Jaguars
    SpeedController left1;
    SpeedController left2;
    //Right side Jaguars
    SpeedController right1;
    SpeedController right2;
    
    
    
    PolyMotorRobotDrive drive;

    public Drivetrain() {
        try {
            left1 = new CANJaguar(CANJaguarIDs.SR_DRIVE_LEFT_1);
            left2 = new CANJaguar(CANJaguarIDs.SR_DRIVE_LEFT_2);
            
            right1 = new CANJaguar(CANJaguarIDs.SR_DRIVE_RIGHT_1);
            right2 = new CANJaguar(CANJaguarIDs.SR_DRIVE_RIGHT_2);
            
        } catch(CANTimeoutException e) {
            System.out.println("CANTimeoutException: " + e);
        }
        drive = new PolyMotorRobotDrive(
                new SpeedController[]{left1, left2}, new SpeedController[]{right1, right2});
    }
    
    public void arcadeDrive(double moveValue, double rotateValue) {
        drive.arcadeDrive(moveValue, rotateValue);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new JoystickDrive());
    }
}
