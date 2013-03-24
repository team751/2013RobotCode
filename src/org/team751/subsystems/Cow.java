package org.team751.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import org.team751.resources.CANJaguarIDs;
import org.team751.resources.DigitalChannels;
import org.team751.util.NamedCANJaguar;
import org.team751.util.StatusReportingSubsystem;
import org.team751.util.SubsystemStatusException;
import org.team751.util.cow.CowGainScheduler;
import org.team751.util.cow.CowGainScheduler.PID;
import org.team751.util.cow.CowPosition;
import org.team751.util.cow.CowStomachs;
import org.team751.util.cow.CowTriggers;
import org.team751.util.cow.ZeroSwitchMoveDetector;

/**
 * Implements the third software revision for the cow
 *
 * @author samcrow
 */
public class Cow extends StatusReportingSubsystem {

    private CANJaguar rotationJaguar;
    /**
     * The currently set control mode
     */
    private CANJaguar.ControlMode currentMode;
    /**
     * The position that is currently targeted
     */
    private CowPosition targetPosition = CowPosition.kShoot3;
    /**
     * The encoder reading that matches the physical zero position
     */
    private double zeroPosition = 0;
    /**
     * Photoswitch for zeroing the cow position
     */
    private final DigitalInput zeroSwitch = new DigitalInput(DigitalChannels.COW_ZERO);
    
    private final CowStomachs stomachs = new CowStomachs();
    
    private CowTriggers triggers;
    
    /**
     * The mechanism used to detect if the zero switch has gone through the
     * light -> dark -> light transition. This is used for precise positioning
     * for shoot positions.
     * 
     * If the cow is not currently moving, or if the current move is not a move
     * forward from one shoot position to another, this will be null.
     */
    private ZeroSwitchMoveDetector shootMoveDetector = null;

    public Cow() {
        super("cow");

        try {
            setupJaguar();
        } catch (CANTimeoutException ex) {
            reportInitFailed(ex);
        }
    }

    /**
     * Set the target position
     *
     * @param newPosition the new target position
     */
    public void setTargetPosition(CowPosition newPosition) {

        if (isSubsystemWorking()) {

            if (currentMode != CANJaguar.ControlMode.kPosition) {
                tryConfigJaguarPosition();
            }
            
            System.out.println("Moving from "+targetPosition+" to "+newPosition);
            
            //Configure the PID constants for this move
            PID newPids = CowGainScheduler.getGainForMove(targetPosition, newPosition);

            
            //Tell the trigger mechanism if the new position is feeding or not
            if(newPosition.isLoadPosition()) {
                triggers.setLoadMode(true);
            } else {
                triggers.setLoadMode(false);
            }
            
            //Check if this is a move down from one shooting position to another
            //If so, set up the move detector that uses the zero switch.
            if(targetPosition.isShootPosition() && newPosition.isShootPosition()) {
                if(targetPosition.moveTypeTo(newPosition) == CowPosition.MoveType.kDown) {
                    shootMoveDetector = new ZeroSwitchMoveDetector(this);
                }
            }

            targetPosition = newPosition;
            try {
                rotationJaguar.setPID(newPids.p, newPids.i, newPids.d);
                rotationJaguar.setX(zeroPosition + newPosition.getEncoderValue());
            } catch (CANTimeoutException ex) {
                reportNotWorking(ex);
            }
        }
    }

    /**
     * Get the target position of the cow
     *
     * @return the target position
     */
    public CowPosition getTargetPosition() {
        return targetPosition;
    }

    /**
     * Get the target encoder value
     *
     * @return
     */
    public double getActualCount() {
        if (isSubsystemWorking()) {
            try {
                return rotationJaguar.getPosition();
            } catch (CANTimeoutException ex) {
                reportNotWorking(ex);
                return 0;
            }
        } else {
            return 0;
        }
    }

    /**
     * Get the target encoder value
     *
     * @return
     */
    public double getTargetCount() {
        return zeroPosition + targetPosition.getEncoderValue();
    }

    /**
     * Determine if the cow is approximately in its target position
     *
     * @return
     */
    public boolean isInPosition() {
        if (isSubsystemWorking()) {
            
            //If the zero switch move detector has been set up for this move,
            //return true if it has gone through its sequence.
//            if(shootMoveDetector != null) {
//                if(shootMoveDetector.isComplete()) {
//                    System.out.println("Shoot move detector found this move to be complete");
//                    return true;
//                }
//            }
            
            try {
                double target = getTargetCount();
                double actual = rotationJaguar.getPosition();

                double diff = Math.abs(target - actual);

                return diff < 3;

            } catch (CANTimeoutException ex) {
                reportNotWorking(ex);
            }
            return false;
        }
        return false;
    }

    /**
     * Configure the jaguar in percent Vbus mode
     * 
     * @throws CANTimeoutException if an exception was encountered
     */
    private void configJaguarVbus() throws CANTimeoutException {
        rotationJaguar.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
        rotationJaguar.setX(0);
        currentMode = CANJaguar.ControlMode.kPercentVbus;
    }
    
    /**
     * Try to configure the jaguar in percent Vbus mode.
     * 
     * This is equivalent to {@link #configJaguarVbus()}, but it does not
     * throw exceptions.
     */
    private void tryConfigJaguarVbus() {
        try {
            configJaguarVbus();
        } catch (CANTimeoutException ex) {
            reportNotWorking(ex);
        }
    }

    /**
     * Configure the jaguar in position mode and enable position control
     * 
     * @throws CANTimeoutException if an exception was encountered
     */
    private void configJaguarPosition() throws CANTimeoutException {
        rotationJaguar.setPositionReference(CANJaguar.PositionReference.kQuadEncoder);
        rotationJaguar.configEncoderCodesPerRev(1);
        rotationJaguar.changeControlMode(CANJaguar.ControlMode.kPosition);

        rotationJaguar.setPID(3, 0, 0);

        rotationJaguar.setX(rotationJaguar.getX());
        rotationJaguar.enableControl();

        currentMode = CANJaguar.ControlMode.kPosition;
    }
    
    /**
     * Try to configure the jaguar in position mode.
     * 
     * This is equivalent to {@link #configJaguarPosition()}, but it does not
     * throw exceptions.
     */
    private void tryConfigJaguarPosition() {
        try {
            configJaguarPosition();
        } catch (CANTimeoutException ex) {
            reportNotWorking(ex);
        }
    }

    /**
     * Manually set the cow to move slowly forward
     */
    public void moveSlowForward() {
        if (isSubsystemWorking()) {
            if (currentMode != CANJaguar.ControlMode.kPercentVbus) {
                tryConfigJaguarVbus();
            }
            try {
                rotationJaguar.setX(-0.3);
            } catch (CANTimeoutException ex) {
                reportNotWorking(ex);
            }
        }
    }
    
    public void moveSlowBack() {
        if (isSubsystemWorking()) {
            if (currentMode != CANJaguar.ControlMode.kPercentVbus) {
                tryConfigJaguarVbus();
            }
            try {
                rotationJaguar.setX(0.2);
            } catch (CANTimeoutException ex) {
                reportNotWorking(ex);
            }
        }
    }

    /**
     * Manually stop the cow
     */
    public void manualStop() {
        if (isSubsystemWorking()) {
            if (currentMode != CANJaguar.ControlMode.kPercentVbus) {
                tryConfigJaguarVbus();
            }
            try {
                rotationJaguar.setX(0);
            } catch (CANTimeoutException ex) {
                reportNotWorking(ex);
            }
        }
    }

    /**
     * Determine if the cow is at the zero position
     *
     * @return
     */
    public boolean isAtZero() {
        return zeroSwitch.get() == false;
    }
    
    public void setThisAsZero() {
        zeroPosition = getActualCount();
        targetPosition = CowPosition.kShoot3;
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
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
     * Constructs the Jaguar object, if it is null, and configures it in
     * percent Vbus mode by default
     * @throws CANTimeoutException if an exception was encountered
     */
    private void setupJaguar() throws CANTimeoutException {
        if(rotationJaguar == null) {
            rotationJaguar = new NamedCANJaguar(CANJaguarIDs.COW_ROTATE, "cow rotation");
        }
        
        configJaguarVbus();
    }
    
    /**
     * Set the rotation Jaguar to brake mode
     */
    public void setBrakeMode() {
        if(isSubsystemWorking()) {
            try {
                rotationJaguar.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            } catch (CANTimeoutException ex) {
                reportNotWorking(ex);
            }
        }
    }
    
    /**
     * Set the rotation Jaguar to coast mode
     */
    public void setCoastMode() {
        if(isSubsystemWorking()) {
            try {
                rotationJaguar.configNeutralMode(CANJaguar.NeutralMode.kCoast);
            } catch (CANTimeoutException ex) {
                reportNotWorking(ex);
            }
        }
    }

    public void moveExtraSlowForward() {
        if(isSubsystemWorking()) {
            try {
                rotationJaguar.setX(-0.1);
            } catch (CANTimeoutException ex) {
                reportNotWorking(ex);
            }
        }
    }
    
    
    public void debugPosition() {
        try {
            System.out.println("Current "+ (rotationJaguar.getPosition() - zeroPosition) +" target "+targetPosition.getEncoderValue());
        } catch (CANTimeoutException ex) {
            reportNotWorking(ex);
        } catch (NullPointerException ex) {
            reportNotWorking(ex);
        }
    }
    
    /**
     * Get the object that keeps track of the stomachs for this cow
     * @return 
     */
    public CowStomachs getStomachs() {
        return stomachs;
    }
    /**
     * Get the object that keeps track of triggers and loading mode
     * @return 
     */
    public CowTriggers getTriggers() {
        return triggers;
    }

    /**
     * Set up the triggers. This must be called after the cow is constructed.
     */
    public void init() {
        triggers = new CowTriggers();
    }
    
}
