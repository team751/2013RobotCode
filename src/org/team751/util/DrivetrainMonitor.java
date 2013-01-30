package org.team751.util;

import edu.wpi.first.wpilibj.CANJaguar;

/**
 * Monitors the speed controllers and motors of the drivetrain
 * @author Sam Crow
 */
public class DrivetrainMonitor {
    
    /**
     * The Jaguars that are being monitored
     */
    private CANJaguar[] jaguars;
    
    /**
     * The motor temperature sensors that are being monitored
     */
    private DrivetrainTemperatureSensor[] temperatureSensors;
    
}
