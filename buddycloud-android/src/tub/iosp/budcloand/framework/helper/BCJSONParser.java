package tub.iosp.budcloand.framework.helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tub.iosp.budcloand.framework.model.BCItem;
import tub.iosp.budcloand.framework.model.BCMetaData;
import tub.iosp.budcloand.framework.model.BCSubscribtion;
import android.util.Log;
import android.util.Pair;

// TODO: Auto-generated Javadoc
/**
 * The Class BCJSONParser contains usefull static methods to parse POJOs
 * into JSON and vise sersa.
 */
public class BCJSONParser {

	
	/** The Constant TAG. For logging */
	private static final String TAG = "BCJSONParser";

	/**
	 * parsing a JSON array String to list of BCitem.
	 *
	 * @param JsonText the json text
	 * @param channelName the channel's jid
	 * @return the parsed list
	 * @throws JSONException if parsing fails
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
	 * parsing a JSON Object String to list of BCSubscribtion.
	 *
	 * @param JsonText the JSON text
	 * @param channelName the channel's jid
	 * @return the parsed list
	 * @throws JSONException if parsing failed
	 */
	public static List<BCSubscribtion> parseSubscribtionList(String JsonText, String channelName) throws JSONException{
		List<BCSubscribtion> list = new ArrayList<BCSubscribtion>();
		
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
	 * parsing a JSON Object String into list of BCMetaData.
	 *
	 * @param JsonText the JSON text
	 * @return the list
	 * @throws JSONException if parsing fails
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
	 * parsing a JSON object to list of Pair<channelName,List<BCItem>>.
	 *
	 * @param JsonText the JSON text
	 * @return the list
	 * @throws JSONException the jSON exception
	 */
	public static List<Pair<String, List<BCItem>>> parseSync(String JsonText) throws JSONException  {
		List<Pair<String, List<BCItem>>> result = new ArrayList<Pair<String, List<BCItem>>>();
		
		Log.d(TAG,"parseSync called: "+JsonText);
		JSONObject object = new JSONObject(JsonText);
		
		Iterator itr = object.keys();
		while (itr.hasNext()) {
			List<BCItem> list = new ArrayList<BCItem>();

			String index = (String) itr.next();
			String[] tmp = index.split("/");
			String channelName = null;
			for(int i = 0;i < tmp.length; ++i){
				if(tmp[i].contains("@")){
					channelName = tmp[i];
					Log.d(TAG,"channel sync:"+channelName);
					break;
				}
			}
			if (channelName != null) {

				JSONArray posts = object.getJSONArray(index);
				for (int i = 0; i < posts.length(); ++i) {
					BCItem item = null;
					try {
						item = new BCItem(posts.getJSONObject(i), channelName);
						Log.d(TAG,"item sync:"+item.getContent()+" @ "+item.getChannel());
					} catch (JSONException e) {
						e.printStackTrace();
					}
					if (item != null)
						list.add(item);
				}

				result.add(new Pair<String, List<BCItem>>(channelName, list));
			}
		}

		return result;
	}

	/**
	 * Parses the sync count.
	 *
	 * @param JsonText the json text
	 * @return a list of name/value pairs; first = channel jid,  second = list of posts
	 * @throws JSONException if parsing fails
	 */
	public static List<Pair<String, Integer>> parseSyncCount(String JsonText) throws JSONException {
		List<Pair<String, Integer>> result = new ArrayList<Pair<String, Integer>>();
		Log.d(TAG,"parseSyncCount called: "+JsonText);
		
		JSONObject object = new JSONObject(JsonText);
		
		Iterator itr = object.keys();
		while (itr.hasNext()) {
			
			String index = (String) itr.next();
			String[] tmp = index.split("/");
			String channelName = null;
			for(int i = 0;i < tmp.length; ++i){
				if(tmp[i].contains("@")){
					channelName = tmp[i];
					break;
				}
			}
			if (channelName != null) {

				int count = object.getInt(index);
				result.add(new Pair<String, Integer>(channelName, count));
				Log.d(TAG,"channel sync:"+channelName+"counter:"+count);
			}
		}

		return result;
	}

}
