/**
 * 
 */
package assign;

import java.util.ArrayList;

/**
 * @author Daniel
 *
 */
public class data implements java.io.Serializable{
// ex. www.ieft.org: type A, class IN
// name, value, type
private static String name = "";
private static String value = "";
private static String type = "";
private static ArrayList<data> extra = new ArrayList<data>();
// type A name is hostname and value is IP
//type NS name is ALIAS name and value is real name
// then for NS add new type A
public data(String hostname, String ip, String t) {
	name = hostname;
	value = ip;
	type = t;
}
public static String getName () {
	return name;
}
public static String getValue () {
	return value;
}
public static String getType () {
	return type;
}
//in case of NS and R types
public static void addLine(String hostname, String ip, String t) {
	if(!type.equals("A")) {
		extra.add(new data(hostname,ip,t));
	}
}
public static void printer() {
	System.out.println(name+" "+value+" "+type);
	if(!extra.isEmpty()) {
		for(data line: extra) {
			System.out.println(line.name+" "+line.value+" "+line.type);
		}
	}
}
}
