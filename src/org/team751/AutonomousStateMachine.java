package org.team751;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Timer;
import org.team751.commands.CommandBase;
import org.team751.util.cow.CowPosition;

/**
 * A state machine for a simple 3-disk autonomous mode
 * @author Sam Crow
 */
public class AutonomousStateMachine {
	
	/**
	 * The phase number
	 * 
	 * 0 = starting up, starting cow zero process
	 * 1 = first cow zero has been hit, moving back for 0.5 seconds
	 * 2 = Moving cow forward again until zero is hit
	 * 3 = setting cow to zero and setting its target position to this
	 * 4 = first shot, extending pusher
	 * 5 = first shot, retracting pusher
	 * 6 = moving cow from shoot3 to shoot2
	 * 7 = waiting
	 * 8 = second shot, extending pusher
	 * 9 = second shot, retracting pusher
	 * 10 = moving cow from shoot2 to shoot1
	 * 11 = waiting
	 * 12 = third shot, extending pusher
	 * 13 = third shot, retracting pusher
	 * 14 = done, turning off shooter
	 * 
	 */
	private int phase = 0;
	
	private Timer timer = new Timer();
	
	public AutonomousStateMachine() {
		phase = 0;
	}
	
	/**
	 * Run the state machine, and do whatever is appropriate for this phase.
	 * This method must not block.
	 */
	public void run() {
		//Debug the phase number
		DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser1,
											   1, "Autonomous phase: "+phase);
		DriverStationLCD.getInstance().updateLCD();
		
		
		switch(phase) {
			case 0:
				CommandBase.shooterWheels.setSpeed(0.75);
				CommandBase.shooterWheels.enable();
				CommandBase.cow.moveExtraSlowForward();
				
				if(CommandBase.cow.isAtZero()) {
					
					CommandBase.cow.manualStop();
					
					phase++;
					//Start the timer that phase 1 uses
					timer.reset();
					timer.start();
				}
				
				break;
				
			case 1:
				
				CommandBase.cow.moveSlowBack();
				
				if(timer.get() > 0.5) {
					CommandBase.cow.manualStop();
					timer.stop();
					phase++;
				}
				
				break;
				
			case 2:
				
				CommandBase.cow.moveExtraSlowForward();
				
				if(CommandBase.cow.isAtZero()) {
					CommandBase.cow.manualStop();
					phase++;
				}
				
				break;
				
			case 3:
				//only runs once
				CommandBase.cow.setThisAsZero();
				CommandBase.cow.setTargetPosition(CowPosition.kShoot3);
				
				phase++;
				break;
				
				//Begin shoot
			case 4:
				
				CommandBase.pusher.push();
				
				if(CommandBase.pusher.isExtended()) {
					phase++;
				}
				
				break;
				
			case 5:
				
				CommandBase.pusher.retract();
				
				if(CommandBase.pusher.isRetracted()) {
					phase++;
				}
				
				break;
				
			case 6:
				CommandBase.cow.setTargetPosition(CowPosition.kShoot2);
				
				if(CommandBase.cow.isInPosition()) {
					phase++;
					//Start the timer that phase 7 needs
					timer.reset();
					timer.start();
				}
				
				break;
				
			case 7:
				
				if(timer.get() > 1) {
					phase++;
					timer.stop();
				}
				
				break;
				//End shoot
				
				//Begin shoot 2
			case 8:
				
				CommandBase.pusher.push();
				
				if(CommandBase.pusher.isExtended()) {
					phase++;
				}
				
				break;
				
			case 9:
				
				CommandBase.pusher.retract();
				
				if(CommandBase.pusher.isRetracted()) {
					phase++;
				}
				
				break;
				
			case 10:
				CommandBase.cow.setTargetPosition(CowPosition.kShoot1);
				
				if(CommandBase.cow.isInPosition()) {
					phase++;
					//Start the timer that phase 7 needs
					timer.reset();
					timer.start();
				}
				
				break;
				
			case 11:
				
				if(timer.get() > 1) {
					phase++;
					timer.stop();
				}
				
				break;
				//End shoot 2
				
				//Begin shoot 3
			case 12:
				
				CommandBase.pusher.push();
				
				if(CommandBase.pusher.isExtended()) {
					phase++;
				}
				
				break;
				
			case 13:
				
				CommandBase.pusher.retract();
				
				if(CommandBase.pusher.isRetracted()) {
					phase++;
				}
				
				break;
				
			case 14:
				
				//clean up
				CommandBase.shooterWheels.disable();
				
				break;
				
			case 15:
				
				//Done. Do nothing.
				
				break;
				
			default:
				break;
		}
		
	}
	
}
