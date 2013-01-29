package org.team751.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team751.resources.CANJaguarIDs;
import org.team751.commands.JoystickDrive;
import org.team751.resources.DigitalChannels;
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
    SpeedController left3;
    //Right side Jaguars
    SpeedController right1;
    SpeedController right2;
    SpeedController right3;
    
    //encoders
    Encoder leftEncoder = new Encoder(DigitalChannels.DRIVE_LEFT_ENCODER_A, DigitalChannels.DRIVE_LEFT_ENCODER_B);
    
    Encoder rightEncoder = new Encoder(DigitalChannels.DRIVE_RIGHT_ENCODER_A, DigitalChannels.DRIVE_RIGHT_ENCODER_B);
    
    PolyMotorRobotDrive drive;

    public Drivetrain() {
        try {
            left1 = new CANJaguar(CANJaguarIDs.DRIVE_LEFT_1);
            left2 = new CANJaguar(CANJaguarIDs.DRIVE_LEFT_2);
            left3 = new CANJaguar(CANJaguarIDs.DRIVE_LEFT_3);
            
            right1 = new CANJaguar(CANJaguarIDs.DRIVE_RIGHT_1);
            right2 = new CANJaguar(CANJaguarIDs.DRIVE_RIGHT_2);
            right3 = new CANJaguar(CANJaguarIDs.DRIVE_RIGHT_3);
            
        } catch(CANTimeoutException e) {
            System.out.println("CANTimeoutException: " + e);
        }
        drive = new PolyMotorRobotDrive(
                new SpeedController[]{left1, left2, left3}, new SpeedController[]{right1, right2, right3});
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
