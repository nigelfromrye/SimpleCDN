/**
 * 
 */
package assign;

/**
 * @author Daniel
 *
 */
import java.io.*;
import java.net.*;
/**
 * Created by nigel on 2018-03-29.
 */
public class hiscinema extends Thread {
    private String clientSentence, capatilizedSentence;
    private final int port = 6789;

    public hiscinema() {
        this.clientSentence = "";
        this.capatilizedSentence = "";
    }

    public int getPort() {
        return this.port;
    }

    public void run() {
        try {
            System.out.println("Server started on port: " + this.port);
            ServerSocket welcomeSocket = new ServerSocket(port);

            while(true) {
                Socket connectionSocket = welcomeSocket.accept();
                System.out.println("Connection Accepted");

                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                this.clientSentence = inFromClient.readLine();
                System.out.println("Client sent: " + this.clientSentence);

                this.capatilizedSentence = "hiscinema.com" + '\n';

                System.out.println("Sending to client: " + this.capatilizedSentence);
                outToClient.writeBytes(capatilizedSentence);
            }
        } catch (IOException e) {
            System.out.println("Server Exception: " + e);
        }
    }
}
