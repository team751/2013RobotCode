package org.team751.subsystems;

import org.team751.util.cow.CowPosition;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team751.commands.CommandBase;
import org.team751.resources.CANJaguarIDs;
import org.team751.resources.DigitalChannels;
import org.team751.util.LimitSwitch;
import org.team751.util.cow.CowProtector;
import org.team751.util.cow.CowStomachStatus;

/**
 * Manages the second, simpler, revision of the cow
 * @author Sam Crow
 */
public class Cow extends Subsystem {

	/**
	 * The Jaguar used to control rotation
	 */
	private CANJaguar rotationMotor;

	private static final double kP = 5;

	private static final double kI = 0;

	private static final double kD = 0;
	
	/**
	 * The encoder reading that is provided at the zero position
	 */
	private double zeroPosition = 0;

	/**
	 * Limit switch for detecting a disk in stomach 0
	 */
	private LimitSwitch stomach0Switch = new LimitSwitch(
			DigitalChannels.COW_STOMACH_0);

	/**
	 * Limit switch for detecting a disk in stomach 1
	 */
	private LimitSwitch stomach1Switch = new LimitSwitch(
			DigitalChannels.COW_STOMACH_1);

	/**
	 * Limit switch for detecting a disk in stomach 2
	 */
	private LimitSwitch stomach2Switch = new LimitSwitch(
			DigitalChannels.COW_STOMACH_2);

	/**
	 * Limit switch for detecting a disk in stomach 3
	 */
	private LimitSwitch stomach3Switch = new LimitSwitch(
			DigitalChannels.COW_STOMACH_3);

	/**
	 * Photoswitch for zeroing the cow position
	 */
	private DigitalInput zeroSwitch = new DigitalInput(DigitalChannels.COW_ZERO);

	public final CowProtector protection = new CowProtector(this);

	/**
	 * The Position that is currently targeted
	 */
	private CowPosition targetPosition = CowPosition.kShoot3;

	public Cow() {
		try {
			rotationMotor = new CANJaguar(CANJaguarIDs.COW_ROTATE);

			configJaguars();

		} catch (CANTimeoutException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Set the target position.
	 * @param position The new target position
	 */
	public void setTargetPosition(CowPosition position) {
		targetPosition = position;
		try {
			rotationMotor.setX(zeroPosition + targetPosition.getEncoderValue());
			rotationMotor.enableControl();
		} catch (CANTimeoutException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Configure the Jaguars to use PID. This should be called after any Jaguar
	 * restarts. This will not enable control.
	 * @throws CANTimeoutException if an exception was encountered
	 */
	private void configJaguars() throws CANTimeoutException {
		rotationMotor.setPositionReference(
				CANJaguar.PositionReference.kQuadEncoder);
		rotationMotor.configEncoderCodesPerRev(1);
		rotationMotor.setPID(kP, kI, kD);

		rotationMotor.setX(zeroPosition);
		
		rotationMotor.setSafetyEnabled(false);
	}

	//<editor-fold defaultstate="collapsed" desc="isFull methods and occupation status">
	/**
	 * Determine if a disk is in position 0
	 * @return true if a disk is present, otherwise false
	 */
	public boolean isStomach0Full() {
		return stomach0Switch.isPressed();
	}

	/**
	 * Determine if a disk is in position 1
	 * @return true if a disk is present, otherwise false
	 */
	public boolean isStomach1Full() {
		return stomach1Switch.isPressed();
	}

	/**
	 * Determine if a disk is in position 2
	 * @return true if a disk is present, otherwise false
	 */
	public boolean isStomach2Full() {
		return stomach2Switch.isPressed();
	}

	/**
	 * Determine if a disk is in position 3
	 * @return true if a disk is present, otherwise false
	 */
	public boolean isStomach3Full() {
		return stomach3Switch.isPressed();
	}

	/**
	 * Get the current occupation status, describing which positions are
	 * occupied.
	 * @return the current occupation status
	 */
	public CowStomachStatus getOccupationStatus() {
		return new CowStomachStatus(isStomach0Full(), isStomach1Full(),
									isStomach2Full(), isStomach3Full());
	}
	//</editor-fold>

	/**
	 * Determine if the Cow rotation is in the position set by
	 * {@link #setTargetPosition(org.team751.subsystems.Cow.Position)}.
	 * @return True if the Cow is in position, otherwise false
	 */
	public boolean isInPosition() {

		double target = targetPosition.getEncoderValue();
		try {
			double actual = rotationMotor.getPosition();

			double difference = Math.abs(target - actual);

			//True if the position is within this many encoder counts of the target
			return difference < 10;

		} catch (CANTimeoutException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Get the current target position
	 * @return the target position
	 */
	public CowPosition getTargetPosition() {
		return targetPosition;
	}

	/**
	 * Disable the rotation PID. This will prevent the motor from
	 * moving.
	 */
	public void disable() {
		try {
			rotationMotor.disableControl();
		} catch (CANTimeoutException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Enable the rotation PID. This will allow the motor to move.
	 */
	public void enable() {

		if (CommandBase.pusher.isRetracted()) {

			try {
				configJaguars();
				rotationMotor.changeControlMode(CANJaguar.ControlMode.kPosition);
				rotationMotor.enableControl();
			} catch (CANTimeoutException ex) {
				ex.printStackTrace();
			}

		} else {
			System.err.println("Protection failure! Attempted to enable cow when"
					+ "pusher is not retracted.");
		}
	}

	/**
	 * Determine if the cow is in the zero position, as defined by the zero
	 * photoswitch
	 * @return
	 */
	public boolean isAtZero() {
		return ! zeroSwitch.get();
	}

	/**
	 * Set the cow to rotate forward, slowly, manually
	 */
	public void manualMoveForwardFast() {
		configJaguarsPercentVbus();
		try {
			rotationMotor.setX(-0.1);
		} catch (CANTimeoutException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Set the cow to rotate back, slowly, manually.
	 * This may not have enough power to lift the cow up from the back
	 * position.
	 */
	public void manualMoveForwardSlow() {
		configJaguarsPercentVbus();
		try {
			rotationMotor.setX(-0.1);
		} catch (CANTimeoutException ex) {
			ex.printStackTrace();
		}
	}
	
	public void manualMoveBack() {
		configJaguarsPercentVbus();
		try {
			rotationMotor.setX(0.4);
		} catch (CANTimeoutException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Set the cow to stop
	 */
	public void manualStop() {
		configJaguarsPercentVbus();
		try {
			rotationMotor.setX(0);
		} catch (CANTimeoutException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Set the current position as zero
	 */
	public void setToZero() {
		try {
			double currentPosition = rotationMotor.getPosition();
			zeroPosition = currentPosition;
		} catch (CANTimeoutException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Configures the Jaguar in percent Vbus mode
	 */
	private void configJaguarsPercentVbus() {
		try {
			if (rotationMotor.getControlMode() != CANJaguar.ControlMode.kPercentVbus) {
				rotationMotor.changeControlMode(
						CANJaguar.ControlMode.kPercentVbus);
			}
		} catch (CANTimeoutException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Get the actual position of the cow, in encoder counts
	 * @return the position
	 */
	public double getActualPosition() {
		try {
			return rotationMotor.getPosition() - zeroPosition;
		} catch (CANTimeoutException ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	protected void initDefaultCommand() {
	}
}
