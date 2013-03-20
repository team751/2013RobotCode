package org.team751.util;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import org.team751.resources.CANSyncGroups;

/**
 * A RobotDrive that works with any number of motors.
 *
 * When this class accesses a speed controller, it is synchronized. This allows
 * it to safely be used when other threads are also accessing them.
 *
 * @author Sam Crow
 */
public class PolyMotorRobotDrive {

    /**
     * The last control mode that the Jaguars were set to. This is used to avoid
     * setting the mode when it is not required.
     */
    private CANJaguar.NeutralMode lastNeutralMode = null;
    
    /**
     * The motors on the left side of the robot
     */
    protected SpeedController[] leftMotors;
    /**
     * The motors on the right side of the robot
     */
    protected SpeedController[] rightMotors;

    /**
     * Constructor
     *
     * @param leftMotors The motors on the left side of the robot
     * @param rightMotors The motors on the right side of the robot
     */
    public PolyMotorRobotDrive(SpeedController[] leftMotors, SpeedController[] rightMotors) {
        this.leftMotors = leftMotors;
        this.rightMotors = rightMotors;
    }

    /**
     * Drive the robot with arcade drive
     *
     * @param moveValue The degree to which the robot should turn left or right.
     * Full left is -1, full right is +1.
     * @param rotateValue The degree to which the robot should be moved
     * forward/back. Full forward is +1, full reverse is -1
     *
     * @throws CANTimeoutException if such an exception was encountered
     */
    public void arcadeDrive(double moveValue, double rotateValue) throws CANTimeoutException {
        //Based on http://www.chiefdelphi.com/media/papers/2661?langid=2

        double max = Math.abs(rotateValue);
        if (Math.abs(moveValue) > max) {
            max = Math.abs(moveValue);
        }
        double sum = rotateValue + moveValue;
        double difference = rotateValue - moveValue;

        double leftPower;
        double rightPower;

        if (rotateValue > 0) {
            if (moveValue >= 0) {
                leftPower = max;
                rightPower = difference;
            } else {
                leftPower = sum;
                rightPower = max;
            }
        } else {
            if (moveValue >= 0) {
                leftPower = sum;
                rightPower = -max;
            } else {
                leftPower = -max;
                rightPower = difference;
            }
        }

        setLeftRightMotorOutputs(leftPower, rightPower);
    }

    /**
     * Set the output of the left and right motors
     *
     * @param leftOutput
     * @param rightOutput
     * @throws CANTimeoutException if an exception was encountered
     */
    public void setLeftRightMotorOutputs(double leftOutput, double rightOutput) throws CANTimeoutException {

        /**
         * If any of the speed controllers is a CANJaguar. If this is true, the
         * CAN sync group will be updated at the end
         */
        boolean usingCan = false;

        //Set the output of each left motor (they won't actually update
        //until later)
        for (int i = 0, max = leftMotors.length; i < max; i++) {

            SpeedController controller = leftMotors[i];

            synchronized (controller) {

                //If this controller is a CANJaguar, set it with CAN
                if (controller instanceof CANJaguar) {
                    usingCan = true;
                    ((CANJaguar) controller).setX(leftOutput, CANSyncGroups.DRIVETRAIN_LEFT);

                } else {
                    //Set it with PWM (or whatever non-CAN interface it uses)
                    controller.set(leftOutput);
                }

            }
        }

        for (int i = 0, max = rightMotors.length; i < max; i++) {

            SpeedController controller = rightMotors[i];

            synchronized (controller) {

                if (controller instanceof CANJaguar) {

                    usingCan = true;
                    ((CANJaguar) controller).setX(rightOutput, CANSyncGroups.DRIVETRAIN_RIGHT);
                } else {
                    controller.set(rightOutput);
                }
            }
        }

        if (usingCan) {
            //Update the sync groups so that the Jaguars will update at the same time
            CANJaguar.updateSyncGroup(CANSyncGroups.DRIVETRAIN_LEFT);
            CANJaguar.updateSyncGroup(CANSyncGroups.DRIVETRAIN_RIGHT);
        }
    }

    /**
     * Set each CAN Jaguar to use brake mode, instead of the mode configured by
     * the physical jumper.
     *
     * @throws CANTimeoutException if such an exception was encountered
     */
    public void setBrakeMode() throws CANTimeoutException {
        setNeutralMode(CANJaguar.NeutralMode.kBrake);
    }

    /**
     * Set each CAN Jaguar to use the default mode specified by the jumper.
     *
     * @throws CANTimeoutException if such an exception was encountered
     */
    public void setDefaultNeutralMode() throws CANTimeoutException {
        setNeutralMode(CANJaguar.NeutralMode.kJumper);
    }

    /**
     * Set the neutral mode of each Jaguar.
     * This class does not send a CAN message if the mode was last set
     * to the same mode that is being requested.
     * @param mode the mode to set
     *
     * @throws CANTimeoutException if such an exception was encountered
     */
    private void setNeutralMode(CANJaguar.NeutralMode mode) throws CANTimeoutException {
        
        //Only set the mode if (a) the neutral mode has been set before
        //and (b) it was set to a different mode
        //Return if this is not the case
        if(lastNeutralMode != null && lastNeutralMode == mode) {
            return;
        }
        
        for (int i = 0; i < leftMotors.length; i++) {
            if (leftMotors[i] instanceof CANJaguar) {
                ((CANJaguar) leftMotors[i]).configNeutralMode(mode);
            }
        }
        for (int i = 0; i < rightMotors.length; i++) {
            if (rightMotors[i] instanceof CANJaguar) {
                try {
                    ((CANJaguar) rightMotors[i]).configNeutralMode(mode);
                } catch (CANTimeoutException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void setCoastMode() throws CANTimeoutException {
        setNeutralMode(CANJaguar.NeutralMode.kCoast);
    }
}
