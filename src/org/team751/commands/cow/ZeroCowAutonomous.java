package org.team751.commands.cow;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Executes all three cow zeroing steps. Uses less initial power,
 * for autonomous mode.
 * @author Sam Crow
 */
public class ZeroCowAutonomous extends CommandGroup {
    
    public ZeroCowAutonomous() {
        addSequential(new ZeroCow1Autonomous());
        addSequential(new ZeroCow2());
        addSequential(new ZeroCow3());
    }
    
}
