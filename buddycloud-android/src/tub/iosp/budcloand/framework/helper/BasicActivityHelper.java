package tub.iosp.budcloand.framework.helper;

import java.util.List;

import tub.iosp.budcloand.framework.types.BCItem;
import tub.iosp.budcloand.framework.types.BCMetaData;
import tub.iosp.budcloand.framework.types.BCSubscribtion;

public interface BasicActivityHelper {

	public List<BCItem> getPosts();

	public List<BCItem> loadMorePosts(int max);

	public BCItem getPost(int index);

	public int getPostCount();

	public List<BCMetaData> getMetaData(List<BCSubscribtion> list);

	public BCMetaData getMetaData(String channelName);

	public List<BCSubscribtion> getSubscribtions();
	
	public List<BCItem> loadNewPosts(int max);

}