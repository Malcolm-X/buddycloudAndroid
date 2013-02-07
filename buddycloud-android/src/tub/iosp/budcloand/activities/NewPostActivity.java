package tub.iosp.budcloand.activities;

import tub.iosp.budcloand.R;
import tub.iosp.budcloand.framework.exceptions.BCIOException;
import tub.iosp.budcloand.framework.helper.CachedNewPostActivityHelper;
import tub.iosp.budcloand.framework.helper.HttpClientHelper;
import tub.iosp.budcloand.framework.helper.NewPostActivityHelper;
import tub.iosp.budcloand.framework.types.BCItem;
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
	
	private ImageView avatar;
	private EditText text;
	private Button submit;
	private Button cancel;
	
	private NewPostActivityHelper helper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_newpost);
		
		helper = new CachedNewPostActivityHelper();
		
		avatar = (ImageView)findViewById(R.id.newpost_avatar);
		text  = (EditText)findViewById(R.id.newpost_editText);
		submit = (Button)findViewById(R.id.newpost_submit);
		cancel = (Button)findViewById(R.id.newpost_cancel);
		
		HttpClientHelper.INSTANCE.loadBitmap(HttpClientHelper.INSTANCE.getHomeChannel(),avatar,70,70);
		
		submit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				String content = text.getEditableText().toString();
				if(content != null && content.compareTo("") != 0){
					BCItem newPost = null;
					
					try {
						newPost = helper.post(HttpClientHelper.INSTANCE.getHomeChannel(), content, null);
					} catch (BCIOException e) {
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
