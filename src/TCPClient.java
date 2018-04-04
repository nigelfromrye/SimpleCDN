/**
 * 
 */
/**
 * 
 */
package assign;

import java.io.*;
import java.net.*;

/**
 * @author Daniel Gomez, Nigel Fernandes, Tenzin Kunhken
 * contacts hiscinema and herCDN with tcp links
 * gets information from localDNS
 */
public class TCPClient extends Thread{
    private String host, sentence, modifiedSentence;
    private int port;
    private static byte[] sendData = new byte[1024];
    private static byte[] receiveData = new byte[1024];
  

    /**
     * constructor
     * @param host - ip address
     * @param port - port number
     */
    public TCPClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.sentence = "";
        this.modifiedSentence = "";
        
    }

    /* 
     * start client 
     */
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
            ByteArrayInputStream baos = new ByteArrayInputStream(receiveData);
            ObjectInputStream oos = new ObjectInputStream(baos);
            data rec = (data)oos.readObject();
            // print data info
            rec.printer();
            
             System.out.println("Client process finished");
            clientSocketLocal.close();
            
            //final tcp connection

        } catch (IOException e) {
            System.out.println("Client Exception: " + e);
        }
        catch(ClassNotFoundException e) {
        	
        }
    }
}


