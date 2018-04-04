package assign;

import java.io.*;

import java.net.*;
import java.util.ArrayList;

/**
 * @author Daniel Gomez, Nigel Fernandes, Tenzin Kunhken
 * localDNS contacts hisDNS and herDNS with udp links
 */
public class localDNS extends Thread {
    private final int port = 6788;
    static byte[] receiveData;
    static byte[] sendData;
    static ArrayList<data> mem = new ArrayList<data>();
    
    /**
     * Starts a memory list containing manditory info
     * creates receiver and sender arrays
     */
    public localDNS() {
    	receiveData = new byte[1024];
    	 sendData= new byte[1024];
    	 mem.add(new data("herCDN.com","NSherCDN.com","NS"));
    	 mem.get(0).addLine("NSherCDN.com","IPher","A");
    	 mem.add(new data("hiscinema.com","NShiscinema.com","NS"));
    	 mem.get(0).addLine("NShiscinema.com","IPhis","A");
    }

    /**
     * @return port to client
     */
    public int getPort() {
        return this.port;
    }
    /*
     * start server and waits for response from client to search
     */
    public void run() {
        try {
            System.out.println("Server started on port: " + this.port);
            //used to be able to use writeObject command which serializes objects
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            
            DatagramSocket serverSocket = new DatagramSocket(this.port);
            //remember to remove this
            data example = new data("www.herCDN.com","192.168.0.12","A");
            oos.writeObject(example);
            oos.flush();
            //data is now in byte array sendData
            sendData= baos.toByteArray();
                        
            while(true) {
            	DatagramPacket receivePacket =
            			new DatagramPacket(receiveData, receiveData.length);
            			serverSocket.receive(receivePacket);
            			String sentence = new String(receivePacket.getData());
            			InetAddress IPAddress = receivePacket.getAddress();
            			int port = receivePacket.getPort();
            			//perform a search of memory with sentance
            			//then perform searchs in other servers
            			//return info to client
            		      DatagramPacket packet = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            		      serverSocket.send(packet);
            			
            			
            }
        } catch (IOException e) {
            System.out.println("Server Exception: " + e);
        }
    }
}
