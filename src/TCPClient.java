/**
 * 
 */
package cps706;

/**
 * @author Daniel
 *
 */
import java.io.*;
import java.net.*;

/**
 * Created by nigel on 2018-03-29.
 */
public class TCPClient extends Thread{
    private String host, sentence, modifiedSentence;
    private int port;

    public TCPClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.sentence = "";
        this.modifiedSentence = "";
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public void run() {
        try {
            System.out.println("Client running ");
            Socket clientSocket = new Socket(this.host,this.port);

            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.println("Sending to server: " + this.sentence);
            outToServer.writeBytes(sentence + '\n');

            this.modifiedSentence = inFromServer.readLine();
            System.out.println("From Server: " + this.modifiedSentence);
            // send info from server to local dns
            
            System.out.println("Client process finished");
            clientSocket.close();

        } catch (IOException e) {
            System.out.println("Client Exception: " + e);
        }
    }
}

