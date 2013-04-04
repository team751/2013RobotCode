package org.team751.commands.cow;

import edu.wpi.first.wpilibj.DriverStationLCD;
import org.team751.commands.CommandBase;

/**
 * Moves the cow to zero, then sets that position as zero and exits.
 * Uses extra-low power for autonomous mode.
 *
 * @author Sam Crow
 */
public class ZeroCow1Autonomous extends CommandBase {

    public ZeroCow1Autonomous() {
        requires(cow);
		setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser1, 1, "ZeroCow1Autonomous starting");
        DriverStationLCD.getInstance().updateLCD();
        cow.moveExtraSlowForward();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return cow.isAtZero();
    }

    // Called once after isFinished returns true
    protected void end() {
        cow.manualStop();
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser1, 1, "ZeroCow1Autonomous finished");
        DriverStationLCD.getInstance().updateLCD();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        cow.manualStop();
    }
}
