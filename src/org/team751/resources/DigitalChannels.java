package org.team751.resources;

import com.sun.squawk.Field;
import com.sun.squawk.Klass;

/**
 * Stores the assignments of available digital I/O channels in module 2
 * @author Sam Crow
 */
public class DigitalChannels {
    
    /**
     * The number of analog channels that are available on this module
     */
    private static final int CHANNEL_COUNT = 14;
    
    private static final int LOWEST_ALLOWED_CHANNEL = 1;
    
    private static final int HIGHEST_ALLOWED_CHANNEL = 14;
    
    
    //Begin actual channel assignments
    
    static {
        //Confirm that there are no duplicate channels assigned
        
        Klass klass = Klass.asKlass(DigitalChannels.class);
        //Get the count of static fields
        int fieldCount = klass.getFieldCount(true);
        
        //Other than CHANNEL_COUNT, there should be a maximum of FIELD_COUNT fields
        //so the maximum number of fields is CHANNEL_COUNT + 1
        if(fieldCount - 3 > CHANNEL_COUNT) {
            System.err.println((fieldCount - 3)+" digital channels are assigned! The maximum number available on this module is "+CHANNEL_COUNT);
        }
        
        int[] usedChannels = new int[fieldCount - 1];
        
        for(int i = 0; i < fieldCount; i++) {
            //get the i'th static field
            Field field = klass.getField(i, true);
            
            //Ignore the fields CHANNEL_COUNT, LOWEST_ALLOWED_CHANNEL, HIGHEST_ALLOWED_CHANNEL
            if(field.getName().equals("CHANNEL_COUNT") || field.getName().equals("LOWEST_ALLOWED_CHANNEL") || field.getName().equals("HIGHEST_ALLOWED_CHANNEL")) continue;
            
            int channelNumber = (int) field.getPrimitiveConstantValue();
            
            //Verify that this channel number has not yet been used
            for(int j = 0; j < fieldCount - 1; j++) {
                if(usedChannels[j] == channelNumber) {
                    System.err.println("Digital channel number "+channelNumber+" for "+field.getName()+" has already been assigned! Check for duplicates.");
                }
            }
            
            //Verify that this channel is within range
            if(channelNumber > HIGHEST_ALLOWED_CHANNEL) {
                System.err.println("Digital channel number "+channelNumber+" for "+field.getName()+" is greater than the maximum allowed channel ("+HIGHEST_ALLOWED_CHANNEL+")!");
            }
            else if(channelNumber < LOWEST_ALLOWED_CHANNEL) {
                System.err.println("Digital channel number "+channelNumber+" for "+field.getName()+" is less than the minimum allowed channel ("+LOWEST_ALLOWED_CHANNEL+")!");
            }
            
            //Add this number to the set of known numbers
            usedChannels[i] = channelNumber;
        }
        
        System.out.println("Finished validating digital channels");
    }
    
    private DigitalChannels() {}
}
