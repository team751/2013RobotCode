package org.team751.commands.cow;

import org.team751.commands.CommandBase;
import org.team751.subsystems.Cow2;

/**
 * Moves the Cow to a set position. Finishes when the cow has reached
 * that position.
 * @author Sam Crow
 */
public class MoveToPosition extends CommandBase {

    /**
     * The position that is targeted
     */
    private Cow2.Position targetPosition;
    
    /**
     * Constructor
     * @param position The position to move to
     */
    public MoveToPosition(Cow2.Position position) {
        targetPosition = position;
        
        requires(cow);
    }
    
    protected void initialize() {
        cow.setTargetPosition(targetPosition);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        
        return cow.isInPosition();
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
