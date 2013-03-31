# Team 751 Inland Empire Regional 2013 field-related autonomous difficulties #

## About the robot ##

### Electrical ###

The robot uses ten Black Jaguars, controlled using CAN bus, to run all its motors. Commands travel from the cRIO over a serial bridge to one Jaguar and are retransmitted over CAN to other Jaguars as necessary.

An Axis M1013 camera is on board the robot, connected to an ethernet port on the DAP-1522.

The robot uses a 4-slot cRIO.

### Software ###

The robot is programmed in Java using the CommandBasedRobot system.

When the issue was first discovered, before any changes were made, the software was at revision a427c8727f2ed58a3cdd34d922b6b1a26592a6ec. The code at this point can be viewed at https://github.com/team751/2013RobotCode/tree/a427c8727f2ed58a3cdd34d922b6b1a26592a6ec .

In addition to the robot main thread (in which robotInit(), disabledInit(), disabledPeriodic(), etc. are called), the software runs three additional threads:
* One thread, using a java.util.Timer, that runs 2 times per second. When it runs, it asks each of the six drivetrain Jaguars to provide its internal temperature. It then sends these temperatures, along with other temperatures from other temperature sensors connected to analog inputs, to the dashboard.
* Two threads that control the speed of the wheels in the disk shooting mechanism. Each of these threads runs the same code, but operates on a different encoder (for sensing speed) and a different Jaguar (for setting motor power).

(Question: Are the CANJaguar APIs, or any other APIs in WPIlib, thread-safe?)

