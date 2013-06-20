package tub.iosp.budcloand.framework.model;

import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;

import tub.iosp.budcloand.framework.helper.IOHelper;


// TODO: Auto-generated Javadoc
/**
 * Representation of a content item on a buddycloud server.
 */
public class BCItem implements BCJSONObject{
	
	/** The Constant TAG. Used for logging */
	private static final String TAG = "BCItem"; 
    
    /** The id the local id for this item in the database. 
     *  Do not set this yourself unless you want to manage your database indexes yourself! */
    private Long id;
    
    /** The remote id for this item. */
    private String remoteId;
    
    /** The channel's jid. */
    private String channel;
    
    /** The author's jid. */
    private String author;
    
    /** The Date the Post was published. */
    private java.util.Date published;
    
    /** The updated timestamp if existing. */
    private java.util.Date updated;
    
    /** The content of the post(text). */
    private String content;
    
    /** The id of the parent post this item replies to. Only used for posts */
    private String replyTo;
    
    /** The item type("post","comment",...). */
    private String itemType;
    
    /** The cache timestamp. */
    private java.util.Date cached;
	
	
	/**
	 * Instantiates a new item from the given JSON-String if it is valid.
	 *
	 * @param object A JSONObject representing the item, will be parsed.
	 * @param channel the channelname of this item.
	 */
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
	
	/**
	 * Instantiates a new bC item from a valid JSON representation of this object.
	 *
	 * @param JsonText A valid JSON representation of this item as a String.
	 * @param Channel the channel's jid for this item
	 * @throws JSONException if the string cannot be parsed to an object
	 */
	public BCItem(String JsonText, String Channel) throws JSONException{
		this(new JSONObject(JsonText), Channel);
	}
	
    /**
     * Instantiates a new empty item.
     */
    public BCItem() {
    }

    /**
     * Instantiates a new bC item with the given local id.
     *
     * @param id the local database id. Set null to let the application manage your indexes
     */
    public BCItem(Long id) {
        this.id = id;
    }


    
    /**
     * Instantiates a new bC item.
     *
     * @param id the local id. the local database id. 
     * Set null to let the application manage your indexes
     * @param remoteId the remote id for this item.
     * @param channel the channel's jid
     * @param author the author's jid (user@server.com)
     * @param published the published timestamp.
     * @param updated the updated timestamp.
     * @param content the content of this item.
     * @param replyTo the parent posts remoteId if this is a comment
     * @param itemType the item type ("post","comment")
     * @param cached the cached time stamp.
     */
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

    /**
     * Gets the id.
     *
     * @return the local database id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the local databse id.
     *
     * @param id the new id. 
     * Setting this manually can corrupt the database.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the remote id.
     *
     * @return the remote id
     */
    public String getRemoteId() {
        return remoteId;
    }

    /**
     * Sets the remote id.
     *
     * @param remoteId the new remote id
     */
    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    /**
     * Gets the channel.
     *
     * @return the channel's jid
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Sets the channel.
     *
     * @param channel the new channelname (jid)
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * Gets the author.
     *
     * @return the author's jid
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author.
     *
     * @param author the new author (jid)
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the published timestamp.
     *
     * @return the published timestamp.
     */
    public java.util.Date getPublished() {
        return published;
    }
    
    /**
     * Gets the published timestamp.
     *
     * @return the published timestamp as String in ISO 8601 format.
     */
    public String getPublishedString(){
    	return IOHelper.dateToString(published);
    }

    /**
     * Sets the published timestamp.
     *
     * @param published the new published timestamp
     */
    public void setPublished(java.util.Date published) {
        this.published = published;
    }
    
    /**
     * Gets the updated.
     *
     * @return the updated
     */
    public java.util.Date getUpdated() {
        return updated;
    }

    /**
     * Sets the updated.
     *
     * @param updated the new updated
     */
    public void setUpdated(java.util.Date updated) {
        this.updated = updated;
    }

    /**
     * Gets the content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content.
     *
     * @param content the new content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the reply to.
     *
     * @return the reply to
     */
    public String getReplyTo() {
        return replyTo;
    }

    /**
     * Sets the reply to.
     *
     * @param replyTo the new reply to
     */
    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    /**
     * Gets the item type.
     *
     * @return the item type
     */
    public String getItemType() {
        return itemType;
    }

    /**
     * Sets the item type.
     *
     * @param itemType the new item type
     */
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    /**
     * Gets the cached.
     *
     * @return the cached
     */
    public java.util.Date getCached() {
        return cached;
    }

    /**
     * Sets the cached.
     *
     * @param cached the new cached
     */
    public void setCached(java.util.Date cached) {
        this.cached = cached;
    }

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.model.BCJSONObject#getJSONObject()
	 */
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

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.model.BCJSONObject#getJSONString()
	 */
	@Override
	public String getJSONString() {
		return this.getJSONObject().toString();
	}
}



