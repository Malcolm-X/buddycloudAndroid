package tub.iosp.budcloand.framework.exceptions;

import java.io.IOException;


// TODO: Auto-generated Javadoc
/**
 * The Class BCIOException is the supertype of all Exceptions thrown
 * by the buddycloud android framework. 
 */
public class BCIOException extends IOException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5843523229171335542L;

	/**
	 * Instantiates a new bCIO exception with the given message.
	 *
	 * @param message the message
	 */
	public BCIOException(String message){
		super(message);
	}
	
	/**
	 * Instantiates a new bCIO exception. with the standart IOExceptions message
	 */
	public BCIOException(){
		super();
	}
	
	
}
