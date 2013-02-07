package tub.iosp.budcloand.framework.database;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import android.database.Cursor;
import tub.iosp.budcloand.framework.exceptions.BCIOException;
import tub.iosp.budcloand.framework.types.BCItem;
import tub.iosp.budcloand.framework.types.BCMetaData;
import tub.iosp.budcloand.framework.types.BCSubscribtion;

public interface BCDatabase {
	
	/* BCItem, BCMetaData, BCPost, BCPostList, BCSubscription, BCSubscriptionList
	 * these are the complete types (e.g. has a constructor using JsonText, and a method getJSONObject())
	 * 
	 * notice that BCPost is different from BCItem
	 * BCPost contains a BCItem post, and a list of comments
	 * and It is NOT recommended to use BCPost's JSON related methods like BCPost(JsonText) or BCPost.getJSONObject
	 * when using the database, my suggestion is to store just BCItems
	 * 
	 */
	
	
	
	//get methods
	public List<BCSubscribtion> getSubscribed(String channelName) throws BCIOException;
	
//	public Cursor getSubscribedCursor(String channelName);

	public BCMetaData getMetadata(String channelName) throws BCIOException;
	
	public List<BCItem> getPosts(String channelName) throws BCIOException;
	
	// get all the comments given the post ID
	public List<BCItem> getComments(String postID, String channel) throws BCIOException;
	
	// return the same BCPost with the comment list filled
	
//	public Cursor getPostsCursor(String channelName);
	
//	public BCPostList getPostsBefore(String channelName,String date);
	
//	public Cursor getPostsBeforeCursor(String channelName,String date);
	
//	public BCPostList getPostsAfter(String channelName,String date);
	
//	public Cursor getPostsAfterCursor(String channelName,String date);
	
	//store methods
	
	public boolean store(channelPostsScope channel) throws BCIOException;
	public boolean store(BCMetaData subscribtion) throws BCIOException;
	public boolean store(BCSubscribtion subscribtion) throws BCIOException;
	public boolean store(BCItem item) throws BCIOException;
	public boolean storeItemList(List<BCItem> list) throws BCIOException;
	public boolean storeMetaDataList(List<BCMetaData> list) throws BCIOException;
	public boolean storeSubscribtionList(List<BCSubscribtion> list) throws BCIOException;
	void close() throws BCIOException;

	public channelPostsScope getChannel(String name) throws BCIOException;

	void startNewSession() throws BCIOException;

	public List<BCItem> getPosts(String channel, Date last) throws BCIOException;

	public boolean store(CacheTimeFrame frame) throws BCIOException;

	public boolean delete(CacheTimeFrame frame) throws BCIOException;


	
	
	
	
}
