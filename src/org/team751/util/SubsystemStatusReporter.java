package org.team751.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Sends output to the console and SmartDashboard indicating the status of the
 * CAN bus for various subsystems
 *
 * @author Sam Crow
 */
public class SubsystemStatusReporter {

    /**
     * The name of the subsystem that is assigned to this instance
     */
    private String name;
    
    /**
     * The name of the dashboard data to send.
     * This is set in the constructor to {@link #name} + " status".
     */
    private String dashboardKey;

    /**
     * If the subsystem was functioning when its status was last reported
     */
    private boolean isWorking = true;
    
    /**
     * Constructor
     *
     * @param subsystemName The name of the subsystem that is assigned to this
     * instance
     */
    public SubsystemStatusReporter(String subsystemName) {
        name = subsystemName;
        
        dashboardKey = name + " status";
    }

    /**
     * Indicate that the subsystem has initialized successfully
     */
    public void reportInitSuccessful() {

        System.out.println("Subsystem " + name + " has initialized successfully.");

        SmartDashboard.putBoolean(dashboardKey, true);
    }

    /**
     * Indicate that the subsystem has failed to initialize
     */
    public void reportInitFailed() {
        System.out.println("Subsystem " + name + " failed to initialize!");

        SmartDashboard.putBoolean(dashboardKey, false);
        
        isWorking = false;
    }

    /**
     * Indicate that the subsystem has failed to initialize.
     *
     * @param e An Exception to provide for diagnostics
     */
    public void reportInitFailed(Exception e) {
        String exceptionType = e.getClass().getSimpleName();

        System.out.print("Subsystem " + name + " failed to initialize. It encountered a " + exceptionType + '!');
        
        SmartDashboard.putBoolean(dashboardKey, false);
        
        isWorking = false;
    }
    
    /**
     * Indicate that the system is working normally. 
     * This method can be called repeatedly.
     */
    public void reportWorking() {
        if(isWorking == false) {
            System.out.println("Subsystem "+name+" is now working.");
            SmartDashboard.putBoolean(dashboardKey, true);
            isWorking = true;
        }
    }
    
    /**
     * Indicate that the system is not working
     * @param e An exception to use to provide information on the failure
     */
    public void reportNotWorking(Exception e) {
        if(isWorking == true) {
            
            if(e != null) {
                System.out.println("Subsystem "+name+" encountered a "+e.getClass().getSimpleName()+" and has stopped working!");
            }
            else {
                System.out.println("Subsystem "+name+" has stopped working!");
            }
            
            SmartDashboard.putBoolean(dashboardKey, false);
            
            isWorking = false;
        }
    }
    
    /**
     * Indicate that the system is not working
     */
    public void reportNotWorking() {
        reportNotWorking(null);
    }
    
    /**
     * Determine if the subsystem is working, according to the last time
     * it was reported to the reporter
     * @return 
     */
    public boolean isSubsystemWorking() {
        return isWorking;
    }
}
