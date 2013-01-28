package tub.iosp.budcloand.activities;

import java.util.List;

import tub.iosp.budcloand.R;
import tub.iosp.budcloand.framework.exceptions.BCIOException;
import tub.iosp.budcloand.framework.helper.HttpClientHelper;
import tub.iosp.budcloand.framework.types.BCItem;
import tub.iosp.budcloand.framework.types.BCPost;
import tub.iosp.budcloand.ui.BCCommentListAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShowPostActivity extends Activity{
	
	private final static String TAG = "ShowPostActivity";
	private BCPost post;
	
	private ImageView showpostAvatar,newcommentAvatar;
	private TextView showpostAuthor,showpostUpdated;
	private LinearLayout showpostContent;
	private ListView commentListView;
	private Button newcommentSubmit;
	private EditText newcommentContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.activity_showpost);
		
		commentListView = (ListView)findViewById(R.id.showpost_commentlist);
		
		
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		String JsonText = bundle.getString("post");
		post = new BCPost(JsonText);
		Log.v(TAG,"showing post:"+post.getContent());
		
		showpostAvatar = (ImageView)findViewById(R.id.showpost_avatar);
		showpostAuthor = (TextView)findViewById(R.id.showpost_author);
		showpostContent = (LinearLayout)findViewById(R.id.showpost_content);
		showpostUpdated = (TextView)findViewById(R.id.showpost_updated);
		newcommentAvatar = (ImageView)findViewById(R.id.newcomment_avatar);
		newcommentSubmit = (Button)findViewById(R.id.newcomment_submit);
		newcommentContent = (EditText)findViewById(R.id.newcomment_editText);
		
		newcommentSubmit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				String newcomment = newcommentContent.getEditableText().toString();
				if(newcomment != null && newcomment.compareTo("") != 0){
					BCItem item = null;
					
					try {
						item = HttpClientHelper.INSTANCE.postComment(HttpClientHelper.INSTANCE.getCurrentChannel(), newcomment, post.getId());
					} catch (BCIOException e) {
						e.printStackTrace();
					}
					if(item != null){
						Intent data = new Intent();
						data.putExtra("tub.iosp.budcloand.activities.newPost", item.getJSONObject().toString());
						setResult(RESULT_OK,data);
						finish();
					}
					else{
						setResult(RESULT_CANCELED,null);
						finish();
					}
				}
				else{
					Toast.makeText(getApplicationContext(), "Comment cannot be empty", Toast.LENGTH_SHORT).show();
				}
			}
			
		});
		
		showpostAuthor.setText(post.getAuthor());
		showpostUpdated.setText(post.getUpdated());
		TextView postContent = new TextView(this);
		postContent.setText(post.getContent());
		showpostContent.addView(postContent);
		
		HttpClientHelper.INSTANCE.loadBitmap(post.getAuthor(), showpostAvatar, 50, 50);
		
		if (post.getCommentCount() > 0) {
			List<BCItem> commentList = post.getCommentList();
			BCCommentListAdapter adapter = new BCCommentListAdapter(this,
					commentList);
			commentListView.setAdapter(adapter);
		}
	}
	
}