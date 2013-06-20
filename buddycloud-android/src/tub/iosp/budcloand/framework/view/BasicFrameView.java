package tub.iosp.budcloand.framework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

// TODO: Auto-generated Javadoc
/**
 * This is the basic frame view of activites
 * extended from a HorizontalScrollView
 */
public class BasicFrameView extends HorizontalScrollView{
	
	/** The tag. */
	private final String TAG="BasicFrameView";
	

	/**
	 * Instantiates a new basic frame view.
	 *
	 * @param context the context
	 */
	public BasicFrameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Instantiates a new basic frame view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public BasicFrameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Instantiates a new basic frame view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 * @param defStyle the def style
	 */
	public BasicFrameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see android.widget.HorizontalScrollView#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return false;
	}
	
	
}