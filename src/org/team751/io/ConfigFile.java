package org.team751.io;

import com.sun.squawk.io.BufferedReader;
import com.sun.squawk.microedition.io.FileConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import javax.microedition.io.Connector;
import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 * Each instance of this class is associated with a JSON file in the root
 * directory of the cRIO filesystem. You can read and write the file.
 *
 * @author Sam Crow
 */
public class ConfigFile {

    /**
     * The file. Used to create/delete the file and check if it exists
     */
    protected FileConnection file = null;
    /*
     * Developer note: The FileConnection interface is documented
     * at http://www.blackberry.com/developers/docs/4.2api/javax/microedition/io/file/FileConnection.html
     * (Except that the documentation includes some methods that are not
     * implemented here)
     */
    
    /**
     * The name of the file
     */
    protected String fileName = "";
    
    /**
     * The PrintStream used to write to the file
     */
    protected PrintStream writer = null;
    /**
     * The Reader used to read from the file;
     */
    protected BufferedReader reader = null;
    /**
     * The data stored in the file.
     */
    protected JSONObject data = new JSONObject();

    /**
     * Returns the data from this file that is currently stored in memory.
     * 
     * If the file does not contain any data, or if {@link #read()} has not
     * yet been called, an empty JSONObject will be returned.
     * 
     * You can modify this object. Any changes that you make will be saved
     * when you call {@link #write()}.
     * 
     * @return The configuration
     */
    public JSONObject getData() {
        return data;
    }
    
    /**
     * Construct a file reference with a given name.
     * 
     * You must call open() before calling read() or write().
     *
     * @param name The name of the file
     */
    public ConfigFile(String name) {
        fileName = name;
        

    }
    
    /**
     * Open the file. If this file does not exist, an
     * empty file will be created.
     */
    public void open() {
        try {
            file = (FileConnection) Connector.open("file:///" + fileName, Connector.READ_WRITE);

            if (!file.exists()) {
                System.err.println("Config file " + fileName + " does not exist. Creating an empty file.");
                initEmptyFile();
            }

            if (writer == null) {
                writer = new PrintStream(file.openOutputStream());
            }

            if (reader == null || !reader.ready()) {
                reader = new BufferedReader(new InputStreamReader(file.openInputStream()));
            }

            //If fewer than 2 bytes, re-create it with emtpy JSON data
            if (file.fileSize() < 2) {
                System.err.println("Config file " + fileName + " is less than 2 bytes long. Replacing it with empty JSON.");
                initEmptyFile();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Deletes everything in the file and replaces it with "{}". This is useful
     * when creating a file (to prevent JSONExceptions when first parsing it).
     *
     * This function requires that {@link #file} not be null, and that it be
     * initialized with a valid URI.
     */
    protected final void initEmptyFile() {
        try {
            //Delete the file, then recreate it and make new input/output streams
            if (file.exists()) {

                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
                file.delete();
            }

            file.create();
            writer = new PrintStream(file.openOutputStream());
            reader = new BufferedReader(new InputStreamReader(file.openInputStream()));

            //Write "{}" to the file
            writer.print("{}");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Read data from the file and store it in memory
     */
    public void read() {

        //Read everything from the file
        String allJsonText = "";
        while (true) {
            String line = null;
            try {
                line = reader.readLine();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (line == null) {
                break;
            }
            allJsonText += line;
        }
        try {
            data = new JSONObject(allJsonText);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Write data from memory into the file
     */
    public void write() {
        try {
            
            data.write(new OutputStreamWriter(file.openOutputStream()));
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
