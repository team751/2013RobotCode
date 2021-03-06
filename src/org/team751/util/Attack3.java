package org.team751.util;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * Represents a Logitech Attack 3 joystick and provides access to its buttons.
 * <p>
 * When using the command-based robot system, the OI class would normally
 * contain something like this:
 * <p>
 * <pre>
 * public class OI {
 *      
 *      public final Joystick driveStick = new Joystick(1);
 * 
 *      private Button driveTrigger = new JoystickButton(driveStick, 1);
 *      ...
 * 
 *      public OI() {
 *          driveTrigger.whenPressed(...);
 *      }
 * }
 * </pre>
 * <p>
 * This technique could be considered to make the OI class needlessly verbose
 * because it specifies the mapping between button numbers and descriptions
 * for each joystick individually.
 * </p>
 * <p>
 * With this class, it is possible to construct an Attack3 and access its buttons
 * directly.
 * </p>
 * 
 * @author Sam Crow
 */
public class Attack3 extends Joystick {

    public Attack3(int port) {
        super(port);
    }
    
    /**
     * The trigger on this joystick.
     */
    public final Button trigger = new JoystickButton(this, 1);
    /**
     * The left side button on the top of the joystick,
     * button 4.
     */
    public final Button topLeft = new JoystickButton(this, 4);
    /**
     * The right side button on the top of the joystick,
     * button 5.
     */
    public final Button topRight = new JoystickButton(this, 5);
    /**
     * Rearward button on the top of the joystick, button 2
     */
    public final Button topBack = new JoystickButton(this, 2);
    
    /**
     * Forward button on the top of the joystick, button 2
     */
    public final Button topFront = new JoystickButton(this, 3);
    /**
     * Forward button on the left side of the base, button 6
     */
    public final Button baseLeftForward = new JoystickButton(this, 6);
    /**
     * Rearward button on the left side of the base, button 7
     */
    public final Button baseLeftBack = new JoystickButton(this, 7);
    
    /**
     * Forward button on the right side of the base, button 11
     */
    public final Button baseRightForward = new JoystickButton(this, 11);
    /**
     * Rearward button on the right side of the base, button 10
     */
    public final Button baseRightBack = new JoystickButton(this, 10);
	/**
	 * Pair of buttons on the back of the joystick, left one of the pair
	 */
	public final Button baseRearLeft = new JoystickButton(this, 11);
	/**
	 * Pair of buttons on the back of the joystick, right one of the pair
	 */
	public final Button baseRearRight = new JoystickButton(this, 12);
}
