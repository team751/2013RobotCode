package org.team751.util.cow;

import edu.wpi.first.wpilibj.DigitalInput;
import org.team751.resources.DigitalChannels;

/**
 * Keeps track of the cow stomach sensors
 * @author Sam Crow
 */
public class CowStomachs {
    
    private DigitalInput stomach0 = new DigitalInput(DigitalChannels.COW_STOMACH_0);
    
    private DigitalInput stomach1 = new DigitalInput(DigitalChannels.COW_STOMACH_1);
    
    private DigitalInput stomach2 = new DigitalInput(DigitalChannels.COW_STOMACH_2);
    
    private DigitalInput stomach3 = new DigitalInput(DigitalChannels.COW_STOMACH_3);

    /**
     * Determine if stomach 0 is full
     * @return 
     */
    public boolean stomach0Full() {
        return stomach0.get();
    }
    
    /**
     * Determine if stomach 1 is full
     * @return 
     */
    public boolean stomach1Full() {
        return stomach1.get();
    }
    
    /**
     * Determine if stomach 2 is full
     * @return 
     */
    public boolean stomach2Full() {
        return stomach2.get();
    }
    
    /**
     * Determine if stomach 3 is full
     * @return 
     */
    public boolean stomach3Full() {
        return stomach3.get();
    }
    
}
