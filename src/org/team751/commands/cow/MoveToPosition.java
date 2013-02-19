package org.team751.commands.cow;

import org.team751.commands.CommandBase;
import org.team751.subsystems.Cow;
import org.team751.util.cow.CowPosition;

/**
 * Moves the Cow to a set position. Finishes when the cow has reached
 * that position.
 * @author Sam Crow
 */
public class MoveToPosition extends CommandBase {

    /**
     * The position that is targeted
     */
    private CowPosition targetPosition;
    
    /**
     * Constructor
     * @param position The position to move to
     */
    public MoveToPosition(CowPosition position) {
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
