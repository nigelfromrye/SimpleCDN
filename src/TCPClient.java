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
    private static byte[] sendData = new byte[1024];
    private static byte[] receiveData = new byte[1024];
  

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
        	//start tcp connection with hiscinema
            System.out.println("Client running ");
            Socket clientSocket = new Socket(this.host,this.port);
            
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.println("Sending to server: " + this.sentence);
            outToServer.writeBytes(sentence + '\n');

            this.modifiedSentence = inFromServer.readLine();
            System.out.println("From Server: " + this.modifiedSentence);
            // tcp connection complete start udp connection
            // send info from server to local dns
            //start udp connection
            clientSocket.close();
            DatagramSocket clientSocketLocal = new DatagramSocket();
            localDNS local = new localDNS();
            local.start();
            
            //set msg recieved from hiscinema
            sendData = modifiedSentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getLocalHost(),6788);
            clientSocketLocal.send(sendPacket);
            
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            
            clientSocketLocal.receive(receivePacket);


            String modifiedSentence =
            new String(receivePacket.getData());

            System.out.println("FROM SERVER:" + modifiedSentence);
            System.out.println("Client process finished");
            clientSocketLocal.close();

        } catch (IOException e) {
            System.out.println("Client Exception: " + e);
        }
    }
}

