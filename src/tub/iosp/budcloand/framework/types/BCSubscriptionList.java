package tub.iosp.budcloand.framework.types;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

/*
 * contain the user's subscriptions
 */
public class BCSubscriptionList extends ArrayList<BCSubscription> implements BCJSONObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -171092685793931201L;
	private final static String TAG = "BCSubscriptionList";
	
	public BCSubscriptionList(){
		super();
	}
	
	/*Construct from a JSON string, for comvenience*/
	
	public BCSubscriptionList(String JsonText){
		super();
		
		try {
			JSONObject object = new JSONObject(JsonText);
			Iterator it = object.keys();
			while(it.hasNext()){
				String channel = (String) it.next();
				String type = object.getString(channel);
				
				String[] tmp = channel.split("/");
				BCSubscription sub = new BCSubscription(tmp[0], type);
				
				// TODO: the channel node is ignored.!!!
				
				if(!this.contains(sub)){
					this.add(sub);
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void subscriptionsUserGet(){
		//Retrieves the list 
		Iterator<BCSubscription> it = this.iterator();
		BCSubscription tempSub;
		while(it.hasNext()){
			tempSub = it.next();
			Log.v(TAG,"user name : " + tempSub.getChannelAddress());
			Log.v(TAG,"member type : " + tempSub.getMemberType());	
		}
		
	}
	
	public void subscriptionsUserPost(BCSubscription s,boolean subscribe){
		 //Subscribes(1) to or unsubscribes(0) from a node
		if(subscribe){
			addSubs(s);
		}else{
			int num = searchItem(s.getChannelAddress());
			if(num == -1)
				Log.e(TAG,"no such element in the list");
			else
				deleteSubs(num);
		}
	}
	
	public void deleteSubs(int i){
		this.remove(i);
	}
	
	public int searchItem(String s){
		Iterator<BCSubscription> it = this.iterator();
		int i = 0;
		while(it.hasNext()){
			if(it.next().getChannelAddress().equals(s))
				return i;
			i++;			
		}
		return -1;
	}
	
	public void addSubs(BCSubscription s){
		this.add(s);
	}
	
	@Override
	public JSONObject getJSONObject(){
		JSONObject object = new JSONObject();
		for(BCSubscription sub:this){
			try {
				object.put(sub.getChannelAddress(), sub.getMemberTypeString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return object;
	}

	@Override
	public String getJSONString() {
		JSONObject object = new JSONObject();
		for(BCSubscription sub:this){
			try {
				object.put(sub.getChannelAddress(), sub.getMemberTypeString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return object.toString();
	}

}

