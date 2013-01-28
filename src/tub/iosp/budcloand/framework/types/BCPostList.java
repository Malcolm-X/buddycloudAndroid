package tub.iosp.budcloand.framework.types;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class BCPostList extends ArrayList<BCPost>{
	//TODO: FIX
	private static final String TAG = "BCPostList";
	
	public BCPostList(){
		super();
	}

	public BCPostList(String JsonText) {
		super();
//		broken
		
//		Log.e(TAG,"create post list: "+JsonText);
//		try {
//			JSONArray array = new JSONArray(JsonText);
//			for(int i=array.length()-1;i>=0;--i){
//				JSONObject obj = array.getJSONObject(i);
//				BCItem item = new BCItem(obj);
//				//String replyTo = item.getReplyTo();
//				if(replyTo != null){
//					for(BCPost post:this){
//						if(post.getPost().getRemoteId().compareTo(replyTo) == 0){
//							post.addComment(item);
//						}
//					}
//				}
//				else{
//					this.add(new BCPost(item));
//				}
//				
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
		
	}

}
