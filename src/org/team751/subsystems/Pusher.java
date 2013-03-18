package org.team751.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import org.team751.resources.CANJaguarIDs;
import org.team751.util.NamedCANJaguar;
import org.team751.util.StatusReportingSubsystem;
import org.team751.util.SubsystemStatusException;

/**
 * A subsystem for the pusher mechanism that pushes disks from the cow into the
 * shooter.
 *
 * This only does anything if the cow is in position. It disables the cow before
 * extending and enables the cow once retracted.
 *
 * @author Sam Crow
 */
public class Pusher extends StatusReportingSubsystem {

    /**
     * The power level (0 to 1) to use for the motor
     */
    private static final double MOTOR_POWER = -0.2;
    /**
     * The Jaguar used to control the pusher. The two limit switches are
     * connected to it.
     */
    private CANJaguar jaguar;

    public Pusher() {
        super("pusher");

        try {
            setupJaguar();
        } catch (CANTimeoutException ex) {
            reportInitFailed(ex);
        }

    }

    /**
     * Determine if the pusher is retracted enough to safely move the cow
     *
     * @return
     */
    public boolean isRetracted() {
        if (isSubsystemWorking()) {
            try {
                return !jaguar.getForwardLimitOK();
            } catch (CANTimeoutException ex) {
                reportNotWorking(ex);
            }
        }
        return false;
    }

    /**
     * Determine if the pusher is fully extended
     *
     * @return
     */
    public boolean isExtended() {
        if (isSubsystemWorking()) {
            try {
                return !jaguar.getReverseLimitOK();
            } catch (CANTimeoutException ex) {
                reportNotWorking(ex);
            }
        }
        return false;
    }

    /**
     * Extend the pusher to push a disk into the shooter. If the cow is not in a
     * safe position to extend the pusher, nothing will happen.
     */
    public void push() {
        if (isSubsystemWorking()) {
//		if(CommandBase.cow.isInPosition()) {
            try {
                //Set the motor to move forward. The Jaguar will detect that
                //the limit switch is pressed and stop the motor by itself.
                jaguar.setX(MOTOR_POWER);
            } catch (CANTimeoutException ex) {
                reportNotWorking(ex);
            }
//		}
//		else {
//			System.err.println("Protection failure! Pusher commanded to extend "
//					+ "when the cow is not in position.");
//		}
        }
    }

    /**
     * Retract the pusher
     */
    public void retract() {
        if (isSubsystemWorking()) {
            try {
                jaguar.setX(-MOTOR_POWER);
            } catch (CANTimeoutException ex) {
                reportNotWorking(ex);
            }
        }
    }

    public void initDefaultCommand() {
        
    }

    public void retry() throws SubsystemStatusException {
        try {
            setupJaguar();
            reportWorking();
        } catch (CANTimeoutException ex) {
            throw new SubsystemStatusException(ex);
        }
    }
    
    /**
     * Construct the jaguar (if it is null) and sets it up
     * @throws CANTimeoutException if an exception was encountered
     */
    private void setupJaguar() throws CANTimeoutException {
        if(jaguar == null) jaguar = new NamedCANJaguar(CANJaguarIDs.PUSHER, "puhser");
    }
}
