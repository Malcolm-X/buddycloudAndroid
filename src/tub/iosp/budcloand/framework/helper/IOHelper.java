package tub.iosp.budcloand.framework.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class IOHelper {
	private static final String encoding = "UTF-8";
	
	public static String streamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is, "UTF-8").useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
	public static String makeQuery(String name, String value, String encoding) throws UnsupportedEncodingException{
		return URLEncoder.encode(name, encoding) + "=" + URLEncoder.encode(value, encoding);
		
	}
}
