package org.team751.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;
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
    private Encoder firstEncoder = new Encoder(DigitalChannels.SHOOTER_FIRST_ENCODER_A, DigitalChannels.SHOOTER_FIRST_ENCODER_B);
    /**
     * The encoder that monitors the second wheel
     */
    private Encoder secondEncoder = new Encoder(DigitalChannels.SHOOTER_SECOND_ENCODER_A, DigitalChannels.SHOOTER_SECOND_ENCODER_B);
    
    //Set speed stuff
   
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
