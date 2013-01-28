package tub.iosp.budcloand.framework.helper;

import java.util.List;

import android.database.Cursor;
import tub.iosp.budcloand.framework.types.BCItem;
import tub.iosp.budcloand.framework.types.BCMetaData;
import tub.iosp.budcloand.framework.types.BCPost;
import tub.iosp.budcloand.framework.types.BCPostList;
import tub.iosp.budcloand.framework.types.BCSubscriptionList;

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
	public BCSubscriptionList getSubscribed(String channelName);
	
	public Cursor getSubscribedCursor(String channelName);

	public BCMetaData getMetadata(String channelName);
	
	public BCPostList getPosts(String channelName);
	
	// get all the comments given the post ID
	public List<BCItem> getComments(String postID);
	
	// return the same BCPost with the comment list filled
	public BCPost getComments(BCPost post);
	
	public Cursor getPostsCursor(String channelName);
	
	public BCPostList getPostsBefore(String channelName,String date);
	
	public Cursor getPostsBeforeCursor(String channelName,String date);
	
	public BCPostList getPostsAfter(String channelName,String date);
	
	public Cursor getPostsAfterCursor(String channelName,String date);
	
	//store methods
	public boolean storeSubscribed(String channelName, BCSubscriptionList list);
	
	public boolean storePosts(String channelName, BCPostList list);
	
	public boolean storeMetaData(String channelName,BCMetaData metadata);
	
	
}
