package org.team751.resources;

/**
 * Stores the assignments of PWM channels
 * @author Sam Crow
 */
public class PWMChannels {
	
	/**
	 * PWM channel for the front gate system servo
	 */
	public static final int GATE_FRONT_RIGHT = 1;
	
	public static final int GATE_FRONT_LEFT = 2;
	
	/**
	 * PWM channel for the back gate system servo
	 */
	public static final int GATE_BACK_RIGHT = 3;
	
	public static final int GATE_BACK_LEFT = 4;
	
	private PWMChannels() {}
}
