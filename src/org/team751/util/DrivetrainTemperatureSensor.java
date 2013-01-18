package org.team751.util;

/**
 * Connects to a TMP36 temperature sensor on a drivetrain motor
 * @author Sam Crow
 */
public class DrivetrainTemperatureSensor extends TMP36TemperatureSensor {

    public DrivetrainTemperatureSensor(int channelNumber, String name) {
        super(channelNumber, name);
    }
    
    /**
     * The temperature in degrees Celsius at which a warning should be
     * displayed for drivetrain motors
     */
    public static double WARNING_TEMPERATURE = 30;
    
    public double getWarningTemperature() {
        return WARNING_TEMPERATURE;
    }
    
}
