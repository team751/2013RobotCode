package org.team751.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team751.resources.CANJaguarIDs;
import org.team751.resources.DigitalChannels;
import org.team751.tasks.ClosedLoopSpeedController;
import org.team751.util.EncoderSpeedSource;

/**
 * A Subsystem that includes the shooter wheels and sensors for measuring their
 * speed.
 * @author Sam Crow
 */
public class ShooterWheels extends Subsystem {
    
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
    private Encoder firstEncoder = new Encoder(DigitalChannels.SHOOTER_FIRST_ENCODER_A, DigitalChannels.SHOOTER_FIRST_ENCODER_B, false, CounterBase.EncodingType.k1X);
    /**
     * The encoder that monitors the second wheel
     */
    private Encoder secondEncoder = new Encoder(DigitalChannels.SHOOTER_SECOND_ENCODER_A, DigitalChannels.SHOOTER_SECOND_ENCODER_B, false, CounterBase.EncodingType.k1X);
    
    //Set speed stuff
    
    /**
     * The target speed of the first (slower) wheel, compared to the speed
     * of the second (faster) wheel
     */
    private static final double FIRST_WHEEL_SPEED_RATIO = 0.8;
    
    /**
     * The maximum speed, in RPM, of the second (faster) wheel
     */
    private static final double MAXIMUM_SPEED = 4000;
    
    
   
    //Speed controllers
    
    private ClosedLoopSpeedController firstController;
    
    private ClosedLoopSpeedController secondController;

    public ShooterWheels() {
        try {
            firstMotor = new CANJaguar(CANJaguarIDs.SHOOTER_FIRST);
            secondMotor = new CANJaguar(CANJaguarIDs.SHOOTER_SECOND);
            
            configJaguars();
            
            firstController = new ClosedLoopSpeedController(new EncoderSpeedSource(firstEncoder), firstMotor);
            secondController = new ClosedLoopSpeedController(new EncoderSpeedSource(secondEncoder), secondMotor);
            
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
        
    }
    
    /**
     * Enable the shooter wheels. If the speed has been set to anything
     * greater than zero, the wheels will spin.
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
     * @param ratio The desired speed, from 0 to 1
     */
    public void setSpeed(double ratio) {
        if(ratio < 0 || ratio > 1) throw new IllegalArgumentException("Ratio must be from 0 to 1");
        
        double secondTarget = MAXIMUM_SPEED * ratio;
        double firstTarget = secondTarget * FIRST_WHEEL_SPEED_RATIO;
        
        firstController.setTargetRpm(firstTarget);
        secondController.setTargetRpm(secondTarget);
    }
    
    /**
     * Configure the Jaguars to use PID. This should be called after any Jaguar
     * restarts.
     * @throws CANTimeoutException if an exception was encountered
     */
    private void configJaguars() throws CANTimeoutException {
        
        //Set motors to coast
        firstMotor.configNeutralMode(CANJaguar.NeutralMode.kCoast);
        secondMotor.configNeutralMode(CANJaguar.NeutralMode.kCoast);
        
        
        //Set the maximum ramp rate to 24 volts/second
        //(1/2 second for a full voltage range traversal)
        //to prevent jerkiness
        firstMotor.setVoltageRampRate(24);
        secondMotor.setVoltageRampRate(24);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
