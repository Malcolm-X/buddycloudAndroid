package tub.iosp.budcloand.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tub.iosp.budcloand.R;
import tub.iosp.budcloand.framework.control.BasicSearchActivityHelper;
import tub.iosp.budcloand.framework.control.adapter.BCSearchListAdapter;
import tub.iosp.budcloand.framework.model.BCMetaData;
import tub.iosp.budcloand.framework.model.BCSubscribtion;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;


/**
 * The Class SearchActivity.
 *
 * @Description activity for searching the relevant channels according to the pass-in search-keyword
 */
public class SearchActivity extends Activity {
	
	/** The Constant TAG. */
	private final static String TAG = "SearchActivity";
	
	/** The search button. */
	Button searchButton;
	
	/** The search text. */
	EditText searchText;
	
	/** The result list. */
	ListView resultList;	
	
	/** The list. */
	List<BCMetaData> list;
	
	/** The result item. */
	LinearLayout resultItem;
//	SearchActivityHelper helper;
	/** The helper. */
	BasicSearchActivityHelper helper;
	
	/** The sub list. */
	List<BCSubscribtion> subList;
	
	/** The search list adapter. */
	BCSearchListAdapter searchListAdapter;
	
	/** The hash map. */
	HashMap<String,String> hashMap;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search);
		
		helper = new BasicSearchActivityHelper();
		subList = helper.getSubscribtions();
		hashMap = new HashMap<String,String>();
		
		searchButton = (Button)findViewById(R.id.searchButton);
		searchText = (EditText)findViewById(R.id.searchWord);
		resultList = (ListView)findViewById(R.id.resultList);	
		resultItem = (LinearLayout)View.inflate(this.getApplicationContext(), R.layout.search_result_item,null);
			
		searchButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				onSearchButton();
			}});
		
		resultList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BCMetaData sub = list.get((int)id);
				Intent data = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("searched", sub.getChannel());
				data.putExtras(bundle);
				
				//data.putExtra("tub.iosp.budcloand.activities.search", sub.getChannel());
				
				setResult(RESULT_OK,data);
				finish();

			}
		});

	
	}

	
	
	
	/**
		 * Search.
		 *
		 * @param searchWord the search word
		 * @return list of search results
		 * @Description search the relevant channels according to the pass-in search-keyword
		 */
	public List<BCMetaData> search(String searchWord){
		List<BCMetaData> ret = new ArrayList<BCMetaData>();
		ret = helper.search(searchWord);
		return ret;
	}
	
	/**
	 * Gets the hash map.
	 *
	 * @return the hash map
	 */
	public HashMap getHashMap(){
		return this.hashMap;
	}
	
	/**
	 * Already followed.
	 *
	 * @param meta the meta
	 * @return true, if successful
	 * @Description check if the pass-in channel is already substribed or not
	 */
	public boolean alreadyFollowed(BCMetaData meta){
		//check if it is already in the followed list of the user
		if(subList == null || subList.isEmpty()){
			Log.d(TAG,"subList is NULL");
		}else{
			for(int i = 0; i < subList.size(); i++){
				BCSubscribtion temp = subList.get(i);
				if(temp.getChannelAddress().equals(meta.getChannel())){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * On search button.
	 *
	 * @Description called with search button is clicked, when there is no result, show a dialog to indicate it, otherwise to show the results in a ListView
	 */
	public void onSearchButton(){
		String searchWord = searchText.getText().toString();
		if(searchWord != null){
			list = search(searchWord);
			if(list == null || list.isEmpty()){
				//pop up a dialog to say there is no search result
				AlertDialog.Builder builder = new Builder(SearchActivity.this);
				builder.setTitle("there is no relevant result");
				builder.setPositiveButton("back", new android.content.DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();						
					}
				});
									
				builder.create().show();
				
				Log.d(TAG,"cannot get list");
			}
			else{
				for(int i = 0; i < list.size() ;i++){
					BCMetaData meta = list.get(i);
					if(alreadyFollowed(meta)){
						
						hashMap.put(meta.getChannel(),"true");
						
						
					}
					else{
						hashMap.put(meta.getChannel(),"false");
					}
				}

				searchListAdapter = new BCSearchListAdapter(this, list,hashMap);
				resultList.setAdapter(searchListAdapter);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			setResult(RESULT_CANCELED, null);
			finish();

		}
		return super.onKeyDown(keyCode, event);
	}

}
