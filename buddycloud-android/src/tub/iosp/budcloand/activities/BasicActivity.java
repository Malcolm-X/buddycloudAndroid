package tub.iosp.budcloand.activities;

import java.util.ArrayList;
import java.util.List;

import tub.iosp.budcloand.R;
import tub.iosp.budcloand.framework.control.BasicActivityHelper;
import tub.iosp.budcloand.framework.control.CachedBasicActivityHelper;
import tub.iosp.budcloand.framework.control.adapter.BCChannelListAdapter;
import tub.iosp.budcloand.framework.control.adapter.BCPostListAdapter;
import tub.iosp.budcloand.framework.control.database.DatabaseHelper;
import tub.iosp.budcloand.framework.control.http.HttpClientHelper;
import tub.iosp.budcloand.framework.model.BCItem;
import tub.iosp.budcloand.framework.model.BCMetaData;
import tub.iosp.budcloand.framework.model.BCSubscribtion;
import tub.iosp.budcloand.framework.view.BasicFrameView;
import tub.iosp.budcloand.framework.view.PostListView;
import tub.iosp.budcloand.framework.view.PostListView.OnRefreshListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
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
	
	/** The Constant TAG. */
	private final static String TAG = "BasicActivity";

	/*handler messages*/
	
	/** The Constant LOAD_MORE_POSTS_DONE. */
	protected static final int LOAD_MORE_POSTS_DONE = 1;
	
	/** The Constant HELPER_CREATED. */
	protected static final int HELPER_CREATED = 2;
	
	/** The Constant ON_CHANNEL_CHANGE. */
	protected static final int ON_CHANNEL_CHANGE = 3;
	
	/** The Constant GET_SUBSCRIPTION. */
	protected static final int GET_SUBSCRIPTION = 4;
	
	/** The new post activity code. */
	private final int NEW_POST_ACTIVITY_CODE = 1;
	
	/** The show post activity code. */
	private final int SHOW_POST_ACTIVITY_CODE = 2;
	
	/** The search activity code. */
	private final int SEARCH_ACTIVITY_CODE = 3;
	
	
	/** The posts per refresh. */
	private final int POSTS_PER_REFRESH = 100;
	
	/** The min of posts. */
	private final int MIN_OF_POSTS = 20;
	
	/** The helper. */
	private BasicActivityHelper helper;
	
	//private PostListView postListView;
	/** The post list view. */
	private PostListView postListView;
	
	/*the basic frame of activities*/
	/** The basic frame. */
	private BasicFrameView basicFrame;
	
	/*container of the three panels*/
	/** The container. */
	private LinearLayout container;
	
	/** The left panel. */
	private LinearLayout leftPanel;
	
	/** The left menu. */
	private ListView leftMenu;
	
	/*midPanel consists of topBar and midContent*/
	/** The mid panel. */
	private LinearLayout midPanel;
	
	/** The top bar. */
	private RelativeLayout topBar;
	
	/** The mid content. */
	private LinearLayout midContent;
	
	/** The right panel. */
	private LinearLayout rightPanel;
	
	/*Buttons on top bar*/
	/** The tb show channel. */
	private ImageView tbShowChannel;
	
	/** The tb chat. */
	private ImageView tbChat;
	
	/** The tb add post. */
	private ImageView tbAddPost;
	
	/** The tb channel info. */
	private ImageView tbChannelInfo;
	
	/** The find channel. */
	private Button findChannel;
	
	
	/*footer view to support load more function*/
	/** The footer. */
	private LinearLayout footer;
	
	/** The load more button. */
	private Button loadMoreButton;
	
	/** The load more progress. */
	private ProgressBar loadMoreProgress;
	
	/** The adapter. */
	private BCPostListAdapter adapter;
	
	/** The sub list. */
	private List<BCSubscribtion> subList;
	
	/** The meta list. */
	private List<BCMetaData> metaList;
	
	/** The screen width. */
	private int screenWidth;
	
	/** The screen height. */
	private int screenHeight;
	
	/** The left panel width. */
	private int leftPanelWidth;
	
	/** The right panel width. */
	private int rightPanelWidth;
	
	/** The left pop out. */
	int leftPopOut = 0;
	
	/** The right pop out. */
	int rightPopOut = 0;
	
	/** The handler. */
	private BasicActivityHandler handler;
	
	/** The progress dialog. */
	private ProgressDialog progressDialog; 

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		this.handler = new BasicActivityHandler();

		this.helper = new CachedBasicActivityHelper(HttpClientHelper.INSTANCE.getCurrentChannel(), this.POSTS_PER_REFRESH, this.MIN_OF_POSTS);
		
		
		this.initial();
		this.setContentView(basicFrame);
		
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		String username = bundle.getString("username");
		String password = bundle.getString("password");
		if(password == null || password.compareTo("") == 0){
			HttpClientHelper.INSTANCE.setHomeChannel(username);
			HttpClientHelper.INSTANCE.setCurrentChannel(username);
		}

		handler.post(new Runnable(){
			@Override
			public void run(){
				progressDialog = new ProgressDialog(BasicActivity.this);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.setMessage("loading post...");
				progressDialog.show();
				helper.setChannel(HttpClientHelper.INSTANCE.getCurrentChannel());
				Message msg = new Message();
				msg.what = HELPER_CREATED;
				
				handler.sendMessage(msg);
			}
		});
		
		this.scrollToMiddle();
	}
	
	/**
	 * Scroll to middle.
	 */
	public void scrollToMiddle(){
		handler.postDelayed(new Runnable(){
			@Override
			public void run(){
				basicFrame.smoothScrollTo(leftPanelWidth, 0);
			}
		}, 100);
	}
	
	/**
	 * Gets the basic activity helper.
	 *
	 * @return the basic activity helper
	 */
	public BasicActivityHelper getBasicActivityHelper(){
		return this.helper;
	}

	/**
	 * Paint left panel.
	 */
	public void paintLeftPanel(){
		/*Initialize the left subscription panel*/
		
		/*left head initial*/
		TextView lefthead_homechannel = (TextView)leftPanel.findViewById(R.id.lefthead_channelName);
		ImageView lefthead_avatar = (ImageView)leftPanel.findViewById(R.id.lefthead_channelAvatar);
		lefthead_homechannel.setText(HttpClientHelper.INSTANCE.getHomeChannel());
		HttpClientHelper.INSTANCE.loadBitmap(HttpClientHelper.INSTANCE.getHomeChannel(), lefthead_avatar, 50, 50, false);
		
		RelativeLayout lefthead = (RelativeLayout)leftPanel.findViewById(R.id.lefthead);
		lefthead.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				onChannelChange(HttpClientHelper.INSTANCE.getHomeChannel());
				//paintPostList(HttpClientHelper.INSTANCE.getHomeChannel());
				
			}
			
		});
		
		handler.post(new Runnable(){
			@Override
			public void run(){
				subList = helper.getSubscribtions();
				if(subList == null){
					//TODO: UI part--fill in if get sub info wrong
				}
				else if (subList.isEmpty()) {
					// TODO: UI part--when no subscriptions
				} 
				else {
					metaList = helper.getMetaData(subList);
					Message msg = new Message();
					msg.what = GET_SUBSCRIPTION;
					handler.sendMessage(msg);
				}
			}
		});
		
		leftMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView text = (TextView) view
						.findViewById(R.id.channellist_name);
				String channelAddress = (String) text.getText();
				onChannelChange(channelAddress);
				// paintPostList(channelAddress);
			}
		});
		
		/*left menu ListView initial
		subList = helper.getSubscribtions();
		if(subList == null){
			//TODO: UI part--fill in if get sub info wrong
		}
		else if (subList.isEmpty()) {
			// TODO: UI part--when no subscriptions
		} 
		else {
			metaList = helper.getMetaData(subList);

			leftMenu.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					TextView text = (TextView) view
							.findViewById(R.id.channellist_name);
					String channelAddress = (String) text.getText();
					onChannelChange(channelAddress);
					// paintPostList(channelAddress);
				}
			});*/

			// BCChannelListAdapter channelListAdapter = new
			// BCChannelListAdapter(this, R.id.channellist_name, subList);
			/*BCChannelListAdapter channelListAdapter = new BCChannelListAdapter(
					this, metaList);
			leftMenu.setAdapter(channelListAdapter);*/
		//}
	}

	/**
	 * Paint right panel.
	 */
	public void paintRightPanel(){
		final String currentChannel = HttpClientHelper.INSTANCE.getCurrentChannel();
		
		BCMetaData meta = null;
		boolean isSubscribed = true;
		
		if(metaList != null){
			for(BCMetaData itr:metaList){
				if(itr.getChannel().compareTo(currentChannel) == 0){
					meta = itr;
				}
			}
		}
		if(meta == null){
			isSubscribed = false;
			meta = this.helper.getMetaData(currentChannel);
		}
		
		TextView channelAddress = (TextView) rightPanel.findViewById(R.id.right_channel_address);
		TextView channelType = (TextView)rightPanel.findViewById(R.id.right_channel_type);
		Button followButton = (Button)rightPanel.findViewById(R.id.right_follow_button);
		
		channelAddress.setText(currentChannel);
		if(meta != null)
			channelType.setText(meta.getChannel_type());

		if (!isSubscribed) {
			followButton.setBackgroundResource(R.drawable.activity_search_follow);
			followButton.setText("follow");
			followButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					helper.callSubscribe(currentChannel);
					//TODO: new thread
					paintLeftPanel();
					onChannelInfo();
				}
			});
		}
		else{
			followButton.setBackgroundResource(R.drawable.activity_search_followed);
			followButton.setText("followed");
		}
	}
	
	/**
	 * Paint post list.
	 *
	 * @param channelName the channel name
	 */
	public void paintPostList(String channelName){
		/*get post list*/
		
		if(helper.getPostCount() == 0){
			//TODO : UI part--when no post
		}
		else{
			postListView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Log.d(TAG,"post list item clicked, position: "+position+",id: "+id );
					Intent intent = new Intent(getApplicationContext(),ShowPostActivity.class);
					Bundle bundle = new Bundle();
					
					BCItem item = helper.getPost((int)id);
					String str = item.getJSONString();
					bundle.putString("post", str);
					bundle.putString("parentChannel", item.getChannel());
					intent.putExtras(bundle);
					startActivityForResult(intent,SHOW_POST_ACTIVITY_CODE);
				}
				
			});
			
			Log.d(TAG,"paintpostList called");

			adapter = new BCPostListAdapter(this, this.helper);
			postListView.setAdapter(adapter);
			footer.setVisibility(View.VISIBLE);
			loadMoreButton.setVisibility(View.VISIBLE);
			loadMoreProgress.setVisibility(View.GONE);
			loadMoreButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					loadMoreButton.setVisibility(View.GONE);
					loadMoreProgress.setVisibility(View.VISIBLE);
					
					/*if (adapter.loadMorePosts(POSTS_PER_REFRESH)) {
						loadMoreButton.setVisibility(View.VISIBLE);
						loadMoreProgress.setVisibility(View.GONE);
					}
					else{
						footer.setVisibility(View.GONE);
						Toast.makeText(getApplicationContext(), "no more posts", Toast.LENGTH_LONG).show();  
					}*/
					handler.post(new Runnable(){

						@Override
						public void run(){
							Message msg = new Message();
							boolean hasMore = adapter.loadMorePosts(POSTS_PER_REFRESH);
							msg.what = LOAD_MORE_POSTS_DONE;
							msg.obj = hasMore;
							handler.sendMessage(msg);
						}
					});
				}
				
			});

			
			postListView.setOnRefreshListener(new OnRefreshListener(){
				@Override
				public void onRefresh() {
					
					/*download New Post*/
					
					new AsyncTask<Void, Void, Void>() {  
	                    protected Void doInBackground(Void... params) {  
	                        try {  
	                            helper.loadNewPosts(100);
	                        	
	                        } catch (Exception e) {  
	                            e.printStackTrace();  
	                        }  
	                        return null;  
	                    }  
	  
	                    @Override  
	                    protected void onPostExecute(Void result) {  
	                        adapter.notifyDataSetChanged();  
	                        postListView.onRefreshComplete();  
	                    }  
	  
	                }.execute(null,null,null);  
				}
			});
		}
	}

	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onMenuItemSelected(int, android.view.MenuItem)
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getItemId()){
		case R.id.basic_menu_logout:
			finish();
			break;
		case R.id.basic_menu_resetDB:
			DatabaseHelper.INSTANCE.resetDB();
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	

	
	/**
	 * Initial.
	 */
	public void initial(){
		DisplayMetrics dm = this.getApplicationContext().getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		
		leftPanelWidth = screenWidth*4/5;
		rightPanelWidth = screenWidth *3/4;
		
		/*inflate the basic frame*/
		basicFrame = (BasicFrameView) LayoutInflater.from(this).inflate(R.layout.activity_basic, null);
		
		/*find views*/
		container = (LinearLayout) basicFrame.findViewById(R.id.container);
		leftPanel = (LinearLayout) container.findViewById(R.id.leftPanel);
		leftMenu = (ListView) basicFrame.findViewById(R.id.leftMenu);
		midPanel = (LinearLayout) container.findViewById(R.id.midPanel);
		topBar = (RelativeLayout) midPanel.findViewById(R.id.topbar);
		midContent = (LinearLayout) midPanel.findViewById(R.id.midContent);
		rightPanel = (LinearLayout) container.findViewById(R.id.rightPanel);
		
		tbShowChannel = (ImageView)topBar.findViewById(R.id.tp_showchannel);
		tbChat = (ImageView)topBar.findViewById(R.id.tp_chat);
		tbAddPost = (ImageView)topBar.findViewById(R.id.tp_addpost);
		tbChannelInfo = (ImageView)topBar.findViewById(R.id.tp_channelinfo);
		
		findChannel = (Button)leftPanel.findViewById(R.id.findChannel);
		findChannel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
				startActivityForResult(intent, SEARCH_ACTIVITY_CODE);
			}
		});
		
		
		LayoutInflater inflator = LayoutInflater.from(this);
		footer = (LinearLayout) inflator.inflate(R.layout.activity_basic_footer, null);
		loadMoreButton = (Button) footer.findViewById(R.id.basic_load);
		loadMoreProgress = (ProgressBar) footer.findViewById(R.id.basic_progress);

		postListView = (PostListView)midPanel.findViewById(R.id.postList);
		postListView.setDivider(null);
		postListView.addFooterView(footer);
		//postListView.setOnScrollListener(this);
		loadMoreButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				loadMoreButton.setVisibility(View.GONE);
				loadMoreProgress.setVisibility(View.VISIBLE);
				
				if (adapter.loadMorePosts(POSTS_PER_REFRESH)) {
					loadMoreButton.setVisibility(View.VISIBLE);
					loadMoreProgress.setVisibility(View.GONE);
				}
				else{
					footer.setVisibility(View.GONE);
					Toast.makeText(getApplicationContext(), "no more posts", Toast.LENGTH_LONG).show();  
				}
				/*handler.post(new Runnable(){

					@Override
					public void run(){
						Message msg = new Message();
						boolean hasMore = adapter.loadMorePosts(POSTS_PER_REFRESH);
						msg.what = LOAD_MORE_POSTS_DONE;
						msg.obj = hasMore;
						handler.sendMessage(msg);
					}
				});*/
				
			}
			
		});
		
		/*set on click listener of topbar Buttons*/
		tbShowChannel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onShowChannel();
			}
		});
		//tbShowChannel.setOnTouchListener(onTouchListener);
		
		tbChat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onChat();
			}
		});
		//tbChat.setOnTouchListener(onTouchListener);
		
		tbAddPost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onAddPost();
			}
		});
		//tbAddPost.setOnTouchListener(onTouchListener);
		
		tbChannelInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onChannelInfo();
			}
		});
		//tbChannelInfo.setOnTouchListener(onTouchListener);
		
		/*initial the width of three panels in the scroll view*/
		LayoutParams rightPanelLP = (LayoutParams)rightPanel.getLayoutParams();
		rightPanelLP.width = rightPanelWidth;//screenWidth/2;
		rightPanel.setLayoutParams(rightPanelLP);
		
		LayoutParams leftPanelLP = (LayoutParams)leftPanel.getLayoutParams();
		leftPanelLP.width = leftPanelWidth;
		leftPanel.setLayoutParams(leftPanelLP);
		
		LayoutParams midPanelLP = (LayoutParams)midPanel.getLayoutParams();
		midPanelLP.width = screenWidth;
		midPanel.setLayoutParams(midPanelLP);
		
	}
	
	
	
	/*OnClickListener methods for the topbar Buttons*/
	/**
	 * On show channel.
	 */
	public void onShowChannel(){
		//todo
		handler.postDelayed(new Runnable(){
			@Override
			public void run(){
				
				if(leftPopOut == 0){
					basicFrame.smoothScrollTo(leftPanelWidth, 0);
				}
				else{
					basicFrame.smoothScrollTo(0, 0);
				}
				leftPopOut = 1 - leftPopOut;
			}
		}, 100);
	}
	
	/**
	 * Process pass in name.
	 *
	 * @param name the name
	 * @return the string
	 * @Description parse pass-in string with the character '@'
	 */
	public String processPassInName(String name){
		String ret = name.substring(0, name.indexOf("@"));
		return ret;
	}
	
	/**
	 * Creates the menu from channel list.
	 *
	 * @Description build talk-target dialog according to the subList
	 */
	public void createMenuFromChannelList(){

		List<String> userList = new ArrayList<String>();
		if(subList == null || subList.isEmpty()){
			//TODO: when no subscription
		}else{
			for(BCSubscribtion sub : subList){
				if(sub != null)
					userList.add(processPassInName(sub.getChannelAddress()));
			}
					
		}		
		
		int size = userList.size();
		final String[] talkList = (String[])userList.toArray(new String[size]);
		
		//to show a friend-list to be selected to perform instant talk based on XMPP
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Talk List");
		builder.setItems(talkList,new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switchToTalk(talkList[which]+"@buddycloud.org");
//				System.out.println("######## : " + talkList[which]);
			}
		});	
		builder.create().show();
		
	}
	
	/**
	 * Switch to talk.
	 *
	 * @param target the target
	 * @Description switch to InstantTalkActivity
	 */
	public void switchToTalk(String target){
		Intent intent = new Intent();
        Bundle bun = this.getIntent().getExtras();
        bun.putString("targetUser", target);
		intent.putExtras(bun);
		intent.setClass(BasicActivity.this, InstantTalkActivity.class);
		startActivity(intent);
	}

	/**
	 * On chat.
	 */
	public void onChat(){
		createMenuFromChannelList();

	}
	
	/**
	 * On add post.
	 */
	public void onAddPost(){
		Intent intent = new Intent(getApplicationContext(),NewPostActivity.class);
		startActivityForResult(intent, this.NEW_POST_ACTIVITY_CODE);
	}
	
	/**
	 * On channel change.
	 *
	 * @param newChannel the new channel
	 */
	public void onChannelChange(final String newChannel){
		HttpClientHelper.INSTANCE.setCurrentChannel(newChannel);
		//helper = new CachedBasicActivityHelper(newChannel, this.POSTS_PER_REFRESH, this.MIN_OF_POSTS);
		scrollToMiddle();
		
		progressDialog = new ProgressDialog(BasicActivity.this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage("loading post...");
		progressDialog.show();
		
		
		handler.post(new Runnable(){
			@Override
			public void run(){
				helper.setChannel(newChannel);
				Message msg = new Message();
				msg.what = ON_CHANNEL_CHANGE;
				msg.obj = newChannel;
				handler.sendMessage(msg);
			}
		});

		
		Log.v(TAG,"changing into channel : "+newChannel);

	}
	
	/**
	 * On channel info.
	 */
	public void onChannelInfo(){
		//TODO
		//finish();
		handler.postDelayed(new Runnable(){
			@Override
			public void run(){
				
				if(rightPopOut == 0){
					basicFrame.smoothScrollTo(leftPanelWidth+screenWidth, 0);
				}
				else{
					basicFrame.smoothScrollTo(leftPanelWidth, 0);
				}
				rightPopOut = 1 - rightPopOut;
			}
		}, 100);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override 
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == this.NEW_POST_ACTIVITY_CODE){
			if(resultCode == this.RESULT_OK){
				//this.postListView.onRefresh();
				//TODO:
				//BCItemList list = HttpClientHelper.INSTANCE.getPostsMax(HttpClientHelper.INSTANCE.getCurrentChannel(), 1);
				//this.postList.merge(list);
				this.postListView.onRefresh();
				this.scrollToMiddle();
				/*this.paintPostList(HttpClientHelper.INSTANCE.getCurrentChannel());
				this.scrollToMiddle();*/
			}
			else{
				//Toast.makeText(getApplicationContext(), "Cannot Post content!", Toast.LENGTH_SHORT).show();
			}
		}
		if(requestCode == this.SHOW_POST_ACTIVITY_CODE){
			if(resultCode == this.RESULT_OK){
				//this.postListView.onRefresh();
				//TODO:
				//BCItemList list = HttpClientHelper.INSTANCE.getPostsMax(HttpClientHelper.INSTANCE.getCurrentChannel(), 1);
				//this.postList.merge(list);
				this.postListView.onRefresh();
				this.scrollToMiddle();
				/*this.paintPostList(HttpClientHelper.INSTANCE.getCurrentChannel());
				this.scrollToMiddle();*/
			}
			else{
				//Toast.makeText(getApplicationContext(), "Cannot Post content!", Toast.LENGTH_SHORT).show();
			}
		}
		if(requestCode == this.SEARCH_ACTIVITY_CODE){
			
			
			
			if(resultCode == RESULT_OK && data != null){
				Bundle bundle = data.getExtras();
				
				String channelName = bundle.getString("tub.iosp.budcloand.activities.search");
				Log.d(TAG,"get search result:"+channelName);
				this.onChannelChange(channelName);
				this.paintLeftPanel();
			}
			else{
				
			}
		}
		
	}
	
	/*private OnTouchListener onTouchListener = new View.OnTouchListener(){

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (v != null) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					((ImageView) v).getDrawable().setAlpha(50);
					((ImageView) v).invalidate();
				} else {
					((ImageView) v).getDrawable().setAlpha(255);
					((ImageView) v).invalidate();
				}
			}
			return false;
		}
		
	};*/
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	public void onDestroy(){
		helper.onDestroy();
		super.onDestroy();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	public void onPause(){
		super.onPause();
		helper.onPause();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	public void onStop(){
		super.onStop();
		helper.onStop();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		helper.onResume();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onRestart()
	 */
	@Override
	public void onRestart(){
		super.onRestart();
		helper.onRestart();
	}
	
	/*private float lastX;
	private int minDistance = 10;
	

	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN)
			lastX = event.getX();
		if(event.getAction() == MotionEvent.ACTION_UP){
			float currentX = event.getX();
			if(lastX - currentX > minDistance){
				//fling from left to right
				if(rightPopOut == 1){
					onChannelInfo();
				}
				else if(leftPopOut == 0){
					onShowChannel();
				}
			}
			if(currentX - lastX > minDistance){
				//fling from right to left
				if(rightPopOut == 0){
					onChannelInfo();
				}
				else if(leftPopOut == 1){
					onShowChannel();
				}
			}
		}
		return super.onTouchEvent(event);
	}*/
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Log.d(TAG,"back key pressed");
		}
		return false;
	}
	
	/**
	 * The Class BasicActivityHandler.
	 */
	private class BasicActivityHandler extends Handler{
		
		/**
		 * Instantiates a new basic activity handler.
		 */
		public BasicActivityHandler(){
			super();
		}
		
		/* (non-Javadoc)
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Log.d(TAG,"handler get message with message.what = "+msg.what);
			
			switch(msg.what){
			
			case GET_SUBSCRIPTION:
				BCChannelListAdapter channelListAdapter = new BCChannelListAdapter(getApplicationContext(), metaList);
				leftMenu.setAdapter(channelListAdapter);
				break;
				
			case LOAD_MORE_POSTS_DONE:
				if ((Boolean)msg.obj) {
					loadMoreButton.setVisibility(View.VISIBLE);
					loadMoreProgress.setVisibility(View.GONE);
				}
				else{
					footer.setVisibility(View.GONE);
					Toast.makeText(getApplicationContext(), "no more posts", Toast.LENGTH_LONG).show();  
				}
				break;
				
			case HELPER_CREATED:
				
				paintLeftPanel();
				paintPostList(HttpClientHelper.INSTANCE.getHomeChannel());
				paintRightPanel();
				if(progressDialog != null)
						progressDialog.dismiss();
				break;
				
			case ON_CHANNEL_CHANGE:
				String newChannel = (String) msg.obj;
				paintPostList(newChannel);
				paintRightPanel();
				if(progressDialog != null)
					progressDialog.dismiss();
				break;
			}
			
		}
		
	
	}
	
}
