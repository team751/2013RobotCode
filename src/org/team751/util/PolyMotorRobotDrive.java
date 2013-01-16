package org.team751.util;

import edu.wpi.first.wpilibj.CANJaguar;
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
     * Other Jaguars on the network must not use this sync group.
     */
    protected static final byte LEFT_SYNCGROUP = 1;
    /**
     * The CAN sync group for the right motors Jaguars set with this sync group
     * will update their output at the same time when
     * {@link edu.wpi.first.wpilibj.CANJaguar#updateSyncGroup(byte)} is called.
     * Other Jaguars on the network must not use this sync group.
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
    
    /**
     * Drive the robot with arcade drive
     * @param moveValue The degree to which the robot should be moved
     * forward/back. Full forward is +1, full reverse is -1
     * @param rotateValue The degree to which the robot should turn left
     * or right. Full left is -1, full right is +1.
     */
    public void arcadeDrive(double moveValue, double rotateValue) {
        //Based on http://www.chiefdelphi.com/media/papers/2661?langid=2
        
        double max = Math.abs(moveValue);
        if(Math.abs(rotateValue) > max) {
            max = Math.abs(rotateValue);
        }
        double sum = moveValue + rotateValue;
        double difference = moveValue - rotateValue;
        
        double leftPower;
        double rightPower;
        
        if(moveValue > 0) {
            if(rotateValue >= 0) {
                leftPower = max;
                rightPower = difference;
            } else {
                leftPower = sum;
                rightPower = max;
            }
        } else {
            if(rotateValue >= 0) {
                leftPower = sum;
                rightPower = -max;
            } else {
                leftPower = -max;
                rightPower = difference;
            }
        }
        
        setLeftRightMotorOutputs(leftPower, rightPower);
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
