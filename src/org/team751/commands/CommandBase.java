package org.team751.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team751.OI;
import org.team751.subsystems.Drivetrain;
import org.team751.subsystems.ExampleSubsystem;
import org.team751.util.Navigator;
import org.team751.util.OnBoardDiagnostics;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 * @author Author
 */
public abstract class CommandBase extends Command {

    public static OI oi;
    // Create a single static instance of all of your subsystems
    public static ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
    public static Drivetrain driveTrain = new Drivetrain();
    
    //Periodic tasks here (these are not subsystems)
    public static Navigator navigator = new Navigator();
    public static OnBoardDiagnostics obd = new OnBoardDiagnostics();
    
    public static void init() {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();

        //Start the periodic tasks
        navigator.start();
        obd.start();
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
