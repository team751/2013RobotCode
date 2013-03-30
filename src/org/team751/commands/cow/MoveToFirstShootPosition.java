package org.team751.commands.cow;

import edu.wpi.first.wpilibj.DriverStationLCD;
import org.team751.commands.CommandBase;
import org.team751.util.cow.CowPosition;

/**
 * Moves the robot to the first and furthest back shooting position, shoot 3
 * @author Sam Crow
 */
public class MoveToFirstShootPosition extends CommandBase {
    
    public MoveToFirstShootPosition() {
        requires(cow);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser1, 1, "MoveToFirstShootPosition starting");
        DriverStationLCD.getInstance().updateLCD();
        cow.setTargetPosition(CowPosition.kShoot3);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return cow.isInPosition();
    }

    // Called once after isFinished returns true
    protected void end() {
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser1, 1, "MoveToFirstShootPosition finished");
        DriverStationLCD.getInstance().updateLCD();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
