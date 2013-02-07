package tub.iosp.budcloand.framework.helper;

import java.util.List;

import android.util.Log;

import tub.iosp.budcloand.framework.types.BCMetaData;
import tub.iosp.budcloand.framework.types.BCSubscribtion;

public class CachedSearchActivityHelper implements SearchActivityHelper {
	private static final String TAG = "CachedSearchActivityHelper";
	@Override
	public List<BCMetaData> search(String keyword) {
		return null;
	}
	@Override
	public List<BCSubscribtion> getSubscribtions() {
		List<BCSubscribtion> subs = DatabaseHelper.INSTANCE.getSubscribtions(HttpClientHelper.INSTANCE.getUser());
		if(subs != null && !subs.isEmpty()){
			for (BCSubscribtion sub : subs) {
				DatabaseHelper.INSTANCE.delete(sub);
			}
		}
		subs = HttpClientHelper.INSTANCE.getSubscribed();
		
		if(subs != null && !subs.isEmpty()){
			for (BCSubscribtion sub : subs) {
				DatabaseHelper.INSTANCE.store(sub);
				Log.d(TAG,sub.getChannelAddress());
			}
		}
		return subs;
	}
}