package tub.iosp.budcloand.framework.control;

import java.util.List;

import tub.iosp.budcloand.framework.model.BCItem;
import tub.iosp.budcloand.framework.model.BCMetaData;
import tub.iosp.budcloand.framework.model.BCSubscribtion;

// TODO: Auto-generated Javadoc
/**
 * The Interface BasicActivityHelper. 
 * Help the activity to communicate with the data sources(Http and Database)
 */
public interface BasicActivityHelper {
	

	/**
	 * Gets the posts of a channel.
	 *
	 * @return the posts
	 */
	public List<BCItem> getPosts();

	/**
	 * Load more posts from a channel.
	 *
	 * @param max the max number of posts to load
	 * @return the list
	 */
	public List<BCItem> loadMorePosts(int max);

	/**
	 * Gets the post.
	 *
	 * @param index the index of the post in list
	 * @return the post
	 */
	public BCItem getPost(int index);

	/**
	 * Gets the post count.
	 *
	 * @return the post count
	 */
	public int getPostCount();

	/**
	 * Gets the meta data of the channels given a subscription list
	 *
	 * @param list the subscription list
	 * @return the meta data
	 */
	public List<BCMetaData> getMetaData(List<BCSubscribtion> list);

	/**
	 * Gets the meta data of a certain channel
	 *
	 * @param channelName the channel name
	 * @return the meta data
	 */
	public BCMetaData getMetaData(String channelName);

	/**
	 * Gets the subscribtions.
	 *
	 * @return the subscribtions
	 */
	public List<BCSubscribtion> getSubscribtions();
	
	/**
	 * Load new posts from data source
	 *
	 * @param max the max number of posts to retrieve
	 * @return the list
	 */
	public List<BCItem> loadNewPosts(int max);

	/**
	 * Sets the current channel the helper is dealing with.
	 *
	 * @param channelName the new channel
	 */
	public void setChannel(String channelName);
	
	/**
	 * Subscribe the new channel.
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	public boolean callSubscribe(String name);
	
	/**
	 * On destroy.
	 */
	public void onDestroy();
	
	/**
	 * On pause.
	 */
	public void onPause();
	
	/**
	 * On stop.
	 */
	public void onStop();

	/**
	 * On resume.
	 */
	public void onResume();
	
	/**
	 * On restart.
	 */
	public void onRestart();
}