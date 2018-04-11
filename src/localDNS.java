import javax.sound.midi.Soundbank;
import javax.xml.transform.sax.SAXSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.*;
import java.net.*;

/**
 * Created by nigel on 2018-04-06.
 */
public class LocalDNS extends DNS {
    public LocalDNS() throws  Exception{
        super(InetAddress.getLocalHost(),40370);
    }

    public void processData(String data,DatagramPacket sender) {
        System.out.println("processing data: " + data);
        String[] args = data.split(": ");

        String[] tempQuery = args[0].split("/");
        String queryIP = tempQuery[0].trim();

        String[] tempType = args[1].split(" ");
        String type = tempType[1].trim();

        DNSRecord result = findRecord(queryIP,type);
        if(result != null) {
            // check other dns 's
            result.print();
            byte[] resultToBytes = DNSRecord.serialize(result);
            sendResponse(resultToBytes, sender.getAddress(), sender.getPort());
        } else {
            String na = "not found";
            // now check other DNS's
            sendResponse(na.toUpperCase().getBytes(),sender.getAddress(), sender.getPort());
        }
    }


    public static void main(String[] args) throws Exception{
        LocalDNS dns = new LocalDNS();
        dns.insertRecord("hiscinema.com","NShiscinema.com","NS",
                new DNSRecord("NShiscinema.com","192.his","A",null));
        dns.insertRecord("herCDN.com","NSherCDN.com","NS",
                new DNSRecord("NSherCDN.com","192.her","A",null));
        dns.insertRecord("video.hiscinema.com","herCDN.com","V",
                new DNSRecord("herCDN.com","www.herCDN.com","CN",
                        new DNSRecord("www.herCDN.com","192.vid.her","A",null)));
        dns.run();
    }
}
