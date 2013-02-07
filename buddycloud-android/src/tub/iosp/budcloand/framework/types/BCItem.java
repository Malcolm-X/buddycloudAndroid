package tub.iosp.budcloand.framework.types;

import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import tub.iosp.budcloand.framework.helper.IOHelper;
import tub.iosp.budcloand.framework.types.BCJSONObject;


import android.util.Log;
//@Table(name = "Items")
public class BCItem implements BCJSONObject{
	private static final String TAG = "BCItem"; 
    private Long id;
    private String remoteId;
    private String channel;
    private String author;
    private java.util.Date published;
    private java.util.Date updated;
    private String content;
    private String replyTo;
    private String itemType;
    private java.util.Date cached;
	
	
	public BCItem(JSONObject object, String channel){

			try {
				this.remoteId = object.getString("id");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			try {
				this.author = object.getString("author");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			try {
				String d = object.getString("published");
				this.published = IOHelper.stringToDate(d);
			} catch (JSONException e2) {
				e2.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			try {
				String d = object.getString("updated");
				this.updated = IOHelper.stringToDate(d);
			} catch (JSONException e1) {
				e1.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			try {
				this.content = object.getString("content");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			try {
				this.replyTo = object.getString("replyTo");
				this.itemType = "comment";
			} catch (JSONException e) {
				this.itemType = "post";
			}
			this.channel = channel;
		
	}
	
	public BCItem(String JsonText, String Channel) throws JSONException{
		this(new JSONObject(JsonText), Channel);
	}
	
    public BCItem() {
    }

    public BCItem(Long id) {
        this.id = id;
    }


    
    public BCItem(Long id, String remoteId, String channel, String author, java.util.Date published, java.util.Date updated, String content, String replyTo, String itemType, java.util.Date cached) {
        this.id = id;
        this.remoteId = remoteId;
        this.channel = channel;
        this.author = author;
        this.published = published;
        this.updated = updated;
        this.content = content;
        this.replyTo = replyTo;
        this.itemType = itemType;
        this.cached = cached;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public java.util.Date getPublished() {
        return published;
    }
    
    public String getPublishedString(){
    	return IOHelper.dateToString(published);
    }

    public void setPublished(java.util.Date published) {
        this.published = published;
    }
    
    public java.util.Date getUpdated() {
        return updated;
    }

    public void setUpdated(java.util.Date updated) {
        this.updated = updated;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public java.util.Date getCached() {
        return cached;
    }

    public void setCached(java.util.Date cached) {
        this.cached = cached;
    }

    

    


	
	
	
	//functionalities
    @Deprecated
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
			if(this.remoteId != null && this.remoteId.compareTo("") != 0)
				object.put("id", this.remoteId);
			if(this.author != null && this.author.compareTo("") != 0)
				object.put("author", this.author);
			if(this.published != null){
				object.put("published",IOHelper.dateToString(this.published));
			}
			if(this.updated != null){
				object.put("updated",IOHelper.dateToString(this.updated));
			}
			if(this.content != null && this.content.compareTo("") != 0)
				object.put("content",this.content);
			if(this.replyTo != null && this.replyTo.compareTo("") != 0)
				object.put("replyTo",this.replyTo);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
		
	}

	@Override
	public String getJSONString() {
		return this.getJSONObject().toString();
	}
}



