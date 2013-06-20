package tub.iosp.budcloand.framework.control;

import tub.iosp.budcloand.framework.control.http.HttpClientHelper;
import tub.iosp.budcloand.framework.exceptions.BCIOException;
import tub.iosp.budcloand.framework.model.BCItem;

/**
 * The Class CachedNewPostActivityHelper.
 */
public class CachedNewPostActivityHelper implements NewPostActivityHelper {
	
	/**
	 * Instantiates a new cached new post activity helper.
	 */
	public CachedNewPostActivityHelper(){
		super();
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.NewPostActivityHelper#post(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public BCItem post(String channel, String content, String replyTo) throws BCIOException{
		if(!HttpClientHelper.INSTANCE.checkNetworkInfo()) throw new BCIOException(
				"Tryed to post a new posts, but no network connection was found.");
		BCItem post = HttpClientHelper.INSTANCE.postComment(channel, content, replyTo);
//		DatabaseHelper.INSTANCE.store(post);
		return post;
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.NewPostActivityHelper#onDestroy()
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.NewPostActivityHelper#onPause()
	 */
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.NewPostActivityHelper#onStop()
	 */
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.NewPostActivityHelper#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.NewPostActivityHelper#onRestart()
	 */
	@Override
	public void onRestart() {
		// TODO Auto-generated method stub
		
	}
}
