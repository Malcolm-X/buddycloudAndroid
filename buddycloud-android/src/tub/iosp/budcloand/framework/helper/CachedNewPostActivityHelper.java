package tub.iosp.budcloand.framework.helper;

import tub.iosp.budcloand.framework.exceptions.BCIOException;
import tub.iosp.budcloand.framework.types.BCItem;

public class CachedNewPostActivityHelper implements NewPostActivityHelper {
	public CachedNewPostActivityHelper(){
		super();
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.NewPostActivityHelper#post(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public BCItem post(String channel, String content, String replyTo) throws BCIOException{
		BCItem post = HttpClientHelper.INSTANCE.postComment(channel, content, replyTo);
		DatabaseHelper.INSTANCE.store(post);
		return post;
	}
}
