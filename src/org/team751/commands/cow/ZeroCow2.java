package org.team751.commands.cow;

import edu.wpi.first.wpilibj.DriverStationLCD;
import org.team751.commands.CommandBase;

/**
 * Moves the cow back for 1 second, in preparation for zero step 3
 * @author Sam Crow
 */
public class ZeroCow2 extends CommandBase {
    
    public ZeroCow2() {
        requires(cow);
        setTimeout(0.5);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser1, 1, "ZeroCow2 starting");
        DriverStationLCD.getInstance().updateLCD();
        cow.moveSlowBack();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
        cow.manualStop();
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser1, 1, "ZeroCow2 finished");
        DriverStationLCD.getInstance().updateLCD();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        cow.manualStop();
    }
}
