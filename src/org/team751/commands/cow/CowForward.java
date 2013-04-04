package org.team751.commands.cow;

import edu.wpi.first.wpilibj.DriverStationLCD;
import org.team751.commands.CommandBase;
import org.team751.util.cow.CowPosition;

/**
 * Sets the cow to move one position forward, then exits immediately
 *
 * @author Sam Crow
 */
public class CowForward extends CommandBase {

    public CowForward() {
        requires(cow);
		setInterruptible(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser1, 1, "CowForward starting");
        DriverStationLCD.getInstance().updateLCD();
        
        CowPosition newPosition = cow.getTargetPosition().nextForward();
        if (newPosition != null) {
            cow.setTargetPosition(newPosition);
            System.out.println("Setting cow to position " + newPosition.toString());
        } else {
            System.out.println("No forward position to move cow to");
        }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//        cow.updateMotion();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return cow.isInPosition();
    }

    // Called once after isFinished returns true
    protected void end() {
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser1, 1, "CowForward finished");
        DriverStationLCD.getInstance().updateLCD();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
