package tub.iosp.budcloand.framework.control;

import java.util.List;

import tub.iosp.budcloand.framework.model.BCMetaData;
import tub.iosp.budcloand.framework.model.BCSubscribtion;

// TODO: Auto-generated Javadoc
/**
 * The Interface SearchActivityHelper is used for classes that shall be used as a
 * datasource for the SearchActivity. Implementations are supposed to be thread safe.
 */
public interface SearchActivityHelper {
	
	/**
	 * Search for channels by the given keyword.
	 *
	 * @param keyword the keyword
	 * @return the list
	 */
	public List<BCMetaData> search(String keyword);

	/**
	 * Search next after a given offset multiplied with the results per page value
	 * supplied in the constructor.
	 *
	 * @param keyword the keyword
	 * @param offset the offset
	 * @return the list
	 */
	public List<BCMetaData> searchNext(String keyword, int offset);
	
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
	
	/**
	 * Call subscribe.
	 *
	 * @param name channel's jid
	 * @return true, if successful
	 */
	public boolean callSubscribe(String name);

	/**
	 * Gets the subscribtions.
	 *
	 * @return the subscribtions
	 */
	public List<BCSubscribtion> getSubscribtions();

}
