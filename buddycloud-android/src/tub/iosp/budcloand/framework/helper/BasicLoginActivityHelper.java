package tub.iosp.budcloand.framework.helper;

public class BasicLoginActivityHelper {
	
	public BasicLoginActivityHelper(){
	}
	public boolean logIn(String home,String user, String pw) throws BCLogInException {
		return HttpClientHelper.INSTANCE.init(home, user, pw);
		
	}
	
	
}
