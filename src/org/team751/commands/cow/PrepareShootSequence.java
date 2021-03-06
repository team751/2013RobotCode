package org.team751.commands.cow;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Moves the cow to the furthest back shoot position, <!-- and then zeroes it -->
 * @author Sam Crow
 */
public class PrepareShootSequence extends CommandGroup {
    
    public PrepareShootSequence() {
        addSequential(new MoveToFirstShootPosition());
		//Zeroing currently disabled. It may not work as specified in a match
		//when the robot is accelerating.
//        addSequential(new ZeroCow());
    }
}
