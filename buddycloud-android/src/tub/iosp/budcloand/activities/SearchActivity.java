package tub.iosp.budcloand.activities;

import java.util.List;

import tub.iosp.budcloand.framework.helper.HttpClientHelper;
import tub.iosp.budcloand.framework.types.BCMetaData;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class SearchActivity extends Activity {
	private final static String TAG = "SearchActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
		List<BCMetaData> list = HttpClientHelper.INSTANCE.searchChannel("first", 10);
		if(list == null || list.isEmpty()){
			Log.d(TAG,"cannot get list");
		}
		else{
			for(BCMetaData meta:list){
				Log.d(TAG,"................................"+meta.getChannel());
			}
		}
	}
	
	/*@Override
	public boolean onSearchRequested() {
		// TODO Auto-generated method stub
		Log.d(TAG,"onSearchRequested");
		return super.onSearchRequested();
	}*/
}
