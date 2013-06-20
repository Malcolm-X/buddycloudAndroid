package tub.iosp.budcloand.activities;

import java.util.List;

import org.json.JSONException;

import tub.iosp.budcloand.R;
import tub.iosp.budcloand.framework.control.CachedShowPostHelper;
import tub.iosp.budcloand.framework.control.ShowPostsHelper;
import tub.iosp.budcloand.framework.control.adapter.BCCommentListAdapter;
import tub.iosp.budcloand.framework.control.http.HttpClientHelper;
import tub.iosp.budcloand.framework.exceptions.BCIOException;
import tub.iosp.budcloand.framework.helper.IOHelper;
import tub.iosp.budcloand.framework.model.BCItem;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class ShowPostActivity. Show the chosen posts and corresponding comments
 * Allow users to post new comments
 */
public class ShowPostActivity extends Activity{
	
	/** The Constant TAG. */
	private final static String TAG = "ShowPostActivity";
	
	/*post to be shown*/
	/** The post. */
	private BCItem post;
	
	/** The helper. */
	private ShowPostsHelper helper;
	
	/** The newcomment avatar. */
	private ImageView showpostAvatar,newcommentAvatar;
	
	/** The showpost updated. */
	private TextView showpostAuthor,showpostUpdated;
	
	/** The showpost content. */
	private LinearLayout showpostContent;
	
	/** The comment list view. */
	private ListView commentListView;
	
	/** The newcomment submit. */
	private Button newcommentSubmit;
	
	/** The newcomment content. */
	private EditText newcommentContent;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		/*get the chosen post from intent*/
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		String JsonText = bundle.getString("post");
		String parentChannel = bundle.getString("parentChannel");
		
		try {
			post = new BCItem(JsonText,parentChannel);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		if(post == null){
			//TODO: UI part--when not get post correctly
			return;
		}
		
		this.helper = new CachedShowPostHelper(post);
		
		LayoutInflater inflater = LayoutInflater.from(this);
		/*footer is the EditText to edit new comment*/
		RelativeLayout footer = (RelativeLayout) inflater.inflate(R.layout.activity_showpost_footer, null);
		/*header is the chosen post*/
		RelativeLayout header = (RelativeLayout) inflater.inflate(R.layout.activity_showpost_header, null);
		
		showpostAvatar = (ImageView)header.findViewById(R.id.showpost_avatar);
		showpostAuthor = (TextView)header.findViewById(R.id.showpost_author);
		showpostContent = (LinearLayout)header.findViewById(R.id.showpost_content);
		showpostUpdated = (TextView)header.findViewById(R.id.showpost_updated);
		
		newcommentAvatar = (ImageView)footer.findViewById(R.id.newcomment_avatar);
		newcommentSubmit = (Button)footer.findViewById(R.id.newcomment_submit);
		newcommentContent = (EditText)footer.findViewById(R.id.newcomment_editText);
		
		/*show the post*/
		showpostAuthor.setText(post.getAuthor());
		java.util.Date date = post.getUpdated();
		
		showpostUpdated.setText(IOHelper.dateToString(date));
		TextView postContent = (TextView) showpostContent.findViewById(R.id.showpost_contentText);
		postContent.setText(post.getContent());
		
		
		/*fetch image*/
		/*List<String> urlList = IOHelper.getUrlFromText(post.getContent());
		Log.d(TAG,"fetch url "+post.getContent());
		if(urlList != null && !urlList.isEmpty()){
			for(String str : urlList){
				Log.d(TAG,str);
				if(IOHelper.isImageUrl(str)){
					//TODO: retrieve extra image
					ImageView image = new ImageView(this);
					showpostContent.addView(image);
					HttpClientHelper.INSTANCE.loadBitmap(str, image, 80, 80, true);
				}
			}
		}*/
		
		/*load avatar of the post*/
		HttpClientHelper.INSTANCE.loadBitmap(post.getAuthor(), showpostAvatar, 65, 65 ,false);
		HttpClientHelper.INSTANCE.loadBitmap(HttpClientHelper.INSTANCE.getHomeChannel(),newcommentAvatar,50,50,false);
		
		/*the comment list*/
		List<BCItem> list = this.helper.getComments();
		if(list == null || list.isEmpty()){
			/*if list is null, just add the header and footer into view*/
			Log.e(TAG, "commentlist wwas null");
			
			showpostAvatar = (ImageView)header.findViewById(R.id.showpost_avatar);
			showpostAuthor = (TextView)header.findViewById(R.id.showpost_author);
			showpostContent = (LinearLayout)header.findViewById(R.id.showpost_content);
			showpostUpdated = (TextView)header.findViewById(R.id.showpost_updated);
			
			newcommentAvatar = (ImageView)footer.findViewById(R.id.newcomment_avatar);
			newcommentSubmit = (Button)footer.findViewById(R.id.newcomment_submit);
			newcommentContent = (EditText)footer.findViewById(R.id.newcomment_editText);
			
			/*when post new comment*/
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
							/*back to BasicActivity with the new added comment*/
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
			
			ScrollView scroll = new ScrollView(this);
			LinearLayout linear = new LinearLayout(this);
			linear.setOrientation(LinearLayout.VERTICAL);
			linear.addView(header);
			linear.addView(footer);
			scroll.addView(linear);
			scroll.setPadding(0, 50, 0, 0);
			this.setContentView(scroll);
		}
		else{
			/*when the comment list is not null, 
			add the chosen post and new comment Editor as header and footer view of the comment list*/
			this.setContentView(R.layout.activity_showpost);
			
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
			
			
			/*initial comment list*/
			commentListView = (ListView)findViewById(R.id.showpost_commentlist);
			
			Log.v(TAG,"showing post:"+post.getContent());
			
			commentListView.addFooterView(footer);
			commentListView.addHeaderView(header);
			
			
			if (helper.getCommentCount() > 0) {
				BCCommentListAdapter adapter = new BCCommentListAdapter(this,
						helper);
				commentListView.setAdapter(adapter);
			}
		}
	}
	
	/*back to BasicActivity when backkey is pressed*/
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