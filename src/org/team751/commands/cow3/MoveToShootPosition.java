package org.team751.commands.cow3;

import org.team751.commands.CommandBase;
import org.team751.util.cow.CowPosition;

/**
 * Moves the cow to the nearest shooting position. Finishes when the cow is in
 * position.
 * @author Sam Crow
 */
public class MoveToShootPosition extends CommandBase {
    
    public MoveToShootPosition() {
        requires(cow);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        
        //Check that the current target stomach is not full
        //If it is, moving forward will cause a collision.
        CowPosition currentPosition = cow.getTargetPosition();
        if(currentPosition == CowPosition.kShoot3 && cow.getStomachs().stomach3Full()) {
            
            return;
        }
        if(currentPosition == CowPosition.kShoot2 && cow.getStomachs().stomach2Full()) {
            
            return;
        }
        if(currentPosition == CowPosition.kShoot2 && cow.getStomachs().stomach2Full()) {
            
            return;
        }
        if(currentPosition == CowPosition.kShoot1 && cow.getStomachs().stomach1Full()) {
            
            return;
        }
        
        
        //Choose which position to move to
//        if(cow.getStomachs().stomach3Full()) {
//            cow.setTargetPosition(CowPosition.kShoot3);
//            return;
//        }
//        if(cow.getStomachs().stomach2Full()) {
//            cow.setTargetPosition(CowPosition.kShoot2);
//            return;
//        }
//        if(cow.getStomachs().stomach1Full()) {
//            cow.setTargetPosition(CowPosition.kShoot1);
//            return;
//        }
//        if(cow.getStomachs().stomach0Full()) {
//            cow.setTargetPosition(CowPosition.kShoot0);
//            return;
//        }
        //Just move forward
        CowPosition nextForward = cow.getTargetPosition().nextForward();
        if(nextForward != null) {
            cow.setTargetPosition(nextForward);
        }
        //Otherwise do nothing
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        cow.debugPosition();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return cow.isInPosition();
    }

    // Called once after isFinished returns true
    protected void end() {
        System.out.println("Cow move finished");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
