package tub.iosp.budcloand.activities;

import java.util.Date;
import java.util.List;

import org.json.JSONException;

import tub.iosp.budcloand.R;
import tub.iosp.budcloand.framework.exceptions.BCIOException;
import tub.iosp.budcloand.framework.helper.CachedShowPostHelper;
import tub.iosp.budcloand.framework.helper.HttpClientHelper;
import tub.iosp.budcloand.framework.helper.IOHelper;
import tub.iosp.budcloand.framework.helper.ShowPostsHelper;
import tub.iosp.budcloand.framework.types.BCItem;
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
	private BCItem post;
	
	private ShowPostsHelper helper;
	
	private ImageView showpostAvatar,newcommentAvatar;
	private TextView showpostAuthor,showpostUpdated;
	private LinearLayout showpostContent;
	private ListView commentListView;
	private Button newcommentSubmit;
	private EditText newcommentContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_showpost);
		
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		String JsonText = bundle.getString("post");
		
		try {
			post = new BCItem(JsonText,HttpClientHelper.INSTANCE.getCurrentChannel());
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		if(post == null){
			//TODO: UI part--when not get post correctly
			return;
		}
		
		this.helper = new CachedShowPostHelper(post);
		
		List<BCItem> list = this.helper.getComments();
		if(list == null) Log.e(TAG, "commentlist wwas null");
		else{
			for (BCItem item : list ){
				Log.e(TAG, item.getContent());
			}
		}
		
		
		commentListView = (ListView)findViewById(R.id.showpost_commentlist);
		
		Log.v(TAG,"showing post:"+post.getContent());
		
		LayoutInflater inflater = LayoutInflater.from(this);
		RelativeLayout footer = (RelativeLayout) inflater.inflate(R.layout.activity_showpost_footer, null);
		RelativeLayout header = (RelativeLayout) inflater.inflate(R.layout.activity_showpost_header, null);
		
		showpostAvatar = (ImageView)header.findViewById(R.id.showpost_avatar);
		showpostAuthor = (TextView)header.findViewById(R.id.showpost_author);
		showpostContent = (LinearLayout)header.findViewById(R.id.showpost_content);
		showpostUpdated = (TextView)header.findViewById(R.id.showpost_updated);
		
		newcommentAvatar = (ImageView)footer.findViewById(R.id.newcomment_avatar);
		newcommentSubmit = (Button)footer.findViewById(R.id.newcomment_submit);
		newcommentContent = (EditText)footer.findViewById(R.id.newcomment_editText);
		
		newcommentSubmit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				String newcomment = newcommentContent.getEditableText().toString();
				if(newcomment != null && newcomment.compareTo("") != 0){
					BCItem item = null;
					
					try {
						item = HttpClientHelper.INSTANCE.postComment(HttpClientHelper.INSTANCE.getCurrentChannel(), newcomment, post.getRemoteId());
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
		java.util.Date date = post.getUpdated();
		
		showpostUpdated.setText(IOHelper.dateToString(date));
		TextView postContent = (TextView) showpostContent.findViewById(R.id.showpost_contentText);
		postContent.setText(post.getContent());
		
		commentListView.addFooterView(footer);
		commentListView.addHeaderView(header);
		
		HttpClientHelper.INSTANCE.loadBitmap(post.getAuthor(), showpostAvatar, 50, 50);
		HttpClientHelper.INSTANCE.loadBitmap(HttpClientHelper.INSTANCE.getHomeChannel(),newcommentAvatar,50,50);
		
		if (helper.getCommentCount() > 0) {
			BCCommentListAdapter adapter = new BCCommentListAdapter(this,
					helper);
			commentListView.setAdapter(adapter);
		}
	}
	
}