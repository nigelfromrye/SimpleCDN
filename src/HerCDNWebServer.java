import java.util.*;
import java.io.*;
import java.net.*;


public class HerCDNWebServer {
    private static InetAddress IP;
    private static int PORT;
    private static int file_length = 0;
    private static byte[] file_stream;

    public HerCDNWebServer(InetAddress ip, int port) {
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
            System.out.println("HerCDNWebServer running on: " + this.IP + " port:" + this.PORT);

            setFileStream();

            while(true) {
                Socket client = server.accept();
                System.out.println("Client connected: " + client.getInetAddress());

                DataOutputStream outToClient = new DataOutputStream(client.getOutputStream());
                PrintWriter out = new PrintWriter(client.getOutputStream());

//                out.println("HTTP/1.0 200 OK");
//                out.println("Server: Java HTTP Server 1.0");
//                out.println("Date: " + new Date());
//                out.println("Content-type: text/plain");
//                out.println("Content-length: " + file_length);
                //out.println("\r\n\r\n");
                //out.println(); //blank line between headers and content

                outToClient.write(this.file_stream); //write file
                outToClient.flush(); //flush binary output stream buffer
                out.flush(); //flush character output stream buffer
                out.close();
                outToClient.close();
                client.close();

            }
        } catch(Exception e) {
            System.out.println("Error in HerCDNWebServer.run()");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        HerCDNWebServer server = new HerCDNWebServer(InetAddress.getLocalHost(),43213);
        server.run();
    }


}
