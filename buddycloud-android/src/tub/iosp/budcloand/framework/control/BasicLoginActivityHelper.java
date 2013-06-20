package tub.iosp.budcloand.framework.control;

import tub.iosp.budcloand.framework.control.http.HttpClientHelper;
import tub.iosp.budcloand.framework.exceptions.BCLogInException;

/**
 * The Class BasicLoginActivityHelper.
 */
public class BasicLoginActivityHelper {
	
	/**
	 * Instantiates a new basic login activity helper.
	 */
	public BasicLoginActivityHelper(){
	}
	
	/**
	 * Log in.
	 *
	 * @param home the home
	 * @param user the user
	 * @param pw the pw
	 * @return true, if successful
	 * @throws BCLogInException the bC log in exception
	 */
	public boolean logIn(String home,String user, String pw) throws BCLogInException {
		return HttpClientHelper.INSTANCE.init(home, user, pw);
		
	}
	
	/**
	 * On destroy.
	 */
	public void onDestroy(){
		
	}
	
	/**
	 * On pause.
	 */
	public void onPause(){
		
	}
	
	/**
	 * On stop.
	 */
	public void onStop(){
		
	}

	/**
	 * On resume.
	 */
	public void onResume(){
		
	}
	
	/**
	 * On restart.
	 */
	public void onRestart(){
		
	}
	
	
}
