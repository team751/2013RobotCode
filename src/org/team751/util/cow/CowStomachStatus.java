package org.team751.util.cow;

/**
 * Stores the status which stomachs of the cow are filled by disks
 * @author Sam Crow
 */
public class CowStomachStatus {

    /**
     * Constructor
     * @param stomach0 if stomach 0 is full
     * @param stomach1 if stomach 1 is full
     * @param stomach2 if stomach 2 is full
     * @param stomach3 if stomach 3 is full
     */
    public CowStomachStatus(boolean stomach0, boolean stomach1, boolean stomach2, boolean stomach3) {
        this.stomach0 = stomach0;
        this.stomach1 = stomach1;
        this.stomach2 = stomach2;
        this.stomach3 = stomach3;
    }
    
    
    
    /**
     * if stomach 0 is full
     */
    public boolean stomach0;
    /**
     * if stomach 1 is full
     */
    public boolean stomach1;
    /**
     * if stomach 2 is full
     */
    public boolean stomach2;
    /**
     * if stomach 3 is full
     */
    public boolean stomach3;
    
    /**
     * Determine if any stomach is full
     * @return true if any stomach is full, otherwise false
     */
    public boolean isAnyFull() {
        return stomach0 || stomach1 || stomach2 || stomach3;
    }
	
	/**
	 * Determine if all stomachs are full
	 * @return true if all stomachs are full, otherwise false
	 */
	public boolean areAllFull() {
		return stomach0 && stomach1 && stomach2 && stomach3;
	}
    
}
