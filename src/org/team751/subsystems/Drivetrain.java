package org.team751.subsystems;

import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import org.team751.cheesy.CheesyDrive;
import org.team751.commands.drivetrain.CheesyJoystickDrive;
import org.team751.resources.AnalogChannels;
import org.team751.resources.CANJaguarIDs;
import org.team751.tasks.DrivetrainMonitor;
import org.team751.util.DrivetrainTemperatureSensor;
import org.team751.util.NamedCANJaguar;
import org.team751.util.PolyMotorRobotDrive;
import org.team751.util.StatusReportingSubsystem;
import org.team751.util.SubsystemStatusException;
import org.team751.util.TemperatureSensor;

/**
 *
 * @author sambaumgarten
 */
public class Drivetrain extends StatusReportingSubsystem {
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
    private SpeedController leftA, leftB, leftC;
    //Right side Jaguars
    private SpeedController rightA, rightB, rightC;
    //Temperature sensors
    private DrivetrainTemperatureSensor leftSensor, rightSensor;
    private PolyMotorRobotDrive drive;
    private long lastRunTime = System.currentTimeMillis();
    private DrivetrainMonitor monitor;
    
    /**
     * Keeps track of Cheesy Drive data
     */
    private CheesyDrive cheeseDrive = new CheesyDrive();

    public Drivetrain() {

        //Note the name of the subsystem
        super("Drivetrain");

        try {
            setupJaguars();
        } catch (CANTimeoutException e) {
            reportInitFailed(e);
            return;
        }
        drive = new PolyMotorRobotDrive(
                new SpeedController[]{leftA, leftB},
                new SpeedController[]{
                    rightA, rightB});

        leftSensor = new DrivetrainTemperatureSensor(AnalogChannels.TEMP_DRIVETRAIN_LEFT, "Motors left");
        rightSensor = new DrivetrainTemperatureSensor(AnalogChannels.TEMP_DRIVETRAIN_RIGHT, "Motors right");

        monitor = new DrivetrainMonitor(
                new SpeedController[]{leftA, leftB, rightA, rightB},
                new TemperatureSensor[]{leftSensor, rightSensor});

        monitor.start();
    }

    /**
     * Drive the robot, arcade drive style
     * @param moveValue Forward/back motion. +1 is full forwards, -1 is full reverse
     * @param rotateValue Rotation. -1 is left, +1 is right
     */
    public void arcadeDrive(double moveValue, double rotateValue) {

        if (isSubsystemWorking()) {

            try {
                drive.arcadeDrive(moveValue, rotateValue);
            } catch (CANTimeoutException ex) {
                reportNotWorking(ex);
            }

        }
    }
    
    /**
     * Drive the robot with the Cheesy Drive algorithm
     * @param throttle Forward/back motion. +1 is full forwards, -1 is full reverse
     * @param wheel Rotation. -1 is left, +1 is right
     * @param quickTurn If quick turn mode should be used
     */
    public void cheesyDrive(double throttle, double wheel, boolean quickTurn) {
        
        CheesyDrive.MotorOutputs outputs = cheeseDrive.cheesyDrive(throttle, wheel, quickTurn);
        try {
            //Invert the right motor output so that it will work
            drive.setLeftRightMotorOutputs(outputs.left, -outputs.right);
        } catch (CANTimeoutException ex) {
            reportNotWorking(ex);
        }
    }

    /**
     * Set the left and right motor outputs
     * @param leftOutput
     * @param rightOutput
     */
    public void setLeftRightMotorOutputs(double leftOutput, double rightOutput) {
        try {
            drive.setLeftRightMotorOutputs(leftOutput, rightOutput);
        } catch (CANTimeoutException ex) {
            reportNotWorking(ex);
        }
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new CheesyJoystickDrive());
    }

    /**
     * Configure the Jaguars to use brake mode. This can be reverted by calling {@link #setDefaultNeutralMode()
     * }.
     */
    public void setBrakeMode() {
        if (isSubsystemWorking()) {
            try {
                drive.setBrakeMode();
            } catch (CANTimeoutException ex) {
                reportNotWorking(ex);
            }
        }
    }

    /**
     * Configure the Jaguars to use the neutral mode specified by the jumper
     */
    public void setDefaultNeutralMode() {
        if (isSubsystemWorking()) {
            try {
                drive.setDefaultNeutralMode();
            } catch (CANTimeoutException ex) {
                reportNotWorking(ex);
            }
        }
    }

    /**
     * Get the drivetrain monitor
     *
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

    public void retry() throws SubsystemStatusException {

        try {
            setupJaguars();
            reportWorking();
        } catch (CANTimeoutException e) {
            throw new SubsystemStatusException(e);
        }

    }

    /**
     * Constructs each Jaguar, if it is null, and configures it for use.
     *
     * @throws CANTimeoutException if an exception was encountered
     */
    private void setupJaguars() throws CANTimeoutException {
        
        
//          leftA = new NamedCANJaguar(CANJaguarIDs.DRIVE_LEFT_A,
//                    "Left A");
//          leftB = new NamedCANJaguar(CANJaguarIDs.DRIVE_LEFT_B,
//                    "Left B");
//          leftC = new NamedCANJaguar(CANJaguarIDs.DRIVE_LEFT_C,
//                    "Left C");
//
//          rightA = new NamedCANJaguar(CANJaguarIDs.DRIVE_RIGHT_A,
//                    "Right A");
//          rightB = new NamedCANJaguar(CANJaguarIDs.DRIVE_RIGHT_B,
//                    "Right B");
//          rightC = new NamedCANJaguar(CANJaguarIDs.DRIVE_RIGHT_C,
//                    "Right C");

        if(leftA == null) leftA = new NamedCANJaguar(CANJaguarIDs.SR_DRIVE_LEFT_A, "Left A");
        if(leftB == null) leftB = new NamedCANJaguar(CANJaguarIDs.SR_DRIVE_LEFT_B, "Left B");
        if(rightA == null) rightA = new NamedCANJaguar(CANJaguarIDs.SR_DRIVE_RIGHT_A, "Right A");
        if(rightB == null) rightB = new NamedCANJaguar(CANJaguarIDs.SR_DRIVE_RIGHT_B, "Right B");

        //Timing note: The software sometimes does other things for a pretty
        //long time (100-500 milliseconds) between running the drive command.
        //When this happens, the motor safety mechanism stops the motors.
        //Stopping the motors in normal matches is not good, although we
        //do not want to fully disable this feature.
        //This sets the motor safety mechanism to not stop the motors
        //until after 1 second of non-response.

        try {
            ((MotorSafety) leftA).setExpiration(1);
            ((MotorSafety) leftB).setExpiration(1);
//            ((MotorSafety) leftC).setExpiration(1);
            ((MotorSafety) rightA).setExpiration(1);
            ((MotorSafety) rightB).setExpiration(1);
//            ((MotorSafety) rightC).setExpiration(1);
        } catch (ClassCastException e) {
            System.out.println("Casting a SpeedController into a MotorSafety failed!");
        }
    }
}
