package org.team751;


import InsightLT.DecimalData;
import InsightLT.InsightLT;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.team751.commands.CommandBase;
import org.team751.commands.ExampleCommand;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot2013 extends IterativeRobot {

    Command autonomousCommand;

    //Test Insight LT
    InsightLT insight = new InsightLT(InsightLT.FOUR_ZONES);
    DecimalData batteryVoltage = new DecimalData("Bat");
    DecimalData jag1 = new DecimalData(" Jag1");
    DecimalData jag2 = new DecimalData(" Jag2");
    DecimalData jag3 = new DecimalData(" Jag3");
    DecimalData jag4 = new DecimalData(" Jag4");
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // instantiate the command used for the autonomous period
        autonomousCommand = new ExampleCommand();

        // Initialize all subsystems
        CommandBase.init();
        
        //Send system.err to the driver station console
        Utility.sendErrorStreamToDriverStation(true);
        
        jag1.setPrecision(1);
        jag2.setPrecision(1);
        jag3.setPrecision(1);
        jag4.setPrecision(1);
        
        insight.registerData(batteryVoltage, 1);
        insight.registerData(jag1, 2);
        insight.registerData(jag2, 2);
        insight.registerData(jag3, 2);
        insight.registerData(jag4, 2);
        insight.startDisplay();
    }

    public void autonomousInit() {
        // schedule the autonomous command (example)
        autonomousCommand.start();
    }

    public void disabledInit() {
        
    }

    public void disabledPeriodic() {
        
        batteryVoltage.setData(DriverStation.getInstance().getBatteryVoltage());
        jag1.setData(CommandBase.driveTrain.getMonitor().getJaguarTemperature(0));
        jag2.setData(CommandBase.driveTrain.getMonitor().getJaguarTemperature(1));
        jag3.setData(CommandBase.driveTrain.getMonitor().getJaguarTemperature(2));
        jag3.setData(CommandBase.driveTrain.getMonitor().getJaguarTemperature(3));
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
	// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
