package org.team751.util;

import edu.wpi.first.wpilibj.Utility;

/**
 * Calculates the near-instantaneous rate of change of a quantity over time
 * @author Sam Crow
 */
public class Differentiator {
	
	private double lastValue = 0;
	
	/**
	 * The time, in microseconds, at which getDerivative() was last called
	 */
	private long lastRunTime = Utility.getFPGATime();
	
	/**
	 * Get the derivative of the data, in units per second, since this method
	 * was last called
	 * @param currentValue The current value of the data
	 * @return The d/dt of the data
	 */
	public double getDerivative(double currentValue) {
		long now = Utility.getFPGATime();
		double dv = currentValue - lastValue;
		
		//Calculate the change in time and convert it into seconds
		double dt = (now - lastRunTime) / 1000000.0;
		
		lastValue = currentValue;
		lastRunTime = now;
		return dv / dt;
	}
}
