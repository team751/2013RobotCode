package org.team751.util.cow;

/**
 * Enumerates different positions to which the cow can be set.
 *
 * There are 4 disk positions in the cow: 0, 1, 2, and 3.
 *
 * With the Cow in its most upright position, position 0 is furthest backwards.
 * Positions 1-3 are forwards of it.
 *
 * When the cow is backward in feeding mode, position 0 is at the bottom. When
 * the cow is forwards in shooting mode, position 0 is at the top. (this also
 * represents its furthest forward limit)
 *
 */
public class CowPosition {

    /**
     * The value, in encoder counts rearward from position kShoot0, that the cow
     * should be set to
     */
    private double encoderValue = 0;
    /**
     * Position for loading into stomach 0. (stomach 0 is at the bottom).
     */
    public static final CowPosition kLoad0 = new CowPosition(30) {
        public String toString() {
            return "Load 0";
        }
    };
    /**
     * Position for loading into stomach 1.
     */
    public static final CowPosition kLoad1 = new CowPosition(50) {
        public String toString() {
            return "Load 1";
        }
    };
    /**
     * Position for loading into stomach 2.
     */
    public static final CowPosition kLoad2 = new CowPosition(70) {
        public String toString() {
            return "Load 2";
        }
    };
    /**
     * Position for loading into stomach 3. (stomach 3 is at the top)
     */
    public static final CowPosition kLoad3 = new CowPosition(90) {
        public String toString() {
            return "Load 3";
        }
    };
    /**
     * Position for shooting from stomach 0. (stomach 0 is at the top)
     */
    public static final CowPosition kShoot0 = new CowPosition(-60) {
        public String toString() {
            return "Shoot 0";
        }
    };
    /**
     * Position for shooting from stomach 1.
     */
    public static final CowPosition kShoot1 = new CowPosition(-40) {
        public String toString() {
            return "Shoot 1";
        }
    };
    /**
     * Position for shooting from stomach 2.
     */
    public static final CowPosition kShoot2 = new CowPosition(-20) {
        public String toString() {
            return "Shoot 2";
        }
    };
    /**
     * Position for shooting from stomach 3. (stomach 3 is at the bottom).
     */
    //kShoot3 is at the zero point
    public static final CowPosition kShoot3 = new CowPosition(0) {
        public String toString() {
            return "Shoot 3";
        }
    };

    /**
     * Constructor
     *
     * @param encoderValue The encoder value for this position
     */
    private CowPosition(double encoderValue) {
        this.encoderValue = encoderValue;
    }

    /**
     * Get the encoder value.
     *
     * @return The target encoder value for this position.
     */
    public double getEncoderValue() {
        return encoderValue;
    }
    
    /**
     * Determine if this position is a shooting position
     * @return 
     */
    public boolean isShootPosition() {
        return this == kShoot0 || this == kShoot1 || this == kShoot2 || this == kShoot3;
    }
    
    /**
     * Determine if this position is a loading position
     * @return 
     */
    public boolean isLoadPosition() {
        return this == kLoad0 || this == kLoad1 || this == kLoad2 || this == kLoad3;
    }

    /**
     * Get the position that is one position back from this one.
     *
     * @return The next back position. If no position is further back than this
     * one, returns null.
     */
    public CowPosition nextBack() {
        if (this == CowPosition.kShoot3) {
            return CowPosition.kLoad0;
        }
        if (this == CowPosition.kShoot2) {
            return CowPosition.kShoot3;
        }
        if (this == CowPosition.kShoot1) {
            return CowPosition.kShoot2;
        }
        if (this == CowPosition.kShoot0) {
            return CowPosition.kShoot1;
        }
        if (this == CowPosition.kLoad0) {
            return CowPosition.kLoad1;
        }
        if (this == CowPosition.kLoad1) {
            return CowPosition.kLoad2;
        }
        if (this == CowPosition.kLoad2) {
            return CowPosition.kLoad3;
        }
        if (this == CowPosition.kLoad3) {
            return null;
        }
        return null;
    }

    /**
     * Get the position that is one forward of this one.
     *
     * @return The next forward position. If not position is further forward
     * than this one, returns null.
     */
    public CowPosition nextForward() {
        if (this == CowPosition.kLoad3) {
            return CowPosition.kLoad2;
        }
        if (this == CowPosition.kLoad2) {
            return CowPosition.kLoad1;
        }
        if (this == CowPosition.kLoad1) {
            return CowPosition.kLoad0;
        }
        if (this == CowPosition.kLoad0) {
            return CowPosition.kShoot3;
        }
        if (this == CowPosition.kShoot3) {
            return CowPosition.kShoot2;
        }
        if (this == CowPosition.kShoot2) {
            return CowPosition.kShoot1;
        }
        if (this == CowPosition.kShoot1) {
            return CowPosition.kShoot0;
        }
        if (this == CowPosition.kShoot0) {
            return null;
        }
        return null;
    }

    /**
     * Enumerates types of moves that the cow can execute
     */
    public static class MoveType {

        /**
         * A name for this position, for debugging purposes
         */
        private String name;

        /**
         * Constructor
         * @param name A name for this position, for debugging purposes
         */
        public MoveType(String name) {
            this.name = name;
        }
        
        public String toString() {
            return name;
        }
        
        /**
         * Move type for moving up
         */
        public static final MoveType kUp = new MoveType("Up");
        /**
         * Move type for moving down
         */
        public static final MoveType kDown = new MoveType("Down");
        /**
         * Move type for no movement
         */
        public static final MoveType kNone = new MoveType("None");
        /**
         * Move type for first moving up, and then over and down to the target
         * position
         */
        public static final MoveType kUpOver = new MoveType("Up and over");
    }

    /**
     * Get the type of move for a movement from this position to another
     * @param other the position to move to
     * @return the type of move, or null if an algorithm error was encountered
     */
    public MoveType moveTypeTo(CowPosition other) {
        
        if(this == other) {
            return MoveType.kNone;
        }
        
        //Shoot 0: The lowest of the shoot positions
        if(this == kShoot0) {
            //Moving up to another shoot position
            if(other == kShoot1 || other == kShoot2 || other == kShoot3) {
                return MoveType.kUp;
            }
            //Moving up and over to a load position
            if(other.isLoadPosition()) {
                return MoveType.kUpOver;
            }
        }
        
        //Shoot 1: one back (up) from shoot 0
        if(this == kShoot1) {
            //Moving down to shoot 0
            if(other == kShoot0) {
                return MoveType.kDown;
            }
            //Moving up to another shoot position
            if(other == kShoot2 || other == kShoot3) {
                return MoveType.kUp;
            }
            //Moving up and over to a load position
            if(other.isLoadPosition()) {
                return MoveType.kUpOver;
            }
        }
        
        //Shoot 2: one back (up) from shoot 1
        if(this == kShoot2) {
            //Moving down to shoot 0 or 1
            if(other == kShoot0 || other == kShoot1) {
                return MoveType.kDown;
            }
            //Moving up to another shoot position
            if(other == kShoot3) {
                return MoveType.kUp;
            }
            
            if(other.isLoadPosition()) {
                return MoveType.kUpOver;
            }
        }
        //Shoot 3
        if(this ==kShoot3) {
            //Moving down to another shoot position
            if(other == kShoot0 || other == kShoot1 || other == kShoot2) {
                return MoveType.kDown;
            }
            
            if(other.isLoadPosition()) {
                return MoveType.kUpOver;
            }
        }
        
        //Load positions
        
        //Load 0: top loading position
        if(this == kLoad0) {
            
            //Moving down to another loading position
            if(other == kLoad1 || other == kLoad2 || other == kLoad3) {
                return MoveType.kDown;
            }
            
            //Moving up and over to a shooting position
            if(other.isShootPosition()) {
                return MoveType.kUpOver;
            }
            
        }
        
        //Load 1 : one below load 0
        if(this == kLoad1) {
            //Moving up to load 0
            if(other == kLoad0) {
                return MoveType.kUp;
            }
            //Moving down to another load position
            if(other == kLoad2 || other == kLoad3) {
                return MoveType.kDown;
            }
            //Moving up and over to a shooting position
            if(other.isShootPosition()) {
                return MoveType.kUpOver;
            }
        }
        
        //Load 2
        if(this == kLoad2) {
            //Moving up to load 0 or 1
            if(other == kLoad0 || other == kLoad1) {
                return MoveType.kUp;
            }
            //Moving down to load 3
            if(other == kLoad3) {
                return MoveType.kDown;
            }
            //Moving up and over to a shooting position
            if(other.isShootPosition()) {
                return MoveType.kUpOver;
            }
        }
        
        //Load 3: lowest loading position
        if(this == kLoad3) {
            //Moving up to another loading position
            if(other.isLoadPosition()) {
                return MoveType.kUp;
            }
            //Moving up and over to a shooting position
            if(other.isShootPosition()) {
                return MoveType.kUpOver;
            }
        }
        
        //Execution should not get here.
        
        return null;
    }
}
