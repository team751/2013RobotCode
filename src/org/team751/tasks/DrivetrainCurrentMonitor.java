<<<<<<< HEAD:src/org/team751/util/DrivetrainCurrentMonitor.java
package org.team751.util;
=======
package org.team751.tasks;
>>>>>>> summer-robot:src/org/team751/tasks/DrivetrainCurrentMonitor.java

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import java.util.Timer;
import java.util.TimerTask;
<<<<<<< HEAD:src/org/team751/util/DrivetrainCurrentMonitor.java
=======
import org.team751.util.FixedCapacityDoubleQueue;
>>>>>>> summer-robot:src/org/team751/tasks/DrivetrainCurrentMonitor.java

/**
 * Monitors the current consumed by drivetrain motors.
 * @author Sam Crow
 */
public class DrivetrainCurrentMonitor {
    
    /**
     * The time, in seconds, to delay between occasions of current monitoring
     */
    public static final double DELAY_TIME = 0.5;
    
    /**
     * The jaguars that are being monitored
     */
    private CANJaguar[] jaguars;
    
    /**
     * The set of current values over time
     */
    /*
     * Because this is accessed both from the main robot thread and from the timer
     * thread, it must always be accessed within a synchronized(currentValues){} block.
     */
    private final FixedCapacityDoubleQueue currentValues = new FixedCapacityDoubleQueue(10);
    
    private TimerTask task = new TimerTask() {
        public void run() {
            
            double totalCurrent = 0;
            
            for(int i = 0; i < jaguars.length; i++) {
                try {
                    totalCurrent += jaguars[i].getOutputCurrent();
                } catch (CANTimeoutException ex) {
                    ex.printStackTrace();
                }
            }
            
            synchronized(currentValues) {
                currentValues.enqueue(totalCurrent);
            }
        } 
    };
    
    /**
     * The timer used to schedule current monitoring
     */
    private Timer timer = new Timer();
    
    /**
     * Start monitoring current
     */
    public final void start() {
        //Schedule the task to be run every 500 ms
        timer.scheduleAtFixedRate(task, 0, MathUtils.round(DELAY_TIME * 1000));
    }
    
    /**
     * Get the current over time consumed by the motors (combined), in
     * watt-seconds
     * @return the current over time
     */
    public double getCurrentOverTime() {
        double value;
        synchronized(currentValues) {
            value = currentValues.trapezoidalIntegral(DELAY_TIME);
        }
        return value;
    }
}
