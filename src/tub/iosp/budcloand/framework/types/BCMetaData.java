package tub.iosp.budcloand.framework.types;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
@Table(name="Metadata")
public class BCMetaData extends BCDatabaseObject implements BCJSONObject, BCStamped{
	@Column(name = "title")
	private String title;
	@Column(name = "description")
	private String description;
	@Column(name = "creation_date")
	private Date creation_date;
	@Column(name = "access_model")
	private String access_model;
	@Column(name = "channel_type")
	private String channel_type;
	
	public BCMetaData(String title, String description, Date creation_date, String access_model, String channel_type){
		this.title = title;
		this.description = description;
		this.creation_date = creation_date;
		this.access_model = access_model;
		this.channel_type = channel_type;
	}
	
	public BCMetaData(String JsonString) {
		
		try {
			JSONObject object = new JSONObject(JsonString);
			this.title = object.getString("title");
			this.description = object.getString("description");
			//this.creation_date = object.getString("creation_data");
			//TODO parse **** Date
			this.access_model = object.getString("access_model");
			this.channel_type = object.getString("channel_type");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setTitle(String s){
		this.title = s;
	}
	
	public void setDescription(String s){
		this.description = s;
	}
	@Deprecated
	public void setCreationDate(Date d){
		this.creation_date = d;
	}
	
	public void setAccessModel(String s){
		this.access_model = s;
	}
	
	public void setChannelType(String s){
		this.channel_type = s;
	}
	
	//getters
	public String getTitle(){
		return this.title;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public String getCreationDate( ){
		return "TODO";
	}
	
	public String getAccessModel( ){
		return this.access_model;
	}
	
	public String getChannelType( ){
		return this.channel_type;
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

	@Override
	public int compareDate(BCStamped stampedEntity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Date getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDate(Date date) {
		// TODO Auto-generated method stub
		
	}
}
