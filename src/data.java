/**
 * 
 */
package cps706;

/**
 * @author Daniel
 *
 */
public class data {
// ex. www.ieft.org: type A, class IN
// name, value, type
private String name = "";
private String value = "";
private String type = "";
// type A name is hostname and value is IP
//type NS name is ALIAS name and value is real name
// then for NS add new type A
public data(String hostname, String ip, String t) {
	name = hostname;
	value = ip;
	type = t;
}


}
