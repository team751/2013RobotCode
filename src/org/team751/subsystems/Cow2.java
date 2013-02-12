package org.team751.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team751.commands.CommandBase;
import org.team751.resources.CANJaguarIDs;
import org.team751.resources.DigitalChannels;
import org.team751.util.LimitSwitch;
import org.team751.util.cow.CowStomachStatus;

/**
 * Manages the second, simpler, revision of the cow
 * @author Sam Crow
 */
public class Cow2 extends Subsystem {

    /**
     * The Jaguar used to control rotation
     */
    private CANJaguar rotationMotor;

    private static final double kP = 0.05;

    private static final double kI = 0;

    private static final double kD = 0;

    /**
     * Limit switch for detecting a disk in stomach 0
     */
    private LimitSwitch stomach0Switch = new LimitSwitch(DigitalChannels.COW_STOMACH_0);
    /**
     * Limit switch for detecting a disk in stomach 1
     */
    private LimitSwitch stomach1Switch = new LimitSwitch(DigitalChannels.COW_STOMACH_1);
    /**
     * Limit switch for detecting a disk in stomach 2
     */
    private LimitSwitch stomach2Switch = new LimitSwitch(DigitalChannels.COW_STOMACH_2);
    /**
     * Limit switch for detecting a disk in stomach 3
     */
    private LimitSwitch stomach3Switch = new LimitSwitch(DigitalChannels.COW_STOMACH_3);

    /**
     * The Position that is currently targeted
     */
    private Position targetPosition = Position.kShoot0;

    //<editor-fold defaultstate="collapsed" desc="Position enumeration">
    /**
     * Enumerates different positions to which the cow can be set.
     *
     * There are 4 disk positions in the cow: 0, 1, 2, and 3.
     *
     * With the Cow in its most upright position, position 0 is furthest backwards.
     * Positions 1-3 are forwards of it.
     *
     * When the cow is backward in feeding mode, position 0 is at the bottom.
     * When the cow is forwards in shooting mode, position 0 is at the top.
     * (this also represents its furthest forward limit)
     *
     */
    public static class Position {

        /**
         * The value, in encoder counts rearward from position kShoot0,
         * that the cow should be set to
         */
        private double encoderValue = 0;

        /**
         * Position for loading into stomach 0. (stomach 0 is at the bottom).
         */
        public static final Position kLoad0 = new Position(100);
        /**
         * Position for loading into stomach 1.
         */
        public static final Position kLoad1 = new Position(115);
        /**
         * Position for loading into stomach 2.
         */
        public static final Position kLoad2 = new Position(130);
        /**
         * Position for loading into stomach 3. (stomach 3 is at the top)
         */
        public static final Position kLoad3 = new Position(145);

        /**
         * Position for shooting from stomach 0. (stomach 0 is at the top)
         */
        public static final Position kShoot0 = new Position(0);
        /**
         * Position for shooting from stomach 1.
         */
        public static final Position kShoot1 = new Position(15);
        /**
         * Position for shooting from stomach 2.
         */
        public static final Position kShoot2 = new Position(30);
        /**
         * Position for shooting from stomach 3. (stomach 3 is at the bottom).
         */
        public static final Position kShoot3 = new Position(45);

        /**
         * Constructor
         * @param encoderValue The encoder value for this position
         */
        private Position(double encoderValue) {this.encoderValue = encoderValue; }

        /**
         * Get the encoder value.
         * @return The target encoder value for this position.
         */
        double getEncoderValue() { return encoderValue; }
    }
    //</editor-fold>

    public Cow2() {
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
    public void setTargetPosition(Position position) {
        targetPosition = position;
        try {
            rotationMotor.setX(targetPosition.getEncoderValue());
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

        rotationMotor.changeControlMode(CANJaguar.ControlMode.kPosition);
        rotationMotor.setPositionReference(CANJaguar.PositionReference.kQuadEncoder);

        rotationMotor.setPID(kP, kI, kD);

        rotationMotor.setX(0);

        rotationMotor.enableControl();
    }

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
		return new CowStomachStatus(isStomach0Full(), isStomach1Full(), isStomach2Full(), isStomach3Full());
	}

    /**
     * Determine if the Cow rotation is in the position set by
     * {@link #setTargetPosition(org.team751.subsystems.Cow2.Position)}.
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
    public Position getTargetPosition() {
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
		
		if(CommandBase.pusher.isRetracted()) {
		
        try {
            rotationMotor.enableControl();
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
		
		}
		else {
			System.err.println("Protection failure! Attempted to enable cow when"
					+ "pusher is not retracted.");
		}
    }

    protected void initDefaultCommand() {
    }

}
