/**
 * 
 */
package assign;

import java.util.ArrayList;

/**
 * @author Daniel Gomez, Nigel Fernandes, Tenzin Kunhken
 * data class that holds packet information
 */
public class data implements java.io.Serializable{
// ex. www.ieft.org: type A, class IN
// name, value, type
private static String name = "";
private static String value = "";
private static String type = "";
//holds all type A's belonging to NS or R types
private static ArrayList<data> extra = new ArrayList<data>();


/**
 * constructor for data type
 * @param hostname - can by Alias or website name
 * @param ip - can be real name or ip address
 * @param t - type
 */
public data(String hostname, String ip, String t) {
	name = hostname;
	value = ip;
	type = t;
}
/**
 * @return name value
 */
public static String getName () {
	return name;
}
/**
 * @return value if IP or real name
 */
public static String getValue () {
	return value;
}
/**
 * @return type of the data
 */
public static String getType () {
	return type;
}

/**
 * add additional lines that belong with this data object
 * @param hostname - Website name
 * @param ip - ip address
 * @param t - type should be A
 */
public static void addLine(String hostname, String ip, String t) {
	if(!type.equals("A")) {
		extra.add(new data(hostname,ip,t));
	}
}

/**
 * Prints all the information held in the object
 */
public static void printer() {
	System.out.println(name+" "+value+" "+type);
	if(!extra.isEmpty()) {
		for(data line: extra) {
			System.out.println(line.name+" "+line.value+" "+line.type);
		}
	}
}
}
