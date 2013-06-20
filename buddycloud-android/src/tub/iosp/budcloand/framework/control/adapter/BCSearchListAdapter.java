package tub.iosp.budcloand.framework.control.adapter;

import java.util.HashMap;
import java.util.List;

import tub.iosp.budcloand.R;
import tub.iosp.budcloand.framework.control.BasicSearchActivityHelper;
import tub.iosp.budcloand.framework.control.SearchActivityHelper;
import tub.iosp.budcloand.framework.control.http.HttpClientHelper;
import tub.iosp.budcloand.framework.model.BCMetaData;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * The Class BCSearchListAdapter.
 *
 * @Desciption Adapter for search-result list
 */
public class BCSearchListAdapter extends BaseAdapter{
	
	/** The Constant TAG. */
	private final static String TAG = "BCSearchListAdapter";
	
	/** The meta list. */
	private List<BCMetaData> metaList;
	
	/** The context. */
	private Context context;
	
	/** The helper. */
	SearchActivityHelper helper;
	
	/** The inflator. */
	private final LayoutInflater inflator;
	
	/** The hash map. */
	HashMap<String,String> hashMap;
	
	/**
	 * The Class ViewHolder.
	 */
	private static class ViewHolder{
		
		/** The result item avatar. */
		public ImageView resultItemAvatar;
		
		/** The result item description. */
		public TextView resultItemDescription;
		
		/** The result item title. */
		public TextView resultItemTitle;
		
		/** The result item name. */
		public TextView resultItemName;
		
		/** The result item follow. */
		public Button resultItemFollow;
	}
	
	
	/**
	 * Instantiates a new bC search list adapter.
	 *
	 * @param context the context
	 * @param metaList the meta list
	 * @param hashMap the hash map
	 * @Description Constructor of BCSearchListAdapter
	 */
	public BCSearchListAdapter(Context context, List<BCMetaData> metaList,HashMap<String,String> hashMap){
		super();
		this.context = context;
		this.metaList = metaList;
		inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		helper = new BasicSearchActivityHelper();
		HttpClientHelper.INSTANCE.setPlaceholder(BitmapFactory.decodeResource(context.getResources(), R.drawable.usericon));
		this.hashMap = hashMap;

	}


	/**
	 * Gets the context.
	 *
	 * @return Context
	 * @Description Getter of context
	 */
	public Context getContext(){
		return this.context;
	}

	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			
		final ViewHolder holder;
		
		
		if(convertView == null){
			convertView = inflator.inflate(R.layout.search_result_item, null);
			holder = new ViewHolder();
			holder.resultItemAvatar = (ImageView)convertView.findViewById(R.id.result_item_avatar);
			holder.resultItemDescription = (TextView)convertView.findViewById(R.id.result_item_description);
			holder.resultItemFollow = (Button)convertView.findViewById(R.id.result_item_follow);
			holder.resultItemName = (TextView)convertView.findViewById(R.id.result_item_name);
			holder.resultItemTitle = (TextView)convertView.findViewById(R.id.result_item_title);

			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		final BCMetaData meta = metaList.get(position);
		holder.resultItemName.setText(meta.getChannel());
		holder.resultItemTitle.setText(meta.getTitle());
		holder.resultItemDescription.setText(meta.getDescription());
		holder.resultItemAvatar.setTag(meta.getChannel());
			
		if(hashMap.containsKey(meta.getChannel()) && hashMap.get(meta.getChannel()).equals("true")){
			//the background color of follow button becomes grey
			holder.resultItemFollow.setBackgroundDrawable(this.getContext().getResources().getDrawable(R.drawable.activity_search_followed));
		}else{
			//the background color of follow button becomes green
			holder.resultItemFollow.setBackgroundDrawable(this.getContext().getResources().getDrawable(R.drawable.activity_search_follow));
		}
		
		holder.resultItemFollow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(hashMap.containsKey(meta.getChannel()) && hashMap.get(meta.getChannel()).equals("false")){
					//add into the followed list of the user
					addToList(meta);
					//set the background color of button
					holder.resultItemFollow.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.activity_search_followed));
				}
			}
		});
		HttpClientHelper.INSTANCE.loadBitmap(meta.getChannel(), holder.resultItemAvatar, 32, 32, false);
		return convertView;
	}
	
	/**
	 * Adds the to list.
	 *
	 * @param data the data
	 * @Description following a channel by subscribing it
	 */
	public void addToList(BCMetaData data){
		String channel = data.getChannel();
		if(helper.callSubscribe(channel)){
			 //set alreadyFollowed of the meta
              hashMap.remove(data.getChannel());
              hashMap.put(data.getChannel(), "true");
		}else{
		} 
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.metaList.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.metaList.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	

}

