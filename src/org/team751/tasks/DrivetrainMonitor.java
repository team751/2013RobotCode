package org.team751.tasks;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Vector;
import org.team751.util.NamedCANJaguar;
import org.team751.util.TemperatureSensor;

/**
 * Monitors the speed controllers and motors of the drivetrain
 *
 * @author Sam Crow
 */
public class DrivetrainMonitor extends PeriodicTask {

    /**
     * The Jaguars that are being monitored
     */
    private CANJaguar[] jaguars;
    /**
     * The motor temperature sensors that are being monitored
     */
    private TemperatureSensor[] temperatureSensors;
    /**
     * The cached temperatures of all the Jaguars
     */
    private double[] jaguarTemperatures;
    /**
     * The time in seconds between the starts of loops
     */
    private static final double LOOP_TIME = 0.5;

    public DrivetrainMonitor(SpeedController[] controllers, TemperatureSensor[] sensors) {

        //Set this task to be executed with the correct frequency
        setTaskTime(LOOP_TIME);
        
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

        jaguarTemperatures = new double[tempJaguars.size()];

        this.temperatureSensors = sensors;
    }

    protected void run() {

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

                    //Cache this temperature
                    jaguarTemperatures[i] = temperature;

                } catch (CANTimeoutException ex) {
                    ex.printStackTrace();
                }
            }

        }

		//Get the temperatures from the drivetrain temperature sensors
		for(int i = 0; i < temperatureSensors.length; i++) {
			TemperatureSensor sensor = temperatureSensors[i];
			//Send data to the dashboard
			SmartDashboard.putNumber(sensor.getName(), sensor.getTemperature());
		}
    }

    public double getJaguarTemperature(int index) {
        return jaguarTemperatures[index];
    }
}
