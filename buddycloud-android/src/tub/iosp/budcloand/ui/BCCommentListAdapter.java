package tub.iosp.budcloand.ui;

import java.util.List;

import tub.iosp.budcloand.R;
import tub.iosp.budcloand.framework.helper.HttpClientHelper;
import tub.iosp.budcloand.framework.helper.IOHelper;
import tub.iosp.budcloand.framework.helper.ShowPostsHelper;
import tub.iosp.budcloand.framework.types.BCItem;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BCCommentListAdapter extends BaseAdapter{
private final String TAG = "BCCommentListAdapter";
	
//	private List<BCItem> commentList;
	private Context context;
	LayoutInflater inflater;

	private ShowPostsHelper helper;
	
	private class ViewHolder{
		public ImageView avatar;
		public TextView author;
		public TextView updated;
		public LinearLayout content;
		public TextView contentText;
	}
	
	public BCCommentListAdapter(Context context, ShowPostsHelper help){
		super();
//		commentList = list;
		this.helper = help;
		this.context = context;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return helper.getCommentCount();
	}

	@Override
	public Object getItem(int position) {
		return helper.getComment(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void dataSetChanged() {
		this.dataSetChanged();
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			
			convertView = inflater.inflate(R.layout.activity_showpost_commentlist_item, null);
			holder = new ViewHolder();
			holder.avatar = (ImageView) convertView
					.findViewById(R.id.comment_avatar);
			holder.author = (TextView) convertView.findViewById(R.id.comment_author);
			holder.updated = (TextView) convertView
					.findViewById(R.id.comment_updated);
			holder.content = (LinearLayout) convertView
					.findViewById(R.id.comment_content);
			holder.contentText = (TextView)convertView.findViewById(R.id.comment_contentText);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder)convertView.getTag();
		}
		BCItem item = helper.getComment(position);

		if (item != null) {
			/* to set the view according to the BCPost attributes */
			holder.author.setText(item.getAuthor());
			holder.updated.setText(IOHelper.dateToString(item.getUpdated()));
			TextView contentText = holder.contentText;
			contentText.setText(item.getContent());
			HttpClientHelper.INSTANCE.loadBitmap(item.getAuthor(), holder.avatar, 50, 50);

			//Log.v(TAG, "adapter for " + position + " called");
		}
		
		return convertView;
	}
}
