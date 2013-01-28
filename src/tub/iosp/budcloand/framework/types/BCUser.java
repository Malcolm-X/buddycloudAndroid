package tub.iosp.budcloand.framework.types;

public class BCUser{
	BCUserSettings userSettings;
	BCSubscriptionList subsUser;
	
	public BCUser(){
		subsUser = new BCSubscriptionList();
		userSettings = new BCUserSettings();
	}
}
