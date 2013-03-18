package org.team751.cheesy.util;

/**
 * A basic interface for different acceleration profiles.
 *
 * Calculates the kinematic physics of a system by determining distance
 * remaining and maximum, intended, and current velocity and acceleration.
 */
public abstract class AccelFilterBase {

    /**
     * Constructor
     * @param currPos The current position
     * @param currVel The current velocity
     * @param currAcc The current acceleration
     */
    public AccelFilterBase(double currPos, double currVel, double currAcc) {
        m_currPos = currPos;
        m_currVel = currVel;
        m_currAcc = currAcc;
    }
    
    /**
     * Constructor with no arguments.
     * Position, velocity, and acceleration are set to zero.
     */
    public AccelFilterBase() {
        this(0, 0, 0);
    }

    // Getter functions
    /**
     * Get the current position
     * @return 
     */
    public double GetCurrPos() {
        return m_currPos;
    }

    /**
     * Get the current velocity
     * @return 
     */
    public double GetCurrVel() {
        return m_currVel;
    }
    /**
     * Get the current acceleration
     * @return 
     */
    public double GetCurrAcc() {
        return m_currAcc;
    }

    /**
     * Recalculate the system
     * @param distance_to_target The distance from the current position to the target position
     * @param v The current velocity
     * @param goal_v The target velocity
     * @param max_a The maximum acceleration that should be used
     * @param max_v The maximum velocity that should be used
     * @param dt The change in time between this call and the most recent call to the same method
     */
    public abstract void CalcSystem(double distance_to_target, double v, double goal_v, double max_a, double max_v, double dt);

    /**
     * default value updater
     * @param acc The change in acceleration
     * @param dt The change in time between this call and the most recent call to the same method
     */
    protected void UpdateVals(double acc, double dt) {
        m_currAcc = acc;
        m_currVel += m_currAcc;
        m_currPos += m_currVel * dt;
        m_currAcc /= dt;
    }
    
    
    // current vars
    /**
     * The current position
     */
    protected double m_currPos;
    /**
     * The current velocity
     */
    protected double m_currVel;
    /**
     * The current acceleration
     */
    protected double m_currAcc;
}
