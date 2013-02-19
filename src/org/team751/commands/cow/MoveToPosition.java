package org.team751.commands.cow;

import org.team751.commands.CommandBase;
import org.team751.subsystems.Cow;

/**
 * Moves the Cow to a set position. Finishes when the cow has reached
 * that position.
 * @author Sam Crow
 */
public class MoveToPosition extends CommandBase {

    /**
     * The position that is targeted
     */
    private Cow.Position targetPosition;
    
    /**
     * Constructor
     * @param position The position to move to
     */
    public MoveToPosition(Cow.Position position) {
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
