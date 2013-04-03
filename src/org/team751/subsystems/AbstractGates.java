/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Defines a gates system.
 * @author Sam Crow
 */
public abstract class AbstractGates extends Subsystem {
    /**
     * If the gates are extended
     */
    protected boolean isExtended;

    /**
     * Extend the front left servo
     */
    public abstract void extendLeft();

    /**
     * Extend the front right servo
     */
    public abstract void extendRight();

    /**
     * Determine if this set of gates is set to extended
     * @return
     */
    public boolean isExtended() {
        return isExtended;
    }

    /**
     * Retract the front left servo
     */
    public abstract void retractLeft();

    /**
     * Retract the front right servo
     */
    public abstract void retractRight();

    /**
     * Set if this set of gates is set to extended
     * @param isExtended
     */
    public void setExtended(boolean isExtended) {
        this.isExtended = isExtended;
    }
    
}
