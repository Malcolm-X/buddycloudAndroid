package tub.iosp.budcloand.framework.control;

import tub.iosp.budcloand.framework.exceptions.BCIOException;
import tub.iosp.budcloand.framework.model.BCItem;

// TODO: Auto-generated Javadoc
/**
 * The Interface NewPostActivityHelper is used for classes who want to serve 
 * as a helper for the NewPostActivity
 */
public interface NewPostActivityHelper {

	/**
	 * send a new post to the server.
	 *
	 * @param channel the channel's jid
	 * @param content the content of the post/comment
	 * @param replyTo the remoteId of the parent, if its a comment or null else
	 * @return the item
	 * @throws BCIOException on any failed IO
	 */
	public BCItem post(String channel, String content, String replyTo)
			throws BCIOException;
	
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