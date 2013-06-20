package tub.iosp.budcloand.framework.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// TODO: Auto-generated Javadoc
/**
 * The Class IOHelper. 
 * This Class contains usefull static methods to support IO-operations.
 * E.g. converting from one format to another
 */
public class IOHelper {
	
	/** The Constant encoding. Used for logging */
	private static final String encoding = "UTF-8";
	
	/**
	 * The Enum DateEncodings contains all known date formats.
	 */
	public enum DateEncodings {

/** The IS o_8601. */
ISO_8601}

	/** The dateformat. (ISO_8601 */
	public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	/**
	 * Stream to string. Convert a Stream into a String
	 *
	 * @param is the InputStream to convert
	 * @return the result String
	 */
	public static String streamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is, "UTF-8").useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
	/**
	 * Make query. Creates and UTF-8 encoded name-value pair to use in URL queries.
	 *
	 * @param name the name
	 * @param value the value
	 * @param encoding the encoding, must be known in java.net.URLEncoder
	 * @return the encoded query element
	 * @throws UnsupportedEncodingException if the encoding is not known
	 */
	public static String makeQuery(String name, String value, String encoding) throws UnsupportedEncodingException{
		return URLEncoder.encode(name, encoding) + "=" + URLEncoder.encode(value, encoding);
	}
	
	/**
	 * Date to string. Parse a given date to the standart buddycloud dateformat
	 *
	 * @param date the date
	 * @return the ISO8601 compliant String representing the date
	 */
	public static String dateToString(Date date){
		String str = format.format(date);
		return str;
	}
	
	/**
	 * String to date. Parses a String in the standard buddycloud format into a valid date
	 *
	 * @param iso_8601 the iso_8601 compliant String (buddycloud version)
	 * @return the date 
	 * @throws ParseException if the date could not be parsed
	 */
	public static Date stringToDate(String iso_8601) throws ParseException{
		Date date = null;
		date = format.parse(iso_8601);
		return date;
	}
	
	
	/**
	 * fetch url connection from a string.
	 *
	 * @param htmlStr String containing url
	 * @return list of matched url
	 */
	public static List<String> getUrlFromText(String htmlStr){
		List<String> list = new ArrayList<String>();
        String regexp
            = "(((http|ftp|https|file)://)|((?<!((http|ftp|https|file)://))www\\.))" 
            + ".*?"                                                                   
            + "(?=(&nbsp;|\\s|　|<br />|$|[<>]))";                                     
        Pattern pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(htmlStr);
        StringBuffer stringbuffer = new StringBuffer();
        while(matcher.find()){
            String url = matcher.group().substring(0, 3).equals("www") ? "http://" + matcher.group() : matcher.group();
            list.add(url);
        }
        return list;
	}
	
	/**
	 * check whether the url is for image.
	 *
	 * @param url : the url need to be checked
	 * @return true, if is image url
	 */
	public static boolean isImageUrl(String url){
		String regexp = ".*(jpg|png|bmp)";
		Pattern pattern = Pattern.compile(regexp,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(url);
		if(matcher.find())
			return true;
		else
			return false;
	}
}
