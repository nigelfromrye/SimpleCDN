package assign;


import java.io.*;
import java.net.*;
import java.util.*;

/**
 * @author Daniel Gomez, Nigel Fernandes, Tenzin Kunhken
 * Starting program
 */
public class Main {

    /**
     * Starts Client actions and first server, while waiting for final response
     * @param args
     */
    public static void main(String[] args) {
        hiscinema server = new hiscinema();
        // ip is local host
        TCPClient client = new TCPClient("192.168.0.12", server.getPort());

        try {
            server.start();

            if(server.isAlive()) {
            	System.out.println("Client starting"); 
                client.start();
               }
        } catch (Exception e) {
            System.out.println("Main Exception: " + e);
        }
    }
}