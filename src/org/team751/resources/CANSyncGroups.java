package org.team751.resources;

import com.sun.squawk.Field;
import com.sun.squawk.Klass;

/**
 * This class stores the IDs of CAN sync groups on the robot network.
 * 
 * A sync group is a set of controllers that are commanded to change their output
 * at the same time after each has been given a command in sequence.
 * Jaguars set with the same group
 * will update their output at the same time when
 * {@link edu.wpi.first.wpilibj.CANJaguar#updateSyncGroup(byte)} is called.
 * 
 * Each sync group must have a unique ID.
 * To be safe, it is probably best to not use 0 or 1.
 * (This is only based on superstition. We can definitely change that if
 * documentation or testing says otherwise.)
 * 
 * This class will automatically check for duplicate/zero/one IDs and
 * will print warning messages if any exist.
 * 
 * @author Sam Crow
 */
public class CANSyncGroups {
    
    /**
     * Sync group for the left side of the drivetrain
     */
    public static final byte DRIVETRAIN_LEFT = 2;
    
    /**
     * Sync group for the right side of the drivetrain
     */
    public static final byte DRIVETRAIN_RIGHT = 3;
    
    
    
    
    static {
        //This code block runs when the class is loaded
        //It verifies that there are no duplicate CAN IDs
        
        //Load the metadata for this class
        Klass klass = Klass.asKlass(CANSyncGroups.class);
        
        //Get all the static fields of this class
        int staticCount = klass.getFieldCount(true);
        
        //Create an array of the CAN IDs that have been assigned
        int[] idsUsed = new int[staticCount];
        //Fill it with zeros
        for(int i = 0; i < staticCount; i++) {
            idsUsed[i] = 0;
        }
        
        for(int i = 0; i < staticCount; i++) {
            //Get the static field with this index
            Field field = klass.getField(i, true);
            
            String name = field.getName();
            byte id = (byte) field.getPrimitiveConstantValue();
            
            //Warn of hazardous IDs
            if(id < 0) {
                System.err.println("CAN sync group "+name+" has a negative value of "+id+"!");
            }
            if(id == 0) {
                System.err.println("CAN sync group "+name+" has an ID of zero!");
                
            }
            if(id == 1) {
                System.err.println("CAN sync group "+name+" has an ID of one!");
            }
            
            //Check if this ID has already been used
            for(int j = 0; j < staticCount; j++) {
                if(idsUsed[j] == id) {
                    
                    System.err.println("CAN sync group "+name+" has a duplicated ID of "+id+"! Check to see if another sync group is already using this ID.");
                    
                }
            }
            
            //Add this ID to the used IDs
            idsUsed[i] = id;
        }
        System.out.println("CAN sync group ID assignment verification finished");
    }
    
    //Prevent this class from being instantiated
    private CANSyncGroups() {}
}
