package tub.iosp.budcloand.activities;

import tub.iosp.budcloand.R;
import tub.iosp.budcloand.framework.exceptions.BCIOException;
import tub.iosp.budcloand.framework.helper.HttpClientHelper;
import tub.iosp.budcloand.framework.types.BCItem;
import tub.iosp.budcloand.framework.types.BCPost;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class NewPostActivity extends Activity{
	private final static String TAG = "NewPostActivity";
	
	ImageView avatar;
	EditText text;
	Button submit;
	Button cancel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_newpost);
		
		avatar = (ImageView)findViewById(R.id.newpost_avatar);
		text  = (EditText)findViewById(R.id.newpost_editText);
		submit = (Button)findViewById(R.id.newpost_submit);
		cancel = (Button)findViewById(R.id.newpost_cancel);
		
		submit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String content = text.getEditableText().toString();
				if(content != null && content.compareTo("") != 0){
					BCPost newPost = null;
					
					try {
						
						newPost = HttpClientHelper.INSTANCE.postPost(HttpClientHelper.INSTANCE.getCurrentChannel(), content);
					} catch (BCIOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(newPost != null){
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
		
	}
	
}
