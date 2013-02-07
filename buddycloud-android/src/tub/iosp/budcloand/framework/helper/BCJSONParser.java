package tub.iosp.budcloand.framework.helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.util.Pair;

import tub.iosp.budcloand.framework.exceptions.BCJSONException;
import tub.iosp.budcloand.framework.types.BCItem;
import tub.iosp.budcloand.framework.types.BCMetaData;
import tub.iosp.budcloand.framework.types.BCSubscribtion;

public class BCJSONParser {

	
	/**
	 * @Description: parsing a JSON array String to list of BCitem
	 * @param JsonText
	 * @param channelName
	 * @return
	 * @throws JSONException
	 */
	public static List<BCItem> parseItemList(String JsonText, String channelName)
			throws JSONException {

		JSONArray array  = new JSONArray(JsonText);
		List<BCItem> list = new ArrayList<BCItem>();
		
		if (array != null) {
			for (int i = 0; i < array.length(); ++i) {
				JSONObject obj = null;
				try {
					obj = array.getJSONObject(i);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if(obj != null){
					BCItem item = new BCItem(obj, channelName);
					list.add(item);
				}
			}
		}
		return list;
	}
	
	/**
	 * @Description: parsing a JSON Object String to list of BCSubscribtion
	 * @param JsonText
	 * @param channelName
	 * @return
	 * @throws JSONException
	 */
	public static List<BCSubscribtion> parseSubscribtionList(String JsonText, String channelName) throws JSONException{
		List<BCSubscribtion> list = new ArrayList<BCSubscribtion>();
		//Log.e("","Get sub list String:"+JsonText);
		
			JSONObject obj = new JSONObject(JsonText);
			Iterator itr = obj.keys();
			
			HashSet<String> set = new HashSet<String>();
			while(itr.hasNext()){
				String name = (String) itr.next();
				String[] tmp = name.split("/");
				BCSubscribtion sub = new BCSubscribtion(null, channelName, tmp[0], obj.getString(name), null);
				if(!set.contains(sub.getChannelAddress())){
					list.add(sub);
					set.add(sub.getChannelAddress());
				}
			}
		return list;
	}
	
	
	/**
	 * @Description: parsing a JSON Object String into list of BCMetaData
	 * @param JsonText: should contain a key "items" mapping to an JSONArray of MetaData
	 * @return
	 * @throws JSONException
	 */
	public static List<BCMetaData> parseMetadataList(String JsonText) throws JSONException {
		List<BCMetaData> list = new ArrayList<BCMetaData>();

		JSONObject object = new JSONObject(JsonText);
		JSONArray array = object.getJSONArray("items");
		
		for (int i = 0; i < array.length(); ++i) {
			try {
				JSONObject obj = array.getJSONObject(i);
				String channel = obj.getString("jid");
				BCMetaData meta = new BCMetaData(obj,channel);
				if(meta != null)
					list.add(meta);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		
		return list;
		
	}
	
	/**
	 * @Description: parsing a JSON object to list of Pair<channelName,List<BCItem>>
	 * @param JsonText
	 * @return
	 * @throws JSONException 
	 */
	public static List<Pair<String, List<BCItem>>> parseSync(String JsonText) throws JSONException  {
		List<Pair<String, List<BCItem>>> result = new ArrayList<Pair<String, List<BCItem>>>();
		JSONObject object = new JSONObject(JsonText);

		Iterator itr = object.keys();
		while (itr.hasNext()) {
			List<BCItem> list = new ArrayList<BCItem>();

			String channelName = (String) itr.next();
			JSONArray posts = object.getJSONArray(channelName);
			for (int i = 0; i < posts.length(); ++i) {
				BCItem item = null;
				try {
					item = new BCItem(posts.getJSONObject(i), channelName);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if(item != null)
					list.add(item);
			}
			result.add(new Pair<String, List<BCItem>>(channelName, list));
		}

		return result;
	}

}
