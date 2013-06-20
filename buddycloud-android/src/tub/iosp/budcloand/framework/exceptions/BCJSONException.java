package tub.iosp.budcloand.framework.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class BCJSONException.
 */
public class BCJSONException extends BCIOException {
	
	/** uid. */
	private static final long serialVersionUID = -6745244513380250255L;

	/**
	 * Instantiates a new bCJSON exception. With the reason in the message
	 *
	 * @param message the reason
	 */
	public BCJSONException(String message){
		super("JSON-parser failed. Reason: " + message);
	}

	/**
	 * Instantiates a new bCJSON exception. With a standart message
	 */
	public BCJSONException() {
		super("JSON-parser failed. Reason: ");
		// TODO Auto-generated constructor stub
	}
}
