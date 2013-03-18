package org.team751.resources;

import com.sun.squawk.Field;
import com.sun.squawk.Klass;

/**
 * This class stores the IDs of CAN Jaguars on the robot network.
 * Each Jaguar must have a unique ID.
 * Do not use 0 (it is reserved for something).
 * Do not use 1 (it is assigned by default to unconfigured Jaguars).
 * 
 * This class will automatically check for duplicate/zero/one IDs and
 * will print warning messages if any exist.
 * 
 * @author Sam Crow
 */
public class CANJaguarIDs {
    
    //Competition robot drivetrain uses 2, 3, 4, 5, 6 and 7
    public static final int DRIVE_LEFT_A = 2;
    
    public static final int DRIVE_LEFT_B = 3;
    
    public static final int DRIVE_LEFT_C = 4;
    
    public static final int DRIVE_RIGHT_A = 5;
    
    public static final int DRIVE_RIGHT_B = 6;
    
    public static final int DRIVE_RIGHT_C = 7;
    
    
    //Summer robot jaguars
    public static final int SR_DRIVE_LEFT_A = 1;
    public static final int SR_DRIVE_LEFT_B = 2;
    public static final int SR_DRIVE_RIGHT_A = 3;
    public static final int SR_DRIVE_RIGHT_B = 4;
    
    /**
     * Jaguar for the cow rotation motor
     */
    public static final int COW_ROTATE = 8;
    
    /**
     * Jaguar for the pusher that pushes disks from the cow into the shooter
     */
    public static final int PUSHER = 9;
    
    /**
     * Jaguar for the slower shooter wheel that the disk contacts first
     */
    public static final int SHOOTER_FIRST = 10;
    /**
     * Jaguar for the faster shooter wheel that the disk contacts second
     */
    public static final int SHOOTER_SECOND = 11;
    
    static {
        //This code block runs when the class is loaded
        //It verifies that there are no duplicate CAN IDs
        
        //Load the metadata for this class
        Klass klass = Klass.asKlass(CANJaguarIDs.class);
        
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
            int id = (int) field.getPrimitiveConstantValue();
            
            //Warn of hazardous IDs
            if(id < 0) {
                System.err.println("CAN Jaguar "+name+" has a negative value of "+id+"!");
            }
            if(id == 0) {
                System.err.println("CAN Jaguar "+name+" has an ID of zero!");
                
            }
            if(id == 1) {
                System.err.println("CAN Jaguar "+name+" has an ID of one!");
            }
            
            //Check if this ID has already been used
            for(int j = 0; j < staticCount; j++) {
                if(idsUsed[j] == id) {
                    
                    System.err.println("CAN Jaguar "+name+" has a duplicated ID of "+id+"! Check to see if another Jaguar is already using this ID.");
                    
                }
            }
            
            //Add this ID to the used IDs
            idsUsed[i] = id;
        }
        System.out.println("CAN Jaguar ID assignment verification finished");
    }
    
    //Prevent this class from being instantiated
    private CANJaguarIDs() {}
}
