package tub.iosp.budcloand.framework.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.ListAdapter;

import de.greenrobot.dao.LazyList;

import tub.iosp.budcloand.framework.database.CacheTimeFrame;
import tub.iosp.budcloand.framework.database.channelPostsScope;
import tub.iosp.budcloand.framework.types.BCItem;
import tub.iosp.budcloand.framework.types.BCMetaData;
import tub.iosp.budcloand.framework.types.BCSubscribtion;
import tub.iosp.budcloand.ui.BCPostListAdapter;

public class CachedBasicActivityHelper implements BasicActivityHelper {
	
	private final String TAG = "CachedBasicActivityHelper";
	
	private channelPostsScope channel;
	private LazyList<BCItem> posts;
	private int REFRESH;
	private int MIN_POSTS;
	private BCItem newestPost;
//	private Handler handler;
	public CachedBasicActivityHelper(String channelName, int postsPerRefresh, int minNumberOfPosts){
		this.REFRESH = postsPerRefresh;
//		this.handler = handler;					
		this.channel = DatabaseHelper.INSTANCE.getCachedChannel(channelName);
		if (channel == null){
			List<BCItem> posts_tmp = HttpClientHelper.INSTANCE.getPosts(channelName, 100);
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
				
				//TODO: what if posts_tmp is empty
				Date date = new Date(System.currentTimeMillis());
				channel = DatabaseHelper.INSTANCE.addChannel(channelName, date , date);
			}
		}
		this.loadNewPosts(100);
		String name = channel.getName();
		CacheTimeFrame frame = channel.getCacheTimeFrame();
		Log.e(TAG, "NEW CACHED BASICACTIVITY HELPER CREATED");
		
		
//		if(frame == null){
//			this.posts = (LazyList<BCItem>)DatabaseHelper.INSTANCE.getPosts(name, new Date());
//		}
		this.posts = (LazyList<BCItem>)DatabaseHelper.INSTANCE.getPosts(name, frame.getLast());

	}
	
	public void init(){
		
	}
	
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.BasicActivity#getPosts()
	 */
	@Override
	public List<BCItem> getPosts() {
		return this.posts;
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.BasicActivity#loadMorePosts(int)
	 */
	@Override
	public List<BCItem> loadMorePosts(int max){
		CacheTimeFrame nextFrame = this.channel.getCacheTimeFrame().getNextTimeFrame();
		CacheTimeFrame thisFrame = this.channel.getCacheTimeFrame();
		List<BCItem> posts_tmp = null;
		if(nextFrame == null){
			posts_tmp = HttpClientHelper.INSTANCE.getPostsAfter(this.channel.getName(), this.channel.getCacheTimeFrame().getLast(), max);
			if(posts_tmp == null) return null;
			for (BCItem post : posts_tmp) {
				if(post.getItemType().equals("comment")) Log.e(TAG, "A Comment was added. id was: " + post.getRemoteId() + ". It replies to: "+ post.getReplyTo());
				DatabaseHelper.INSTANCE.store(post);
			}
			thisFrame.setLast(posts_tmp.get(posts_tmp.size()-1).getUpdated());
			DatabaseHelper.INSTANCE.store(thisFrame);
			
		}
		else{
			//TODO: comming with new api
		}
		this.posts = (LazyList<BCItem>)DatabaseHelper.INSTANCE.getPosts(channel.getName(), this.channel.getCacheTimeFrame().getLast()); 
		
		return posts_tmp;
	}
	
	
	public List<BCItem> loadNewPosts(int max){
		Date latest = this.channel.getCacheTimeFrame().getFirst();
		Date curr = new Date(System.currentTimeMillis());
		List<BCItem> posts_tmp =  HttpClientHelper.INSTANCE.getNewerPosts(this.channel.getName(), latest, max);
		if(posts_tmp != null){
			for (BCItem post : posts_tmp) {
				if(post.getItemType().equals("comment")) Log.e(TAG, "A Comment was added. id was: " + post.getRemoteId() + ". It replies to: "+ post.getReplyTo());
				DatabaseHelper.INSTANCE.store(post);
			}
			CacheTimeFrame oldC = channel.getCacheTimeFrame();
			CacheTimeFrame newC = new CacheTimeFrame();
			newC.setFirst(posts_tmp.get(0).getUpdated());
			if(posts_tmp.get(posts_tmp.size()-1).getRemoteId().equals(posts.get(0).getRemoteId())){
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
			this.posts = (LazyList<BCItem>)DatabaseHelper.INSTANCE.getPosts(channel.getName(), this.channel.getCacheTimeFrame().getLast());
		}
		return posts_tmp;
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.BasicActivity#getPost(int)
	 */
	@Override
	public BCItem getPost(int index){
		return this.posts.get(index);
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.BasicActivity#getPostCount()
	 */
	@Override
	public int getPostCount(){
		return this.posts.size();
	}
	
//	@Override
//	public void addPreviousPosts(int number) {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void addNextPosts(int number) {
//		// TODO Auto-generated method stub
//
//	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.BasicActivity#getMetaData(java.util.List)
	 */
	@Override
	public List<BCMetaData> getMetaData(List<BCSubscribtion> list) {
		List<BCMetaData> result = new ArrayList<BCMetaData>();
		if(list != null && !list.isEmpty()){
			for(BCSubscribtion sub : list){
				BCMetaData meta = getMetaData(sub.getChannelAddress());
				if(meta != null){
					Log.d(TAG,"get meta data : "+meta.getChannel());
					result.add(meta);
				}
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.BasicActivity#getMetaData(java.lang.String)
	 */
	@Override
	public BCMetaData getMetaData(String channelName) {
		BCMetaData metaDB = DatabaseHelper.INSTANCE.getMetadata(channelName);
		BCMetaData newMeta = HttpClientHelper.INSTANCE.getMetadata(channelName);
		if(metaDB != null){
			newMeta.setId(metaDB.getId());
		}
		DatabaseHelper.INSTANCE.store(newMeta);
		return newMeta;
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.BasicActivity#getSubscribtions()
	 */
	@Override
	public List<BCSubscribtion> getSubscribtions() {
		List<BCSubscribtion> subs = DatabaseHelper.INSTANCE.getSubscribtions(HttpClientHelper.INSTANCE.getUser());
		if(subs != null && !subs.isEmpty()){
			for (BCSubscribtion sub : subs) {
				DatabaseHelper.INSTANCE.delete(sub);
			}
		}
		subs = HttpClientHelper.INSTANCE.getSubscribed();
		
		if(subs != null && !subs.isEmpty()){
			for (BCSubscribtion sub : subs) {
				DatabaseHelper.INSTANCE.store(sub);
				Log.d(TAG,sub.getChannelAddress());
			}
		}
		return subs;
	}

}
