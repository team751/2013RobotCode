package org.team751.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team751.commands.JoystickDrive;
import org.team751.resources.CANJaguarIDs;
import org.team751.util.DrivetrainMonitor;
import org.team751.util.NamedCANJaguar;
import org.team751.util.PolyMotorRobotDrive;

/**
 *
 * @author sambaumgarten
 */
public class Drivetrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    /*
     * Important note about all these CANJaguars:
     * The drivetrain monitor accesses them through a timer,
     * in a seperate thread.
     * Therefore, anything that you to that accesses one of them
     * must be inside a synchronized block, once you start the drivetrain
     * monitor.
     */
    //Left side Jaguars
    private SpeedController left1;
    private SpeedController left2;
    //Right side Jaguars
    private SpeedController right1;
    private SpeedController right2;
    private PolyMotorRobotDrive drive;
    private long lastRunTime = System.currentTimeMillis();
    
    private DrivetrainMonitor monitor;

    public Drivetrain() {
        try {
            left1 = new NamedCANJaguar(CANJaguarIDs.SR_DRIVE_LEFT_1, "Drivetrain left 1");
            left2 = new NamedCANJaguar(CANJaguarIDs.SR_DRIVE_LEFT_2, "Drivetrain left 2");

            right1 = new NamedCANJaguar(CANJaguarIDs.SR_DRIVE_RIGHT_1, "Drivetrain right 1");
            right2 = new NamedCANJaguar(CANJaguarIDs.SR_DRIVE_RIGHT_2, "Drivetrain right 2");

        } catch (CANTimeoutException e) {
            System.out.println("CANTimeoutException: " + e);
        }
        drive = new PolyMotorRobotDrive(
                new SpeedController[]{left1, left2}, new SpeedController[]{right1, right2});


        //Timing note: The software sometimes does other things for a pretty
        //long time (100-500 milliseconds) between running the drive command.
        //When this happens, the motor safety mechanism stops the motors.
        //This is not good.
        //This sets the motor safety mechanism to not stop the motors
        //until after 1 second of non-response.

        try {
            ((CANJaguar) left1).setExpiration(1);
            ((CANJaguar) left2).setExpiration(1);
            ((CANJaguar) right1).setExpiration(1);
            ((CANJaguar) right2).setExpiration(1);
        } catch (ClassCastException e) {
        }
        
        monitor = new DrivetrainMonitor(new SpeedController[]{left1, left2, right1, right2}, null);
        monitor.start();
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
    
    /**
     * Get the drivetrain monitor
     * @return the drivetrain monitor
     */
    public DrivetrainMonitor getMonitor() {
        return monitor;
    }
}
