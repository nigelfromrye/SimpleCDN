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
import java.util.*;


public class Main {

    public static void main(String[] args) {
        hiscinema server = new hiscinema();
        // ip is local host
        TCPClient client = new TCPClient("192.168.0.12", server.getPort());

        Scanner sc = new Scanner(System.in);


        try {
            server.start();

            if(server.isAlive()) {
            	//remove string input
                System.out.println("Enter a string");
                String sentence = sc.nextLine();
    
                client.setSentence(sentence);
                client.start();
                sc.close();

            }
        } catch (Exception e) {
            System.out.println("Main Exception: " + e);
        }
    }
}