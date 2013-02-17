package org.team751.tasks;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import org.team751.resources.AnalogChannels;
import org.team751.resources.DigitalChannels;
import org.team751.util.Vec2;

/**
 * This subsystem-like class keeps track of the robot's location relative to its
 * starting point.
 *
 * The coordinate system is defined based on the initial location of the robot
 * when it is started, or when {@link #reset()} was last called. The origin of
 * this coordinate system is at the robot's initial location. It does not rotate
 * when the robot turns.
 *
 * @author Sam Crow
 */
public class Navigator extends PeriodicTask implements Sendable,
													   LiveWindowSendable {

	//Constants for measuring movement
	/**
	 * The number of encoder counts for every wheel revolution
	 */
	private static final int COUNTS_PER_REVOLUTION = 250;

	/**
	 * The diameter of the wheel, in meters
	 */
	private static final double WHEEL_DIAMETER = 0.1524;

	/**
	 * The distance, in meters, that the robot moves for each encoder count
	 */
	private static final double ROBOT_DISTANCE_PER_COUNT = (1 / (double) COUNTS_PER_REVOLUTION) * WHEEL_DIAMETER * Math.PI;

//	private ADXL345_I2C accel;

	private Gyro gyro;
	//encoders

	Encoder leftEncoder;

	Encoder rightEncoder;

	/**
	 * The heading of the robot, in degrees to the right of its initial heading
	 */
	private volatile double heading = 0;

	/**
	 * The velocity of the robot, in meters/second
	 */
	private volatile Vec2 velocity = new Vec2();

	/**
	 * The location of the robot, in meters
	 */
	private volatile Vec2 location = new Vec2();

	/**
	 * The distance in meters that the robot has traveled forward, according to
	 * the drivetrain encoders. This is the average of the left encoder distance
	 * and the right encoder distance. Note: Each encoder must have been
	 * configured with the correct distance per pulse in meters.
	 */
	private volatile double encoderDistance = 0;

	/**
	 * The timestamp, in milliseconds, at which processing started for the
	 * previous call to {@link #run()}. This timing is used to calculate
	 * velocity and position from acceleration.
	 */
	private long lastProcessingTime = System.currentTimeMillis();

	public Navigator() {
		System.out.println("Navigator constructor called");



		System.out.println("Starting gyro init");
		gyro = new Gyro(AnalogChannels.GYRO);
		System.out.println("Gyro init done");

		System.out.println("Starting accelerometer init");
//		accel = new ADXL345_I2C(1, ADXL345_I2C.DataFormat_Range.k4G);

		System.out.println("Starting encoder init");
		leftEncoder = new Encoder(DigitalChannels.DRIVE_LEFT_ENCODER_A,
								  DigitalChannels.DRIVE_LEFT_ENCODER_B);
		rightEncoder = new Encoder(DigitalChannels.DRIVE_RIGHT_ENCODER_A,
								   DigitalChannels.DRIVE_RIGHT_ENCODER_B);
		System.out.println("Encoder init done");

		//Set the periodic task to run this 10 times/second
		setTaskTime(0.1);

		//Configure encoders
		leftEncoder.setDistancePerPulse(ROBOT_DISTANCE_PER_COUNT);
		rightEncoder.setDistancePerPulse(ROBOT_DISTANCE_PER_COUNT);
		//Reverse the right side encoder so that forward will give a positive value for both encoders
		leftEncoder.setReverseDirection(true);

		//Start counting encoder pulses
		leftEncoder.start();
		rightEncoder.start();
	}

	/**
	 * Set the heading, velocity, and location to zero
	 */
	public synchronized void reset() {
		heading = 0;
		velocity = new Vec2();
		location = new Vec2();
	}

	public void run() {

		System.out.println("Navigator synchronizing self");
		synchronized (this) {

			long newTime = System.currentTimeMillis();
			//Get the time in seconds since processing was last done
			double timeSeconds = (newTime - lastProcessingTime) / 1000.0;

			lastProcessingTime = newTime;

			//Get the Y-axis (local to the robot, longitudinal) acceleration and convert
			//it from Gs to m/s^2
//			double accelY = accel.getAcceleration(ADXL345_I2C.Axes.kY) / 9.8;

			//update the heading
			heading = gyro.getAngle();
//
//			//Append the velocity with the change in velocity over the last time step
//			//90 degrees is added to the heading because Navigator uses forward for 0
//			//and Vec2 uses +X for 0.
//			velocity = velocity.add(Vec2.fromAngle(heading + 90,
//												   accelY * timeSeconds));
//
//			//Append the position
//			location = location.add(velocity.multiply(timeSeconds));
//
//			//Optimization: Correct for accelerometer drift by setting velocity
//			//to zero if the encoders say that it is zero
//			if (leftEncoder.getStopped() && rightEncoder.getStopped()) {
//				velocity = new Vec2(0, 0);
//			}
//
//			//Update the encoder distance
//			encoderDistance = (leftEncoder.getDistance() + rightEncoder.
//					getDistance()) / 2.0;

			//Debug
			SmartDashboard.putNumber("Encoder distance", encoderDistance);
			//Limit heading heading to [0, 360] degrees
			double dashboardHeading = heading % 360;
			if (dashboardHeading < 0) {
				dashboardHeading += 360;
			}

			SmartDashboard.putNumber("Heading", dashboardHeading);

			System.out.println("Navigator releasing self");
		}

	}

	//Methods to access location information
	/**
	 * Get the robot heading
	 *
	 * @return the heading, in degrees
	 */
	public synchronized double getHeading() {
		return heading;
	}

	/**
	 * Get the X location of the robot
	 *
	 * @return the location, in meters
	 */
	public synchronized double getX() {
		return location.getX();
	}

	/**
	 * Get the Y location of the robot
	 *
	 * @return the location, in meters
	 */
	public synchronized double getY() {
		return location.getY();
	}

	/**
	 * Get the distance, in meters, that the robot has moved forwards or
	 * backwards since the last call to {@link #resetEncoderDistance()}.
	 *
	 * @return The distance in meters
	 */
	public synchronized double getEncoderDistance() {
		return encoderDistance;
	}

	/**
	 * Reset the encoder distance, returned by {@link #getEncoderDistance()}, to
	 * zero.
	 */
	public synchronized void resetEncoderDistance() {
		leftEncoder.reset();
		rightEncoder.reset();
	}
	//SmartDashboard/Live Window support section
	/**
	 * The table used to send data
	 */
	private ITable table;

	public String getName() {
		return "navigator";
	}

	public void initTable(ITable itable) {
		table = itable;
		updateTable();
	}

	public ITable getTable() {
		return table;
	}

	public String getSmartDashboardType() {
		return "navigator";
	}

	public void updateTable() {
		if (table != null) {
			table.putNumber("speed", velocity.getMagnitude());
			table.putNumber("heading", heading);
			table.putNumber("X location", location.getX());
			table.putNumber("Y location", location.getY());
		}
	}

	public void startLiveWindowMode() {
	}

	public void stopLiveWindowMode() {
	}
	//PID sources
	/**
	 * A PID source that returns the heading, in degrees
	 */
	public final PIDSource headingPidSource = new PIDSource() {

		public double pidGet() {
			synchronized (Navigator.this) {
				return getHeading();
			}
		}
	};

	public final PIDSource movementPidSource = new PIDSource() {

		public double pidGet() {
			synchronized (Navigator.this) {
				return getEncoderDistance();
			}
		}
	};

}
