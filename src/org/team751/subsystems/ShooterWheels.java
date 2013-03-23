package org.team751.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team751.resources.CANJaguarIDs;
import org.team751.resources.DigitalChannels;
import org.team751.speedcontrol.BangBangSpeedController;
import org.team751.speedcontrol.TakeBackHalfSpeedController;
import org.team751.speedcontrol.ThreadedSpeedController;
import org.team751.util.CounterSpeedSource;
import org.team751.util.StatusReportingSubsystem;
import org.team751.util.SubsystemStatusException;

/**
 * A Subsystem that includes the shooter wheels and sensors for measuring their
 * speed.
 *
 * @author Sam Crow
 */
public class ShooterWheels extends StatusReportingSubsystem {

    /**
     * The Jaguar controlling the slower wheel that the disk contacts first
     */
    private CANJaguar firstMotor;
    /**
     * The Jaguar controlling the faster wheel that the disk contacts second
     */
    private CANJaguar secondMotor;
    /**
     * The encoder that monitors the first wheel
     */
    private Counter firstEncoder = new Counter(DigitalChannels.SHOOTER_FIRST_ENCODER_A);
    /**
     * The encoder that monitors the second wheel
     */
    private Counter secondEncoder = new Counter(DigitalChannels.SHOOTER_SECOND_ENCODER_A);
    //Set speed stuff
    /**
     * The target speed of the first (slower) wheel, compared to the speed of
     * the second (faster) wheel
     */
    private static final double FIRST_WHEEL_SPEED_RATIO = 1;
    /**
     * The maximum speed, in RPM, of the second (faster) wheel
     */
    private static final double MAXIMUM_SPEED = 2800;
    //Speed controllers
    private ThreadedSpeedController firstController;
    private ThreadedSpeedController secondController;
    
    /**
     * Target speed, ratio from 0 to 1
     */
    private double targetSpeedRatio = 0;

    public ShooterWheels() {
        super("Shooter wheels");
        
        try {
            configJaguars();

            firstController = new TakeBackHalfSpeedController(new CounterSpeedSource(firstEncoder), firstMotor);
            secondController = new TakeBackHalfSpeedController(new CounterSpeedSource(secondEncoder), secondMotor);

        } catch (CANTimeoutException ex) {
            reportInitFailed(ex);
        }

        firstEncoder.setReverseDirection(true);
        secondEncoder.setReverseDirection(true);

        //Start counting encoder pulses
        firstEncoder.start();
        secondEncoder.start();

        setSpeed(1);
    }

    /**
     * Enable the shooter wheels. If the speed has been set to anything greater
     * than zero, the wheels will spin.
     */
    public void enable() {
        firstController.start();
        firstController.enable();
        secondController.start();
        secondController.enable();
    }

    /**
     * Disable the shooter wheels. They will stop after they spin down.
     */
    public void disable() {
        firstController.disable();
        secondController.disable();
    }

    /**
     * Set the speed of the shooter wheels
     *
     * @param ratio The desired speed, from 0 to 1
     */
    public void setSpeed(double ratio) {
        if(ratio > 1) {
            ratio = 1;
        }
        if(ratio < 0) {
            ratio = 1;
        }

        double secondTarget = MAXIMUM_SPEED * ratio;
        double firstTarget = secondTarget * FIRST_WHEEL_SPEED_RATIO;

        firstController.setTargetRpm(firstTarget);
        secondController.setTargetRpm(secondTarget);
        
        targetSpeedRatio = ratio;
    }

    /**
     * Determine if both wheels are close to their target speeds. This is
     * defined by {@link BangBangSpeedController#isOnTarget() }.
     *
     * @return True if both are on target, otherwise false.
     */
    public boolean isOnTarget() {
        return firstController.isOnTarget() && secondController.isOnTarget();
    }

    /**
     * Configure the Jaguars to use PID. This should be called after any Jaguar
     * restarts.
     *
     * @throws CANTimeoutException if an exception was encountered
     */
    private void configJaguars() throws CANTimeoutException {
        
        if(firstMotor == null) {
            firstMotor = new CANJaguar(CANJaguarIDs.SHOOTER_FIRST);
        }
        if(secondMotor == null) {
            secondMotor = new CANJaguar(CANJaguarIDs.SHOOTER_SECOND);
        }

        //Set motors to coast
        firstMotor.configNeutralMode(CANJaguar.NeutralMode.kCoast);
        secondMotor.configNeutralMode(CANJaguar.NeutralMode.kCoast);

        firstMotor.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
        firstMotor.changeControlMode(CANJaguar.ControlMode.kPercentVbus);

        //Set the maximum ramp rate to 24 volts/second
        //(1/2 second for a full voltage range traversal)
        //to prevent jerkiness
        firstMotor.setVoltageRampRate(0);
        secondMotor.setVoltageRampRate(0);
    }

    public void initDefaultCommand() {
    }

    public void setSpeedOpenLoop(double speed) {
        if (speed < 0) {
            speed = 0;
        }
        try {

            firstMotor.setX(speed);
            secondMotor.setX(speed);

        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }

        SmartDashboard.putNumber("Shooter speed", speed);
    }

    /**
     * Get the recently set open-loop speed
     *
     * @return
     */
    public double getSpeedOpenLoop() {
        try {
            return secondMotor.getX();
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public double getFirstEncoderCount() {
        return firstEncoder.get();
    }

    public double getSecondEncoderCount() {
        return secondEncoder.get();
    }

    public double getFirstRpm() {
        return firstController.getActualRpm();
    }

    public double getSecondRpm() {
        return secondController.getActualRpm();
    }

    public void retry() throws SubsystemStatusException {
        try {
            configJaguars();
        } catch (CANTimeoutException ex) {
            throw new SubsystemStatusException(ex);
        }
    }

    /**
     * Get the target speed ratio, 0-1
     * @return 
     */
    public double getTargetSpeedRatio() {
        return targetSpeedRatio;
    }
}
