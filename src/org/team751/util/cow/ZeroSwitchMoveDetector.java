package org.team751.util.cow;

import org.team751.subsystems.Cow;

/**
 * Detects when the zero switch has changed its state from detecting light
 * to dark, and then back to light
 * @author Programmer
 */
public class ZeroSwitchMoveDetector {
    
    /**
     * The current phase of this detector
     * 
     * 0: The switch currently detects light, and has not detected dark yet
     * 1: The switch has transitioned from light to dark
     * 2: The switch has transitioned from dark back to light, and the process is complete
     */
    private int phase = 0;
    
    private Cow cow;

    /**
     * Constructor
     * @param parent The cow to monitor
     */
    public ZeroSwitchMoveDetector(Cow parent) {
        cow = parent;
        System.out.println("ZeroSwitchMoveDetector created. Current zero switch status "+cow.isAtZero());
    }
    
    
    
    
    public boolean isComplete() {
        update();
        return phase == 2;
    }
    
    private void update() {
        switch(phase) {
            case 0:
                //Move to the next phase if darkness is detected
                if( ! cow.isAtZero()) {
                    phase++;
                    System.out.println("Darkness now detected. Moving up to phase 1");
                }
                break;
                
            case 1:
                //Move to the next phase if light is detected
                if(cow.isAtZero()) {
                    phase++;
                    System.out.println("Light now detected. Moving up to phase 2");
                }
                break;
                
            default:
                break;
        }
    }
}
