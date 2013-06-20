package tub.iosp.budcloand.framework.model;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;


// TODO: Auto-generated Javadoc
/**
 * A representation of a users subscription to a channelnode.
 */
public class BCSubscribtion implements BCJSONObject{
	
	/** The Constant TAG. Used for logging. */
	private final static String TAG = "BCSubscription";
		
	/** The database id for this subscription. Managed by the application*/
	private Long id;
	
	/** The subscribers jid. */
	private String subscriber;
    
    /** The channel address. */
    private String channelAddress;
    
    /** The member type. */
    private String memberType;
    
    /** The cached timestamp. */
    private java.util.Date cached;
	
	
    /**
     * Standard Constructor.
     *
     * @param id the database id of this subscription.
     * set to null to let the Application manage your db keys. 
     * @param subscriber the subscriber's jid
     * @param channelAddress the channel's jid
     * @param memberType the member type 
     * valid: "member", "publisher", "owner"
     * @param cached the cached timestamp
     */
    public BCSubscribtion(Long id, String subscriber, String channelAddress, String memberType, java.util.Date cached) {
        this.id = id;
        this.subscriber = subscriber;
        this.channelAddress = channelAddress;
        this.memberType = memberType;
        this.cached = cached;
    }
	
	/**
	 * Instantiates a new subscription. From a valid JSON representation.
	 * The Object may contain fields that are not used or less fields than the
	 * actual entity.
	 *
	 * @param object the JSON object
	 * @param subscriber the subscriber
	 */
	public BCSubscribtion(JSONObject object, String subscriber) {
		if (object != null) {
			Iterator<String> itr = object.keys();
			if (itr.hasNext()) {
				String name = itr.next();
				String type = "";
				try {
					type = object.getString(name);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				this.setMemberType(type);
				String[] tmp = name.split("/");
				this.channelAddress = tmp[0];
			}
		}
		this.subscriber = subscriber;
	}
	
	/**
	 * Instantiates a new bC meta data. From a valid JSON representation.
	 * The String may contain fields that are not used or less fields than the
	 * actual entity.
	 * @param JsonText the JSON text
	 * @param subscriber the subscriber
	 */
	public BCSubscribtion(String JsonText, String subscriber) {
		// TODO Now the channel node is ignored. 
		//      e.g.this object only contains the channeladdress and its memberType

		JSONObject object = null;
		try {
			object = new JSONObject(JsonText);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (object != null) {
			Iterator<String> itr = object.keys();
			if (itr.hasNext()) {
				String name = itr.next();
				String type = null;
				try {
					type = object.getString(name);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if(type != null)
					this.setMemberType(type);
				String[] tmp = name.split("/");
				this.channelAddress = tmp[0];
			}
		}
		this.subscriber = subscriber;
	}


	/**
	 * Constructor.
	 */
	public BCSubscribtion() {
    }

    /**
     * Instantiates a new bC subscribtion.
     *
     * @param id the db id
     * set null to let the apllication manage your db keys
     */
    public BCSubscribtion(Long id) {
        this.id = id;
    }
    
    /**
     * Gets the subscriber.
     *
     * @return the subscriber's jid
     */
    public String getSubscriber() {
        return subscriber;
    }

    /**
     * Sets the subscriber.
     *
     * @param subscriber the new jid of the subscriber
     */
    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    /**
     * Gets the dbid.
     *
     * @return the id of this subscription in the db
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id in the database
     * setting id's manually can cause db corruption!
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the channel address.
     *
     * @return the channel' jid
     */
    public String getChannelAddress() {
        return channelAddress;
    }

    /**
     * Sets the channel address.
     *
     * @param channelAddress the new channel address (jid)
     */
    public void setChannelAddress(String channelAddress) {
        this.channelAddress = channelAddress;
    }

    /**
     * Gets the member type.
     *
     * @return the member type
     */
    public String getMemberType() {
        return memberType;
    }

    /**
     * Sets the member type.
     *
     * @param memberType the new member type. valid:("member","owner","publisher") 
     */
    public void setMemberType(String memberType) {
        this.memberType = memberType;
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
			object.put(this.channelAddress, memberType);
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
			object.put(this.channelAddress, memberType);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object.toString();
	}

}

