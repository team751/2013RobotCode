package org.team751.util;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 * Wraps a CANJaguar and silently ignores all exceptions that it throws
 * (except for exceptions thrown by the constructor)
 * @author Sam Crow
 */
public class SilentCANJaguar extends CANJaguar {

    public SilentCANJaguar(int deviceNumber) throws CANTimeoutException {
        super(deviceNumber);
    }

    public SilentCANJaguar(int deviceNumber, ControlMode controlMode) throws CANTimeoutException {
        super(deviceNumber, controlMode);
    }

    public void setX(double outputValue) {
        try {
            super.setX(outputValue);
        } catch (CANTimeoutException ex) {
        }
    }

    public void setX(double outputValue, byte syncGroup) {
        try {
            super.setX(outputValue, syncGroup);
        } catch (CANTimeoutException ex) {
        }
    }

    /**
     * @see edu.wpi.first.wpilibj.CANJaguar#getX() 
     * @return If an exception was encountered, returns 0
     */
    public double getX() {
        try {
            return super.getX();
        } catch (CANTimeoutException ex) {
            return 0;
        }
    }

    /**
     * @see edu.wpi.first.wpilibj.CANJaguar#setTransaction(int, byte[], byte) 
     * @return If an exception was encountered, returns 0
     */
    protected byte setTransaction(int messageID, byte[] data, byte dataSize) {
        try {
            return super.setTransaction(messageID, data, dataSize);
        } catch (CANTimeoutException ex) {
            return 0;
        }
    }
    
    /**
     * @see edu.wpi.first.wpilibj.CANJaguar#getTransaction(int, byte[])
     * @return If an exception was encountered, returns 0
     */
    protected byte getTransaction(int messageID, byte[] data) {
        try {
            return super.getTransaction(messageID, data);
        } catch (CANTimeoutException ex) {
            return 0;
        }
    }

    public void setSpeedReference(SpeedReference reference) {
        try {
            super.setSpeedReference(reference);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @see edu.wpi.first.wpilibj.CANJaguar#getSpeedReference()
     * @return If an exception was encountered, returns null
     */
    public SpeedReference getSpeedReference() {
        try {
            return super.getSpeedReference();
        } catch (CANTimeoutException ex) {
            return null;
        }
    }

    public void setPositionReference(PositionReference reference) {
        try {
            super.setPositionReference(reference);
        } catch (CANTimeoutException ex) {
        }
    }

    /**
     * @see edu.wpi.first.wpilibj.CANJaguar#getPositionReference() 
     * @return If an exception was encountered, returns 0
     */
    public PositionReference getPositionReference() {
        try {
            return super.getPositionReference();
        } catch (CANTimeoutException ex) {
            return null;
        }
    }

    public void setPID(double p, double i, double d) {
        try {
            super.setPID(p, i, d);
        } catch (CANTimeoutException ex) {
        }
    }

    /**
     * @see edu.wpi.first.wpilibj.CANJaguar#getP() 
     * @return If an exception was encountered, returns 0
     */
    public double getP() {
        try {
            return super.getP();
        } catch (CANTimeoutException ex) {
            return 0;
        }
    }

    /**
     * @see edu.wpi.first.wpilibj.CANJaguar#getI() 
     * @return if an exception was encountered, returns 0
     */
    public double getI() {
        try {
            return super.getI();
        } catch (CANTimeoutException ex) {
            return 0;
        }
    }

    /**
     * @see edu.wpi.first.wpilibj.CANJaguar#getD() 
     * @return If an exception was encountered, returns 0
     */
    public double getD() {
        try {
            return super.getD();
        } catch (CANTimeoutException ex) {
            return 0;
        }
    }

    public void enableControl() {
        try {
            super.enableControl();
        } catch (CANTimeoutException ex) {
        }
    }

    public void enableControl(double encoderInitialPosition) {
        try {
            super.enableControl(encoderInitialPosition);
        } catch (CANTimeoutException ex) {
        }
    }

    public void disableControl() {
        try {
            super.disableControl();
        } catch (CANTimeoutException ex) {
        }
    }

    public void changeControlMode(ControlMode controlMode) {
        try {
            super.changeControlMode(controlMode);
        } catch (CANTimeoutException ex) {
        }
    }

    /**
     * @see edu.wpi.first.wpilibj.CANJaguar#getControlMode() 
     * @return If an exception was encountered, returns null
     */
    public ControlMode getControlMode() {
        try {
            return super.getControlMode();
        } catch (CANTimeoutException ex) {
            return null;
        }
    }

    /**
     * @see edu.wpi.first.wpilibj.CANJaguar#getBusVoltage() 
     * @return If an exception was encountered, returns 0
     */
    public double getBusVoltage() {
        try {
            return super.getBusVoltage();
        } catch (CANTimeoutException ex) {
            return 0;
        }
    }

    /**
     * @see edu.wpi.first.wpilibj.CANJaguar#getOutputVoltage() 
     * @return If an exception was encountered, returns 0
     */
    public double getOutputVoltage() {
        try {
            return super.getOutputVoltage();
        } catch (CANTimeoutException ex) {
            return 0;
        }
    }

    /**
     * @see edu.wpi.first.wpilibj.CANJaguar#getOutputCurrent() 
     * @return If an exception was encountered, returns 0
     */
    public double getOutputCurrent() {
        try {
            return super.getOutputCurrent();
        } catch (CANTimeoutException ex) {
            return 0;
        }
    }

    /**
     * @see edu.wpi.first.wpilibj.CANJaguar#getTemperature() 
     * @return If an exception was encountered, returns 0
     */
    public double getTemperature() {
        try {
            return super.getTemperature();
        } catch (CANTimeoutException ex) {
            return 0;
        }
    }

    /**
     * @see edu.wpi.first.wpilibj.CANJaguar#getPosition() 
     * @return If an exception was encountered, returns 0
     */
    public double getPosition() {
        try {
            return super.getPosition();
        } catch (CANTimeoutException ex) {
            return 0;
        }
    }

    /**
     * @see edu.wpi.first.wpilibj.CANJaguar#getSpeed() 
     * @return If an exception was encountered, returns 0
     */
    public double getSpeed() {
        try {
            return super.getSpeed();
        } catch (CANTimeoutException ex) {
            return 0;
        }
    }

    /**
     * @see edu.wpi.first.wpilibj.CANJaguar#getForwardLimitOK() 
     * @return If an exception was encountered, returns false
     */
    public boolean getForwardLimitOK() {
        try {
            return super.getForwardLimitOK();
        } catch (CANTimeoutException ex) {
            return false;
        }
    }

    /**
     * @see edu.wpi.first.wpilibj.CANJaguar#getReverseLimitOK() 
     * @return If an exception was encountered, returns false
     */
    public boolean getReverseLimitOK() {
        try {
            return super.getReverseLimitOK();
        } catch (CANTimeoutException ex) {
            return false;
        }
    }

    /**
     * @see edu.wpi.first.wpilibj.CANJaguar#getFaults() 
     * @return If an exception was encountered, returns 0
     */
    public short getFaults() {
        try {
            return super.getFaults();
        } catch (CANTimeoutException ex) {
            return 0;
        }
    }

    /**
     * @see edu.wpi.first.wpilibj.CANJaguar#getPowerCycled() 
     * @return If an exception was encountered, returns false
     */
    public boolean getPowerCycled() {
        try {
            return super.getPowerCycled();
        } catch (CANTimeoutException ex) {
            return false;
        }
    }

    public void setVoltageRampRate(double rampRate) {
        try {
            super.setVoltageRampRate(rampRate);
        } catch (CANTimeoutException ex) {
        }
    }

    /**
     * @see edu.wpi.first.wpilibj.CANJaguar#getFirmwareVersion() 
     * @return If an exception was encountered, returns 0
     */
    public int getFirmwareVersion() {
        try {
            return super.getFirmwareVersion();
        } catch (CANTimeoutException ex) {
            return 0;
        }
    }

    /**
     * @see edu.wpi.first.wpilibj.CANJaguar#getHardwareVersion() 
     * @return If an exception was encountered, returns 0
     */
    public byte getHardwareVersion() {
        try {
            return super.getHardwareVersion();
        } catch (CANTimeoutException ex) {
            return 0;
        }
    }

    public void configNeutralMode(NeutralMode mode) {
        try {
            super.configNeutralMode(mode);
        } catch (CANTimeoutException ex) {
        }
    }

    public void configEncoderCodesPerRev(int codesPerRev) {
        try {
            super.configEncoderCodesPerRev(codesPerRev);
        } catch (CANTimeoutException ex) {
        }
    }

    public void configPotentiometerTurns(int turns) {
        try {
            super.configPotentiometerTurns(turns);
        } catch (CANTimeoutException ex) {
        }
    }

    public void configSoftPositionLimits(double forwardLimitPosition, double reverseLimitPosition) {
        try {
            super.configSoftPositionLimits(forwardLimitPosition, reverseLimitPosition);
        } catch (CANTimeoutException ex) {
        }
    }

    public void disableSoftPositionLimits() {
        try {
            super.disableSoftPositionLimits();
        } catch (CANTimeoutException ex) {
        }
    }

    public void configMaxOutputVoltage(double voltage) {
        try {
            super.configMaxOutputVoltage(voltage);
        } catch (CANTimeoutException ex) {
        }
    }

    public void configFaultTime(double faultTime) {
        try {
            super.configFaultTime(faultTime);
        } catch (CANTimeoutException ex) {
        }
    }
}
