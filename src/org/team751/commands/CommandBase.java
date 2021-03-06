package org.team751.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team751.OI;
import org.team751.subsystems.*;
import org.team751.tasks.Navigator;
import org.team751.util.StatusReportingSubsystem;
import org.team751.util.SubsystemStatusException;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use
 * CommandBase.exampleSubsystem
 *
 * @author Team 751
 */
public abstract class CommandBase extends Command {

    public static OI oi;
    // Create a single static instance of all of your subsystems
    
    /**
     * The drivetrain
     */
    public static final Drivetrain driveTrain = new Drivetrain();
    /**
     * The cow, which stores and indexes disks
     */
    public static final Cow cow = new Cow();
    /**
     * The pusher, which pushes disks from the cow into the shooter
     */
    public static final Pusher pusher = new Pusher();
    /**
     * The shooter wheels, including speed control
     */
    public static final ShooterWheels shooterWheels = new ShooterWheels();
    /**
     * The Cerberus (disk gates) for the front of the robot
     */
    public static final CerberusFront cerberusFront = new CerberusFront();
    /**
     * The Cerberus (disk gates) for the back of the robot
     */
    public static final CerberusBack cerberusBack = new CerberusBack();
    
    
    
    //Periodic tasks here (these are not subsystems)
    public static Navigator navigator = new Navigator();
    //On-board diagnostics are currently disabled - see issue #5
//    public static OnBoardDiagnostics obd = new OnBoardDiagnostics();
    
    /**
     * All the subsystems that report their status and can be checked
     */
    private static final StatusReportingSubsystem[] allSubsystems = {
        driveTrain,
        cow,
        pusher,
    };
    
    /**
     * The timestamp, in milliseconds, when the subsystems were last checked
     * for functioning
     */
    private static long lastCheckTime = -1;

    public static void init() {

        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();

        //Send command data to SmartDashboard
        SmartDashboard.putData(driveTrain);
        SmartDashboard.putData(cow);
        SmartDashboard.putData(pusher);
        SmartDashboard.putData(shooterWheels);
        
        //Get the cow to do the initialization elements that require
        //the cow to be not null
        cow.init();
        navigator.start();
    }
    
    /**
     * Access each subsystem and determine if it is working. If it is not working,
     * tell it to try to recover.
     * 
     * This will only have an effect periodically.
     */
    public static void checkSubsystems() {
        //Initializing case: first call. Don't do the check.
        if(lastCheckTime == -1) {
            lastCheckTime = System.currentTimeMillis();
        }
        else {
            //Determine how long it has been
            long timeSince = System.currentTimeMillis() - lastCheckTime;
            
            if(timeSince > 10000) {
                //actually do the checks
                doSubsystemChecks();
                
                lastCheckTime = System.currentTimeMillis();
            }
        }
    }
    
    /**
     * Check all the subsystems and tell any non-functioning ones to try
     * to recover.
     * This is called by {@link #checkSubsystems()}.
     */
    private static void doSubsystemChecks() {
        for(int i = 0; i < allSubsystems.length; i++) {
            
            StatusReportingSubsystem subsystem = allSubsystems[i];
            
            if( ! subsystem.isSubsystemWorking()) {
                try {
                    subsystem.retry();
                    
                    System.out.println("Subsystem "+subsystem.getName()+" recovered successfully.");
                } catch (SubsystemStatusException ex) {
                    System.out.println("Subsytem "+subsystem.getName()+" failed to recover!");
                }
            }
        }
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
