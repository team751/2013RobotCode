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
     * The name of the dashboard data to send
     */
    private String dashboardKey;

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

        SmartDashboard.putBoolean(name, true);
    }

    /**
     * Indicate that the subsystem has failed to initialize
     */
    public void reportInitFailed() {
        System.out.println("Subsystem " + name + " failed to initialize!");

        SmartDashboard.putBoolean(name, false);
    }

    /**
     * Indicate that the subsystem has failed to initialize.
     *
     * @param e An Exception to provide for diagnostics
     */
    public void reportInitFailed(Exception e) {
        String exceptionType = e.getClass().getSimpleName();

        System.out.print("Subsystem " + name + " failed to initialize. It encountered a " + exceptionType + '!');
        
        SmartDashboard.putBoolean(name, false);
    }
}
