import java.io.*;
import java.net.*;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        TCPServer server = new TCPServer();
        TCPClient client = new TCPClient("192.168.16.1", server.getPort());

        Scanner sc = new Scanner(System.in);


        try {
            server.start();

            if(server.isAlive()) {
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
