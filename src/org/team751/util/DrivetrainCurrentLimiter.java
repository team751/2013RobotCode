package org.team751.util;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * Keeps track of the electrical current that the drivetrain
 * is consuming and determines how much power limiting is required
 * to prevent the 120A main breaker from tripping
 * @author Sam Crow
 */
public class DrivetrainCurrentLimiter {
    
    /**
     * The Jaguars that are being monitored
     */
    private CANJaguar[] jaguars;
    
    /**
     * If this limiter should do anything (true if all the provided SpeedControllers
     * are indeed CANJaguars)
     */
    private boolean enabled = false;
    
    /**
     * Constructor
     * @param controllers A set of CANJaguar controllers to monitor.
     * If one or more of these is not a CANJaguar, no monitoring nor
     * limiting will be performed.
     */
    public DrivetrainCurrentLimiter(SpeedController[] controllers) {
        //Initialize the jaguars array to be the same size as the controllers array
        jaguars = new CANJaguar[controllers.length];
        
        try {
            //No foreach in J2ME 1.5, so use a for loop
            for(int i = 0, max = controllers.length; i < max; i++) {
                SpeedController controller = controllers[i];

                //Try to cast the controller into a CANJaguar
                CANJaguar jaguar = (CANJaguar) controller;

                jaguars[i] = jaguar;
            }
        }
        catch(ClassCastException e) {
            //If any of the SpeedControllers couldn't be cast into a CANJaguar
            System.err.println("a SpeedController was not a CANJaguar! No current monitoring will be performed.");
        }
    }
}
