package org.team751;

import com.sun.squawk.Field;
import com.sun.squawk.Klass;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team751.commands.CommandBase;
import org.team751.commands.autonomous.*;
import org.team751.util.DashboardInterface;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot2013 extends IterativeRobot {
    
    /**
     * The autonomous command
     */
    private Command autonomous;
    
    private SendableChooser autonomousChooser = new SendableChooser();

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

        printRevision();
        // Initialize all subsystems
        CommandBase.init();
        
        //Put the cow in coast mode, for easy disk loading
        CommandBase.cow.setCoastMode();
        
        autonomousChooser.addObject("3-disk", new ThreeDiskAutonomous());
        autonomousChooser.addDefault("2-disk", new TwoDiskAutonomous());
		autonomousChooser.addObject("Drive+3 from left", new DriveFromLeftShoot3Autonomous());
		autonomousChooser.addObject("Drive+3 from right", new DriveFromRightShoot3Autonomous());
		autonomousChooser.addObject("Do nothing", new DoNothingAutonomous());
        SmartDashboard.putData("Autonomous", autonomousChooser);
        
		//Set up a checkbox for gyro init
		SmartDashboard.putBoolean("Do gyro init", false);
		
        System.out.println("Ready");
    }

    public void autonomousInit() {
        System.out.print("About to start autonomous mode... ");
		autonomousPeriodic();
        //Set the cow to brake mode, for normal operation
        CommandBase.cow.setBrakeMode();
        
        autonomous = (Command) autonomousChooser.getSelected();
		
		//Backup: If the chooser isn't there, choose one
		if(autonomous == null) {
			System.out.println("Using standby autonomous mode");
			autonomous = new TwoDiskAutonomous();
		}
		
        autonomous.start();
		autonomousPeriodic();
        System.out.println("Command started");
		System.out.println("Autonomous command is "+autonomous.getClass().getName());
    }

    public void disabledInit() {
        CommandBase.cow.setCoastMode();
    }

    public void disabledPeriodic() {
        DashboardInterface.update();
		
		if(SmartDashboard.getBoolean("Do gyro init", false)) {
			CommandBase.navigator.initializeGyro();
			SmartDashboard.putBoolean("Do gyro init", true);
		}
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        DashboardInterface.update();
    }

    public void teleopInit() {
        CommandBase.cow.setBrakeMode();
        if(autonomous != null) {
            autonomous.cancel();
        }
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
        CommandBase.checkSubsystems();
        DashboardInterface.update();
        
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }

    public void printRevision() {
        try {
            //Try to load the class
            Klass revClass = Klass.asKlass(Class.forName("org.team751.Revision"));

            String project = "";
            String branch = "";
            String revision = "";

            for (int i = 0, max = revClass.getFieldCount(true); i < max; i++) {
                Field field = revClass.getField(i, true);

                if (field.getName().equals("revision")) {
                    revision = field.getStringConstantValue();
                }
                if (field.getName().equals("branch")) {
                    branch = field.getStringConstantValue();
                }
                if (field.getName().equals("project")) {
                    project = field.getStringConstantValue();
                }
            }

            System.out.println("Running "+project+", compiled from revision "+revision+" on branch "+branch);

        } catch (ClassNotFoundException ex) {
            System.err.println("Revision information not found.");
        }
    }
}
