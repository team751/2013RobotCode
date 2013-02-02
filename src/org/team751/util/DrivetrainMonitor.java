package org.team751.util;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * Monitors the speed controllers and motors of the drivetrain
 *
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

    /**
     * The time in seconds between the starts of loops
     */
    private static final double LOOP_TIME = 0.5;
    
    private Timer timer = new Timer();
    
    public DrivetrainMonitor(SpeedController[] controllers, DrivetrainTemperatureSensor[] sensors) {

        //A temporary resizable list of the CANJaguars that have been provided
        Vector tempJaguars = new Vector();

        //accept whatever controllers are CANJaguars
        for (int i = 0; i < controllers.length; i++) {

            SpeedController controller = controllers[i];

            //If this controller is a CANJaguar
            if (controller instanceof CANJaguar) {
                tempJaguars.addElement(controller);
            }
        }

        //Copy the temporary list into the main array
        jaguars = new CANJaguar[tempJaguars.size()];
        tempJaguars.copyInto(jaguars);

        this.temperatureSensors = sensors;
    }
    
    public void start() {
        timer.scheduleAtFixedRate(task, 0, (long)(LOOP_TIME * 1000));
    }
    
    /**
     * The task that accesses the stuff and sends information to the dashboard
     */
    private TimerTask task = new TimerTask() {
        public void run() {

            //Get the temperature of each Jaguar
            for (int i = 0; i < jaguars.length; i++) {
                CANJaguar jaguar = jaguars[i];

                synchronized (jaguar) {
                    try {
                        double temperature = jaguar.getTemperature();

                        String name;
                        if (jaguar instanceof NamedCANJaguar) {
                            name = ((NamedCANJaguar) jaguar).getName();
                        } else {
                            name = jaguar.getDescription();
                        }
                        SmartDashboard.putNumber(name, temperature);
                    } catch (CANTimeoutException ex) {
                        ex.printStackTrace();
                    }
                }

            }

        }
    };
}
