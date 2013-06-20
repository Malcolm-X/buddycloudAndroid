package tub.iosp.budcloand.framework.control.adapter;

import tub.iosp.budcloand.R;
import tub.iosp.budcloand.framework.control.BasicActivityHelper;
import tub.iosp.budcloand.framework.control.http.HttpClientHelper;
import tub.iosp.budcloand.framework.helper.IOHelper;
import tub.iosp.budcloand.framework.model.BCItem;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class BCPostListAdapter.
 */
public class BCPostListAdapter extends BaseAdapter {
	
	/** The tag. */
	private final String TAG = "BCPostListAdapter";
	
//	private BCPostList postList;
	/** The context. */
private Context context;
	
	/** The inflater. */
	LayoutInflater inflater;

	/** The helper. */
	private BasicActivityHelper helper;
	
	/** The has next. */
	private boolean hasNext = true;
	
	/**
	 * The Class ViewHolder to improve the performance of Listview.
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
	}
	
	/**
	 * Instantiates a new bC post list adapter.
	 *
	 * @param context the context
	 * @param help the help
	 */
	public BCPostListAdapter(Context context, BasicActivityHelper help){
		this.helper = help;
		this.context = context;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return helper.getPostCount();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return helper.getPost(position);
	}
	
	/**
	 * Load new posts.
	 */
	public void loadNewPosts(){
		helper.loadNewPosts(100);
	}
	
	/**
	 * Data set changed.
	 */
	public void dataSetChanged(){
		this.notifyDataSetChanged();
	}
	
	/**
	 * Load more posts.
	 *
	 * @param max the max
	 * @return true, if successful
	 */
	public boolean loadMorePosts(int max){
		if(helper.loadMorePosts(max) != null){
			this.notifyDataSetChanged();
			return true;
		}
		else{
			this.hasNext = false;
			return false;
		}
	}
	
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		//return ((BCItem)(this.getItem(position))).getId();
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
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
			holder.updated.setText(post.getUpdated().toGMTString());
			
			TextView contentText = (TextView)holder.content.findViewById(R.id.post_contentText);

			contentText.setText(post.getContent());
			
			HttpClientHelper.INSTANCE.loadBitmap(post.getAuthor(), holder.avatar, 60, 60, false);

			int height = contentText.getHeight();
		}
		
		return convertView;
	}
	
	/**
	 * Checks for next.
	 *
	 * @return true, if successful
	 */
	public boolean hasNext(){
		return this.hasNext;
	}
}
