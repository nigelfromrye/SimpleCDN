package assign;


import java.io.*;
import java.net.*;

/**
 * @author Daniel Gomez, Nigel Fernandes, Tenzin Kunhken
 * first tcp connection
 */
public class hiscinema extends Thread {
    private String capatilizedSentence;
    private final int port = 6789;

    /**
     * constructor
     */
    public hiscinema() {
        
    }

    /**
     * @return port number for client
     */
    public int getPort() {
        return this.port;
    }
    /*
     * start server and return a string
     */
    public void run() {
        try {
            System.out.println("Server started on port: " + this.port);
            ServerSocket welcomeSocket = new ServerSocket(port);

            while(true) {
                Socket connectionSocket = welcomeSocket.accept();
                System.out.println("Connection Accepted");
                // no input at the moment
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                //output is name currently set in capatilized Sentance
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                this.capatilizedSentence = "hiscinema.com" + '\n';

                System.out.println("Sending to client: " + this.capatilizedSentence);
                outToClient.writeBytes(capatilizedSentence);
            }
        } catch (IOException e) {
            System.out.println("Server Exception: " + e);
        }
    }
}

