# Team 751 Inland Empire Regional 2013 field-related autonomous difficulties #

During the 2013 Inland Empire Regional, Team 751 (Barn 2 Robotics) encountered a problem in which its autonomous code would run in practice but not during matches on the field.

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
* Two threads that control the speed of the wheels in the disk shooting mechanism. Each of these threads runs the same code, but operates on a different encoder (for sensing speed) and a different Jaguar (for setting motor power). Each runs [this code](https://github.com/team751/2013RobotCode/blob/master/src/org/team751/speedcontrol/TakeBackHalfSpeedController.java), in which runSpeedControl() is called frequently. It is called from [the run() method of the superclass](https://github.com/team751/2013RobotCode/blob/master/src/org/team751/speedcontrol/ThreadedSpeedController.java#L73).

SmartDashboard data is sent from two places:

 * [The DashboardInterface](https://github.com/team751/2013RobotCode/blob/ca82817f40e3b0b0271eeb8c48971b952fab2121/src/org/team751/util/DashboardInterface.java) gets information from the other subsystems (excluding the drivetrain) and sends it to the dashboard. The update() method is called from autonomousPeriodic(), disabledPeriodic(), and teleopPeriodic().

* The drivetrain monitor thread, described above, sends data from the drivetrain 2 times per second.

(Question: Are the CANJaguar APIs, or any other APIs in WPIlib, thread-safe?)


## Timeline ##

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

This happened during every match on Friday. (We had not been to any practice matches on Thursday.)

On Friday afternoon, with help from the CSA at the event, we did some diagnostics and noticed that our packet losses and trip times were higher than normal. We made a list of changes that we could make that might fix the autonomous problem:
 * Remove all SmartDashboard method calls during autonomous mode
 * Replace the ethernet cable between the DAP-1522 and the cRIO (that we had assembled) with a potentially more reliable COTS one
    (Later we realized that this was unlikely to change anything, because all network traffic passes through it, both on and off the field.)
 * Check the settings of the Axis camera and potentially decrease its resolution
 * Disable our code that tracks the status of the robot mechanisms and that, if buggy, might prevent the robot from operating

Before our second qualification match on Saturday, we implemented all the changes, excluding the Axis camera resolution change. The following are some details:
 * We added debug statements that use the DriverStationLCD class to send text to the display in the driver station software

During our first match, the robot behaved as before (it did not work as expected). The new debugging statements showed that the first command in the sequence had finished and that the second one had started. This might have been consistent with the motion of the robot.

Before our second match, we had time to reduce the resolution of the Axis camera. It had been set to 480x360. We reduced it to the recommended resolution of 320x240.

In our second match, the robot did the same thing. We did not look at what the debug statements said.

After the match, we looked at the driver station logs and saw that our trip times and packet losses had decreased significantly.

The autonomous command sequence had consistently worked in practice but not on the field.

Before our third match, we replaced our autonomous command sequence with [a new, super-simple procedural autonmous](https://github.com/team751/2013RobotCode/blob/ca82817f40e3b0b0271eeb8c48971b952fab2121/src/org/team751/Robot2013.java#L144). This code executes the same logic as the command sequence, but it does it procedurally and entirely within autonomousInit(), using Thread.sleep() to wait for conditions.

In our third match, using the simple autonomous, the robot performed as expected.

