import javax.swing.plaf.basic.BasicToolBarUI;
import java.util.*;
import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) {
        try {
            Socket clientTCPSocket = new Socket(InetAddress.getLocalHost(),43212);

            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientTCPSocket.getInputStream()));
            char[] data = new char[1024];
            inFromServer.read(data,0,data.length);
            StringBuilder builder = new StringBuilder();
            for(char c : data) {
                builder.append(c);
            }
            String line = builder.toString();
            String[] options = line.split("\\r?\\n");

            if(options.length > 1) {
                System.out.println("Please select a video to watch: ");
                int count =0;
                for(String s : options) {
                 System.out.println(count + " for " + s);
                 count++;
                }
                Scanner sc = new Scanner(System.in);
                int input = sc.nextInt();
                sc.close();
                String query = options[input] + ": type V";
                System.out.println("fetching video for " + query);

                System.out.println("Checking Local DNS");
                inFromServer.close();

                DatagramSocket clientUDPSocket = new DatagramSocket(new InetSocketAddress(InetAddress.getLocalHost(),40371));

                InetAddress IPAddress = InetAddress.getLocalHost();

                byte[] sendData;
                byte[] receiveData = new byte[1024];

                sendData = query.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAddress,40370);
                clientUDPSocket.send(sendPacket);

                DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);

                clientUDPSocket.receive(receivePacket);
                DNSRecord result = DNSRecord.deserialize(receivePacket.getData());

                System.out.println("Response: ");
                result.print();

            } else {
                System.out.println("No data read from file");
            }

        } catch (Exception e) {
            System.out.println("Error in Main: ");
            e.printStackTrace();
        }
    }
}
