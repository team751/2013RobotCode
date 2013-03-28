package org.team751.resources;

/**
 * Stores the assignments of PWM channels
 * @author Sam Crow
 */
public class PWMChannels {
	
	/**
	 * PWM channel for the front gate system servo
	 */
	public static final int GATE_FRONT_A = 1;
	
	public static final int GATE_FRONT_B = 2;
	
	/**
	 * PWM channel for the back gate system servo
	 */
	public static final int GATE_BACK_A = 3;
	
	public static final int GATE_BACK_B = 4;
	
	private PWMChannels() {}
}
