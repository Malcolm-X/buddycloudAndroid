package tub.iosp.budcloand.framework.exceptions;


// TODO: Auto-generated Javadoc
/**
 * The Class BCLogInException.
 */
public class BCLogInException extends BCIOException {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7665895245191360071L;

	/**
	 * Instantiates a new bC log in exception with no reason.
	 */
	public BCLogInException(){
		super("The Login failed. Reason:");
	}
	
	/**
	 * Instantiates a new bC log in exception with the given reason.
	 *
	 * @param reason the reason why login failed
	 */
	public BCLogInException(String reason){
		super("The Login failed. Reason: " + reason);
	}
}
