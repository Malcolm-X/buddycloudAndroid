package tub.iosp.budcloand.framework.helper;

import tub.iosp.budcloand.framework.exceptions.BCIOException;
import tub.iosp.budcloand.framework.types.BCItem;

public interface NewPostActivityHelper {

	public BCItem post(String channel, String content, String replyTo)
			throws BCIOException;

}