package tub.iosp.budcloand.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import tub.iosp.budcloand.R;
import tub.iosp.budcloand.R.id;
import tub.iosp.budcloand.framework.helper.HttpClientHelper;
import tub.iosp.budcloand.framework.types.BCMetaData;
import tub.iosp.budcloand.ui.BCSearchListAdapter;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class SearchActivity extends Activity {
	private final static String TAG = "SearchActivity";
	
	Button searchButton;
	EditText searchText;
	ListView resultList;	
	List<BCMetaData> list;
	LinearLayout resultItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		searchButton = (Button)findViewById(R.id.searchButton);
		searchText = (EditText)findViewById(R.id.searchWord);
		resultList = (ListView)findViewById(R.id.resultList);	
		resultItem = (LinearLayout)View.inflate(this.getApplicationContext(), R.layout.search_result_item,null);
			
		searchButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				onSearchButton();
			}});

		//setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
	
	}
	
	public List<BCMetaData> search(String searchWord){
		List<BCMetaData> ret = new ArrayList<BCMetaData>();
		//switch to this later, the following is just for test..
		BCMetaData testData1 = new BCMetaData(Long.getLong("1"), "title", "testData1@buddycloud.org", "i am testData1", new Date(), "access_model", "channel_type", new Date());
		BCMetaData testData2 = new BCMetaData(Long.getLong("2"), "title", "testData2@buddycloud.org", "i am testData2", new Date(), "access_model", "channel_type", new Date());

		ret.add(testData1);
		ret.add(testData2);
//		ret = HttpClientHelper.INSTANCE.search("first");

		return ret;
	}
		
	public void addToFollowedList(BCMetaData meta){
		//TODO add this channel to the channel-follow list of the user
		
	}
	
	public boolean alreadyFollowed(BCMetaData meta){
		//TODO check if it is already in the followed list of the user
		return false;
	}
	
	public void onSearchButton(){
		String searchWord = searchText.getText().toString();
		if(searchWord != null){
			list = search(searchWord);
			if(list == null || list.isEmpty()){
				Log.d(TAG,"cannot get list");
			}
			else{
				for(int i = 0; i < list.size() ;i++){
					BCMetaData meta = list.get(i);
					if(alreadyFollowed(meta)){
						meta.setAlreadyFollowed(true);	
					}
					else{
						meta.setAlreadyFollowed(false);	
						addToFollowedList(meta);
					}
				}
				
				resultList.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
//						TextView text = (TextView) view.findViewById(R.id.channellist_name);
//						String channelAddress = (String) text.getText();
//						onChannelChange(channelAddress);
						// paintPostList(channelAddress);
					}
				});

				BCSearchListAdapter searchListAdapter = new BCSearchListAdapter(this, list);
				resultList.setAdapter(searchListAdapter);
			}
		}
	}

}
