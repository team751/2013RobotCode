package org.team751.util;

/**
 * A data structure that stores a fixed number of double values.
 * 
 * Values can be accessed from any point.
 * 
 * @author Sam Crow
 */
class FixedCapacityDoubleQueue {
    
    /**
     * The values stored
     */
    private double[] values;
    
    /**
     * The number of values in the data structure
     */
    public int length;
    
    /**
     * Construct a queue. Each value will be set to zero
     * @param capacity The number of values that the structure should contain
     */
    public FixedCapacityDoubleQueue(int capacity) {
        
        values = new double[capacity];
        
        //set each value to 0
        for(int i = 0; i < capacity; i++) {
            values[i] = 0;
        }
    }
    
    /**
     * Add a value to the 0th position in the structure. All other values
     * will be shifted up, and the final one will be discarded.
     * @param value The value to add.
     */
    public void enqueue(double value) {
        
        //Shift everything up 1
        for(int i = values.length - 2; i >= 0; i--) {
            values[i + 1] = values[i];
        }
        
        values[0] = value;
    }
    
    /**
     * Get the value at an index
     * @param i the index
     * @return the value
     */
    public double at(int i) {
        return values[i];
    }
    
}
