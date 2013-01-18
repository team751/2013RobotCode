package org.team751.util;

import edu.wpi.first.wpilibj.NamedSendable;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;

/**
 * An abstract class for a temperature sensor. This is any device that can
 * return a temperature measurement. A temperature sensor has a name and can
 * provide a temperature value.
 */
public abstract class TemperatureSensor implements NamedSendable {

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
        table = NetworkTable.getTable("temperature_sensor_"+getName());
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
     * Get the temperature that is currently sensed, in degrees celsius
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

    public void initTable(ITable itable) {
    }

    //Smart Dashboard support
    
    private ITable table;
    
    public ITable getTable() {
        return table;
    }

    public String getSmartDashboardType() {
        return "temperature_sensor";
    }
}
