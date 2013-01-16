package org.team751.util;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 * A RobotDrive that works with any number of motors
 *
 * @author Sam Crow
 */
public class PolyMotorRobotDrive {

    /**
     * The motors on the left side of the robot
     */
    protected CANJaguar[] leftMotors;
    /**
     * The motors on the right side of the robot
     */
    protected CANJaguar[] rightMotors;
    /**
     * The CAN sync group for the left motors Jaguars set with this sync group
     * will update their output at the same time when
     * {@link edu.wpi.first.wpilibj.CANJaguar#updateSyncGroup(byte)} is called.
     */
    protected static final byte LEFT_SYNCGROUP = 1;
    /**
     * The CAN sync group for the right motors Jaguars set with this sync group
     * will update their output at the same time when
     * {@link edu.wpi.first.wpilibj.CANJaguar#updateSyncGroup(byte)} is called.
     */
    protected static final byte RIGHT_SYNCGROUP = 2;

    /**
     * Constructor
     *
     * @param leftMotors The motors on the left side of the robot
     * @param rightMotors The motors on the right side of the robot
     */
    public PolyMotorRobotDrive(CANJaguar[] leftMotors, CANJaguar[] rightMotors) {
        this.leftMotors = leftMotors;
        this.rightMotors = rightMotors;
    }

    protected void setLeftRightMotorOutputs(double leftOutput, double rightOutput) {
        //Set the output of each left motor (they won't actually update
        //until later)
        for (int i = 0, max = leftMotors.length; i < max; i++) {
            try {
                leftMotors[i].setX(leftOutput, LEFT_SYNCGROUP);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
        }

        for (int i = 0, max = rightMotors.length; i < max; i++) {
            try {
                rightMotors[i].setX(rightOutput, RIGHT_SYNCGROUP);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
        }

        try {
            //Update the sync groups so that the Jaguars will update at the same time
            CANJaguar.updateSyncGroup(LEFT_SYNCGROUP);
            CANJaguar.updateSyncGroup(RIGHT_SYNCGROUP);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
}
