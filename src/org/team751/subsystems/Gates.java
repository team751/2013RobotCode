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
	
	private Servo frontServo = new Servo(PWMChannels.GATE_FRONT);
	
	private Servo backServo = new Servo(PWMChannels.GATE_BACK);

	/**
	 * Extend the front gate
	 */
	public void extendFront() {
		frontServo.set(1);
	}
	/**
	 * Retract the front gate
	 */
	public void retractFront() {
		frontServo.set(0);
	}
	
	/**
	 * Extend the back gate
	 */
	public void extendBack() {
		backServo.set(1);
	}
	
	/**
	 * Retract the back gate
	 */
	public void retractBack() {
		backServo.set(0);
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
