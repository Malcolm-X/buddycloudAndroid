package tub.iosp.budcloand.framework.control;

import java.util.List;

import tub.iosp.budcloand.framework.control.database.DatabaseHelper;
import tub.iosp.budcloand.framework.control.http.HttpClientHelper;
import tub.iosp.budcloand.framework.model.BCMetaData;
import tub.iosp.budcloand.framework.model.BCSubscribtion;
import de.greenrobot.dao.LazyList;




// TODO: Auto-generated Javadoc
/**
 * The Class BasicSearchActivityHelper.
 *
 * @Description impl of SearchActivityHelper
 * @author lsy
 */
public class BasicSearchActivityHelper implements SearchActivityHelper {
	
	/** The Constant TAG. */
	private static final String TAG = "CachedSearchActivityHelper";
	
	/** The results per page. */
	private final int RESULTS_PER_PAGE;
	
	/** The subscriptions. */
	private LazyList<BCSubscribtion> subscriptions;
	
	/**
	 * Instantiates a new basic search activity helper.
	 */
	public BasicSearchActivityHelper(){
		this.RESULTS_PER_PAGE = 20;
	}
	
	/**
	 * Instantiates a new basic search activity helper.
	 *
	 * @param resultsPerPage the results per page
	 */
	public BasicSearchActivityHelper(int resultsPerPage){
		this.RESULTS_PER_PAGE = resultsPerPage;
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.SearchActivityHelper#search(java.lang.String)
	 */
	@Override
	public List<BCMetaData> search(String keyword) {
		if (keyword!=null && HttpClientHelper.INSTANCE.checkNetworkInfo()) {
			return HttpClientHelper.INSTANCE.searchChannel(keyword, RESULTS_PER_PAGE);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.SearchActivityHelper#searchNext(java.lang.String, int)
	 */
	@Override
	public List<BCMetaData> searchNext(String keyword, int offset) {
		//TODO: Method-stub for later use of project-source
		return null;
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.SearchActivityHelper#getSubscribtions()
	 */
	@Override
	synchronized public List<BCSubscribtion> getSubscribtions() {
		if(this.subscriptions != null) this.subscriptions.close();
		this.subscriptions = (LazyList<BCSubscribtion>) DatabaseHelper.INSTANCE.getSubscribed(HttpClientHelper.INSTANCE.getUser());
		return this.subscriptions;
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.SearchActivityHelper#onDestroy()
	 */
	@Override
	public void onDestroy() {
		if(this.subscriptions != null) this.subscriptions.close();
		
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.SearchActivityHelper#onPause()
	 */
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.SearchActivityHelper#onStop()
	 */
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.SearchActivityHelper#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.SearchActivityHelper#onRestart()
	 */
	@Override
	public void onRestart() {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.SearchActivityHelper#callSubscribe(java.lang.String)
	 */
	@Override
	public boolean callSubscribe(String name) {
		return HttpClientHelper.INSTANCE.subscribe(name);

	}
}