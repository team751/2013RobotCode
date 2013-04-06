package org.team751.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team751.commands.CommandBase;

/**
 * Manages information and sends it to the dashboard
 * @author Sam Crow
 */
public class DashboardInterface {
    
    /**
     * Gets data and sends it to the dashboard.
     * 
     * 
     * The following information is sent:
     * Shooter:
     *      Target and actual RPM for each wheel
     *      Target speed ratio
     * 
     * Cow:
     *      Stomach sensor status
     *      Zero sensor status
     *      Target position name and position
     *      Actual position
     * 
     * Cerberus/Gates:
     *      Front and back statuses
     * 
     * Drivetrain data is handled separately by the Drivetrain class.
     * 
     */
    public static void update() {
        
        //Shooter section
        SmartDashboard.putNumber("Shooter 1 target RPM", CommandBase.shooterWheels.getFirstTargetRpm());
        SmartDashboard.putNumber("Shooter 1 actual RPM", CommandBase.shooterWheels.getFirstRpm());
        
        SmartDashboard.putNumber("Shooter 2 target RPM", CommandBase.shooterWheels.getSecondTargetRpm());
        SmartDashboard.putNumber("Shooter 2 actual RPM", CommandBase.shooterWheels.getSecondRpm());
        
        SmartDashboard.putNumber("Shooter speed ratio", CommandBase.shooterWheels.getTargetSpeedRatio());
        
        //Cow section
        SmartDashboard.putBoolean("Stomach 0", CommandBase.cow.getStomachs().stomach0Full());
        SmartDashboard.putBoolean("Stomach 1", CommandBase.cow.getStomachs().stomach1Full());
        SmartDashboard.putBoolean("Stomach 2", CommandBase.cow.getStomachs().stomach2Full());
        SmartDashboard.putBoolean("Stomach 3", CommandBase.cow.getStomachs().stomach3Full());
        
        SmartDashboard.putBoolean("Cow zero", CommandBase.cow.isAtZero());
        
        SmartDashboard.putString("Cow target", CommandBase.cow.getTargetPosition().toString());
//        SmartDashboard.putNumber("Cow target position", CommandBase.cow.getTargetCount());
//        SmartDashboard.putNumber("Cow position", CommandBase.cow.getActualCount());
        
        //Gates/Cerberus section
        SmartDashboard.putBoolean("Cerberus front", CommandBase.cerberusFront.isExtended());
        SmartDashboard.putBoolean("Cerberus back", CommandBase.cerberusBack.isExtended());
    }
    
    private DashboardInterface() {}
}
