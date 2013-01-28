package tub.iosp.budcloand.framework.types;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.activeandroid.annotation.Table;

import android.util.Log;
@Table(name = "Subscriptions")
public class BCSubscription extends BCDatabaseObject implements BCJSONObject{
	
	private final static String TAG = "BCSubscription";
	
	/*private final static int MEMBERTYPE_OWNER = 1;
	private final static int MEMBERTYPE_PUBLISHER = 2;
	private final static int MEMBERTYPE_MEMBER = 3;*/
	
	
	
	String channelAddress;
	SubscriptionType memberType;//"owner", "publisher" or "member"
	
	
	public BCSubscription(String channelAddress,SubscriptionType role){
		this.channelAddress = channelAddress;
		this.memberType = role;
	}
	
	public BCSubscription(String channelAddress,String type){
		this.channelAddress = channelAddress;
		if(type.compareTo("owner") == 0){
			this.memberType = SubscriptionType.OWNER;
		}
		else if(type.compareTo("publisher") == 0){
			this.memberType = SubscriptionType.PUBLISHER;
		}
		else{
			this.memberType =  SubscriptionType.MEMBER;
		}
	}
	
	public BCSubscription(String JsonText) {
		// TODO Now the channel node is ignored. 
		//      e.g.this object only contains the channeladdress and its memberType
		try {
			JSONObject object = new JSONObject(JsonText);
			Iterator<String> itr = object.keys();
			if(itr.hasNext()){
				String name = itr.next();
				this.setMemberType(object.getString(name));
				String[] tmp = name.split("/");
				this.channelAddress = tmp[0];
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void setChannelAddress(String s){
		this.channelAddress = s;
	}
	
	
	public void setMemberType(SubscriptionType role){
		this.memberType = role;
	}
	
	public void setMemberType(String type){
		if(type.compareTo("owner") == 0){
			this.memberType = SubscriptionType.OWNER;
		}
		else if(type.compareTo("publisher") == 0){
			this.memberType = SubscriptionType.PUBLISHER;
		}
		else{
			this.memberType = SubscriptionType.MEMBER;
		}
	}
	
	public String getChannelAddress(){
		return this.channelAddress;
	}
	
	public SubscriptionType getMemberType(){
		return this.memberType;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject object = new JSONObject();
		
		try {
			object.put(this.channelAddress, this.getMemberTypeString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	public String getMemberTypeString(){
		String type = "";
		if(this.memberType == SubscriptionType.OWNER){
			type = "owner";
		}
		else if(this.memberType == SubscriptionType.PUBLISHER){
			type = "publisher";
		}
		else{
			type = "member";
		}
		return type;
	}

	@Override
	public String getJSONString() {
JSONObject object = new JSONObject();
		
		try {
			object.put(this.channelAddress, this.getMemberTypeString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object.toString();
	}

}

