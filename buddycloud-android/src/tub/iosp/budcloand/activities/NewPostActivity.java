package tub.iosp.budcloand.activities;

/**
 * NewPostActivity.java
 * 
 * allow users to post new Posts
 *
 */

import tub.iosp.budcloand.R;
import tub.iosp.budcloand.framework.control.CachedNewPostActivityHelper;
import tub.iosp.budcloand.framework.control.NewPostActivityHelper;
import tub.iosp.budcloand.framework.control.http.HttpClientHelper;
import tub.iosp.budcloand.framework.exceptions.BCIOException;
import tub.iosp.budcloand.framework.model.BCItem;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * The Class NewPostActivity. 
 * 
 * Allow users to post new posts in current channel.
 */
public class NewPostActivity extends Activity{
	
	/** The Constant TAG. */
	private final static String TAG = "NewPostActivity";
	
	/** The avatar. */
	private ImageView avatar;
	
	/** The text. */
	private EditText text;
	
	/** The submit. */
	private Button submit;
	
	/** The cancel. */
	private Button cancel;
	
	/*helper to support post*/
	/** The helper. */
	private NewPostActivityHelper helper;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.activity_newpost);
		
		helper = new CachedNewPostActivityHelper();
		
		avatar = (ImageView)findViewById(R.id.newpost_avatar);
		text  = (EditText)findViewById(R.id.newpost_editText);
		submit = (Button)findViewById(R.id.newpost_submit);
		cancel = (Button)findViewById(R.id.newpost_cancel);
		
		/*asynchr loading the user's avatar*/		
		HttpClientHelper.INSTANCE.loadBitmap(HttpClientHelper.INSTANCE.getHomeChannel(),avatar,70,70,false);
		
		submit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				String content = text.getEditableText().toString();
				if(content != null && content.compareTo("") != 0){
					BCItem newPost = null;
					
					try {
						newPost = helper.post(HttpClientHelper.INSTANCE.getCurrentChannel(), content, null);
					} catch (BCIOException e) {
						e.printStackTrace();
					}
					if(newPost != null){
						/*back to basic activity with the new added post*/
						Intent data = new Intent();
						data.putExtra("tub.iosp.budcloand.activities.newPost", newPost.getJSONObject().toString());
						setResult(RESULT_OK,data);
						finish();
					}
					else{
						setResult(RESULT_CANCELED,null);
						finish();
					}
				}
				else{
					Toast.makeText(getApplicationContext(), "Cannot post empty content!", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		cancel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED,null);
				finish();
			}
			
		});
		
	}
	
	/*called when the back key is pressed. back to BasicActivity*/
	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			setResult(RESULT_CANCELED,null);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
