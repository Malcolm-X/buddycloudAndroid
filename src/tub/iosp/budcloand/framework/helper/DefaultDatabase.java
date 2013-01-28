package tub.iosp.budcloand.framework.helper;

import java.util.List;

import android.database.Cursor;
import tub.iosp.budcloand.framework.types.BCItem;
import tub.iosp.budcloand.framework.types.BCMetaData;
import tub.iosp.budcloand.framework.types.BCPost;
import tub.iosp.budcloand.framework.types.BCPostList;
import tub.iosp.budcloand.framework.types.BCSubscriptionList;

public class DefaultDatabase implements BCDatabase{

	@Override
	public BCSubscriptionList getSubscribed(String channelName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cursor getSubscribedCursor(String channelName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BCMetaData getMetadata(String channelName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BCPostList getPosts(String channelName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cursor getPostsCursor(String channelName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BCPostList getPostsBefore(String channelName, String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cursor getPostsBeforeCursor(String channelName, String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BCPostList getPostsAfter(String channelName, String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cursor getPostsAfterCursor(String channelName, String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean storeSubscribed(String channelName, BCSubscriptionList list) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean storePosts(String channelName, BCPostList list) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean storeMetaData(String channelName, BCMetaData metadata) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<BCItem> getComments(String postID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BCPost getComments(BCPost post) {
		// TODO Auto-generated method stub
		return null;
	}



}
