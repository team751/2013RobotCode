package org.team751.commands.cow3;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Executes all three cow zeroing steps
 * @author Sam Crow
 */
public class ZeroCow extends CommandGroup {
    
    public ZeroCow() {
        addSequential(new ZeroCow1());
        addSequential(new ZeroCow2());
        addSequential(new ZeroCow3());
    }
    
}
