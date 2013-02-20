package org.team751.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team751.resources.CANJaguarIDs;
import org.team751.util.cow.CowPosition;

/**
 * Implements the third software revision for the cow
 * @author samcrow
 */
public class Cow3 extends Subsystem {
	
	private CANJaguar rotationJaguar;
	
	/**
	 * The currently set control mode
	 */
	private CANJaguar.ControlMode currentMode;
	
	/**
	 * The position that is currently targeted
	 */
	private CowPosition targetPosition = CowPosition.kShoot3;
	
	public Cow3() {
		try {
			rotationJaguar = new CANJaguar(CANJaguarIDs.COW_ROTATE);
		} catch (CANTimeoutException ex) {
			ex.printStackTrace();
		}
		
		configJaguarVbus();
	}
	
	/**
	 * Set the target position
	 * @param newPosition the new target position
	 */
	public void setTargetPosition(CowPosition newPosition) {
		if(currentMode != CANJaguar.ControlMode.kPosition) {
			configJaguarPosition();
		}
		
		targetPosition = newPosition;
	}
	
	/**
	 * Get the target position of the cow
	 * @return the target position
	 */
	public CowPosition getTargetPosition() {
		return targetPosition;
	}
	
	/**
	 * Configure the jaguar in percent Vbus mode
	 */
	private void configJaguarVbus() {
		try {
			rotationJaguar.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
			rotationJaguar.setX(0);
			currentMode = CANJaguar.ControlMode.kPercentVbus;
		} catch (CANTimeoutException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Configure the jaguar in position mode and enable position control
	 */
	private void configJaguarPosition() {
		try {
			rotationJaguar.setPositionReference(CANJaguar.PositionReference.kQuadEncoder);
			rotationJaguar.configEncoderCodesPerRev(1);
			rotationJaguar.changeControlMode(CANJaguar.ControlMode.kPosition);
			
			rotationJaguar.setPID(5, 0, 0);
			
			rotationJaguar.enableControl();
			rotationJaguar.setX(rotationJaguar.getX());
			
			currentMode = CANJaguar.ControlMode.kPosition;
		} catch (CANTimeoutException ex) {
			ex.printStackTrace();
		}
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
}
