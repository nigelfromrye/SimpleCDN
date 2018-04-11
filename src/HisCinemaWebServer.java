import java.util.*;
import java.io.*;
import java.net.*;

public class HisCinemaWebServer {
    private static InetAddress IP;
    private static int PORT;
    private static int file_length = 0;
    private static byte[] file_stream;

    public HisCinemaWebServer(InetAddress ip, int port) {
        this.IP = ip;
        this.PORT = port;
    }

    public void setFileStream() {
        try {
            URL url = getClass().getResource("index.txt");
            File file = new File(url.getPath());
            file_length = (int)file.length();

            file_stream = new byte[file_length];
            FileInputStream in = new FileInputStream(file);
            in.read(file_stream);
            in.close();

        } catch (Exception e) {
            System.out.println("Error");

        }
    }

    public void run() {
        try {
            SocketAddress address = new InetSocketAddress(this.IP,this.PORT);
            ServerSocket server = new ServerSocket();
            server.bind(address);
            System.out.println("HisCinemaWebServer running on: " + this.IP + " port:" + this.PORT);

            setFileStream();

            while(true) {
                Socket client = server.accept();
                System.out.println("Client connected: " + client.getInetAddress());

                DataOutputStream outToClient = new DataOutputStream(client.getOutputStream());
                PrintWriter out = new PrintWriter(client.getOutputStream());

                outToClient.write(this.file_stream); //write file
                outToClient.flush(); //flush binary output stream buffer
                out.flush(); //flush character output stream buffer
                out.close();
                outToClient.close();
                client.close();

            }
        } catch(Exception e) {
            System.out.println("Error in HisCinemaWebServer.run()");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        HisCinemaWebServer server = new HisCinemaWebServer(InetAddress.getLocalHost(),43212);
        server.run();
    }


}
