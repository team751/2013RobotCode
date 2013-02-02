package org.team751.util;

import java.util.Timer;

/**
 * A base class for anything that should run periodically, independent of
 * the command-based system timing. This uses a java.util.Timer to schedule
 * tasks.
 * @author Sam Crow
 */
public abstract class PeriodicTask {
    
    private Timer timer = new Timer();
    
    /**
     * The time, in seconds, that should pass between times when the task
     * is run.
     */
    private double periodicTaskTime = 1;
    
    /**
     * The run method
     * The timer will call this method.
     * Subclasses should place code in this method to be run.
     */
    protected abstract void run();
}
