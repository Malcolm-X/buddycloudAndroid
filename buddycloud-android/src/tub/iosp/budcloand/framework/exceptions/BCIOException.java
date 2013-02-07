package tub.iosp.budcloand.framework.exceptions;

import java.io.IOException;


public class BCIOException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5843523229171335542L;

	public BCIOException(String message){
		super(message);
	}
	
	public BCIOException(){
		super();
	}
	
	
}
