package tub.iosp.budcloand.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.HorizontalScrollView;

/**
 * This is the basic frame view of activites
 * Now it is just a HorizontalScrollView
 * Will be changed later to gain better scroll performance
 *
 */
public class BasicFrameView extends HorizontalScrollView{
	
	private final String TAG="BasicFrameView";
	

	public BasicFrameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public BasicFrameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public BasicFrameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	
	
}