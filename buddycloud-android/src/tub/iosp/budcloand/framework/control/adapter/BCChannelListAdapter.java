package tub.iosp.budcloand.framework.control.adapter;

import java.util.List;

import tub.iosp.budcloand.R;
import tub.iosp.budcloand.framework.control.http.HttpClientHelper;
import tub.iosp.budcloand.framework.model.BCMetaData;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * The Class BCChannelListAdapter.
 * 
 */
public class BCChannelListAdapter extends BaseAdapter{
	
	/** The Constant TAG. */
	private final static String TAG = "BCChannelListAdapter";
	
	/** The meta list. */
	private List<BCMetaData> metaList;
	
	/** The context. */
	private Context context;
	
	/** The inflator. */
	private final LayoutInflater inflator;
	/**
 * The Class ViewHolder to improve the performance of Listview
 */
private static class ViewHolder{
		
		/** The channel address. */
		public TextView channelAddress;
		
		/** The channel avatar. */
		public ImageView channelAvatar;
		
		/** The channel title. */
		public TextView channelTitle;
		
		/** The channel description. */
		public TextView channelDescription;
	}
	
	/**
	 * Instantiates a new bC channel list adapter.
	 *
	 * @param context the context
	 * @param metaList the meta list of channels the user subscribed to.
	 */
	public BCChannelListAdapter(Context context, List<BCMetaData> metaList){
		super();
		this.context = context;
		this.metaList = metaList;
//		this.helper = 
		inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		HttpClientHelper.INSTANCE.setPlaceholder(BitmapFactory.decodeResource(context.getResources(), R.drawable.usericon));
		Log.v(TAG,"channel adapter created,size : "+metaList.size());
	}
	

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		
		Log.e(TAG,"getView called: "+position);
		
		if(convertView == null){
			convertView = inflator.inflate(R.layout.left_menu_item, null);
			holder = new ViewHolder();
			holder.channelAddress = (TextView)convertView.findViewById(R.id.channellist_name);
			holder.channelTitle = (TextView)convertView.findViewById(R.id.channellist_title);
			holder.channelDescription = (TextView)convertView.findViewById(R.id.channellist_description);
			holder.channelAvatar = (ImageView)convertView.findViewById(R.id.channellist_avatar);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		BCMetaData meta = metaList.get(position);
		holder.channelAddress.setText(meta.getChannel());
		holder.channelTitle.setText(meta.getTitle());
		holder.channelDescription.setText(meta.getDescription());
		holder.channelAvatar.setTag(meta.getChannel());
		HttpClientHelper.INSTANCE.loadBitmap(meta.getChannel(), holder.channelAvatar, 32, 32, false);
		return convertView;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return this.metaList.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return this.metaList.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

}
