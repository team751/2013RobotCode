package org.team751.util;

import edu.wpi.first.wpilibj.Encoder;

/**
 * A class that wraps an Encoder and implements SpeedSource
 * @author Sam Crow
 */
public class EncoderSpeedSource implements SpeedSource {

    /**
     * The number of encoder counts for each revolution
     */
    private static final double COUNTS_PER_REV = 360;
    
    /**
     * The encoder that is used to measure speed
     */
    private Encoder encoder;
    
    /**
     * Constructor
     * @param encoder The encoder to measure the speed of
     */
    public EncoderSpeedSource(Encoder encoder) {
        this.encoder = encoder;
    }
    
    public double getRpm() {
        
        //Get the time in seconds between counts
        double secondsPerCount = encoder.getPeriod();
        
        double countsPerSecond = 1 / secondsPerCount;
        
        double revsPerSecond = countsPerSecond / COUNTS_PER_REV;
        
        double rpm = revsPerSecond * 60;
        
        return rpm;
    }
    
}
