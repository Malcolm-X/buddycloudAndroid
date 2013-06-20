package tub.iosp.budcloand.framework.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tub.iosp.budcloand.framework.control.database.DatabaseHelper;
import tub.iosp.budcloand.framework.control.http.HttpClientHelper;
import tub.iosp.budcloand.framework.model.BCItem;
import tub.iosp.budcloand.framework.model.BCMetaData;
import tub.iosp.budcloand.framework.model.BCSubscribtion;
import tub.iosp.budcloand.framework.model.CacheTimeFrame;
import tub.iosp.budcloand.framework.model.channelPostsScope;
import android.util.Log;
import de.greenrobot.dao.LazyList;

/**
 * The Class CachedBasicActivityHelper.
 */
public class CachedBasicActivityHelper implements BasicActivityHelper {
	
	/** The tag. */
	private final String TAG = "CachedBasicActivityHelper";
	
	/** The channel. */
	private channelPostsScope channel;
	
	/** The posts. */
	private LazyList<BCItem> posts;
	
	/** The refresh. */
	private final int REFRESH;
	
	/** The post lock. */
	private Object postLock;
	
	/** The subscriptions. */
	private LazyList<BCSubscribtion> subscriptions;
	/**
 * Instantiates a new cached basic activity helper.
 *
 * @param channelName the channel name
 * @param postsPerRefresh the posts per refresh
 * @param minNumberOfPosts the min number of posts, not implemented
 */
public CachedBasicActivityHelper(String channelName, int postsPerRefresh, int minNumberOfPosts){
		this.REFRESH = postsPerRefresh;
		this.postLock = new Object();
		Log.d(TAG, "NEW CACHED BASICACTIVITY HELPER CREATED");
	}
	
	/**
	 * Inits the.
	 */
	public void init(){
		
	}
	
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.BasicActivity#getPosts()
	 */
	@Override
	public List<BCItem> getPosts() {
		synchronized(this.postLock){
			return this.posts;
		}
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.BasicActivity#loadMorePosts(int)
	 */
	@Override
	public List<BCItem> loadMorePosts(int max){
		synchronized(this.postLock){
			List<BCItem> posts_tmp = null;
			// TODO: Maybe display posts from older frames (how to mark the barrier between frames?)
			if (HttpClientHelper.INSTANCE.checkNetworkInfo()){
				CacheTimeFrame nextFrame = this.channel.getCacheTimeFrame().getNextTimeFrame();
				CacheTimeFrame thisFrame = this.channel.getCacheTimeFrame();
				if(this.posts==null) posts_tmp = HttpClientHelper.INSTANCE.getPosts(this.channel.getName(), max);	
				else posts_tmp = HttpClientHelper.INSTANCE.getPostsAfter(this.channel.getName(), this.posts.get(this.getPostCount()-1).getRemoteId(), max);
				if(posts_tmp == null || posts_tmp.isEmpty()) return null;
				if(nextFrame == null){
					for (BCItem post : posts_tmp) {
						DatabaseHelper.INSTANCE.store(post);
					}
					thisFrame.setLast(posts_tmp.get(posts_tmp.size()-1).getUpdated());
					this.channel.setCacheTimeFrame(thisFrame);
				    DatabaseHelper.INSTANCE.store(this.channel);
					this.channel.setCacheTimeFrame(thisFrame);
					DatabaseHelper.INSTANCE.store(this.channel);
					if(this.posts != null) this.posts.close();
					this.posts = (LazyList<BCItem>)DatabaseHelper.INSTANCE.getPosts(channel.getName(), this.channel.getCacheTimeFrame().getLast()); 
				}
				
				else{
					posts.addAll(posts_tmp);
					/* 
					 * TODO: comming with new api
					 * as soon as the since parameter is introduced to the /content endpoint
					 * the missing posts will be downloaded and stored to the database
					 * then the posts list will be recreated from the database
					 */
				}
			}
			
			return posts_tmp;
		}
	}
	
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.BasicActivityHelper#loadNewPosts(int)
	 */
	public List<BCItem> loadNewPosts(int max){
		synchronized(postLock){
			if(!HttpClientHelper.INSTANCE.checkNetworkInfo())return null;
			Date latest = this.channel.getCacheTimeFrame().getFirst();
			Log.d(TAG,"in loadNewPosts in CachedBasicActivityHelper, getFirst is "+latest.toGMTString()+", getLast is "+this.channel.getCacheTimeFrame().getLast().toGMTString());
			List<BCItem> posts_tmp =  HttpClientHelper.INSTANCE.getNewerPosts(this.channel.getName(), latest, max);
			if(posts_tmp != null){
				for (BCItem post : posts_tmp) {
					if(post.getItemType().equals("comment")) Log.e(TAG, "A Comment was added. id was: " + post.getRemoteId() + ". It replies to: "+ post.getReplyTo());
					DatabaseHelper.INSTANCE.store(post);
				}
				CacheTimeFrame oldC = channel.getCacheTimeFrame();
				CacheTimeFrame newC = new CacheTimeFrame();
				newC.setFirst(posts_tmp.get(0).getUpdated());
				//if(posts_tmp.get(posts_tmp.size()-1).getRemoteId().equals(posts.get(0).getRemoteId())){
				//waiting for real sync in new httpapi
				if(posts_tmp.size() == max){
					newC.setLast(oldC.getLast());
					newC.setNextFrame(oldC.getNextFrame());
					DatabaseHelper.INSTANCE.delete(oldC);
				}
				else {
					newC.setLast(posts_tmp.get(posts_tmp.size()-1).getUpdated());
					newC.setNextTimeFrame(oldC);
				}
				DatabaseHelper.INSTANCE.store(newC);
				channel.setCacheTimeFrame(newC);
				DatabaseHelper.INSTANCE.store(channel);
				if(this.posts != null) this.posts.close();
				this.posts = (LazyList<BCItem>)DatabaseHelper.INSTANCE.getPosts(channel.getName(), this.channel.getCacheTimeFrame().getLast());
				
			}
			return posts_tmp;
		}
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.BasicActivity#getPost(int)
	 */
	@Override
	public BCItem getPost(int index){
		synchronized(this.postLock){
			return this.posts.get(index);
		}
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.BasicActivity#getPostCount()
	 */
	@Override
	public int getPostCount(){
		synchronized(this.postLock){
			return this.posts.size();
		}
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.BasicActivity#getMetaData(java.util.List)
	 */
	@Override
	synchronized public List<BCMetaData> getMetaData(List<BCSubscribtion> list) {
		if(list == null || list.isEmpty()) return null;
		List<BCMetaData> result = new ArrayList<BCMetaData>();
		for(BCSubscribtion sub : list){
			if (sub == null) continue;
			BCMetaData meta = this.getMetaData(sub.getChannelAddress());
			if(meta != null){
				Log.d(TAG,"get meta data : "+meta.getChannel());
				result.add(meta);
			}
			
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.BasicActivity#getMetaData(java.lang.String)
	 */
	@Override
	synchronized public BCMetaData getMetaData(String channelName) {
		
		BCMetaData metaDB = DatabaseHelper.INSTANCE.getMetadata(channelName);
		if(!HttpClientHelper.INSTANCE.checkNetworkInfo()) return metaDB;
		BCMetaData newMeta = HttpClientHelper.INSTANCE.getMetadata(channelName);
		if(metaDB != null){
			newMeta.setId(metaDB.getId());
		}
		DatabaseHelper.INSTANCE.store(newMeta);
		// TODO: store data in new thread
		return newMeta;
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.BasicActivity#getSubscribtions()
	 */
	@Override
	synchronized public List<BCSubscribtion> getSubscribtions() {
		/*
		 * TODO: can be speeded up with extended database-functionality
		 * create index to user and only delete users subscribtions 
		 * will enable cashing for other channels subscriptions
		 */
		
		if(HttpClientHelper.INSTANCE.checkNetworkInfo()) {
			/*
			 * TODO: drop Tables in new Thread;
			 */
			List<BCSubscribtion> subsHTTP = HttpClientHelper.INSTANCE.getSubscribed();
			
			DatabaseHelper.INSTANCE.dropSubscriptions();
			if(subsHTTP != null && !subsHTTP.isEmpty()){
				for (BCSubscribtion sub: subsHTTP) {
					DatabaseHelper.INSTANCE.store(sub);
					Log.d(TAG,sub.getChannelAddress());
				}
			}
		}
		if(this.subscriptions != null) this.subscriptions.close();
		Log.d(TAG,"getuser: "+HttpClientHelper.INSTANCE.getUser());
		this.subscriptions = (LazyList<BCSubscribtion>) DatabaseHelper.INSTANCE.getSubscribed(HttpClientHelper.INSTANCE.getUser());
		return this.subscriptions;
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.BasicActivityHelper#setChannel(java.lang.String)
	 */
	@Override
	public void setChannel(String channelName){
		Log.d(TAG,"the current channel is "+channelName);
		synchronized (postLock) {
			this.channel = DatabaseHelper.INSTANCE.getCachedChannel(channelName);
			if (channel == null){
				List<BCItem> posts_tmp = null;
				if (HttpClientHelper.INSTANCE.checkNetworkInfo()) {
					posts_tmp = HttpClientHelper.INSTANCE.getPosts(channelName, REFRESH);
				}
				if (posts_tmp != null && !posts_tmp.isEmpty()) {
					for (BCItem item : posts_tmp) {
						if(item.getItemType().equals("comment")) Log.e(TAG, "A Comment was added. id was: " + item.getRemoteId() + ". It replies to: "+ item.getReplyTo());
						DatabaseHelper.INSTANCE.store(item);
						Log.d(TAG,item.getContent());
					}
					channel = DatabaseHelper.INSTANCE.addChannel(channelName, posts_tmp.get(0).getUpdated(), posts_tmp.get(posts_tmp.size()-1).getUpdated());
				}
				else{
					Log.d(TAG,"posts_tmp is empty");
					Date date = new Date(System.currentTimeMillis());
					channel = DatabaseHelper.INSTANCE.addChannel(channelName, date , date);
				}
			}
			// wont block because lock is acquired from the same thread
			List<BCItem> newPosts = this.loadNewPosts(REFRESH);
			String name = channel.getName();
			CacheTimeFrame frame = channel.getCacheTimeFrame();
			Log.d(TAG, "NEW CHANNEL WAS SET");
			if(newPosts == null || newPosts.isEmpty()){
				Log.d(TAG, "no new posts loaded");
				if(this.posts != null) this.posts.close();
				this.posts = (LazyList<BCItem>)DatabaseHelper.INSTANCE.getPosts(name, frame.getLast());
			}
		}
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.BasicActivityHelper#onDestroy()
	 */
	@Override
	public void onDestroy() {
		if(this.posts != null) this.posts.close();
		if(this.subscriptions != null) this.subscriptions.close();
		
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.BasicActivityHelper#onPause()
	 */
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.BasicActivityHelper#onStop()
	 */
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.BasicActivityHelper#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.BasicActivityHelper#onRestart()
	 */
	@Override
	public void onRestart() {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.BasicActivityHelper#callSubscribe(java.lang.String)
	 */
	@Override
	public boolean callSubscribe(String name) {
		return HttpClientHelper.INSTANCE.subscribe(name);
	}

}
