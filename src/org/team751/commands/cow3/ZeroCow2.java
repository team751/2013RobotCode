package org.team751.commands.cow3;

import edu.wpi.first.wpilibj.Timer;
import org.team751.commands.CommandBase;

/**
 * Moves the cow back for 1 second, in preparation for zero step 3
 * @author Sam Crow
 */
public class ZeroCow2 extends CommandBase {
    
    private Timer timer = new Timer();
    
    public ZeroCow2() {
        requires(cow);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        timer.reset();
        timer.start();
        cow.moveSlowBack();
        System.out.println("Cow zero step 2 starting");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        boolean timedOut = timer.get() > 3;
        if(timedOut) {
            System.out.println("Zero cow step 2 has timed out");
        }
        return timedOut;
    }

    // Called once after isFinished returns true
    protected void end() {
        cow.manualStop();
        System.out.println("Cow zero step 3 finished");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        cow.manualStop();
    }
}
