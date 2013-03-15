package org.team751.util;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * A type of DigitalInput that can be used to conveniently access a limit switch.
 * 
 * The digital inputs on the digital sidecar are pulled up (high) by default
 * until they are set low by being connected to ground. 
 * 
 * Limit switches should be wired in a fail-safe manner - so that if one becomes
 * disconnected, the software should read it as being pressed. This prevents one
 * major case in which the software might not protect the mechanisms from overrun.
 * 
 * To fulfill this, each limit switch should be wired with the normally closed
 * contact connected to ground. When the switch is disconnected or pressed,
 * the input will be pulled high and the software will read it as pressed.
 * 
 * This class provides an extra method {@link #isPressed()} that returns true
 * if the input is high (pressed or not connected) and false if the input
 * is low (not pressed).
 * 
 * @author Sam Crow
 */
public class LimitSwitch extends DigitalInput {

    public LimitSwitch(int channel) {
        super(channel);
    }

    public LimitSwitch(int moduleNumber, int channel) {
        super(moduleNumber, channel);
    }
    
    /**
     * Determine if this limit switch is pressed.
     * @return True if the switch is pressed, otherwise false.
     */
    public boolean isPressed() {
        return get();
    }
    
}
