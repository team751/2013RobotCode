package org.team751.commands.cow;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Combines all three steps to zero the cow
 * @author Sam Crow
 */
public class ZeroCow extends CommandGroup {
	
	public ZeroCow() {
		addSequential(new ZeroCowStep1());
		addSequential(new ZeroCowStep2());
		addSequential(new ZeroCowStep3());
	}
	
}
