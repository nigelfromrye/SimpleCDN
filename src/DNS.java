import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by nigel on 2018-04-06.
 */
abstract class DNS {
    private ArrayList<DNSRecord> record;
    private InetAddress IP;
    private int PORT;
    private DatagramSocket socket;
    private byte[] receiveData;
    private byte[] sendData;

    public DNS(InetAddress ip,int port) {
        this.IP = ip;
        this.PORT = port;
        record = new ArrayList<DNSRecord>();
        this.socket = null;
    }

    public DatagramSocket getSocket() {
        return this.socket;
    }

    public int getPORT() {
        return this.PORT;
    }

    public InetAddress getIP(){
        return this.IP;
    }

    public void insertRecord(String name, String value,String type, DNSRecord link) {
        this.record.add(new DNSRecord(name,value,type,link));
    }

    public DNSRecord findRecord(String ip, String type) {
        DNSRecord found = null;
        System.out.println(type.equals("V\n"));
        for(DNSRecord r : this.record) {
            DNSRecord link = r.getLink();
            if(r.getName().equals(ip)) {
                if(r.getType().equals(type)) {
                    found = r;
                } else {
                    if(link != null && link.getType().equals(type)) {
                        found = r;
                    }
                }
            }
        }
        return found;
    }

    public String parseRequest(String data) {

        return data;
    }

    public void sendResponse(byte[] data, InetAddress ip, int port) {
        try {
            if(this.socket != null) {
                System.out.println("Sending response");
                DatagramPacket response = new DatagramPacket(data,data.length,ip,port);
                this.socket.send(response);
            } else {
                System.out.println("No socket exists");
            }
        } catch(Exception e) {
            System.out.println("Error in writing response");
        }
    }

    abstract void processData(String data, DatagramPacket sender);

    public void run() {
        try {
            socket = new DatagramSocket(this.PORT);
            receiveData = new byte[1024];
            sendData = new byte[1024];
            System.out.println("DNS running on Port: " + PORT);

            while(true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
                socket.receive(receivePacket);

                String data = parseRequest(new String(receivePacket.getData()));
                this.processData(data,receivePacket);
            }
        } catch(Exception e) {
            System.out.println("Error in DNS.run()");
            e.printStackTrace();
        }
    }

}
