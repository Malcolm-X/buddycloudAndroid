package tub.iosp.budcloand.framework.control;

import java.util.List;

import tub.iosp.budcloand.framework.control.database.DatabaseHelper;
import tub.iosp.budcloand.framework.model.BCItem;
import android.util.Log;
import de.greenrobot.dao.LazyList;

// TODO: Auto-generated Javadoc
/**
 * The Class CachedShowPostHelper is a cached implementation of the ShowPostsHelper interface
 */
public class CachedShowPostHelper implements ShowPostsHelper {
	
	/** The Constant TAG. */
	private static final String TAG = "CSP Helper";
	
	/** The posts lock. */
	private Object postsLock;
	
	/** The parent. */
	private BCItem parent;
	
	/** The comments. */
	private LazyList<BCItem> comments;
	
	/**
	 * Instantiates a new cached show post helper.
	 *
	 * @param parentPost the parent post
	 */
	public CachedShowPostHelper(BCItem parentPost) {
		this.postsLock = new Object();
		this.parent = parentPost;
		Log.d(TAG, "ChachedShowPostsHelper started with id : " + parent.getRemoteId());
		Log.d(TAG, "channel for parent is : " + parent.getChannel());
		Log.d(TAG,"post's author is "+parent.getAuthor());
		//TODO: getComments not implemented (Always return null)
		this.comments =(LazyList<BCItem>) DatabaseHelper.INSTANCE.getComments(parent);
//		if (this.comments== null) this.comments =new ArrayList<BCItem>();
		Log.d(TAG, "ChachedShowPostsHelper started with id : " + parent.getRemoteId());
		Log.d(TAG, "channel for parent is : " + parent.getChannel());
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.ShowPostsHelper#getNewPosts()
	 */
	@Override
	public List<BCItem> getNewComments(){
		// TODO: not needed for basic client, go back and load new posts
		return null;
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.ShowPostsHelper#getComments()
	 */
	@Override
	public List<BCItem> getComments() {
		synchronized (this.postsLock) {
			return this.comments;
		}
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.ShowPostsHelper#getComment(int)
	 */
	@Override
	public BCItem getComment(int index){
		synchronized (this.postsLock) {
			if (comments == null) return null;
			return comments.get(index);
		}
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.ShowPostsHelper#setComment(int, tub.iosp.budcloand.framework.types.BCItem)
	 */
	@Override
	public void setComment(int index, BCItem item){
		synchronized (this.postsLock) {
			comments.set(index, item);
		}
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.ShowPostsHelper#getCommentCount()
	 */
	@Override
	public int getCommentCount(){
		synchronized (this.postsLock) {
			if(comments == null) return 0;
			return comments.size();
		}
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.ShowPostsHelper#onDestroy()
	 */
	@Override
	public void onDestroy() {
		if(this.comments != null) comments.close();
		
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.ShowPostsHelper#onPause()
	 */
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.ShowPostsHelper#onStop()
	 */
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.ShowPostsHelper#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.ShowPostsHelper#onRestart()
	 */
	@Override
	public void onRestart() {
		// TODO Auto-generated method stub
		
	}
}
