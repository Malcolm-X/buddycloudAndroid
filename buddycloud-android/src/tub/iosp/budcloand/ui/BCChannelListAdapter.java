package tub.iosp.budcloand.ui;

import java.util.List;

import tub.iosp.budcloand.R;
import tub.iosp.budcloand.framework.helper.HttpClientHelper;
import tub.iosp.budcloand.framework.types.BCMetaData;
import tub.iosp.budcloand.framework.types.BCSubscribtion;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BCChannelListAdapter extends BaseAdapter{
	
	private final static String TAG = "BCChannelListAdapter";
	
	private List<BCMetaData> metaList;
	private Context context;
	private final LayoutInflater inflator;
//	private BasicActivityHelper helper;
	private static class ViewHolder{
		public TextView channelAddress;
		public ImageView channelAvatar;
		public TextView channelTitle;
		public TextView channelDescription;
	}
	
	public BCChannelListAdapter(Context context, List<BCMetaData> metaList){
		super();
		this.context = context;
		this.metaList = metaList;
//		this.helper = 
		inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		HttpClientHelper.INSTANCE.setPlaceholder(BitmapFactory.decodeResource(context.getResources(), R.drawable.usericon));
		Log.v(TAG,"channel adapter created,size : "+metaList.size());
	}
	

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
		HttpClientHelper.INSTANCE.loadBitmap(meta.getChannel(), holder.channelAvatar, 32, 32);
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.metaList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.metaList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}
