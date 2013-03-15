package org.team751.io;

import java.io.IOException;
import java.io.OutputStreamWriter;
import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 * A ConfigFile that has a default configuration value
 * and automatically writes this default config to the file
 * if the file is nonexistent or empty.
 * 
 * 
 * 
 * @author Sam Crow
 */
public class DefaultableConfigFile extends ConfigFile {
    
    private JSONObject defaultConfig = new JSONObject();
    
    public DefaultableConfigFile(String name, JSONObject defaultConfig) {
        super(name);
        
        this.defaultConfig = defaultConfig;
    }
    
    public void open() {
        super.open();
        try {
            //If the file is devoid of data, and the default config is not,
            //write the default config to the file.
            if(file.fileSize() <= 2 && defaultConfig.length() > 0) {
                
                System.err.println("Config file "+fileName+" is empty of data. Writing its default value.");
                
                defaultConfig.write(new OutputStreamWriter(file.openOutputStream()));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
    
}
