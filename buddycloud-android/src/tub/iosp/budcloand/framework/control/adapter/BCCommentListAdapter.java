package tub.iosp.budcloand.framework.control.adapter;

import tub.iosp.budcloand.R;
import tub.iosp.budcloand.framework.control.ShowPostsHelper;
import tub.iosp.budcloand.framework.control.http.HttpClientHelper;
import tub.iosp.budcloand.framework.helper.IOHelper;
import tub.iosp.budcloand.framework.model.BCItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * The Class BCCommentListAdapter.
 */
public class BCCommentListAdapter extends BaseAdapter{

/** The tag. */
private final String TAG = "BCCommentListAdapter";
	
	/** The context. */
private Context context;
	
	/** The inflater. */
	LayoutInflater inflater;

	/** The helper. */
	private ShowPostsHelper helper;
	
	/**
	 * The Class ViewHolder.
	 */
	private class ViewHolder{
		
		/** The avatar. */
		public ImageView avatar;
		
		/** The author. */
		public TextView author;
		
		/** The updated. */
		public TextView updated;
		
		/** The content. */
		public LinearLayout content;
		
		/** The content text. */
		public TextView contentText;
	}
	
	/**
	 * Instantiates a new bC comment list adapter.
	 *
	 * @param context the context
	 * @param help the help
	 */
	public BCCommentListAdapter(Context context, ShowPostsHelper help){
		super();
//		commentList = list;
		this.helper = help;
		this.context = context;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return helper.getCommentCount();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return helper.getComment(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	/**
	 * notify Data set changed.
	 */
	public void dataSetChanged() {
		this.dataSetChanged();
	}
	
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
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
			HttpClientHelper.INSTANCE.loadBitmap(item.getAuthor(), holder.avatar, 50, 50, false);

		}
		
		return convertView;
	}
}
