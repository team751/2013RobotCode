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
 *
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
     *
     * @return True if the connection was successful, otherwise false
     */
    public boolean tryToConnect() {
        try {
            connection = (SocketConnection) Connector.open("socket://" + pandaboardAddress + ':' + portNumber, Connector.READ_WRITE);

            input = new BufferedReader(new InputStreamReader(connection.openInputStream()));

            output = new PrintStream(connection.openOutputStream());

            return true;

        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        } catch (Exception ex) {
            //No exception in this class should escape up and terminate the main code
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Check if the connection is open, and try to open it if it is not open.
     * Returns true on success or false on failure.
     */
    private boolean checkConnection() {
        try {
            if (connection == null || input == null || !input.ready() || output == null) {
                return tryToConnect();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return tryToConnect();
        }

        return true;
    }

    /**
     * Read data, if any, from the pandaboard
     */
    public void readInput() {

        if (checkConnection()) {
            try {
                String line = input.readLine();
                
                
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

    }
}
