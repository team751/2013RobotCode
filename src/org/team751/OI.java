package org.team751;

import org.team751.commands.Fire;
import org.team751.commands.cow.CowBack;
import org.team751.commands.cow.CowForward;
import org.team751.commands.cow.MoveToFirstLoadPosition;
import org.team751.commands.cow.PrepareShootSequence;
import org.team751.commands.cow.ZeroCow;
import org.team751.commands.shooter.ShooterOff;
import org.team751.commands.shooter.ShooterOn;
import org.team751.commands.shooter.ShooterSpeedDecrease;
import org.team751.commands.shooter.ShooterSpeedIncrease;
import org.team751.util.Attack3;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.

    public Attack3 driveStick = new Attack3(1);
    public Attack3 operatorStick = new Attack3(2);
    
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

    public OI() {
        //Drive joystick
        driveStick.trigger.whenPressed(new Fire());
        
        operatorStick.trigger.whenPressed(new ZeroCow());
        
        operatorStick.baseLeftForward.whenPressed(new CowForward());
        operatorStick.baseLeftBack.whenPressed(new CowBack());
        
        operatorStick.topBack.whenPressed(new ShooterOn());
        operatorStick.topBack.whenReleased(new ShooterOff());
        
        operatorStick.topLeft.whenPressed(new ShooterSpeedDecrease());
        operatorStick.topRight.whenPressed(new ShooterSpeedIncrease());
        
        operatorStick.baseRightForward.whenPressed(new PrepareShootSequence());
        operatorStick.baseRightBack.whenPressed(new MoveToFirstLoadPosition());
    }
}
