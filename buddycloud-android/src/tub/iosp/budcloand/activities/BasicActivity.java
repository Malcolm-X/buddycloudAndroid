package tub.iosp.budcloand.activities;

import java.util.ArrayList;
import java.util.List;

import tub.iosp.budcloand.R;
import tub.iosp.budcloand.framework.helper.BasicActivityHelper;
import tub.iosp.budcloand.framework.helper.CachedBasicActivityHelper;
import tub.iosp.budcloand.framework.helper.HttpClientHelper;
import tub.iosp.budcloand.framework.types.BCItem;
import tub.iosp.budcloand.framework.types.BCMediaData;
import tub.iosp.budcloand.framework.types.BCMetaData;
import tub.iosp.budcloand.framework.types.BCSubscribtion;
import tub.iosp.budcloand.ui.BCChannelListAdapter;
import tub.iosp.budcloand.ui.BCPostListAdapter;
import tub.iosp.budcloand.ui.BasicFrameView;
import tub.iosp.budcloand.ui.PostListView;
import tub.iosp.budcloand.ui.PostListView.OnRefreshListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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
	
	
	private final int POSTS_PER_REFRESH = 20;
	private final int MIN_OF_POSTS = 20;
	
	private BasicActivityHelper helper;
	
	//private PostListView postListView;
	private PostListView postListView;
	
	/*the basic frame of activities*/
	private BasicFrameView basicFrame;
	
	/*container of the three panels*/
	private LinearLayout container;
	private LinearLayout leftPanel;
	private ListView leftMenu;
	
	/*midPanel consists of topBar and midContent*/
	private LinearLayout midPanel;
	private RelativeLayout topBar;
	private LinearLayout midContent;
	private LinearLayout rightPanel;
	
	/*Buttons on top bar*/
	private ImageView tbShowChannel;
	private ImageView tbChat;
	private ImageView tbAddPost;
	private ImageView tbChannelInfo;
	
	private Button findChannel;
	
	List<BCSubscribtion> subList;
	
	private int screenWidth;
	private int screenHeight;
	
	int leftPopOut = 0;
	int rightPopOut = 0;
	
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		this.helper = new CachedBasicActivityHelper(HttpClientHelper.INSTANCE.getCurrentChannel(), this.POSTS_PER_REFRESH, this.MIN_OF_POSTS);
		
		this.initial();
		
		this.setContentView(basicFrame);
		
		this.handler = new Handler();
		
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
		
		/*left head initial*/
		TextView lefthead_homechannel = (TextView)leftPanel.findViewById(R.id.lefthead_channelName);
		ImageView lefthead_avatar = (ImageView)leftPanel.findViewById(R.id.lefthead_channelAvatar);
		lefthead_homechannel.setText(HttpClientHelper.INSTANCE.getHomeChannel());
		HttpClientHelper.INSTANCE.loadBitmap(HttpClientHelper.INSTANCE.getHomeChannel(), lefthead_avatar, 50, 50);
		RelativeLayout lefthead = (RelativeLayout)leftPanel.findViewById(R.id.lefthead);
		lefthead.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				onChannelChange(HttpClientHelper.INSTANCE.getHomeChannel());
				//paintPostList(HttpClientHelper.INSTANCE.getHomeChannel());
				scrollToMiddle();
			}
			
		});
		
		
		/*left menu ListView initial*/
		List<BCSubscribtion> subList = helper.getSubscribtions();
		if(subList == null){
			//TODO: UI part--fill in if get sub info wrong
		}
		else if (subList.isEmpty()) {
			// TODO: UI part--when no subscriptions
		} 
		else {
			List<BCMetaData> metaList = helper.getMetaData(subList);

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

			// BCChannelListAdapter channelListAdapter = new
			// BCChannelListAdapter(this, R.id.channellist_name, subList);
			BCChannelListAdapter channelListAdapter = new BCChannelListAdapter(
					this, metaList);
			leftMenu.setAdapter(channelListAdapter);
		}
	}

	public void paintRightPanel(){
		String currentChannel = HttpClientHelper.INSTANCE.getCurrentChannel();
		
		// TODO: complete the rightpanel
	}
	
	public void paintPostList(String channelName){
		this.scrollToMiddle();
		
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
					
					//Important : 	the list is in a reverse state
					//				so every index should also be reversed
					//int index = postList.size()-1-(int)id;
					BCItem item = helper.getPost((int)id);
					String str = item.getJSONString();
					bundle.putString("post", str);
					intent.putExtras(bundle);
					TODO:startActivityForResult(intent,SHOW_POST_ACTIVITY_CODE);
				}
				
			});

			final BCPostListAdapter adapter = new BCPostListAdapter(this, this.helper);
			postListView.setAdapter(adapter);
			
			postListView.setOnRefreshListener(new OnRefreshListener(){
				@Override
				public void onRefresh() {
					
					/*download New Posts
					
					test code*/
					//postList.add(0, "new Post");
					
					new AsyncTask<Void, Void, Void>() {  
	                    protected Void doInBackground(Void... params) {  
	                        try {  
	                            Thread.sleep(1000);  
	                        } catch (Exception e) {  
	                            e.printStackTrace();  
	                        }  
	                        //data.add("ˢ�º���ӵ�����");  
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
				//startActivityForResult(intent, this.NEW_POST_ACTIVITY_CODE);
				startActivity(intent);
			}
		});
		
		postListView = (PostListView)midPanel.findViewById(R.id.postList);
		postListView.setDivider(null);
		
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
				onNewComment();
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
	
	public String processPassInName(String name){
		String ret = name.substring(0, name.indexOf("@"));
		return ret;
	}
	
	public void createMenuFromChannelList(){

		List<String> userList = new ArrayList<String>();
		if(subList == null || subList.isEmpty()){
			//TODO can be better in future...
			userList.add("helen5haha");
			userList.add("user2");
			userList.add("user3");
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
	
	public void switchToTalk(String target){
		Intent intent = new Intent();
        Bundle bun = this.getIntent().getExtras();
        bun.putString("targetUser", target);
		intent.putExtras(bun);
		intent.setClass(BasicActivity.this, InstantTalkActivity.class);
		startActivity(intent);
	}

	public void onNewComment(){
		createMenuFromChannelList();

	}
	
	public void onAddPost(){
		Intent intent = new Intent(getApplicationContext(),NewPostActivity.class);
		startActivityForResult(intent, this.NEW_POST_ACTIVITY_CODE);
	}
	
	public void onChannelChange(String newChannel){
		HttpClientHelper.INSTANCE.setCurrentChannel(newChannel);
		helper = new CachedBasicActivityHelper(newChannel, this.POSTS_PER_REFRESH, this.MIN_OF_POSTS);
		paintPostList(newChannel);
		Log.v(TAG,"changing into channel : "+newChannel);

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
		
	}
	
	private OnTouchListener onTouchListener = new View.OnTouchListener(){

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
		
	};
	
	
}
