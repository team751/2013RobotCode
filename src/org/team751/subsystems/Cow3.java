package org.team751.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team751.resources.CANJaguarIDs;
import org.team751.resources.DigitalChannels;
import org.team751.util.SubsystemStatusReporter;
import org.team751.util.cow.CowPosition;

/**
 * Implements the third software revision for the cow
 *
 * @author samcrow
 */
public class Cow3 extends Subsystem {

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
    private DigitalInput zeroSwitch = new DigitalInput(DigitalChannels.COW_ZERO);
    /**
     * The reporter that tracks the status of this system
     */
    private SubsystemStatusReporter reporter = new SubsystemStatusReporter("cow");

    public Cow3() {
        try {
            rotationJaguar = new CANJaguar(CANJaguarIDs.COW_ROTATE);

            configJaguarVbus();
        } catch (CANTimeoutException ex) {
            reporter.reportInitFailed(ex);
        }
    }

    /**
     * Set the target position
     *
     * @param newPosition the new target position
     */
    public void setTargetPosition(CowPosition newPosition) {

        if (reporter.isSubsystemWorking()) {

            if (currentMode != CANJaguar.ControlMode.kPosition) {
                configJaguarPosition();
            }

            targetPosition = newPosition;

            try {
                rotationJaguar.setX(zeroPosition + newPosition.getEncoderValue());
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
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
        if (reporter.isSubsystemWorking()) {
            try {
                return rotationJaguar.getPosition();
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
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
        if (reporter.isSubsystemWorking()) {
            try {
                double target = getTargetCount();
                double actual = rotationJaguar.getPosition();

                double diff = Math.abs(target - actual);

                return diff < 10;

            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
            return false;
        }
        return false;
    }

    /**
     * Configure the jaguar in percent Vbus mode
     */
    private void configJaguarVbus() {
        try {
            rotationJaguar.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
            rotationJaguar.setX(0);
            currentMode = CANJaguar.ControlMode.kPercentVbus;
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Configure the jaguar in position mode and enable position control
     */
    private void configJaguarPosition() {
        try {
            rotationJaguar.setPositionReference(CANJaguar.PositionReference.kQuadEncoder);
            rotationJaguar.configEncoderCodesPerRev(1);
            rotationJaguar.changeControlMode(CANJaguar.ControlMode.kPosition);

            rotationJaguar.setPID(5, 0, 0);

            rotationJaguar.setX(rotationJaguar.getX());
            rotationJaguar.enableControl();

            currentMode = CANJaguar.ControlMode.kPosition;
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Manually set the cow to move slowly forward
     */
    public void moveSlowForward() {
        if (reporter.isSubsystemWorking()) {
            if (currentMode != CANJaguar.ControlMode.kPercentVbus) {
                configJaguarVbus();
            }
            try {
                rotationJaguar.setX(-0.1);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Manually stop the cow
     */
    public void manualStop() {
        if (reporter.isSubsystemWorking()) {
            if (currentMode != CANJaguar.ControlMode.kPercentVbus) {
                configJaguarVbus();
            }
            try {
                rotationJaguar.setX(0);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Determine if the cow is at the zero position
     *
     * @return
     */
    public boolean isAtZero() {
        return !zeroSwitch.get();
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
