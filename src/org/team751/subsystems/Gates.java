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
	
	private Servo frontA = new Servo(PWMChannels.GATE_FRONT_A);
	
	private Servo frontB = new Servo(PWMChannels.GATE_FRONT_B);
	
	private Servo backA = new Servo(PWMChannels.GATE_BACK_A);
	
	private Servo backB = new Servo(PWMChannels.GATE_BACK_B);

	/**
	 * Extend the front gate
	 */
	public void extendFront() {
		frontA.set(1);
		frontB.set(1);
	}
	/**
	 * Retract the front gate
	 */
	public void retractFront() {
		frontA.set(0);
		frontB.set(0);
	}
	
	/**
	 * Extend the back gate
	 */
	public void extendBack() {
		backA.set(1);
		backB.set(1);
	}
	
	/**
	 * Retract the back gate
	 */
	public void retractBack() {
		backA.set(0);
		backB.set(0);
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
