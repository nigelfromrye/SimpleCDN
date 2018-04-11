import java.io.*;

/**
 * Created by nigel on 2018-04-06.
 */
public class DNSRecord implements Serializable {
    private String name;
    private String value;
    private String type;
    private DNSRecord link;

    public DNSRecord(String name, String value, String type, DNSRecord link){
        this.name = name;
        this.value = value;
        this.type = type;
        this.link = link;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public String getType() { return this.type; }

    public DNSRecord getLink() { return this.link; }

    public static byte[] serialize(DNSRecord record) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out;
        try {
            out = new ObjectOutputStream(bos);

            out.writeObject(record);
            out.flush();

            return bos.toByteArray();
        } catch (Exception e) {
            System.out.printf("Error in DNSRecord.serialize()");
            e.printStackTrace();
            return null;
        }
    }

    public static DNSRecord deserialize(byte[] data) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ObjectInput in = new ObjectInputStream(bis);

            return (DNSRecord) in.readObject();
        } catch (Exception e) {
            System.out.println("Error in DNSRecord.deserialize()");
            e.printStackTrace();
            return null;
        }
    }

    public void print() {
        System.out.println(this.name + " " + this.value + " " + this.type);
        if(this.link != null) {
            this.link.print();
        }
    }

}
