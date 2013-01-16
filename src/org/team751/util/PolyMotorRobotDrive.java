package org.team751.util;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * A RobotDrive that works with any number of motors
 * @author Sam Crow
 */
public class PolyMotorRobotDrive extends RobotDrive {
    
    /**
     * Constructor
     * @param leftMotors
     * @param rightMotors 
     */
    public PolyMotorRobotDrive(SpeedController[] leftMotors, SpeedController[] rightMotors) {
        super(leftMotors[0], rightMotors[0]);
    }

    //Constructors with PWM Jaguar channel numbers are inaccessible
    private PolyMotorRobotDrive(int leftMotorChannel, int rightMotorChannel) {
        super(leftMotorChannel, rightMotorChannel);
    }

    private PolyMotorRobotDrive(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor) {
        super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
    }

    public PolyMotorRobotDrive(SpeedController leftMotor, SpeedController rightMotor) {
        super(leftMotor, rightMotor);
    }

    public PolyMotorRobotDrive(SpeedController frontLeftMotor, SpeedController rearLeftMotor, SpeedController frontRightMotor, SpeedController rearRightMotor) {
        super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
    }
    
    
    
}
