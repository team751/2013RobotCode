package org.team751.util;

import edu.wpi.first.wpilibj.NamedSendable;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.tables.ITable;

/**
 * An abstract class for a temperature sensor. This is any device that can
 * return a temperature measurement. A temperature sensor has a name and can
 * provide a temperature value.
 */
public abstract class TemperatureSensor implements NamedSendable, LiveWindowSendable {

    /**
     * The name of this sensor, used for dashboard display and data logging
     */
    private String name;

    /**
     * Constructor. Subclasses should call this constructor to set the name.
     *
     * @param name The name of this sensor.
     */
    protected TemperatureSensor(String name) {
        this.name = name;
    }

    /**
     * Get the name of this sensor
     *
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * Get the temperature that is currently sensed, in degrees Celsius
     *
     * @return the temperature
     */
    public abstract double getTemperature();

    /**
     * Get the temperature above which a warning message should be
     * displayed/logged
     *
     * @return The temperature, or NaN if this is not applicable for this sensor
     */
    public abstract double getWarningTemperature();

    public String toString() {
        return getName() + ':' + getTemperature();
    }
    /**
     * If a message has been logged indicating that this sensor is at a warning
     * temperature level. This is used by the {@link RobotMonitor} (it is
     * package private).
     */
    boolean warningLogged = false;
    
    
    //Smart Dashboard/Live Window support section
    
    /**
     * Table used to communicate
     */
    private ITable table;

    public void initTable(ITable itable) {
        table = itable;
        updateTable();
    }

    public ITable getTable() {
        return table;
    }

    public String getSmartDashboardType() {
        return "temperature sensor";
    }

    public void updateTable() {
        if(table != null) {
            table.putNumber("temperature", getTemperature());
        }
    }

    public void startLiveWindowMode() {
    }

    public void stopLiveWindowMode() {
    }
    
    
    
}
