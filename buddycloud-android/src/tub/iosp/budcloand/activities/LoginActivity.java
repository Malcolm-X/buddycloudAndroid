package tub.iosp.budcloand.activities;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import tub.iosp.budcloand.R;
import tub.iosp.budcloand.framework.control.BasicActivityHelper;
import tub.iosp.budcloand.framework.control.BasicLoginActivityHelper;
import tub.iosp.budcloand.framework.control.BasicSearchActivityHelper;
import tub.iosp.budcloand.framework.control.CachedBasicActivityHelper;
import tub.iosp.budcloand.framework.control.SearchActivityHelper;
import tub.iosp.budcloand.framework.control.database.DatabaseHelper;
import tub.iosp.budcloand.framework.control.http.HttpClientHelper;
import tub.iosp.budcloand.framework.exceptions.BCLogInException;
import tub.iosp.budcloand.framework.model.BCSubscribtion;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * LoginActivity.java
 * 
 * implementing a login UI
 *
 */

public class LoginActivity extends Activity {
	
	/** The tag. */
	private final String TAG = "LoginActivity";
	
	/*Views in the page*/
	/** The username box. */
	private EditText usernameBox;
	
	/** The password box. */
	private EditText passwordBox;
	
	/** The login button. */
	private Button loginButton;
	
	/*helper to handle authentication*/
	/** The helper. */
	private BasicLoginActivityHelper helper;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		
	
		/*initial the http network manager and database*/
		HttpClientHelper.INSTANCE.setContext(this);
		DatabaseHelper.INSTANCE.init(this, null);
		
		if(!HttpClientHelper.INSTANCE.checkNetworkInfo()){
			Intent intent = new Intent(getApplicationContext(),
					BasicActivity.class);
			Bundle bundle = new Bundle();
			// pass-in the login username and its passwort
			String lastuser = this.getLastUser();
			bundle.putString("username", lastuser);
			bundle.putString("password", "");
			intent.putExtras(bundle);
			if (lastuser != null && lastuser.compareTo("") != 0) {
				String[] tmp = lastuser.split("@");
				HttpClientHelper.INSTANCE.init(tmp[1], tmp[0], "");
				HttpClientHelper.INSTANCE.setCurrentChannel(lastuser);
				HttpClientHelper.INSTANCE.setHomeChannel(lastuser);

				startActivity(intent);
			}
		}
		
		/*find views*/
		usernameBox = (EditText)findViewById(R.id.e_mail_textbox);
		passwordBox = (EditText)findViewById(R.id.password_textbox);
		loginButton = (Button)findViewById(R.id.login_button);
		
		/*TODO: to be removed*/
		usernameBox.setText("ywison@buddycloud.org");
		passwordBox.setText("111112");
		
		loginButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				/*get the user input*/
				String username = usernameBox.getEditableText().toString();
				String password = passwordBox.getEditableText().toString();
				
				/*check if fields are empty*/
				if(username == null || username.compareTo("") == 0){
					Toast.makeText(getApplicationContext(), "Email Address cannot be empty!", Toast.LENGTH_SHORT).show();
				}
				
				if(password == null || password.compareTo("") == 0){
					Toast.makeText(getApplicationContext(), "Password cannot be empty!", Toast.LENGTH_SHORT).show();
				}
				
				/*check if username match the email address pattern*/
				String[] result = checkUsername(username);
				
				if(result == null){
					Toast.makeText(getApplicationContext(), "Username must be an Email Address!", Toast.LENGTH_SHORT).show();
				}

				else {
					if(!HttpClientHelper.INSTANCE.checkNetworkInfo()){
						Toast.makeText(getApplicationContext(), "No Internet connection!", Toast.LENGTH_SHORT).show();
					}
					else {
						helper = new BasicLoginActivityHelper();
						boolean login = false;
						try {
							login = helper
									.logIn(result[1], result[0], password);
						} catch (BCLogInException e) {
							e.printStackTrace();
						}
						if (login) {
							String lastUser = getLastUser();
							if (lastUser != null
									&& lastUser.compareTo(username) != 0) {
								DatabaseHelper.INSTANCE.resetDB();
								Log.d(TAG, "database reset due to user change!");
							}
							HttpClientHelper.INSTANCE.setHomeChannel(username);
							HttpClientHelper.INSTANCE.setPassword(password);
							HttpClientHelper.INSTANCE.setCurrentChannel(username);
							setLastUser(username);

							/* turn to basic activity when the button is clicked */
							Intent intent = new Intent(getApplicationContext(),
									BasicActivity.class);
							Bundle bundle = new Bundle();
							// pass-in the login username and its passwort
							bundle.putString("username", username);
							bundle.putString("password", password);
							intent.putExtras(bundle);

							startActivity(intent);
						} else {
							Toast.makeText(getApplicationContext(),
									"Wrong username or password!",
									Toast.LENGTH_SHORT).show();
						}
					}
				}
			}

		});
	}
	
	
	/**
	 * check if the given username match an Emailaddress pattern.
	 *
	 * @param username the username
	 * @return the string[]
	 */
	private String[] checkUsername(String username){
		Pattern p=Pattern.compile("\\w+@(\\w+\\.)+[a-z]{2,3}");
		if(p.matcher(username).matches()){
			String[] result = username.split("@");
			return result;
		}
		else
			return null;
	}
	
	/**
	 * Read the last logged user from file.
	 *
	 * @return the last logged user if exist, null otherwise
	 */
	private String getLastUser(){
		String result = null;
		
		try {
			FileInputStream in = this.openFileInput("lastuser");
			byte[] buffer = new byte[256];
			if(in != null){
				int length = in.read(buffer);
				if(length>0){
					byte[] tmp = new byte[length];
					for(int i=0;i<length;++i)
						tmp[i]=buffer[i];
					result = new String(tmp);
					Log.d(TAG,"get last user:"+result+",length:"+length);
					
				}
				in.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * write the last logged user into file.
	 *
	 * @param user the user
	 * @return true if write successfully
	 */
	private boolean setLastUser(String user){
		try {
			FileOutputStream out = this.openFileOutput("lastuser", MODE_PRIVATE);
			byte[] buffer = user.getBytes();
			out.write(buffer);
			out.close();
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;
		
	}

}
