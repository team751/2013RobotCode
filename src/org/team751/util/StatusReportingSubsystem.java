package org.team751.util;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A type of subsystem that keeps track of its status and reports it to the
 * console and dashboard.
 *
 * @author Sam Crow
 */
public abstract class StatusReportingSubsystem extends Subsystem {

    /**
     * If this subsystem is currently working
     */
    private boolean isWorking = true;

    /**
     * Indicate that the subsystem has initialized successfully
     */
    public void reportInitSuccessful() {

        System.out.println("Subsystem " + getName() + " has initialized successfully.");

        SmartDashboard.putBoolean(getName(), true);
    }

    /**
     * Indicate that the subsystem has failed to initialize
     */
    public void reportInitFailed() {
        System.out.println("Subsystem " + getName() + " failed to initialize!");

        SmartDashboard.putBoolean(getName(), false);

        isWorking = false;
    }

    /**
     * Indicate that the subsystem has failed to initialize.
     *
     * @param e An Exception to provide for diagnostics
     */
    public void reportInitFailed(Exception e) {
        String exceptionType = e.getClass().getName();

        System.out.print("Subsystem " + getName() + " failed to initialize. It encountered a " + exceptionType + '!');

        SmartDashboard.putBoolean(getName(), false);

        isWorking = false;
    }

    /**
     * Indicate that the system is working normally. This method can be called
     * repeatedly.
     */
    public void reportWorking() {
        if (isWorking == false) {
            System.out.println("Subsystem " + getName() + " is now working.");
            SmartDashboard.putBoolean(getName(), true);
            isWorking = true;
        }
    }

    /**
     * Indicate that the system is not working
     *
     * @param e An exception to use to provide information on the failure
     */
    public void reportNotWorking(Exception e) {
        if (isWorking == true) {

            if (e != null) {
                System.out.println("Subsystem " + getName() + " encountered a " + e.getClass().getName() + " and has stopped working!");
            } else {
                System.out.println("Subsystem " + getName() + " has stopped working!");
            }

            SmartDashboard.putBoolean(getName(), false);

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
     * Determine if the subsystem is working, according to the last time it was
     * reported to the reporter
     *
     * @return
     */
    public boolean isSubsystemWorking() {
        return isWorking;
    }
}
