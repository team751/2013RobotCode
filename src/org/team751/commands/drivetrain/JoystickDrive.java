/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.commands.drivetrain;

import org.team751.commands.CommandBase;

/**
 *
 * @author sambaumgarten
 */
public class JoystickDrive extends CommandBase {

    public JoystickDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double x = oi.driveStick.getX();
        double y = oi.driveStick.getY();

        //Scale down the rotation
        x *= 0.7;

        //Square the values to make things smoother
        double xSquared = x * x;
        if (x < 0) {
            xSquared = -xSquared;
        }
        double ySquared = y * y;
        if (y < 0) {
            ySquared = -ySquared;
        }

        //For the joystick Y axis, forward is negative.
        //This changes it back so that forward is positive.
        ySquared = -ySquared;

        driveTrain.arcadeDrive(ySquared, xSquared);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        driveTrain.arcadeDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
