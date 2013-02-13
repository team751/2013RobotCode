package org.team751;


import com.sun.squawk.Klass;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import com.sun.squawk.Field;
import org.team751.commands.CommandBase;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot2013 extends IterativeRobot {
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

		printRevision();
		
        // Initialize all subsystems
        CommandBase.init();
        
        //Send system.err to the driver station console
        Utility.sendErrorStreamToDriverStation(true);
    }

    public void autonomousInit() {
    }

    public void disabledInit() {
        
    }

    public void disabledPeriodic() {
        
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
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
	
	public void printRevision() {
		try {
			//Try to load the class
			Klass revClass = Klass.asKlass(Class.forName("org.team751.Revision"));
			
			String branch = "";
			String revision = "";
			
			for(int i = 0, max = revClass.getFieldCount(true); i < max; i++) {
				Field field = revClass.getField(i, true);
				
				if(field.getName().equals("revisionString")) {
					revision = field.getStringConstantValue();
				}
				if(field.getName().equals("branchName")) {
					branch = field.getStringConstantValue();
				}
			}
			
			System.out.println("Code compiled from branch "+branch+", revision "+revision);
			
		} catch (ClassNotFoundException ex) {
			System.err.println("Revision information not found.");
		}
	}
}
