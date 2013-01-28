package tub.iosp.budcloand.activities;

import tub.iosp.budcloand.R;
import tub.iosp.budcloand.framework.helper.HttpClientHelper;
import tub.iosp.budcloand.framework.types.BCPostList;
import tub.iosp.budcloand.framework.types.BCSubscriptionList;
import tub.iosp.budcloand.ui.BCChannelListAdapter;
import tub.iosp.budcloand.ui.BCPostListAdapter;
import tub.iosp.budcloand.ui.BasicFrameView;
import tub.iosp.budcloand.ui.PostListView;
import tub.iosp.budcloand.ui.PostListView.OnRefreshListener;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * BasicActivity.java
 * 
 * A three-Tab scrollable UI
 * Left panel is the channel list
 * Middle panel is the post list
 * Right panel is the channel information
 *
 */
public class BasicActivity extends Activity {
	
	private final static String TAG = "BasicActivity";
	
	private final int NEW_POST_ACTIVITY_CODE = 1;
	private final int SHOW_POST_ACTIVITY_CODE = 2;
	
	private BCPostList postList;
	//private PostListView postListView;
	private ListView postListView;
	
	/*the basic frame of activities*/
	private BasicFrameView basicFrame;
	
	/*container of the three panels*/
	private LinearLayout container;
	private LinearLayout leftPanel;
	private ListView leftMenu;
	
	/*midPanel consists of topBar and midContent*/
	private LinearLayout midPanel;
	private LinearLayout topBar;
	private LinearLayout midContent;
	private LinearLayout rightPanel;
	
	/*Buttons on top bar*/
	private ImageButton tbShowChannel;
	private ImageButton tbNewComment;
	private ImageButton tbAddPost;
	private ImageButton tbChannelInfo;
	
	private Button findChannel;
	
	
	private int screenWidth;
	private int screenHeight;
	
	int leftPopOut = 0;
	int rightPopOut = 0;
	
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initial();
		this.setContentView(basicFrame);
		
		handler = new Handler();
		
		this.paintLeftPanel();
		
		this.paintPostList(HttpClientHelper.INSTANCE.getHomeChannel());

		this.scrollToMiddle();
	}
	
	public void scrollToMiddle(){
		handler.postDelayed(new Runnable(){
			@Override
			public void run(){
				basicFrame.smoothScrollTo(screenWidth/3*2, 0);
			}
		}, 100);
	}
	
	public void paintLeftPanel(){
		/*Initialize the left subscription panel*/
		Bundle bundle = this.getIntent().getExtras();
		String subInfo = bundle.getString("subInfo");
		BCSubscriptionList subList = new BCSubscriptionList(subInfo);
		
		leftMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView text = (TextView)view.findViewById(R.id.channellist_name);
				String channelAddress = (String) text.getText();
				Log.v(TAG,"changing into channel : "+channelAddress);
				paintPostList(channelAddress);
			}
		});
		
		TextView lefthead_homechannel = (TextView)leftPanel.findViewById(R.id.lefthead_channelName);
		ImageView lefthead_avatar = (ImageView)leftPanel.findViewById(R.id.lefthead_channelAvatar);
		lefthead_homechannel.setText(HttpClientHelper.INSTANCE.getHomeChannel());
		HttpClientHelper.INSTANCE.loadBitmap(HttpClientHelper.INSTANCE.getHomeChannel(), lefthead_avatar, 50, 50);
		RelativeLayout lefthead = (RelativeLayout)leftPanel.findViewById(R.id.lefthead);
		lefthead.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				paintPostList(HttpClientHelper.INSTANCE.getHomeChannel());
				scrollToMiddle();
			}
			
		});
		
		BCChannelListAdapter channelListAdapter = new BCChannelListAdapter(this, R.id.channellist_name, subList);
		leftMenu.setAdapter(channelListAdapter);
	}
	
	public void paintRightPanel(){
		String currentChannel = HttpClientHelper.INSTANCE.getCurrentChannel();
		
		// TODO: complete the rightpanel
	}
	
	public void paintPostList(String channelName){
		this.scrollToMiddle();
		HttpClientHelper.INSTANCE.setCurrentChannel(channelName);
		
		/*get post list*/
		postList = HttpClientHelper.INSTANCE.getPosts(channelName);
		
		if(postList == null){
			
		}
		else{
			/*postListView.setOnRefreshListener(new OnRefreshListener(){
				
				@Override
				public void onRefresh() {
					
					download New Posts
					
					test code
					//postList.add(0, "new Post");
					
					
					handler.postDelayed(new Runnable(){
						@Override
						public void run(){
							postListView.onRefreshComplete();
						}
					}, 2000);
					//postListView.onRefreshComplete();
				}
			});*/
			
			postListView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Log.e(TAG,"post list item clicked, position: "+position+",id: "+id );
					Intent intent = new Intent(getApplicationContext(),ShowPostActivity.class);
					Bundle bundle = new Bundle();
					
					String str = postList.get((int) id).getJSONObject().toString();
					bundle.putString("post", str);
					intent.putExtras(bundle);
					startActivityForResult(intent,SHOW_POST_ACTIVITY_CODE);
				}
				
			});

			BCPostListAdapter adapter = new BCPostListAdapter(this, postList);
			postListView.setAdapter(adapter);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}
	
	public void initial(){
		DisplayMetrics dm = this.getApplicationContext().getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		
		/*inflate the basic frame*/
		basicFrame = (BasicFrameView) LayoutInflater.from(this).inflate(R.layout.basic_frame, null);
		
		/*find views*/
		container = (LinearLayout) basicFrame.findViewById(R.id.container);
		leftPanel = (LinearLayout) container.findViewById(R.id.leftPanel);
		leftMenu = (ListView) basicFrame.findViewById(R.id.leftMenu);
		midPanel = (LinearLayout) container.findViewById(R.id.midPanel);
		topBar = (LinearLayout) midPanel.findViewById(R.id.topbar);
		midContent = (LinearLayout) midPanel.findViewById(R.id.midContent);
		rightPanel = (LinearLayout) container.findViewById(R.id.rightPanel);
		
		tbShowChannel = (ImageButton)topBar.findViewById(R.id.tp_showchannel);
		tbNewComment = (ImageButton)topBar.findViewById(R.id.tp_newcomment);
		tbAddPost = (ImageButton)topBar.findViewById(R.id.tp_addpost);
		tbChannelInfo = (ImageButton)topBar.findViewById(R.id.tp_channelinfo);
		
		findChannel = (Button)leftPanel.findViewById(R.id.findChannel);
		findChannel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		postListView = (ListView)midPanel.findViewById(R.id.postList);
		
		/*set on click listener of topbar Buttons*/
		tbShowChannel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onShowChannel();
			}
		});
		tbNewComment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onNewComment();
			}
		});
		tbAddPost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onAddPost();
			}
		});
		tbChannelInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onChannelInfo();
			}
		});
		
		/*initial the width of three panels in the scroll view*/
		LayoutParams rightPanelLP = (LayoutParams)rightPanel.getLayoutParams();
		rightPanelLP.width = screenWidth/2;
		rightPanel.setLayoutParams(rightPanelLP);
		
		LayoutParams leftPanelLP = (LayoutParams)leftPanel.getLayoutParams();
		leftPanelLP.width = screenWidth/3*2;
		leftPanel.setLayoutParams(leftPanelLP);
		
		LayoutParams midPanelLP = (LayoutParams)midPanel.getLayoutParams();
		midPanelLP.width = screenWidth;
		midPanel.setLayoutParams(midPanelLP);
		
	}
	
	
	/*OnClickListener methods for the topbar Buttons*/
	public void onShowChannel(){
		//todo
		new Handler().postDelayed(new Runnable(){
			@Override
			public void run(){
				
				if(leftPopOut == 0){
					basicFrame.smoothScrollTo(screenWidth/3*2, 0);
				}
				else{
					basicFrame.smoothScrollTo(0, 0);
				}
				leftPopOut = 1 - leftPopOut;
			}
		}, 100);
	}
	
	public void onNewComment(){
		//todo
		Toast.makeText(getApplicationContext(), "Button 'New Comment' is clicked", Toast.LENGTH_SHORT).show();
	}
	
	public void onAddPost(){
		//todo
		Intent intent = new Intent(getApplicationContext(),NewPostActivity.class);
		startActivityForResult(intent, this.NEW_POST_ACTIVITY_CODE);
	}
	
	public void onChannelInfo(){
		//todo
		new Handler().postDelayed(new Runnable(){
			@Override
			public void run(){
				
				if(rightPopOut == 0){
					basicFrame.smoothScrollTo(screenWidth/3*2+screenWidth, 0);
				}
				else{
					basicFrame.smoothScrollTo(screenWidth/3*2, 0);
				}
				rightPopOut = 1 - rightPopOut;
			}
		}, 100);
	}
	
	@Override 
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == this.NEW_POST_ACTIVITY_CODE){
			if(resultCode == this.RESULT_OK){
				//this.postListView.onRefresh();
			}
			else{
				Toast.makeText(getApplicationContext(), "Cannot Post content!", Toast.LENGTH_SHORT).show();
			}
		}
		if(requestCode == this.SHOW_POST_ACTIVITY_CODE){
			
		}
	}

}
