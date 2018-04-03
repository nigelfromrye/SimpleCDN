package assign;
/**
 * @author Daniel
 *
 */

import java.io.*;

import java.net.*;
import java.util.ArrayList;
/**
 * Created by nigel on 2018-03-29.
 */
public class hisDNS extends Thread {
    private final int port = 6788;
    static byte[] receiveData;
    static byte[] sendData;
    static ArrayList<data> mem = new ArrayList<data>();
    public hisDNS() {
    	receiveData = new byte[1024];
    	 sendData= new byte[1024];
    	 mem.add(new data("herCDN.com","NSherCDN.com","NS"));
    	 mem.get(0).addLine("NSherCDN.com","IPher","A");
    	 mem.add(new data("hiscinema.com","NShiscinema.com","NS"));
    	 mem.get(0).addLine("NShiscinema.com","IPhis","A");
    }

    public int getPort() {
        return this.port;
    }

    public void run() {
        try {
            System.out.println("Server started on port: " + this.port);
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            
            DatagramSocket serverSocket = new DatagramSocket(6788);
            data example = new data("www.herCDN.com","192.168.0.12","A");
            oos.writeObject(example);
            oos.flush();
            byte[] Buf= baos.toByteArray();
                        
            while(true) {
            	DatagramPacket receivePacket =
            			new DatagramPacket(receiveData, receiveData.length);
            			serverSocket.receive(receivePacket);
            			String sentence = new String(receivePacket.getData());
            			InetAddress IPAddress = receivePacket.getAddress();
            			int port = receivePacket.getPort();
            			
            		      DatagramPacket packet = new DatagramPacket(Buf, Buf.length, IPAddress, port);
            		      serverSocket.send(packet);
            			
            			
            }
        } catch (IOException e) {
            System.out.println("Server Exception: " + e);
        }
    }
}
