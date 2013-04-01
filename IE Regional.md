# Team 751 Inland Empire Regional 2013 field-related autonomous difficulties #

## About the robot ##

### Electrical ###

The robot uses ten Black Jaguars, controlled using CAN bus, to run all its motors. Commands travel from the cRIO over a serial bridge to one Jaguar and are retransmitted over CAN to other Jaguars as necessary.

An Axis M1013 camera is on board the robot, connected to an ethernet port on the DAP-1522.

The robot uses a 4-slot cRIO.

During all testing at competitions off the field, the driver station computer was connected to the DAP-1522 with an ethernet cable. Another ethernet cable connected the DAP-1522 to the cRIO.

On the field, the robot was connected to the field network.

### Software ###

The robot is programmed in Java using the CommandBasedRobot system.

In addition to the robot main thread (in which robotInit(), disabledInit(), disabledPeriodic(), etc. are called), the software runs three additional threads:
* One thread, using a java.util.Timer, that runs 2 times per second. When it runs, it asks each of the six drivetrain Jaguars to provide its internal temperature. It then sends these temperatures, along with other temperatures from other temperature sensors connected to analog inputs, to the dashboard.
* Two threads that control the speed of the wheels in the disk shooting mechanism. Each of these threads runs the same code, but operates on a different encoder (for sensing speed) and a different Jaguar (for setting motor power).

(Question: Are the CANJaguar APIs, or any other APIs in WPIlib, thread-safe?)


### Timeline ###

#### Resolved off-field problems ####

Before the issue was fully recognized, we encountered a possibly related issue off the field, using the driver station software to run the robot in autonomous mode.

At this point, the code was at revision de88e3183ffc2a0db489d98c61e69083c2bbc7e2. The code at this point can be viewed at https://github.com/team751/2013RobotCode/blob/de88e3183ffc2a0db489d98c61e69083c2bbc7e2 .

We observed the following unexpected behavior:
 * The robot was switched on and connected
 * The driver station software was set to autonomous mode and was enabled
 * The autonomousInit() method was called, and the robot started the autonomous CommandGroup. ([The autonomousInit() code is available here](https://github.com/team751/2013RobotCode/blob/de88e3183ffc2a0db489d98c61e69083c2bbc7e2/src/org/team751/Robot2013.java#L54))
 * The autonomousPeriodic() method was called periodically, as expected
 * No command in the autonomous CommandGroup actually ran. The robot did not move.
 * The robot was disabled
 * The robot was enabled in autonomous mode again
 * This time, the command group and all its commands ran as expected, and the robot performed as planned

We repeated this sequence about three times. Then, we changed the autonomousInit() method: We added a call to autonomousPeriodic() at the beginning of autonomousInit() and immediately after the autonomous command's start() method was called. This is revision 59785bd393ba9ea5366d12a37b52814493e62aeb. [The new autonomousInit() method code is here](https://github.com/team751/2013RobotCode/blob/59785bd393ba9ea5366d12a37b52814493e62aeb/src/org/team751/Robot2013.java#L57).

This change fixed the immediate issue, and the robot began running the autonomous command every time it was enabled in autonomous mode.


#### First on-field problems ####

When the issue was first discovered, before any changes were made, the software was at revision a427c8727f2ed58a3cdd34d922b6b1a26592a6ec. The code at this point can be viewed at https://github.com/team751/2013RobotCode/tree/a427c8727f2ed58a3cdd34d922b6b1a26592a6ec .

When the match started, one mechanism on the robot actuated for a small fraction of a second. This actuation was consistent with the robot's expected autonomous behavior. After that small fraction of a section ended, the robot stopped moving.

During the teleoperated period, the robot functioned normally. No commands unexpectedly stopped.

