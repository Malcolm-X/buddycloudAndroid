package tub.iosp.budcloand.framework.types;

import java.text.ParseException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import tub.iosp.budcloand.framework.helper.IOHelper;

public class BCMetaData implements BCJSONObject{
	private Long id;
    private String title;
    private String channel;
    private String description;
    private java.util.Date creation_date;
    private String access_model;
    private String channel_type;
    private java.util.Date cached;
	
	
    public BCMetaData() {
    }

    public BCMetaData(Long id) {
        this.id = id;
    }

    public BCMetaData(Long id, String title, String channel, String description, java.util.Date creation_date, String access_model, String channel_type, java.util.Date cached) {
        this.id = id;
        this.title = title;
        this.channel = channel;
        this.description = description;
        this.creation_date = creation_date;
        this.access_model = access_model;
        this.channel_type = channel_type;
        this.cached = cached;
    }
	
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

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.util.Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(java.util.Date creation_date) {
        this.creation_date = creation_date;
    }

    public String getAccess_model() {
        return access_model;
    }

    public void setAccess_model(String access_model) {
        this.access_model = access_model;
    }

    public String getChannel_type() {
        return channel_type;
    }

    public void setChannel_type(String channel_type) {
        this.channel_type = channel_type;
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
			object.put("title", this.title);
			object.put("description",this.description);
			//object.put("creation_data",this.creation_date);
			//TODO parse **** Date
			object.put("access_model",this.access_model);
			object.put("channel_type",this.channel_type);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public String getJSONString() {
		//TODO only parse the content, Date will be filled by Server OR set Date to current Time(global Time)
		JSONObject object = new JSONObject();
		try {
			object.put("title", this.title);
			object.put("description",this.description);
			//object.put("creation_data",this.creation_date);
			//TODO parse **** Date
			object.put("access_model",this.access_model);
			object.put("channel_type",this.channel_type);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object.toString();
	}

	
}
