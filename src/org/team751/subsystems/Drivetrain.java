/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team751.commands.JoystickDrive;
import org.team751.util.PolyMotorRobotDrive;

/**
 *
 * @author sambaumgarten
 */
public class Drivetrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    CANJaguar left1;
    CANJaguar left2;
    CANJaguar right1;
    CANJaguar right2;
    PolyMotorRobotDrive drive;

    public Drivetrain() {
        try {
            left1 = new CANJaguar(1);
            left2 = new CANJaguar(2);
            right1 = new CANJaguar(3);
            right2 = new CANJaguar(4);
        } catch(CANTimeoutException e) {
            System.out.println("CANTimeoutException: " + e);
        }
        drive = new PolyMotorRobotDrive(
                new CANJaguar[]{left1, left2}, new CANJaguar[]{right1, right2});
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
