package org.team751.util;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.SpeedController;
import java.util.Vector;

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
    
    public DrivetrainMonitor(SpeedController[] controllers, DrivetrainTemperatureSensor[] sensors) {
        
        //A temporary resizable list of the CANJaguars that have been provided
        Vector tempJaguars = new Vector();
        
        //accept whatever controllers are CANJaguars
        for(int i = 0; i < controllers.length; i++) {
            
            SpeedController controller = controllers[i];
            
            //If this 
            if(controller instanceof CANJaguar) {
                tempJaguars.addElement(controller);
            }
        }
        
        //Copy the temporary list into the main array
        tempJaguars.copyInto(jaguars);
        
        this.temperatureSensors = sensors;
    }
    
    
}
