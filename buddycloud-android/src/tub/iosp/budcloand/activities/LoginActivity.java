package tub.iosp.budcloand.activities;


import java.util.regex.Pattern;

import tub.iosp.budcloand.R;
import tub.iosp.budcloand.framework.helper.BCLogInException;
import tub.iosp.budcloand.framework.helper.BasicLoginActivityHelper;
import tub.iosp.budcloand.framework.helper.DatabaseHelper;
import tub.iosp.budcloand.framework.helper.HttpClientHelper;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
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
	
	private final String TAG = "LoginActivity";
	
	private EditText usernameBox;
	private EditText passwordBox;
	private Button loginButton;
	
	private BasicLoginActivityHelper helper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		DatabaseHelper.INSTANCE.init(this, null);
		
		
		usernameBox = (EditText)findViewById(R.id.e_mail_textbox);
		passwordBox = (EditText)findViewById(R.id.password_textbox);
		loginButton = (Button)findViewById(R.id.login_button);
		
		usernameBox.setText("ywison@buddycloud.org");
		passwordBox.setText("111112");
		
		loginButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				String username = usernameBox.getEditableText().toString();
				String password = passwordBox.getEditableText().toString();
				
				if(username == null || username.compareTo("") == 0){
					Toast.makeText(getApplicationContext(), "Email Address cannot be empty!", Toast.LENGTH_SHORT).show();
				}
				
				if(password == null || password.compareTo("") == 0){
					Toast.makeText(getApplicationContext(), "Password cannot be empty!", Toast.LENGTH_SHORT).show();
				}
				
				String[] result = checkUsername(username);
				
				if(result == null){
					Toast.makeText(getApplicationContext(), "Username must be an Email Address!", Toast.LENGTH_SHORT).show();
				}

				else {
					helper = new BasicLoginActivityHelper();
					boolean login = false;
					try {
						login = helper.logIn(result[1], result[0], password);
					} catch (BCLogInException e) {
						e.printStackTrace();
					}
					if(login){
						HttpClientHelper.INSTANCE.setHomeChannel(username);
						HttpClientHelper.INSTANCE.setPassword(password);
						HttpClientHelper.INSTANCE.setCurrentChannel(username);
						
						/* turn to basic activity when the button is clicked */
						Intent intent = new Intent(getApplicationContext(),
								BasicActivity.class);
						/*Bundle bundle = new Bundle();
						bundle.putString("subInfo", subList.getJSONObject().toString());
						intent.putExtras(bundle);*/
						startActivity(intent);
					}
					else{
						Toast.makeText(getApplicationContext(), "Wrong username or password!", Toast.LENGTH_SHORT).show();
					}

					/*BCSubscriptionList subList = HttpClientHelper.INSTANCE.getSubscribed(
							result[1], result[0], password);
					
					if(subList == null){
						Toast.makeText(getApplicationContext(), "Login failed!", Toast.LENGTH_SHORT).show();
					}
					else{
						HttpClientHelper.INSTANCE.setHomeChannel(username);
						HttpClientHelper.INSTANCE.setPassword(password);
						HttpClientHelper.INSTANCE.setCurrentChannel(username);
						
						 turn to basic activity when the button is clicked 
						Intent intent = new Intent(getApplicationContext(),
								BasicActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("subInfo", subList.getJSONObject().toString());
						intent.putExtras(bundle);
						startActivity(intent);
					}*/
				}
			}
			
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}
	
	private String[] checkUsername(String username){
		Pattern p=Pattern.compile("\\w+@(\\w+\\.)+[a-z]{2,3}");
		if(p.matcher(username).matches()){
			String[] result = username.split("@");
			return result;
		}
		else
			return null;
	}

}
