package org.team751.commands.drivetrain;

/**
 * Stores an initial position and a target position
 * @author Sam Crow
 */
public class PositionSet {
	
	/**
	 * The initial position
	 */
	private double initial;
	
	/**
	 * The target position
	 */
	private double target;

	/**
	 * Constructor
	 * @param initial The initial position
	 * @param target The current position
	 */
	public PositionSet(double initial, double target) {
		this.initial = initial;
		this.target = target;
	}
	
	/**
	 * Calculate the distance from the current position to the target position
	 * @param currentPosition
	 * @return 
	 */
	public double distanceToTarget(double currentPosition) {
		return target - currentPosition;
	}
	
	/**
	 * Get the ratio of progress from the initial to the target position.
	 * This will be between 0 and 1 if the system is between the initial and
	 * target positions. Otherwise, it may be outside that range.
	 * @param currentPosition
	 * @return 
	 */
	public double getProgress(double currentPosition) {
		return (currentPosition - initial) / (target - initial);
	}

	public double getInitialPosition() {
		return initial;
	}

	public double getTargetPosition() {
		return target;
	}
	
	
}
