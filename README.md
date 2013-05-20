# Team 751 2013 Robot Code #

This code controls Team 751's 2013 competition robot. It is based on the Command-Based Robot template.

Our code is located in the `org.team751` package and its subpackages. Insight LT third-party code is located in the InsightLT package.

More information on the command-based system is available at http://wpilib.screenstepslive.com/s/3120/m/7952

## Files ##

`Robot2013.java` contains the core functions. It does not contain much actual code. It uses the commmand system for its actual logic.

`OI.java` holds the two `Attack3` objects for the two joysticks on the operator console and maps the joystick buttons to robot commands. `Attack3` is a subclass of `Joystick` that allows access to well-defined buttons on an Attack 3 joystick.

Files in the resources directory define the PWM, digital I/O, CAN bus, and analog channels that various components of the robot use.

The subsystems directory contains subsystem classes for various robot subsystems. Each subsystem class holds motor controllers, relays, and/or sensors that its subsystem uses.

The commands directory contains many commands that control the subsystem logic. They are organized in subdirectories corresponding to different robot subsystems.

The util directory contains utility classes for additional functions and sensors that are not in the wpilibj library.

## Autonomous ##

The robot code has ended up with a state-machine based autonomous, contained in the file AutonomousStateMachine.java.
