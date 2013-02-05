package org.team751.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team751.resources.CANJaguarIDs;

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
    
    /*
     * Initially, the encoders will be connected to the Jaguars. This might change.
     * Instead of using PID for speed control, we will initially be using our
     * own system on the cRIO.
     */
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public ShooterWheels() {
        try {
            firstMotor = new CANJaguar(CANJaguarIDs.SHOOTER_FIRST);
            secondMotor = new CANJaguar(CANJaguarIDs.SHOOTER_SECOND);
            
            configJaguars();
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
        
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
