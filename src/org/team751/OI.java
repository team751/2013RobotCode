
package org.team751;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.team751.commands.Fire;
import org.team751.commands.cow3.CowBack;
import org.team751.commands.cow3.CowForward;
import org.team751.commands.cow3.ZeroCow;
import org.team751.commands.shooter.ShooterOff;
import org.team751.commands.shooter.ShooterOn;
import org.team751.commands.shooter.ShooterSpeedDecrease;
import org.team751.commands.shooter.ShooterSpeedIncrease;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    public Joystick driveStick = new Joystick(1);
    public Joystick operatorStick = new Joystick(2);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // Another type of button you can create is a DigitalIOButton, which is
    // a button or switch hooked up to the cypress module. These are useful if
    // you want to build a customized operator interface.
    // Button button = new DigitalIOButton(1);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
	
	/**
	 * Drive joystick, trigger
	 */
	private Button driveTrigger = new JoystickButton(driveStick, 1);
	
	private Button driveTopFront = new JoystickButton(driveStick, 3);
        
        /**
         * Operator joystick, trigger
         */
        private Button operatorTrigger = new JoystickButton(operatorStick, 1);
	
	/**
	 * Operator joystick, base, left side, forward button
	 */
	private Button driveBaseLeftForward = new JoystickButton(driveStick, 6);
	
	/**
	 * Operator joystick, base, left side, back button
	 */
	private Button driveBaseLeftBack = new JoystickButton(driveStick, 7);
	
	/**
	 * Operator joystick, base, right side, forward button
	 */
	private Button operatorBaseRightForward = new JoystickButton(operatorStick, 11);
	
	/**
	 * Operator joystick, base, right side, back button
	 */
	private Button operatorBaseRightBack = new JoystickButton(operatorStick, 10);
	
	/**
	 * Operator joystick, top, back
	 */
	private Button operatorStickTopBack = new JoystickButton(operatorStick, 2);
	
	public OI() {
		
		driveTrigger.whenPressed(new Fire());
		driveTopFront.whenPressed(new ShooterOn());
		driveTopFront.whenReleased(new ShooterOff());
		
                operatorTrigger.whenPressed(new ZeroCow());
                
                driveBaseLeftForward.whenPressed(new CowForward());
                driveBaseLeftBack.whenPressed(new CowBack());
	}
}

