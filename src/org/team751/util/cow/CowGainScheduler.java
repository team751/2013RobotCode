package org.team751.util.cow;

/**
 * 
 * @author samcrow
 */
public class CowGainScheduler {
    
    private static final PID upGains = new PID(5, 0.01, 0.2);
    
    private static final PID downGains = new PID(3, 0, 0.8);
    
    private static final PID upOverGains = new PID(3, 0, 0.2);
    
    //<editor-fold defaultstate="collapsed" desc="PID class">
    /**
     * Encapsulates P, I, and D parameters
     */
    public static class PID {
        /**
         * P parameter
         */
        public double p;
        /**
         * I parameter
         */
        public double i;
        /**
         * D parameter
         */
        public double d;

        public PID(double p, double i, double d) {
            this.p = p;
            this.i = i;
            this.d = d;
        }
        
        /**
         * A set of PID constants that are all zero
         */
        public static final PID zeroes = new PID(0, 0, 0);
    }
    //</editor-fold>
    
    /**
     * Get the PID gain parameters for a move from one CowPosition to another
     * @param current The current cow position
     * @param target The position to move to
     * @return the PID values for this move
     */
    public static PID getGainForMove(CowPosition current, CowPosition target) {
        
        CowPosition.MoveType type = current.moveTypeTo(target);
        
        System.out.println("Move type: "+type.toString());
        
        if(type == CowPosition.MoveType.kUp) {
            return upGains;
        }
        if(type == CowPosition.MoveType.kDown) {
            return downGains;
        }
        if(type == CowPosition.MoveType.kUpOver) {
            return upOverGains;
        }
        
        System.err.println("Cow gain scheduler got unexpected input. Returning up values.");
        return upGains;
    }
    
	
	private CowGainScheduler() {}
}
