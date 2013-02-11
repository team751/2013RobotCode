package org.team751.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team751.resources.CANJaguarIDs;

/**
 * A subsystem for the pusher mechanism that pushes disks from the cow
 * into the shooter.
 * 
 * This only does anything if the cow is in position. It disables the cow
 * before extending and enables the cow once retracted.
 * 
 * @author Sam Crow
 */
public class Pusher extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    /**
     * The Jaguar used to control the pusher. The two limit switches are
     * connected to it.
     */
    private CANJaguar jaguar;
    
    public Pusher() {
        
        try {
            jaguar = new CANJaguar(CANJaguarIDs.PUSHER);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
        
    }
    
    /**
     * Determine if the pusher is retracted enough to safely move the cow
     * @return 
     */
    public boolean isSafeToMoveCow() {
        try {
            return !jaguar.getReverseLimitOK();
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void initDefaultCommand() {
        
    }
}
