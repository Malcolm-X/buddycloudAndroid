package tub.iosp.budcloand.framework.helper;

import java.util.List;

import tub.iosp.budcloand.framework.types.BCItem;

public interface ShowPostsHelper {

	public List<BCItem> getNewComments();

	public List<BCItem> getComments();

	public BCItem getComment(int index);

	public void setComment(int index, BCItem item);

	public int getCommentCount();

}