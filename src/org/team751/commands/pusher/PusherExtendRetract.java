/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.commands.pusher;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Extends the pusher, then retracts it
 * @author samcrow
 */
public class PusherExtendRetract extends CommandGroup {
	
	public PusherExtendRetract() {
		addSequential(new ExtendPusher());
		addSequential(new RetractPusher());
	}
}
