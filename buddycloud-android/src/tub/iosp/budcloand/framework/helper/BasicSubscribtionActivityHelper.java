package tub.iosp.budcloand.framework.helper;

import java.util.ArrayList;
import java.util.List;

import tub.iosp.budcloand.framework.types.BCMetaData;

public class BasicSubscribtionActivityHelper implements SubscribtionActivityHelper {
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.SubscribtionActivityHelper#search(java.lang.String[])
	 */
	@Override
	public List<BCMetaData> search(String[] keywords){
		List<BCMetaData> result = new ArrayList<BCMetaData>();
		for (String s : keywords) result.addAll(HttpClientHelper.INSTANCE.searchChannel(s, 10));
		return result;
		
	}
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.helper.SubscribtionActivityHelper#subscribe(java.lang.String)
	 */
	@Override
	public boolean subscribe(String channel) {
		boolean result = HttpClientHelper.INSTANCE.subscribe(channel);
		return result;
	}
}
