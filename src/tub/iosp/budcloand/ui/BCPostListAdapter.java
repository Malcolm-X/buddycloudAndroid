package tub.iosp.budcloand.ui;

import java.util.List;

import tub.iosp.budcloand.framework.helper.HttpClientHelper;
import tub.iosp.budcloand.framework.types.BCPost;
import tub.iosp.budcloand.framework.types.BCPostList;
import tub.iosp.budcloand.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BCPostListAdapter extends BaseAdapter {
	
	private final String TAG = "BCPostListAdapter";
	
	private BCPostList postList;
	private Context context;
	LayoutInflater inflater;
	
	private class ViewHolder{
		public ImageView avatar;
		public TextView author;
		public TextView updated;
		public LinearLayout content;
	}
	
	public BCPostListAdapter(Context context, BCPostList list){
		this.postList = list;
		this.context = context;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return postList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			
			convertView = inflater.inflate(R.layout.postlist_item_layout, null);
			holder = new ViewHolder();
			holder.avatar = (ImageView) convertView
					.findViewById(R.id.post_avatar);
			holder.author = (TextView) convertView.findViewById(R.id.post_author);
			holder.updated = (TextView) convertView
					.findViewById(R.id.post_updated);
			holder.content = (LinearLayout) convertView
					.findViewById(R.id.post_content);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder)convertView.getTag();
		}
		BCPost post = postList.get(position);

		if (post != null) {
			/* to set the view according to the BCPost attributes */
			holder.author.setText(post.getAuthor());
			holder.updated.setText(post.getUpdated());
			TextView contentText = new TextView(context);
			contentText.setText(post.getContent());
			holder.content.addView(contentText);
			HttpClientHelper.INSTANCE.loadBitmap(post.getAuthor(), holder.avatar, 50, 50);

			//Log.v(TAG, "adapter for " + position + " called");
		}
		
		return convertView;
	}
	
}
