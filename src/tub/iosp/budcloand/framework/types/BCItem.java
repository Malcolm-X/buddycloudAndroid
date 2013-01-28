package tub.iosp.budcloand.framework.types;

import java.lang.reflect.Modifier;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import android.util.Log;
@Table(name = "Items")
public class BCItem extends BCDatabaseObject implements BCJSONObject, BCStamped{
	private static final String TAG = "BCItem"; 
	
	@Column(name = "remoteId")
	private String remoteId;
	@Column(name= "author")
	private String author;
	@Column(name = "publisher")
	private String published;
	@Column(name = " updated")
	private Date updated;
	@Column(name= "content")
	private String content;
	//@Column(name = "replyTo")
	//private String replyTo;
	
	
	public BCItem(JSONObject object){
		try {
			this.remoteId = object.getString("id");
			this.author = object.getString("author");
			this.published = object.getString("published");
			//this.updated = object.getString("updated");
			//TODO parse this DATE!!!
			this.content = object.getString("content");
			//this.replyTo = object.getString("replyTo");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BCItem(String JsonText){
		try {
			/*JSONTokener jsonTokener = new JSONTokener(JsonText);*/
			/*JSONObject object = (JSONObject) jsonTokener.nextValue();*/
			JSONObject object = new JSONObject(JsonText);
			this.remoteId = object.getString("id");
			this.author = object.getString("author");
			this.published = object.getString("published");
			//this.updated = object.getString("updated");
			//TODO DATE parsing
			this.content = object.getString("content");
			//this.replyTo = object.getString("replyTo");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BCItem(String remoteId,String author,String published,Date updated,String content){
	    this.remoteId = remoteId;
	    this.author = author;
	    this.published = published;
	    this.updated = updated;
	    this.content = content;
	    //this.replyTo = "";
	}
	
	public BCItem(String remoteId,String author,String published,Date updated,String content,String replyTo){
	    this.remoteId = remoteId;
	    this.author = author;
	    this.published = published;
	    this.updated = updated;
	    this.content = content;
	    //this.replyTo = replyTo;
	}
	
	public BCItem(){
		
	}
	
	
	
	
	//setters
	public void setRemoteId(String s){
		this.remoteId = s;
	}
	
	public void setAuthor(String s){
		this.author = s;
	}
	
	public void setPublished(String s){
		this.published = s;
	}
	
	public void setUpdated(Date s){
		this.updated = s;
	}
	
	public void setContent(String s){
		this.content = s;
	}
	
//	public void setReplyTo(String s){
//		this.replyTo = s;
//	}

	//getters
	public String getRemoteId(){
		return this.remoteId;
	}
	
	public String getAuthor(){
		return this.author;
	}
	
	public String getPublished(){
		return this.published;
	}
	
	public String getUpdated(){
		//TODO: create String
		//return this.updated;
		return "";
	}
	
	public String getContent( ){
		return this.content;
	}
	
//	public String getReplyTo(){
//		return this.replyTo;
//	}
	
	//functionalities
	public void PostGet(){
		//Retrieves an item of a channel node.
		Log.v(TAG,"id : " + this.remoteId);
		Log.v(TAG,"author : " + this.author);
		Log.v(TAG,"published : "+this.published);
		Log.v(TAG,"updated : " + this.updated);
		Log.v(TAG,"content : " + this.content);	
		//Log.v(TAG,"replyTo : "+this.replyTo);
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject object = new JSONObject();
		try {
			object.put("id", this.remoteId);
			object.put("author", this.author);
			object.put("published",this.published);
			object.put("updated",this.updated);
			object.put("content",this.content);
			//object.put("replyTo",this.replyTo);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
		
	}

	@Override
	public String getJSONString() {
		JSONObject object = new JSONObject();
		try {
			object.put("id", this.remoteId);
			object.put("author", this.author);
			object.put("published",this.published);
			object.put("updated",this.updated);
			object.put("content",this.content);
			//object.put("replyTo",this.replyTo);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object.toString();
	}

	@Override
	public int compareDate(BCStamped stampedEntity) {
		return this.updated.compareTo(stampedEntity.getDate());
	}

	@Override
	public Date getDate() {
		// TODO Auto-generated method stub
		return this.updated;
	}

	@Override
	public void setDate(Date date) {
		this.updated = date;
		
	}
}
