package tub.iosp.budcloand.framework.view;

import java.util.Date;

import tub.iosp.budcloand.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * A pull to refresh listview
 */
public class PostListView extends ListView implements OnScrollListener {

	/** The Constant TAG. */
	private static final String TAG = "listview";

	/** The Constant RELEASE_To_REFRESH. */
	private final static int RELEASE_To_REFRESH = 0;
	
	/** The Constant PULL_To_REFRESH. */
	private final static int PULL_To_REFRESH = 1;
	
	/** The Constant REFRESHING. */
	private final static int REFRESHING = 2;
	
	/** The Constant DONE. */
	private final static int DONE = 3;
	
	/** The Constant LOADING. */
	private final static int LOADING = 4;
	
	// the ratio of the padding distance compared to the real height
	/** The Constant RATIO. */
	private final static int RATIO = 3;

	/** The inflater. */
	private LayoutInflater inflater;

	/** The head view. */
	private LinearLayout headView;

	/** The tips textview. */
	private TextView tipsTextview;
	
	/** The last updated text view. */
	private TextView lastUpdatedTextView;
	
	/** The arrow image view. */
	private ImageView arrowImageView;
	
	/** The progress bar. */
	private ProgressBar progressBar;


	/** The animation. */
	private RotateAnimation animation;
	
	/** The reverse animation. */
	private RotateAnimation reverseAnimation;

	//to make sure the value of startY only be recored once in a touch event
	/** The is recored. */
	private boolean isRecored;
	
	/** The head content width. */
	private int headContentWidth;
	
	/** The head content height. */
	private int headContentHeight;

	/** The start y. */
	private int startY;
	
	/** The first item index. */
	private int firstItemIndex;

	/** The state. */
	private int state;

	/** The is back. */
	private boolean isBack;

	/** The refresh listener. */
	private OnRefreshListener refreshListener;

	/** The is refreshable. */
	private boolean isRefreshable;

	/**
	 * Instantiates a new post list view.
	 *
	 * @param context the context
	 */
	public PostListView(Context context) {
		super(context);
		init(context);
	}

	/**
	 * Instantiates a new post list view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public PostListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * Inits the.
	 *
	 * @param context the context
	 */
	private void init(Context context) {
		setCacheColorHint(context.getResources().getColor(R.color.transparent));
		inflater = LayoutInflater.from(context);

		headView = (LinearLayout) inflater.inflate(R.layout.pull_to_refresh_header, null);

		arrowImageView = (ImageView) headView
				.findViewById(R.id.head_arrowImageView);
		arrowImageView.setMinimumWidth(70);
		arrowImageView.setMinimumHeight(50);
		progressBar = (ProgressBar) headView
				.findViewById(R.id.head_progressBar);
		tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);
		lastUpdatedTextView = (TextView) headView
				.findViewById(R.id.head_lastUpdatedTextView);

		measureView(headView);
		headContentHeight = headView.getMeasuredHeight();
		headContentWidth = headView.getMeasuredWidth();

		headView.setPadding(0, -1 * headContentHeight, 0, 0);
		headView.invalidate();

		Log.v("size", "width:" + headContentWidth + " height:"
				+ headContentHeight);

		addHeaderView(headView, null, false);
		setOnScrollListener(this);

		animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);

		reverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);

		state = DONE;
		isRefreshable = false;
	}

	/* (non-Javadoc)
	 * @see android.widget.AbsListView.OnScrollListener#onScroll(android.widget.AbsListView, int, int, int)
	 */
	public void onScroll(AbsListView arg0, int firstVisiableItem, int arg2,
			int arg3) {
		firstItemIndex = firstVisiableItem;
	}

	/* (non-Javadoc)
	 * @see android.widget.AbsListView.OnScrollListener#onScrollStateChanged(android.widget.AbsListView, int)
	 */
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
	}

	/* (non-Javadoc)
	 * @see android.widget.AbsListView#onTouchEvent(android.view.MotionEvent)
	 */
	public boolean onTouchEvent(MotionEvent event) {

		if (isRefreshable) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (firstItemIndex == 0 && !isRecored) {
					isRecored = true;
					startY = (int) event.getY();
					//Log.v(TAG, "on touch event down");
				}
				break;

			case MotionEvent.ACTION_UP:

				if (state != REFRESHING && state != LOADING) {
					if (state == DONE) {
						// do nothing
					}
					if (state == PULL_To_REFRESH) {
						state = DONE;
						changeHeaderViewByState();

						//Log.v(TAG, "pull to refresh -> done");
					}
					if (state == RELEASE_To_REFRESH) {
						state = REFRESHING;
						changeHeaderViewByState();
						onRefresh();

						//Log.v(TAG, "release to update -> done");
					}
				}

				isRecored = false;
				isBack = false;

				break;

			case MotionEvent.ACTION_MOVE:
				int tempY = (int) event.getY();

				if (!isRecored && firstItemIndex == 0) {
					//Log.v(TAG, "on touch event move");
					isRecored = true;
					startY = tempY;
				}

				if (state != REFRESHING && isRecored && state != LOADING) {


					if (state == RELEASE_To_REFRESH) {

						setSelection(0);

						if (((tempY - startY) / RATIO < headContentHeight)
								&& (tempY - startY) > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();

							//Log.v(TAG, "release -> pull");
						}
						else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();

							//Log.v(TAG, "releas -> done");
						}
						else {
						}
					}
					if (state == PULL_To_REFRESH) {

						setSelection(0);

						if ((tempY - startY) / RATIO >= headContentHeight) {
							state = RELEASE_To_REFRESH;
							isBack = true;
							changeHeaderViewByState();

							//Log.v(TAG, "done -> release");
						}
						else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();

							//Log.v(TAG, "done/release -> done");
						}
					}

					// on done
					if (state == DONE) {
						if (tempY - startY > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();
						}
					}

					if (state == PULL_To_REFRESH) {
						headView.setPadding(0, -1 * headContentHeight
								+ (tempY - startY) / RATIO, 0, 0);

					}

					if (state == RELEASE_To_REFRESH) {
						headView.setPadding(0, (tempY - startY) / RATIO
								- headContentHeight, 0, 0);
					}

				}

				break;
			}
		}

		return super.onTouchEvent(event);
	}

	// called when state changed
	/**
	 * Change header view by state.
	 */
	private void changeHeaderViewByState() {
		switch (state) {
		case RELEASE_To_REFRESH:
			arrowImageView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			arrowImageView.clearAnimation();
			arrowImageView.startAnimation(animation);

			tipsTextview.setText("Release To Update");

			//Log.v(TAG, "on release");
			break;
		case PULL_To_REFRESH:
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.VISIBLE);
			if (isBack) {
				isBack = false;
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(reverseAnimation);

				tipsTextview.setText("Pull To Refresh");
			} else {
				tipsTextview.setText("Pull To Refresh");
			}
			//Log.v(TAG, "on Pull");
			break;

		case REFRESHING:

			headView.setPadding(0, 0, 0, 0);

			progressBar.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.GONE);
			tipsTextview.setText("Refreshing...");
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			//Log.v(TAG, "current : Refreshing...");
			break;
		case DONE:
			headView.setPadding(0, -1 * headContentHeight, 0, 0);

			progressBar.setVisibility(View.GONE);
			arrowImageView.clearAnimation();
			arrowImageView.setImageResource(R.drawable.arrow);
			tipsTextview.setText("Pull To Refresh");
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			//Log.v(TAG, "current : done");
			break;
		}
	}

	/**
	 * Sets the on refresh listener.
	 *
	 * @param refreshListener the new on refresh listener
	 */
	public void setOnRefreshListener(OnRefreshListener refreshListener) {
		this.refreshListener = refreshListener;
		isRefreshable = true;
	}

	/**
	 * The listener interface for receiving onRefresh events.
	 * The class that is interested in processing a onRefresh
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addOnRefreshListener<code> method. When
	 * the onRefresh event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see OnRefreshEvent
	 */
	public interface OnRefreshListener {
		
		/**
		 * On refresh.
		 */
		public void onRefresh();
	}

	/**
	 * On refresh complete.
	 */
	public void onRefreshComplete() {
		state = DONE;
		lastUpdatedTextView.setText("Last Updated:" + new Date().toLocaleString());
		changeHeaderViewByState();
	}

	/**
	 * On refresh.
	 */
	public void onRefresh() {
		if (refreshListener != null) {
			refreshListener.onRefresh();
		}
	}

	/**
	 * Measure view.
	 *
	 * @param child the child
	 */
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	/**
	 * Sets the adapter.
	 *
	 * @param adapter the new adapter
	 */
	public void setAdapter(BaseAdapter adapter) {
		lastUpdatedTextView.setText("Last Updated:" + new Date().toLocaleString());
		super.setAdapter(adapter);
	}

}
