package org.team751.util;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 * This subsystem-like class keeps track of the robot's location relative to
 * its starting point.
 * @author Sam Crow
 */
public class Navigator {
    
    
    private JSONObject defaultConfig;
    
    {
        try {
            defaultConfig = new JSONObject();
            
            JSONObject accel = new JSONObject()
            .put("module", 1)
            .put("range", "4G")
                    ;
            
            JSONObject gyro = new JSONObject()
                    .put("module", 1)
                    .put("channel", 1);
            
            JSONObject leftEncoder = new JSONObject()
                    .put("module", 1)
                    .put("channelA", 1)
                    .put("channelB", 2);
            
            defaultConfig.put("accel", accel);
            defaultConfig.put("gyro", gyro);
            defaultConfig.put("leftEncoder", leftEncoder);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
