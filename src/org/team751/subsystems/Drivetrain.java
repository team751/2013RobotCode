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
    private SpeedController left1;
    private SpeedController left2;
    private SpeedController left3;
    //Right side Jaguars
    private SpeedController right1;
    private SpeedController right2;
    private SpeedController right3;
    
    private PolyMotorRobotDrive drive;
    
    
    private long lastRunTime = System.currentTimeMillis();

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
    
        //Timing note: The software sometimes does other things for a pretty
        //long time (100-500 milliseconds) between running the drive command.
        //When this happens, the motor safety mechanism stops the motors.
        //This is not good.
        //This sets the motor safety mechanism to not stop the motors
        //until after 1 second of non-response.

        try {
            ((CANJaguar) left1).setExpiration(1);
            ((CANJaguar) left2).setExpiration(1);
            ((CANJaguar) left3).setExpiration(1);
            ((CANJaguar) right1).setExpiration(1);
            ((CANJaguar) right2).setExpiration(1);
            ((CANJaguar) right3).setExpiration(1);
        } catch (ClassCastException e) {
        }
    
    }
    
    public void arcadeDrive(double moveValue, double rotateValue) {
        //timing
        long now = System.currentTimeMillis();
        long loopTime = now - lastRunTime;

        lastRunTime = now;

        if (loopTime > 100) {
            System.out.println("Long loop! " + loopTime + " ms");
        }
        
        drive.arcadeDrive(moveValue, rotateValue);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new JoystickDrive());
    }
}

