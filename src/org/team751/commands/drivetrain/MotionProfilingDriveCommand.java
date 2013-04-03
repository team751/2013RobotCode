package org.team751.commands.drivetrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team751.PIDConstants;
import org.team751.cheesy.util.AccelFilterBase;
import org.team751.cheesy.util.ContinuousAccelFilter;
import org.team751.commands.CommandBase;

/**
 * A command that calculates motion profiling and drives the robot to follow the
 * path that it calculates over time. This command can be used for both moving
 * and rotation. Subclasses can specialize to do either movement or rotation
 * alone.
 *
 * @author Sam Crow
 */
public class MotionProfilingDriveCommand extends CommandBase {

    /**
     * The maximum forward velocity of the robot, in meters/second
     */
    private static final double kMoveMaxVelocity = 4.3;
    /**
     * The maximum forward acceleration of the robot, in meters/second^2
     */
    private static final double kMoveMaxAcceleration = 4;
    /**
     * The maximum angular velocity of the robot, in degrees/second
     */
    private static final double kRotateMaxVelocity = 200;
    /**
     * The maximum angular acceleration of the robot, in degrees/second^2
     */
    private static final double kRotateMaxAcceleration = 400;
    /**
     * Calculates profiles for movement
     */
    private AccelFilterBase moveProfile;
    /**
     * Calculates profiles for rotation
     */
    private AccelFilterBase rotateProfile;
    /**
     * Controls movement
     */
    private PIDController moveController;
    /**
     * Controls rotation
     */
    private PIDController rotateController;
    /**
     * The move command, set by a PID controller and given to the drivetrain's
     * arcade drive method
     */
    private double moveValue;
    /**
     * The rotate command, set by a PID controller and given to the drivetrain's
     * arcade drive method
     */
    private double rotateValue;

    /**
     * Constructor
     *
     * @param moveMeters The distance, in meters, to move. Positive is forward.
     * @param turnDegrees The angle, in degrees, to turn. Positive is right.
     */
    public MotionProfilingDriveCommand(double moveMeters, double turnDegrees) {

        moveProfile = new ContinuousAccelFilter();
        rotateProfile = new ContinuousAccelFilter();

        moveController = new PIDController(PIDConstants.DRIVE_MOVE_P,
                PIDConstants.DRIVE_MOVE_I,
                PIDConstants.DRIVE_MOVE_D,
                navigator.movementPidSource, moveOutput);

        //Set the move controller to consider itself on-target if within ±10cm
        //of the goal
        moveController.setAbsoluteTolerance(0.1);

        moveController.setSetpoint(navigator.getEncoderDistance() + moveMeters);

        rotateController = new PIDController(PIDConstants.DRIVE_ROTATE_P,
                PIDConstants.DRIVE_ROTATE_I,
                PIDConstants.DRIVE_ROTATE_D,
                navigator.headingPidSource, rotateOutput);

        //Set the rotate controller to consider itself on-target if within ±10 degrees
        //of the goal
        rotateController.setAbsoluteTolerance(10);

        rotateController.setSetpoint(navigator.getHeading() + turnDegrees);

    }

    protected void initialize() {
        SmartDashboard.putData("Move controller", moveController);
        SmartDashboard.putData("Rotate controller", rotateController);

        moveController.enable();
        rotateController.enable();
    }

    protected void execute() {
        //unfinished
    }

    protected boolean isFinished() {
        //unfinished
        return true;
    }

    protected void end() {
        
    }

    protected void interrupted() {
        
    }
    /**
     * An output that sets the moveValue field of this class. Later, when
     * execute() is called, the drivetrain is given this value.
     */
    private final PIDOutput moveOutput = new PIDOutput() {
        public void pidWrite(double output) {
            moveValue = output;
        }
    };
    /**
     * An output that sets the rotateValue field of this class. Later, when
     * execute() is called, the drivetrain is given this value.
     */
    private final PIDOutput rotateOutput = new PIDOutput() {
        public void pidWrite(double output) {
            rotateValue = output;
        }
    };
}
