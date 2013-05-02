package org.team751.util;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import org.team751.cheesy.util.ContinuousAccelFilter;
import org.team751.util.cow.CowGainScheduler.PID;

/**
 * Controls movement using a motion profiler with its output chained into a
 * PID controller
 * @author Sam Crow
 */
public class ProfilePidChain {
	
	/**
	 * The motion profiler used to generate target positions
	 */
	private ContinuousAccelFilter profile;
	
	private PIDController pid;
	
	private double maxVelocity;
	private double maxAcceleration;
	
	/**
	 * Constructor
	 * @param pidConstants The set of PID constants to use. These values should normally
	 * be somewhat high, because the controller is expected to precisely follow a profile
	 * and approach a nearby setpoint.
	 * @param feedbackSource The source used to get the input position
	 * @param output The output used to set output power
	 * @param maxVelocity The maximum velocity that the movement should achieve
	 * @param maxAcceleration The maximum acceleration that the movement should achieve
	 */
	public ProfilePidChain(PID pidConstants, PIDSource feedbackSource, PIDOutput output, double maxVelocity, double maxAcceleration) {
		this.maxVelocity = maxVelocity;
		this.maxAcceleration = maxAcceleration;
		
		profile = new ContinuousAccelFilter();
		
		pid = new PIDController(pidConstants.p, pidConstants.i, pidConstants.d, feedbackSource, output);
		
	}
	
	public void recalculate() {
		
		
		
	}
	
}
