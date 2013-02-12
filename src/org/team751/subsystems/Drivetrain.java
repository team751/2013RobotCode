package org.team751.subsystems;

import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team751.commands.drivetrain.JoystickDrive;
import org.team751.resources.AnalogChannels;
import org.team751.resources.CANJaguarIDs;
import org.team751.tasks.DrivetrainMonitor;
import org.team751.util.DrivetrainTemperatureSensor;
import org.team751.util.NamedCANJaguar;
import org.team751.util.PolyMotorRobotDrive;
import org.team751.util.TemperatureSensor;

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
	private SpeedController leftA, leftB, leftC;

	//Right side Jaguars
	private SpeedController rightA, rightB, rightC;
	
	//Temperature sensors
	private DrivetrainTemperatureSensor leftSensor, rightSensor;

	private PolyMotorRobotDrive drive;

	private long lastRunTime = System.currentTimeMillis();

	private DrivetrainMonitor monitor;

	public Drivetrain() {
		try {
			leftA = new NamedCANJaguar(CANJaguarIDs.DRIVE_LEFT_A,
									   "Drivetrain left A");
			leftB = new NamedCANJaguar(CANJaguarIDs.DRIVE_LEFT_B,
									   "Drivetrain left B");
			leftC = new NamedCANJaguar(CANJaguarIDs.DRIVE_LEFT_C,
									   "Drivetrain left C");

			rightA = new NamedCANJaguar(CANJaguarIDs.DRIVE_RIGHT_A,
										"Drivetrain right A");
			rightB = new NamedCANJaguar(CANJaguarIDs.DRIVE_RIGHT_B,
										"Drivetrain right B");
			rightC = new NamedCANJaguar(CANJaguarIDs.DRIVE_RIGHT_C,
										"Drivetrain right C");

		} catch (CANTimeoutException e) {
			System.out.println("CANTimeoutException: " + e);
		}
		drive = new PolyMotorRobotDrive(
				new SpeedController[]{leftA, leftB, leftC},
				new SpeedController[]{
					rightA, rightB, rightC});



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
			((MotorSafety) leftC).setExpiration(1);
			((MotorSafety) rightA).setExpiration(1);
			((MotorSafety) rightB).setExpiration(1);
			((MotorSafety) rightC).setExpiration(1);
		} catch (ClassCastException e) {
		}
		
		leftSensor = new DrivetrainTemperatureSensor(AnalogChannels.TEMP_DRIVETRAIN_LEFT, "Drivetrain left");
		rightSensor = new DrivetrainTemperatureSensor(AnalogChannels.TEMP_DRIVETRAIN_RIGHT, "Drivetrain right");

		monitor = new DrivetrainMonitor(
				new SpeedController[]{leftA, leftB, leftC, rightA, rightB, rightC},
				new TemperatureSensor[] { leftSensor, rightSensor });
		
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
