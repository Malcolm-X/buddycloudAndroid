package tub.iosp.budcloand.ui;

import tub.iosp.budcloand.R;
import tub.iosp.budcloand.framework.helper.HttpClientHelper;
import tub.iosp.budcloand.framework.types.BCSubscription;
import tub.iosp.budcloand.framework.types.BCSubscriptionList;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BCChannelListAdapter extends ArrayAdapter<BCSubscription>{
	
	private final static String TAG = "BCChannelListAdapter";
	
	private BCSubscriptionList subList;
	private Context context;
	private final LayoutInflater inflator;
	private static class ViewHolder{
		public ImageView channelAvatar;
		public TextView channelAddress;
		public TextView channelDescription;
	}
	
	public BCChannelListAdapter(Context context,int resourceId, BCSubscriptionList subList){
		super(context,resourceId,subList);
		this.context = context;
		this.subList = subList;
		inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		HttpClientHelper.INSTANCE.setPlaceholder(BitmapFactory.decodeResource(context.getResources(), R.drawable.usericon));
		Log.v(TAG,"channel adapter created");
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
			holder.channelDescription = (TextView)convertView.findViewById(R.id.channellist_description);
			holder.channelAvatar = (ImageView)convertView.findViewById(R.id.channellist_avatar);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		BCSubscription sub = subList.get(position);
		holder.channelAddress.setText(sub.getChannelAddress());
		holder.channelDescription.setText("");
		holder.channelAvatar.setTag(sub.getChannelAddress());
		HttpClientHelper.INSTANCE.loadBitmap(sub.getChannelAddress(), holder.channelAvatar, 32, 32);
		return convertView;
	}

}
