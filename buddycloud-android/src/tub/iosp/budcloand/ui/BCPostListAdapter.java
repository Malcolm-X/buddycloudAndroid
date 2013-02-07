package tub.iosp.budcloand.ui;

import java.util.List;

import tub.iosp.budcloand.framework.helper.BasicActivityHelper;
import tub.iosp.budcloand.framework.helper.HttpClientHelper;
import tub.iosp.budcloand.framework.helper.IOHelper;
import tub.iosp.budcloand.framework.types.BCItem;
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
	
//	private BCPostList postList;
	private Context context;
	LayoutInflater inflater;

	private BasicActivityHelper helper;
	
	private class ViewHolder{
		public ImageView avatar;
		public TextView author;
		public TextView updated;
		public LinearLayout content;
	}
	
	public BCPostListAdapter(Context context, BasicActivityHelper help){
//		this.postList = list;
		this.helper = help;
		this.context = context;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		Log.d(TAG,"Helper.getPostCount() = "+helper.getPostCount());
		for(int i=0;i<helper.getPostCount();++i){
			Log.d(TAG,"............"+helper.getPost(i).getContent());
		}
	}
	
	@Override
	public int getCount() {
		return helper.getPostCount();
	}

	@Override
	public Object getItem(int position) {
		return helper.getPost(position);
	}
	
	public void loadNewPosts(){
		helper.loadNewPosts(100);
	}
	public void dataSetChanged(){
		this.notifyDataSetChanged();
	}
	
	public void loadMorePosts(){
		helper.loadMorePosts(100);
		this.notifyDataSetChanged();
	}
	
	
	@Override
	public long getItemId(int position) {
		//return ((BCItem)(this.getItem(position))).getId();
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
		BCItem post = helper.getPost(position);

		if (post != null) {
			/* to set the view according to the BCPost attributes */
			holder.author.setText(post.getAuthor());
			holder.updated.setText(IOHelper.dateToString(post.getUpdated()));
			
			TextView contentText = (TextView)holder.content.findViewById(R.id.post_contentText);

			contentText.setText(post.getContent());
			
			HttpClientHelper.INSTANCE.loadBitmap(post.getAuthor(), holder.avatar, 50, 50);

			int height = contentText.getHeight();
			Log.v(TAG,"the height of item "+position+" is: "+height);
		}
		
		return convertView;
	}
	
}
