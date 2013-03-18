/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team751.resources.CANJaguarIDs;

/**
 * A simplified cow for testing
 * @author samcrow
 */
public class SimpleCow extends Subsystem {

	private CANJaguar rotationJaguar;
	
	public SimpleCow() {
		try {
			rotationJaguar = new CANJaguar(CANJaguarIDs.COW_ROTATE, CANJaguar.ControlMode.kPosition);
			
			rotationJaguar.setPID(5, 0, 0);
			rotationJaguar.setPositionReference(
					CANJaguar.PositionReference.kQuadEncoder);
			rotationJaguar.enableControl();
		} catch (CANTimeoutException ex) {
			ex.printStackTrace();
		}
		
		
	}
	
	public void moveBack() {
		try {
			rotationJaguar.setX(rotationJaguar.getX() + 1);
		} catch (CANTimeoutException ex) {
			ex.printStackTrace();
		}
	}
	
	
	public void moveForward() {
		try {
			rotationJaguar.setX(rotationJaguar.getX() - 1);
		} catch (CANTimeoutException ex) {
			ex.printStackTrace();
		}
	}
	
	protected void initDefaultCommand() {
		
	}
	
}
