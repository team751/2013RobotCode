package org.team751.util.cow;

/**
 * Stores the status which slots of the cow are occupied by disks
 * @author Sam Crow
 */
public class CowOccupationStatus {

    /**
     * Constructor
     * @param slot0 if slot 0 is occupied
     * @param slot1 if slot 1 is occupied
     * @param slot2 if slot 2 is occupied
     * @param slot3 if slot 3 is occupied
     */
    public CowOccupationStatus(boolean slot0, boolean slot1, boolean slot2, boolean slot3) {
        this.slot0 = slot0;
        this.slot1 = slot1;
        this.slot2 = slot2;
        this.slot3 = slot3;
    }
    
    
    
    /**
     * if slot 0 is occupied
     */
    public boolean slot0;
    /**
     * if slot 1 is occupied
     */
    public boolean slot1;
    /**
     * if slot 2 is occupied
     */
    public boolean slot2;
    /**
     * if slot 3 is occupied
     */
    public boolean slot3;
    
    /**
     * Determine if any slot is occupied
     * @return true if any slot is occupied, otherwise false
     */
    public boolean isAnyOccupied() {
        return slot0 || slot1 || slot2 || slot3;
    }
    
}
