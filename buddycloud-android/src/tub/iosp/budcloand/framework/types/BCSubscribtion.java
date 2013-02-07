package tub.iosp.budcloand.framework.types;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


import android.util.Log;
public class BCSubscribtion implements BCJSONObject{
	
	private final static String TAG = "BCSubscription";
	
	/*private final static int MEMBERTYPE_OWNER = 1;
	private final static int MEMBERTYPE_PUBLISHER = 2;
	private final static int MEMBERTYPE_MEMBER = 3;*/
	
	
	private Long id;
	private String subscriber;
    private String channelAddress;
    private String memberType;
    private java.util.Date cached;
	
	
    public BCSubscribtion(Long id, String subscriber, String channelAddress, String memberType, java.util.Date cached) {
        this.id = id;
        this.subscriber = subscriber;
        this.channelAddress = channelAddress;
        this.memberType = memberType;
        this.cached = cached;
    }
	
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


	public BCSubscribtion() {
    }

    public BCSubscribtion(Long id) {
        this.id = id;
    }
    
    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannelAddress() {
        return channelAddress;
    }

    public void setChannelAddress(String channelAddress) {
        this.channelAddress = channelAddress;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public java.util.Date getCached() {
        return cached;
    }

    public void setCached(java.util.Date cached) {
        this.cached = cached;
    }

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

