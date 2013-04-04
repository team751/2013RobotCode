package org.team751;

import com.sun.squawk.Field;
import com.sun.squawk.Klass;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team751.commands.CommandBase;
import org.team751.util.DashboardInterface;
import org.team751.util.cow.CowPosition;

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
	
	private AutonomousStateMachine asm = new AutonomousStateMachine();

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
        
		//Set up a checkbox for gyro init
		SmartDashboard.putBoolean("Do gyro init", false);
		
        System.out.println("Ready");
    }

    public void autonomousInit() {
//        System.out.print("About to start autonomous mode... ");
//		autonomousPeriodic();
//        //Set the cow to brake mode, for normal operation
//        CommandBase.cow.setBrakeMode();
//        
//		autonomous = new ThreeDiskAutonomous();
//		
//        autonomous.start();
//		autonomousPeriodic();
//        System.out.println("Command started");
//		System.out.println("Autonomous command is "+autonomous.getClass().getName());
		
		asm = new AutonomousStateMachine();
		
    }

    public void disabledInit() {
        CommandBase.cow.setCoastMode();
    }

    public void disabledPeriodic() {
        DashboardInterface.update();
		
		if(SmartDashboard.getBoolean("Do gyro init", false)) {
			CommandBase.navigator.initializeGyro();
			SmartDashboard.putBoolean("Do gyro init", false);
		}
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
//        Scheduler.getInstance().run();
		//Run the autonomous state machine
		asm.run();
    }

    public void teleopInit() {
        CommandBase.cow.setBrakeMode();
		CommandBase.shooterWheels.disable();
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
	
	/**
	 * Runs a super-simple 3-disk autonomous
	 * CAUTION: This may get stuck in a while loop and prevent the robot from running
	 * in teleop!
	 */
	private void runSuperSimpleAutonomous() {
		
		//Set shooter wheels to 80% power
		CommandBase.shooterWheels.setSpeed(0.75);
		
		//Turn on shooter wheels
		CommandBase.shooterWheels.enable();
		//Begin zeroing
		//Move cow forward until zeroed
		while(!CommandBase.cow.isAtZero()) {
			CommandBase.cow.moveExtraSlowForward();
			debugShooterSpeed();
		}
		CommandBase.cow.manualStop();
		//Move cow back for 1/2 second
		Timer timer = new Timer();
		timer.start();
		while(timer.get() < 0.5) {
			CommandBase.cow.moveSlowBack();
			debugShooterSpeed();
		}
		CommandBase.cow.manualStop();
		//Move cow forward until zeroed
		while(!CommandBase.cow.isAtZero()) {
			CommandBase.cow.moveExtraSlowForward();
			debugShooterSpeed();
		}
		
		CommandBase.cow.manualStop();
		CommandBase.cow.setThisAsZero();
		//End zeroing
		//Begin first shot
		CommandBase.cow.setTargetPosition(CowPosition.kShoot3);
		debugShooterSpeed();
		Timer.delay(0.1);
		debugShooterSpeed();
		
		System.out.print("Pushing...");
		while(!CommandBase.pusher.isExtended()) {
			CommandBase.pusher.push();
			Timer.delay(0.001);
		}
		System.out.print(" extended...");
		while(!CommandBase.pusher.isRetracted()) {
			CommandBase.pusher.retract();
			Timer.delay(0.001);
		}
		System.out.println(" done.");
		
		debugShooterSpeed();
		Timer.delay(1);
		debugShooterSpeed();
		//End first shot
		//Begin second shot
		CommandBase.cow.setTargetPosition(CowPosition.kShoot2);
		while(!CommandBase.cow.isInPosition()) {
			debugShooterSpeed();
			Timer.delay(0.01);
		}
		
		Timer.delay(1);
		debugShooterSpeed();
		
		System.out.print("Pushing...");
		while(!CommandBase.pusher.isExtended()) {
			CommandBase.pusher.push();
			Timer.delay(0.001);
		}
		System.out.print(" extended...");
		while(!CommandBase.pusher.isRetracted()) {
			CommandBase.pusher.retract();
			Timer.delay(0.001);
		}
		System.out.println(" done.");
		
		debugShooterSpeed();
		//End second shot
		//Begin third shot
		CommandBase.cow.setTargetPosition(CowPosition.kShoot1);
		while(!CommandBase.cow.isInPosition()) {
			debugShooterSpeed();
			Timer.delay(0.01);
		}
		
		Timer.delay(1);
		debugShooterSpeed();
		
		System.out.print("Pushing...");
		while(!CommandBase.pusher.isExtended()) {
			CommandBase.pusher.push();
			Timer.delay(0.001);
		}
		System.out.print(" extended...");
		while(!CommandBase.pusher.isRetracted()) {
			CommandBase.pusher.retract();
			Timer.delay(0.001);
		}
		System.out.println(" done.");
		
		debugShooterSpeed();
		//End third shot
		
		Timer.delay(1);
		
		
		CommandBase.shooterWheels.disable();
		CommandBase.pusher.retract();
	}
	
	private void debugShooterSpeed() {
		SmartDashboard.putNumber("Shooter 1 actual RPM",
								 CommandBase.shooterWheels.getFirstRpm());
		SmartDashboard.putNumber("Shooter 2 actual RPM",
								 CommandBase.shooterWheels.getSecondRpm());
	}
}
