package org.team751.util;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 * A CANJaguar that stores a descriptive name
 * @author Sam Crow
 */
public class NamedCANJaguar extends CANJaguar {

    /**
     * A descriptive name for this motor controller, like "Drive left A"
     */
    protected String name = "";
    
    /**
     * The CAN ID of this Jaguar
     */
    protected int canId = 0;
    
    public NamedCANJaguar(int deviceNumber, String name) throws CANTimeoutException {
        super(deviceNumber);
        this.name = name;
        this.canId = deviceNumber;
    }

    public NamedCANJaguar(int deviceNumber, ControlMode controlMode, String name) throws CANTimeoutException {
        super(deviceNumber, controlMode);
        this.name = name;
        this.canId = deviceNumber;
    }
    
    public String getName() {
        return name;
    }
    
    public String toString() {
        return "CAN Jaguar ID "+canId+", "+name;
    }
}
