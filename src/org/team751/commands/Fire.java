package org.team751.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team751.commands.cow.WaitForCowInPosition;
import org.team751.commands.pusher.ExtendPusher;
import org.team751.commands.pusher.RetractPusher;

/**
 * Fires a disk
 * @author Sam Crow
 */
public class Fire extends CommandGroup {
    
	public Fire() {
		//Wait for the cow to reach its set position, then disable it
		addSequential(new WaitForCowInPosition());
		//Extend the pusher
		addSequential(new ExtendPusher());
		//Retract the pusher
		addSequential(new RetractPusher());
	}
	
}
