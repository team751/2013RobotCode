package org.team751.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team751.commands.cow3.ZeroCow;

/**
 * The main command group that runs during autonomous mode
 * @author Sam Crow
 */
public class Autonomous extends CommandGroup {
    public Autonomous() {
        addSequential(new ZeroCow());
        addSequential(new FireAll());
    }
}
