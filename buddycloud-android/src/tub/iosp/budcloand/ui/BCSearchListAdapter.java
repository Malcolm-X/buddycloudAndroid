package tub.iosp.budcloand.ui;

import java.util.List;

import tub.iosp.budcloand.R;
import tub.iosp.budcloand.framework.helper.HttpClientHelper;
import tub.iosp.budcloand.framework.types.BCMetaData;
import tub.iosp.budcloand.framework.types.BCSubscribtion;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BCSearchListAdapter extends BaseAdapter{
	
	private final static String TAG = "BCSearchListAdapter";
	
	private List<BCMetaData> metaList;
	private Context context;
	private final LayoutInflater inflator;
	private static class ViewHolder{
		public ImageView resultItemAvatar;
		public TextView resultItemDescription;
		public TextView resultItemTitle;
		public TextView resultItemName;
		public Button resultItemFollow;
	}
	
	public BCSearchListAdapter(Context context, List<BCMetaData> metaList){
		super();
		this.context = context;
		this.metaList = metaList;
		inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		HttpClientHelper.INSTANCE.setPlaceholder(BitmapFactory.decodeResource(context.getResources(), R.drawable.usericon));
		Log.v(TAG,"search adapter created,size : "+metaList.size());
	}

	public Context getContext(){
		return this.context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			
		final ViewHolder holder;
		
		Log.e(TAG,"getView called: "+position);
		
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
			
		if(meta.getAlreadyFollowed()){
			//the background color of follow button becomes grey
			holder.resultItemFollow.setBackgroundDrawable(this.getContext().getResources().getDrawable(R.drawable.activity_search_followed));
		}else{
			//the background color of follow button becomes green
			holder.resultItemFollow.setBackgroundDrawable(this.getContext().getResources().getDrawable(R.drawable.activity_search_follow));
		}
		
		holder.resultItemFollow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!meta.getAlreadyFollowed()){
					//add into the followed list of the user
					addToList(meta);
					//set the background color of button
					holder.resultItemFollow.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.activity_search_followed));
					//set alreadyFollowed of the meta
					meta.setAlreadyFollowed(false);
				}
			}
		});
		HttpClientHelper.INSTANCE.loadBitmap(meta.getChannel(), holder.resultItemAvatar, 32, 32);
		return convertView;
	}
	
	public void addToList(BCMetaData data){
		//TODO 
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

