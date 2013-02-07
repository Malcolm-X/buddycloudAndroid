package tub.iosp.budcloand.framework.helper;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import tub.iosp.budcloand.framework.types.BCItem;

public class CachedShowPostHelper implements ShowPostsHelper {
	private static final String TAG = "CSP Helper";

	private BCItem parent;
	private List<BCItem> comments;
	
	public CachedShowPostHelper(BCItem parentPost) {
		this.parent = parentPost;
		//TODO: getComments not implemented (Always return null)
		this.comments = DatabaseHelper.INSTANCE.getComments(parent);
//		if (this.comments== null) this.comments =new ArrayList<BCItem>();
		Log.d(TAG, "ChachedShowPostsHelper started with id : " + parent.getRemoteId());
		Log.d(TAG, "channel for parent is : " + parent.getChannel());
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.ShowPostsHelper#getNewPosts()
	 */
	@Override
	public List<BCItem> getNewComments(){
		// TODO: not yet implemented, waiting for api change
		return null;
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.ShowPostsHelper#getComments()
	 */
	@Override
	public List<BCItem> getComments() {
		return this.comments;
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.ShowPostsHelper#getComment(int)
	 */
	@Override
	public BCItem getComment(int index){
		if (comments == null) return null;
		return comments.get(index);
		
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.ShowPostsHelper#setComment(int, tub.iosp.budcloand.framework.types.BCItem)
	 */
	@Override
	public void setComment(int index, BCItem item){
		comments.set(index, item);
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.ShowPostsHelper#getCommentCount()
	 */
	@Override
	public int getCommentCount(){
		if(comments == null) return 0;
		return comments.size();
	}
}
