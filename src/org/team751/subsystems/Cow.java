package org.team751.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team751.resources.CANJaguarIDs;

/**
 * A Subsystem that controls the Cow, and its position and angle status.
 * @author Sam Crow
 */
public class Cow extends Subsystem {
    
    /**
     * The Jaguar that controls the rear motor
     */
    private CANJaguar rearMotor;
    /**
     * The Jaguar that controls the front motor
     */
    private CANJaguar frontMotor;
    
    /**
     * The Encoder that measures the position of the rear lead screw
     */
    private Encoder rearEncoder;
    /**
     * The Encoder that measures the position
     */
    private Encoder frontEncoder;
    
    //PID constants
    /**
     * The proportional constant
     */
    private static final double kP = 0.1;
    /**
     * The integral constant
     */
    private static final double kI = 0;
    /**
     * The derivative constant
     */
    private static final double kD = 0;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public Cow() {
        
        try {
            rearMotor = new CANJaguar(CANJaguarIDs.COW_REAR);
            frontMotor = new CANJaguar(CANJaguarIDs.COW_FRONT);
            
            configJaguars();
            
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
        
    }
    
    /**
     * Set up the Jaguars to use PID, and any other custom settings.
     * This should be called if a Jaguar has reset.
     * @throws CANTimeoutException if a CANTimeoutException was encountered
     */
    private void configJaguars() throws CANTimeoutException {
        rearMotor.changeControlMode(CANJaguar.ControlMode.kPosition);
        frontMotor.changeControlMode(CANJaguar.ControlMode.kPosition);
        
        rearMotor.setPID(kP, kI, kD);
        frontMotor.setPID(kP, kI, kD);
        
        rearMotor.enableControl();
        frontMotor.enableControl();
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
