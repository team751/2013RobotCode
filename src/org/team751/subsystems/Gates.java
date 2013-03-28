package org.team751.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team751.resources.PWMChannels;

/**
 * Manages the gate system that controls the trapping of disks under
 * the robot
 * @author Sam Crow
 */
public class Gates extends Subsystem {
	
	private static final double kFrontRightRetracted = 1;
	
	private static final double kFrontRightExtended = 0.5;
	
	private static final double kFrontLeftRetracted = 0;
	
	private static final double kFrontLeftExtended = 0.5;
	
	private static final double kBackRightRetracted = 0;
	
	private static final double kBackRightExtended = 0.5;
	
	private static final double kBackLeftRetracted = 1;
	
	private static final double kBackLeftExtended = 0.5;
	
	private Servo frontRight = new Servo(PWMChannels.GATE_FRONT_RIGHT);
	
	private Servo frontLeft = new Servo(PWMChannels.GATE_FRONT_LEFT);
	
	private Servo backRight = new Servo(PWMChannels.GATE_BACK_RIGHT);
	
	private Servo backLeft = new Servo(PWMChannels.GATE_BACK_LEFT);

	/**
	 * Extend the front gate
	 */
	public void extendFront() {
		frontRight.set(kFrontRightExtended);
		frontLeft.set(kFrontLeftExtended);
	}
	/**
	 * Retract the front gate
	 */
	public void retractFront() {
		frontRight.set(kFrontRightRetracted);
		frontLeft.set(kFrontLeftRetracted);
	}
	
	/**
	 * Extend the back gate
	 */
	public void extendBack() {
		backRight.set(kBackRightExtended);
		backLeft.set(kBackLeftExtended);
	}
	
	/**
	 * Retract the back gate
	 */
	public void retractBack() {
		backRight.set(kBackRightRetracted);
		backLeft.set(kBackLeftRetracted);
	}
	
	/**
	 * Extend both gates
	 */
	public void extendBoth() {
		extendFront();
		extendBack();
	}
	
	/**
	 * Retract both gates
	 */
	public void retractBoth() {
		retractFront();
		retractBack();
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
}
