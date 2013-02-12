package org.team751.subsystems;

<<<<<<< HEAD
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team751.commands.JoystickDrive;
import org.team751.resources.CANJaguarIDs;
=======
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team751.commands.drivetrain.JoystickDrive;
import org.team751.resources.CANJaguarIDs;
import org.team751.tasks.DrivetrainMonitor;
import org.team751.util.NamedCANJaguar;
>>>>>>> summer-robot
import org.team751.util.PolyMotorRobotDrive;

/**
 *
 * @author sambaumgarten
 */
public class Drivetrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
<<<<<<< HEAD
    
    //Left side Jaguars
    private SpeedController left1;
    private SpeedController left2;
    private SpeedController left3;
    //Right side Jaguars
    private SpeedController right1;
    private SpeedController right2;
    private SpeedController right3;
    
=======

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
>>>>>>> summer-robot
    private PolyMotorRobotDrive drive;
    
    
    private long lastRunTime = System.currentTimeMillis();
<<<<<<< HEAD

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
    
=======
    
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


>>>>>>> summer-robot
        //Timing note: The software sometimes does other things for a pretty
        //long time (100-500 milliseconds) between running the drive command.
        //When this happens, the motor safety mechanism stops the motors.
        //This is not good.
        //This sets the motor safety mechanism to not stop the motors
        //until after 1 second of non-response.

        try {
<<<<<<< HEAD
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
=======
            ((MotorSafety) left1).setExpiration(1);
            ((MotorSafety) left2).setExpiration(1);
            ((MotorSafety) right1).setExpiration(1);
            ((MotorSafety) right2).setExpiration(1);
        } catch (ClassCastException e) {
        }
        
        monitor = new DrivetrainMonitor(new SpeedController[]{left1, left2, right1, right2}, null);
        monitor.start();
    }

    public void arcadeDrive(double moveValue, double rotateValue) {

>>>>>>> summer-robot
        //timing
        long now = System.currentTimeMillis();
        long loopTime = now - lastRunTime;

        lastRunTime = now;

        if (loopTime > 100) {
            System.out.println("Long loop! " + loopTime + " ms");
        }
<<<<<<< HEAD
        
        drive.arcadeDrive(moveValue, rotateValue);
    }
    
=======

        
        drive.arcadeDrive(moveValue, rotateValue);
    }

>>>>>>> summer-robot
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new JoystickDrive());
    }
<<<<<<< HEAD
}

=======
    
    /**
     * Get the drivetrain monitor
     * @return the drivetrain monitor
     */
    public DrivetrainMonitor getMonitor() {
        return monitor;
    }
    
    //PID support for turning in place
    /**
     * A PID output used to turn in place
     */
    public final PIDOutput turningPidOutput = new PIDOutput() {

        public void pidWrite(double d) {
            //Arcade drive with the given rotate value
            arcadeDrive(0, d);
        }
        
    };
}
>>>>>>> summer-robot
