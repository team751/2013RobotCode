package org.team751.vision;

import com.sun.squawk.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

/**
 * Connects to the Pandaboard over ethernet (TCP) and receives the vision data
 * that it sends.
 * @author Sam Crow
 */
public class VisionInterface {
    
    /**
     * The connection to the Pandaboard
     */
    private SocketConnection connection;
    
    /**
     * The stream reader used to get input
     */
    private BufferedReader input;
    
    /**
     * The stream used to send output
     */
    private PrintStream output;
    
    /**
     * The IP address of the Pandaboard
     */
    private static final String pandaboardAddress = "10.7.51.19";
    
    /**
     * The TCP port number to connect to on the Pandaboard
     */
    private static final int portNumber = 7510;
    
    /**
     * Attempt to connect to the Pandaboard
     */
    public void tryToConnect() {
        try {
            connection = (SocketConnection) Connector.open("socket://"+pandaboardAddress+':'+portNumber, Connector.READ_WRITE);
            
            input = new BufferedReader(new InputStreamReader(connection.openInputStream()));
            
            output = new PrintStream(connection.openOutputStream());
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        catch (Exception ex) {
            //No exception in this class should escape up and terminate the main code
            ex.printStackTrace();
        }
    }
    
    /**
     * Read data, if any, from the pandaboard
     */
    public void readInput() {
        
    }
    
}
