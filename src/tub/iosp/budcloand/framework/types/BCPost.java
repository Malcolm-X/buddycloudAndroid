package tub.iosp.budcloand.framework.types;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import android.util.Log;

// TODO: FIX CONSTRUCTORS, GETTERS & SETTERS
/* Single item of a channel node
 * 
 */
@Table(name = "Posts")
public class BCPost extends BCItem implements BCJSONObject{
	
	private static final String TAG = "BCPost";
//	@Column(name = "post")
//	private BCItem post;
	private ArrayList<BCItem> commentList;
	@Deprecated
	public BCPost(BCItem post){
//		super(post);
//		this.post = post;
		commentList = new ArrayList<BCItem>();
	}
	
	public BCPost(String JsonText){
		commentList = new ArrayList<BCItem>();
		try {
			JSONObject object = new JSONObject(JsonText);
//			this.post = new BCItem(object.getJSONObject("post"));
			JSONArray array = object.getJSONArray("comments");
			for(int i=0;i<array.length();++i){
				JSONObject comment = array.getJSONObject(i);
				this.addComment(new BCItem(comment));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Deprecated
	public BCItem getPost(){
		return this;
	}
	
	public ArrayList<BCItem> getCommentList(){
		return this.commentList;
	}
	
	public void addComment(BCItem comment){
		commentList.add(comment);
	}

	
	@Override
	public JSONObject getJSONObject() {
		JSONObject object = new JSONObject();
		try {
			//object.put("post", this.post.getJSONObject());
			if(commentList != null && commentList.size()>0){
				JSONArray array = new JSONArray();
				for(BCItem comment:commentList){
					array.put(comment.getJSONObject());
				}
				object.put("comments", array);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}
	
	public int getCommentCount(){
		return this.commentList.size();
	}
	
	public String getRemoteId(){
		return this.getRemoteId();
	}
	
	public String getAuthor(){
		return this.getAuthor();
	}
	
//	public Date getUpdated(){
//		return this.getUpdated();
//	}
	
	public String getPublished(){
		return this.getPublished();
	}
	
	public String getContent(){
		return this.getContent();
	}

	@Override
	public String getJSONString() {
		
		//TODO only parse the content, Date, author, etc. will be filled by Server 
		//OR set Date to current Time(global Time)
		JSONObject object = new JSONObject();
		try {
			//object.put("post", this.post.getJSONObject());
			if(commentList != null && commentList.size()>0){
				JSONArray array = new JSONArray();
				for(BCItem comment:commentList){
					array.put(comment.getJSONObject());
				}
				object.put("comments", array);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object.toString();
	}
}
