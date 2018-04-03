/**
 * 
 */
package cps706;

/**
 * @author Daniel
 *
 */
/**
 * @author Daniel
 *
 */

import java.io.*;

import java.net.*;
/**
 * Created by nigel on 2018-03-29.
 */
public class localDNS extends Thread {
    private String clientSentence, capatilizedSentence;
    private final int port = 6788;
    static byte[] receiveData;
    static byte[] sendData;
    public localDNS() {
    	receiveData = new byte[1024];
    	 sendData= new byte[1024];
    }

    public int getPort() {
        return this.port;
    }

    public void run() {
        try {
            System.out.println("Server started on port: " + this.port);
            DatagramSocket serverSocket = new DatagramSocket(6788);


            while(true) {
            	DatagramPacket receivePacket =
            			new DatagramPacket(receiveData, receiveData.length);
            			serverSocket.receive(receivePacket);
            			String sentence = new String(receivePacket.getData());
            			InetAddress IPAddress = receivePacket.getAddress();
            			int port = receivePacket.getPort();
            			String capitalizedSentence = sentence.toUpperCase();
            			sendData = capitalizedSentence.getBytes();
            			DatagramPacket sendPacket =
            			new DatagramPacket(sendData, sendData.length, IPAddress, port);
            			serverSocket.send(sendPacket);
            }
        } catch (IOException e) {
            System.out.println("Server Exception: " + e);
        }
    }
}
