package org.team751.util;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.tables.ITable;
import org.team751.resources.AnalogChannels;
import org.team751.resources.DigitalChannels;

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
public class Navigator extends PeriodicTask implements Sendable, LiveWindowSendable {

    private ADXL345_I2C accel = new ADXL345_I2C(2, ADXL345_I2C.DataFormat_Range.k4G);
    private Gyro gyro = new Gyro(AnalogChannels.GYRO);
    //encoders
    Encoder leftEncoder = new Encoder(DigitalChannels.DRIVE_LEFT_ENCODER_A, DigitalChannels.DRIVE_LEFT_ENCODER_B);
    Encoder rightEncoder = new Encoder(DigitalChannels.DRIVE_RIGHT_ENCODER_A, DigitalChannels.DRIVE_RIGHT_ENCODER_B);
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
     * The timestamp, in milliseconds, at which processing started for the
     * previous call to {@link #run()}. This timing is used to calculate
     * velocity and position from acceleration.
     */
    private long lastProcessingTime = System.currentTimeMillis();

    public Navigator() {

        //Set the periodic task to run this 10 times/second
        setTaskTime(0.1);

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

        synchronized (this) {

            long newTime = System.currentTimeMillis();
            //Get the time in seconds since processing was last done
            double timeSeconds = (newTime - lastProcessingTime) / 1000.0;

            lastProcessingTime = newTime;

            //Get the Y-axis (local to the robot, longitudinal) acceleration and convert
            //it from Gs to m/s^2
            double accelY = accel.getAcceleration(ADXL345_I2C.Axes.kY) / 9.8;

            //update the heading
            heading = gyro.getAngle();

            //Append the velocity with the change in velocity over the last time step
            //90 degrees is added to the heading because Navigator uses forward for 0
            //and Vec2 uses +X for 0.
            velocity = velocity.add(Vec2.fromAngle(heading + 90, accelY * timeSeconds));

            //Append the position
            location = location.add(velocity.multiply(timeSeconds));
        }

    }

    //Methods to access location information
    /**
     * Get the robot heading
     * @return the heading, in degrees
     */
    public synchronized double getHeading() {
        return heading;
    }
    
    /**
     * Get the X location of the robot
     * @return the location, in meters
     */
    public synchronized double getX() {
        return location.getX();
    }
    
    /**
     * Get the Y location of the robot
     * @return the location, in meters
     */
    public synchronized double getY() {
        return location.getY();
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
}
