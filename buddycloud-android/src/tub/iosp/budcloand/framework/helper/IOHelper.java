package tub.iosp.budcloand.framework.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.format.DateFormat;

public class IOHelper {
	private static final String encoding = "UTF-8";
	public enum DateEncodings {ISO_8601}

	public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
	
	public static String streamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is, "UTF-8").useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
	public static String makeQuery(String name, String value, String encoding) throws UnsupportedEncodingException{
		return URLEncoder.encode(name, encoding) + "=" + URLEncoder.encode(value, encoding);
	}
	
	public static String dateToString(Date date){
		String str = format.format(date);
		return str;
	}
	
	public static Date stringToDate(String iso_8601) throws ParseException{
		Date date = null;
		date = format.parse(iso_8601);
		return date;
	}
}
