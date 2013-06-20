package tub.iosp.budcloand.framework.model;

import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;

import tub.iosp.budcloand.framework.helper.IOHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class BCMetaData.
 * A representation of the metadata that describe a buddycloud-channelnode.
 */
public class BCMetaData implements BCJSONObject{
	
	/** The database id. Only change this if you wish to manage your
	 *  database keys manually*/
	private Long id;
    
    /** The title. */
    private String title;
    
    /** The channelname. */
    private String channel;
    
    /** The description. */
    private String description;
    
    /** The creation date. */
    private java.util.Date creation_date;
    
    /** The access model. */
    private String access_model;
    
    /** The channel type. */
    private String channel_type;
    
    /** The cached timestamp. */
    private java.util.Date cached;

    /**
     * Constructor.
     */
    public BCMetaData() {
    }

    /**
     * Constructor.
     *
     * @param id the id
     */
    public BCMetaData(Long id) {
        this.id = id;
    }

    /**
     * Instantiates a new bC meta data.
     *
     * @param id the database id. Set null to let the application handle the keys.
     * @param title the title
     * @param channel the channel
     * @param description the description
     * @param creation_date the creation_date
     * @param access_model the access_model
     * @param channel_type the channel_type
     * @param cached the cached
     */
    public BCMetaData(Long id, String title, String channel, String description, java.util.Date creation_date, String access_model, String channel_type, java.util.Date cached) {
        this.id = id;
        this.title = title;
        this.channel = channel;
        this.description = description;
        this.creation_date = creation_date;
        this.access_model = access_model;
        this.channel_type = channel_type;
        this.cached = cached;
//        this.alreadyFollowed = false;

    }
	
	/**
	 * Instantiates a new bC meta data. From a valid JSON representation.
	 * The String may contain fields that are not used or less fields than the
	 * actual entity.
	 * @param JsonString the JSON-string
	 * @param channel the channelname
	 */
	public BCMetaData(String JsonString, String channel) {
		this.channel = channel;
		JSONObject object = null;
		try {
			object = new JSONObject(JsonString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(object != null){
			try {
				this.title = object.getString("title");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			try {
				this.description = object.getString("description");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			try {
				String d = object.getString("creation_date");
				this.creation_date = IOHelper.stringToDate(d);
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			try {
				this.access_model = object.getString("access_model");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			try {
				this.channel_type = object.getString("channel_type");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * Instantiates a new bC meta data. From a valid JSON representation.
	 * The Object may contain fields that are not used or less fields than the
	 * actual entity.
	 *
	 * @param object the JSON-object
	 * @param channel the channelname
	 */
	public BCMetaData(JSONObject object, String channel){
		this.channel = channel;
		if(object != null){
			try {
				this.title = object.getString("title");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			try {
				this.description = object.getString("description");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			try {
				String d = object.getString("creation_date");
				this.creation_date = IOHelper.stringToDate(d);
			} catch (JSONException e) {
				try{
					this.channel_type = object.getString("creationDate");
				}catch(JSONException e1){
					e1.printStackTrace();
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			try {
				this.access_model = object.getString("access_model");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			try {
				this.channel_type = object.getString("channel_type");
			} catch (JSONException e) {
				try{
					this.channel_type = object.getString("channelType");
				}catch(JSONException e1){
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * Gets the database id for this item.
	 *
	 * @return the id in the db
	 */
	public Long getId() {
        return id;
    }

    /**
     * Sets the databse id for this item.
     *
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the channelname.
     *
     * @return the channelname
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Sets the channelname.
     *
     * @param channel the new channelname
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the creationdate.
     *
     * @return the creationdate
     */
    public java.util.Date getCreation_date() {
        return creation_date;
    }

    /**
     * Sets the creationdate.
     *
     * @param creation_date the new creation_date
     */
    public void setCreation_date(java.util.Date creation_date) {
        this.creation_date = creation_date;
    }

    /**
     * Gets the accessmodel.
     *
     * @return the accessmodel
     */
    public String getAccess_model() {
        return access_model;
    }

    /**
     * Sets the access_model.
     *
     * @param access_model the new access_model
     */
    public void setAccess_model(String access_model) {
        this.access_model = access_model;
    }

    /**
     * Gets the channel_type.
     *
     * @return the channel_type
     */
    public String getChannel_type() {
        return channel_type;
    }

    /**
     * Sets the channeltype.
     *
     * @param channel_type the new channel_type
     */
    public void setChannel_type(String channel_type) {
        this.channel_type = channel_type;
    }

    /**
     * Gets the cached timestamp.
     *
     * @return the cached timestamp
     */
    public java.util.Date getCached() {
        return cached;
    }

    /**
     * Sets the cached timestamp.
     *
     * @param cached the new cached timestamp
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
			if(this.title!= null) object.put("title", this.title);
			if(this.description!= null) object.put("description",this.description);
			if(this.creation_date!= null) object.put("creation_data",IOHelper.dateToString(this.creation_date));
			if(this.access_model!= null) object.put("access_model",this.access_model);
			if(this.channel_type!= null) object.put("channel_type",this.channel_type);
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
		JSONObject object = new JSONObject();
		try {
			if(this.title!= null)object.put("title", this.title);
			if(this.description != null) object.put("description",this.description);
			if(this.creation_date!= null)object.put("creation_data",IOHelper.dateToString(this.creation_date));
			if(this.access_model!= null) object.put("access_model",this.access_model);
			if(this.channel_type!= null) object.put("channel_type",this.channel_type);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object.toString();
	}

	
}
