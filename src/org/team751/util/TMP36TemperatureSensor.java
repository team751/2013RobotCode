package org.team751.util;

import edu.wpi.first.wpilibj.AnalogChannel;

/**
 * Reads a TMP36 temperature sensor (https://www.sparkfun.com/products/10988)
 * @author Sam Crow
 */
public abstract class TMP36TemperatureSensor extends TemperatureSensor {
    
    private AnalogChannel channel;
    
    public TMP36TemperatureSensor(int channelNumber, String name) {
        super(name);
        channel = new AnalogChannel(channelNumber);
        
        //Configure oversampling and averaging if necessary
    }

    public double getTemperature() {
        return (100 * channel.getVoltage() ) - 50;
    }
    
    
    
}
