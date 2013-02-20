package org.team751.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team751.OI;
import org.team751.subsystems.*;
import org.team751.tasks.Navigator;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 * @author Author
 */
public abstract class CommandBase extends Command {

    public static OI oi;
    // Create a single static instance of all of your subsystems
    public static Drivetrain driveTrain = new Drivetrain();
    
//    public static Cow cow = new Cow();
	
	public static SimpleCow simpleCow = new SimpleCow();
	
	public static Cow3 cow3 = new Cow3();
	
	public static Pusher pusher = new Pusher();
	
	public static ShooterWheels shooterWheels = new ShooterWheels();
    
    //Periodic tasks here (these are not subsystems)
    public static Navigator navigator;
	//On-board diagnostics are currently disabled - see issue #5
//    public static OnBoardDiagnostics obd = new OnBoardDiagnostics();
    
    public static void init() {
        
        System.out.println("CommandBase.init() called");
        
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        System.out.println("About to init OI");
        oi = new OI();

        //Start the periodic tasks
        //navigator.start();
//        obd.start();
		
		//Send command data to SmartDashboard
		SmartDashboard.putData(driveTrain);
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
