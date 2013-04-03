package org.team751.commands.gates;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Retracts the two front gates, with a delay in between to ensure that they
 * don't crash into each other.
 * @author Sam Crow
 */
public class FrontRetract extends CommandGroup {
    public FrontRetract() {
        addSequential(new FrontRightRetract());
        //wait for a certain number of seconds
        addSequential(new WaitCommand(0.2));
        addSequential(new FrontLeftRetract());
    }
}
