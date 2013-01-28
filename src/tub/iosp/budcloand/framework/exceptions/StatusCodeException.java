package tub.iosp.budcloand.framework.exceptions;


/**
 * This Exception is thrown when the Server didnt respond with the correct status code (get:200, post:201)
 * @author Malcolm-X
 */
public class StatusCodeException extends BCIOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6602884386503560599L;

	/**
	 * @param statuscode The response statuscode.
	 * @param message The response status message.
	 */
	public StatusCodeException(int statuscode, String message){
		super("Server Responded with: " + statuscode + " : "+ message);
	}
	
	/**
	 * @param statuscode The response statuscode.
	 * @param message The response status message.
	 * @param expected The expected statuscode.
	 */
	public StatusCodeException(int statuscode, String message, int expected){
		super("Server Responded with: " + statuscode + " : "+ message + " Expected was: " + expected);
	}
	
	/**
	 * @param statuscode The response statuscode.
	 * @param message The response status message.
	 * @param expected The expected statuscode.
	 * @param successMsg The expected response status message.
	 */
	public StatusCodeException(int statuscode, String message, int expected, String successMsg){
		super("Server Responded with: " + statuscode + " : "+ message + " - Expected was: " + expected + " : " + successMsg);
	}
}
