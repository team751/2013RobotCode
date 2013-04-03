package org.team751.subsystems;

import edu.wpi.first.wpilibj.Servo;
import org.team751.resources.PWMChannels;

/**
 * Manages the gate system that controls the trapping of disks under the robot.
 * This class is for the two servos on the front of the robot.
 *
 * @author Sam Crow
 */
public class CerberusFront extends AbstractCerberus {

    private static final double kFrontRightRetracted = 0.946;
    private static final double kFrontRightExtended = 0.18;
    private static final double kFrontLeftRetracted = 0.285;
    private static final double kFrontLeftExtended = 0.88;
    
    /**
     * The front right servo
     */
    private Servo frontRight = new Servo(PWMChannels.GATE_FRONT_RIGHT);
    /**
     * The front left servo
     */
    private Servo frontLeft = new Servo(PWMChannels.GATE_FRONT_LEFT);

    /**
     * Extend the front left servo
     */
    public void extendLeft() {
        frontLeft.set(kFrontLeftExtended);
    }

    /**
     * Retract the front left servo
     */
    public void retractLeft() {
        frontLeft.set(kFrontLeftRetracted);
    }

    /**
     * Extend the front right servo
     */
    public void extendRight() {
        frontRight.set(kFrontRightExtended);
    }

    /**
     * Retract the front right servo
     */
    public void retractRight() {
        frontRight.set(kFrontRightRetracted);
    }
    
    

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
