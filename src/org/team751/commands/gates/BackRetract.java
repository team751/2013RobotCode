package org.team751.commands.gates;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Retracts the two back gates, with a delay in between to ensure that they
 * don't crash into each other.
 * @author Sam Crow
 */
public class BackRetract extends CommandGroup {
    public BackRetract() {
        addSequential(new BackRightRetract());
        //wait for a certain number of seconds
        addSequential(new WaitCommand(0.2));
        addSequential(new BackLeftRetract());
    }
}
