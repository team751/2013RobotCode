package org.team751.subsystems;

import edu.wpi.first.wpilibj.Servo;
import org.team751.resources.PWMChannels;

/**
 * Manages the gate system that controls the trapping of disks under the robot.
 * This class is for the two servos on the back of the robot.
 *
 * @author Sam Crow
 */
public class CerberusBack extends AbstractCerberus {

    private static final double kBackRightRetracted = 0.28;
    private static final double kBackRightExtended = 1.1;
    private static final double kBackLeftRetracted = 0.4675;
    private static final double kBackLeftExtended = -0.1;
    
    /**
     * The back right servo
     */
    private Servo backRight = new Servo(PWMChannels.GATE_BACK_RIGHT);
    /**
     * The back left servo
     */
    private Servo backLeft = new Servo(PWMChannels.GATE_BACK_LEFT);

    /**
     * Extend the back left servo
     */
    public void extendLeft() {
        backLeft.set(kBackLeftExtended);
    }

    /**
     * Retract the back left servo
     */
    public void retractLeft() {
        backLeft.set(kBackLeftRetracted);
    }

    /**
     * Extend the back right servo
     */
    public void extendRight() {
        backRight.set(kBackRightExtended);
    }

    /**
     * Retract the back right servo
     */
    public void retractRight() {
        backRight.set(kBackRightRetracted);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
