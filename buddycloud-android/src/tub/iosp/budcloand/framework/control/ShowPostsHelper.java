package tub.iosp.budcloand.framework.control;

import java.util.List;

import tub.iosp.budcloand.framework.model.BCItem;

// TODO: Auto-generated Javadoc
/**
 * The Interface ShowPostsHelper is used for Classes that work as a datasource for the
 * ShowPostsActivity. This is the Controler for this Activity. 
 * Implementations are supposed to be thread safe
 */
public interface ShowPostsHelper {

	/**
	 * Gets the new comments.
	 *
	 * @return the new comments
	 */
	public List<BCItem> getNewComments();

	/**
	 * Gets the comments.
	 *
	 * @return the comments
	 */
	public List<BCItem> getComments();

	/**
	 * Gets the comment from the commentlist.
	 *
	 * @param index the index
	 * @return the comment
	 */
	public BCItem getComment(int index);

	/**
	 * Sets the comment at this index (needed by adapter).
	 *
	 * @param index the index
	 * @param item the item
	 */
	public void setComment(int index, BCItem item);

	/**
	 * Gets the comment count.
	 *
	 * @return the comment count
	 */
	public int getCommentCount();
	
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