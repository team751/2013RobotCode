package org.team751;

import com.sun.squawk.Field;
import com.sun.squawk.Klass;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.team751.commands.Autonomous;
import org.team751.commands.CommandBase;
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
    private Command autonomous = new Autonomous();

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
        
        System.out.println("Ready");
    }

    public void autonomousInit() {
        System.out.print("About to start autonomous mode... ");
        //Set the cow to brake mode, for normal operation
        CommandBase.cow.setBrakeMode();
        
        autonomous = new Autonomous();
        autonomous.start();
        System.out.println("Command started");
    }

    public void disabledInit() {
        CommandBase.cow.setCoastMode();
    }

    public void disabledPeriodic() {
        DashboardInterface.update();
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
        autonomous.cancel();
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
