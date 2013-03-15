package org.team751.tasks;

import edu.wpi.first.wpilibj.SpeedController;
import org.team751.util.SpeedSource;

/**
 * Implements a take-back-half speed control system.
 * 
 * For information, see: http://www.chiefdelphi.com/forums/showthread.php?threadid=105965
 * http://www.chiefdelphi.com/media/papers/2674?
 * 
 * @author Sam Crow
 */
public class TakeBackHalfSpeedController extends PeriodicTask implements ClosedLoopSpeedController {
    
    /**
     * The source used to get the current speed
     */
    private SpeedSource source;
    
    /**
     * The speed controller used to send output
     */
    private SpeedController controller;
    
    /**
     * The target speed, in RPM
     */
    private volatile double targetRpm = 0;
    /**
     * If the controller is enabled
     */
    private volatile boolean enabled = false;
    
    //Control data
    
    /**
     * Gain (G)
     */
    private static final double gain = 0.1;
    
    /**
     * The error encountered during the last loop
     */
    private double lastError = 10;
    
    /**
     * The take-back-half variable (b)
     */
    private double tbh = 0;
    
    /**
     * Constructor
     * @param source The source to use to get the speed
     * @param controller The speed controller to control 
     */
    public TakeBackHalfSpeedController(SpeedSource source, SpeedController controller) {
        this.source = source;
        this.controller = controller;
        
        setTaskTime(0.1);
    }

    protected void run() {
        
        if(enabled) {
            double error = targetRpm - getActualRpm();
            
            double motorPower = controller.get();
            motorPower += gain * error;
            motorPower = clamp(motorPower);
            
            //If the error has changed in sign since the last processing
            if(isPositive(lastError) != isPositive(error)) {
                motorPower = 0.5 * (motorPower + tbh);
                tbh = motorPower;
                
                lastError = error;
            }
            
            controller.set(motorPower);
        }
        
    }

    public void disable() {
        enabled = false;
        controller.set(0);
    }

    public void enable() {
        enabled = true;
    }

    public double getActualRpm() {
        return source.getRpm();
    }

    public boolean isOnTarget() {
        double difference = getActualRpm() - targetRpm;
        
        return Math.abs(difference) < 100;
    }

    public void setTargetRpm(double rpm) {
        targetRpm = rpm;
    }
    
    /**
     * Clamp a value to within +/- 1, for use with speed controllers
     * @param input The value to clamp
     * @return the clamped value
     */
    private static double clamp(double input) {
        if(input > 1) {
            return 1;
        }
        if(input < -1) {
            return -1;
        }
        return 0;
    }
    
    /**
     * Determine if a value is positive
     * @param input
     * @return 
     */
    private static boolean isPositive(double input) {
        return input > 0;
    }
}
