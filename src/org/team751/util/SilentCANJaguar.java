package org.team751.util;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.tables.ITable;

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

    public void set(double outputValue) {
        super.set(outputValue);
    }

    public void setX(double outputValue, byte syncGroup) {
        try {
            super.setX(outputValue, syncGroup);
        } catch (CANTimeoutException ex) {
        }
    }

    public void set(double outputValue, byte syncGroup) {
        super.set(outputValue, syncGroup);
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

    public double get() {
        return super.get();
    }

    public void disable() {
        super.disable();
    }

    public void pidWrite(double output) {
        super.pidWrite(output);
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

    //In progress from here
    
    protected byte getTransaction(int messageID, byte[] data) throws CANTimeoutException {
        return super.getTransaction(messageID, data);
    }

    public void setSpeedReference(SpeedReference reference) throws CANTimeoutException {
        super.setSpeedReference(reference);
    }

    public SpeedReference getSpeedReference() throws CANTimeoutException {
        return super.getSpeedReference();
    }

    public void setPositionReference(PositionReference reference) throws CANTimeoutException {
        super.setPositionReference(reference);
    }

    public PositionReference getPositionReference() throws CANTimeoutException {
        return super.getPositionReference();
    }

    public void setPID(double p, double i, double d) throws CANTimeoutException {
        super.setPID(p, i, d);
    }

    public double getP() throws CANTimeoutException {
        return super.getP();
    }

    public double getI() throws CANTimeoutException {
        return super.getI();
    }

    public double getD() throws CANTimeoutException {
        return super.getD();
    }

    public void enableControl() throws CANTimeoutException {
        super.enableControl();
    }

    public void enableControl(double encoderInitialPosition) throws CANTimeoutException {
        super.enableControl(encoderInitialPosition);
    }

    public void disableControl() throws CANTimeoutException {
        super.disableControl();
    }

    public void changeControlMode(ControlMode controlMode) throws CANTimeoutException {
        super.changeControlMode(controlMode);
    }

    public ControlMode getControlMode() throws CANTimeoutException {
        return super.getControlMode();
    }

    public double getBusVoltage() throws CANTimeoutException {
        return super.getBusVoltage();
    }

    public double getOutputVoltage() throws CANTimeoutException {
        return super.getOutputVoltage();
    }

    public double getOutputCurrent() throws CANTimeoutException {
        return super.getOutputCurrent();
    }

    public double getTemperature() throws CANTimeoutException {
        return super.getTemperature();
    }

    public double getPosition() throws CANTimeoutException {
        return super.getPosition();
    }

    public double getSpeed() throws CANTimeoutException {
        return super.getSpeed();
    }

    public boolean getForwardLimitOK() throws CANTimeoutException {
        return super.getForwardLimitOK();
    }

    public boolean getReverseLimitOK() throws CANTimeoutException {
        return super.getReverseLimitOK();
    }

    public short getFaults() throws CANTimeoutException {
        return super.getFaults();
    }

    public boolean getPowerCycled() throws CANTimeoutException {
        return super.getPowerCycled();
    }

    public void setVoltageRampRate(double rampRate) throws CANTimeoutException {
        super.setVoltageRampRate(rampRate);
    }

    public int getFirmwareVersion() throws CANTimeoutException {
        return super.getFirmwareVersion();
    }

    public byte getHardwareVersion() throws CANTimeoutException {
        return super.getHardwareVersion();
    }

    public void configNeutralMode(NeutralMode mode) throws CANTimeoutException {
        super.configNeutralMode(mode);
    }

    public void configEncoderCodesPerRev(int codesPerRev) throws CANTimeoutException {
        super.configEncoderCodesPerRev(codesPerRev);
    }

    public void configPotentiometerTurns(int turns) throws CANTimeoutException {
        super.configPotentiometerTurns(turns);
    }

    public void configSoftPositionLimits(double forwardLimitPosition, double reverseLimitPosition) throws CANTimeoutException {
        super.configSoftPositionLimits(forwardLimitPosition, reverseLimitPosition);
    }

    public void disableSoftPositionLimits() throws CANTimeoutException {
        super.disableSoftPositionLimits();
    }

    public void configMaxOutputVoltage(double voltage) throws CANTimeoutException {
        super.configMaxOutputVoltage(voltage);
    }

    public void configFaultTime(double faultTime) throws CANTimeoutException {
        super.configFaultTime(faultTime);
    }

    public void setExpiration(double timeout) {
        super.setExpiration(timeout);
    }

    public double getExpiration() {
        return super.getExpiration();
    }

    public boolean isAlive() {
        return super.isAlive();
    }

    public boolean isSafetyEnabled() {
        return super.isSafetyEnabled();
    }

    public void setSafetyEnabled(boolean enabled) {
        super.setSafetyEnabled(enabled);
    }

    public String getDescription() {
        return super.getDescription();
    }

    public void stopMotor() {
        super.stopMotor();
    }

    public String getSmartDashboardType() {
        return super.getSmartDashboardType();
    }

    public void initTable(ITable subtable) {
        super.initTable(subtable);
    }

    public void updateTable() {
        super.updateTable();
    }

    public ITable getTable() {
        return super.getTable();
    }

    public void startLiveWindowMode() {
        super.startLiveWindowMode();
    }

    public void stopLiveWindowMode() {
        super.stopLiveWindowMode();
    }
    
    
}
