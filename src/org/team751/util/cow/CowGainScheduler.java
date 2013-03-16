package org.team751.util.cow;

/**
 * 
 * @author samcrow
 */
public class CowGainScheduler {
    
    //<editor-fold defaultstate="collapsed" desc="PID class">
    /**
     * Encapsulates P, I, and D parameters
     */
    private static class PID {
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
    }
    //</editor-fold>
    
    /**
     * Get the PID gain parameters for a move from one CowPosition to another
     * @param from The current cow position
     * @param to The position to move to
     * @return the PID values for this move
     */
    private PID getGainForMove(CowPosition from, CowPosition to) {
        return null;
    }
    
}
