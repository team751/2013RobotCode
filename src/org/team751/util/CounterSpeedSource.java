package org.team751.util;

import edu.wpi.first.wpilibj.Counter;

/**
 * A class that wraps a Counter and implements SpeedSource, used to determine
 * the rotation rate of something connected to a counter
 * @author Sam Crow
 */
public class CounterSpeedSource implements SpeedSource {

    /**
     * The number of counter counts for each revolution
     */
    private static final double COUNTS_PER_REV = 360;
    
    /**
     * The counter that is used to measure speed
     */
    private Counter counter;
    
    /**
     * Constructor
     * @param counter The counter to measure the speed of
     */
    public CounterSpeedSource(Counter counter) {
        this.counter = counter;
    }
    
    public double getRpm() {
        
        //Get the time in seconds between counts
        //At 2800 RPM, this will be on the order of 10^-5 seconds
        double secondsPerCount = counter.getPeriod();
        
        double countsPerSecond = 1 / secondsPerCount;
        
        double revsPerSecond = countsPerSecond / COUNTS_PER_REV;
        
        double rpm = revsPerSecond * 60;
        
        return rpm;
    }
    
}
