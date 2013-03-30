package org.team751.cheesy;

/**
 * A utility class that stores constants for Cheesy Drive algorithms.
 * Values have been modified by team 751.
 * @author Team 254, translated to Java by Sam Crow
 */
public class CheesyDriveConstants {
    
    /**
     * Controls the non-linear response of the steering.
     * A smaller value makes the response "slower".
     */
    public static final double kWheelNonLinearity = 0.1;
    
    public static final double kNegInertiaLowMore = 2.5;
    
    public static final double kNegInertiaLowLessExt = 5;
    
    public static final double kNegInertiaLowLess = 3;
    
    public static final double kSenseLow = 1.1;
    
    public static final double kSenseCutoff = 0.1;
    
    public static final double kQuickStopTimeConstant = 0.1;
    
    public static final double kQuickStopStickScalar = 5;
    
    private CheesyDriveConstants() {}
}
