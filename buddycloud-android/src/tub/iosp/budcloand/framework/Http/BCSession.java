/**
 * 
 */
package tub.iosp.budcloand.framework.Http;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.message.BasicHttpResponse;
import org.json.JSONException;

import android.graphics.Bitmap;

import tub.iosp.budcloand.framework.exceptions.BCIOException;
import tub.iosp.budcloand.framework.types.*;

//import tub.iosp.budcloand.framework.DefaultSession.LoginResponse;

/**
 * This is an Interface for the Buddycloud Http Sessions. Implementations of the interface can be used to exchange data with a given buddycloud apiserver
 * using a specified set of userdata.
 * @author Malcolm-X
 *
 */
public interface BCSession {

	//public boolean login();
	
	public String getUser();
	/**
	 * Get all Channels the user subscribed to. Use to verify the supplied login data
	 * @return The subscription or null if connection failed. Call the responses .getEntity() to retrieve the response body. If the body is null, check the statusline.
	 * @throws BCIOException 
	 * @throws JSONException 
	 */
	public List<BCSubscribtion> getSubscribed() throws BCIOException, JSONException;

	/**
	 * @param name Name of the Entity (channelnode or item).
	 * @return The content of the users home channel or null if connection failed. Call the responses .getEntity() to retrieve the response body. If the body is null, check the statusline.
	 * @throws BCIOException 
	 */
	//public BCResponse getContent(String name) throws BCIOException;
	
	/**
	 * @param channel The channelname: <channel>@<server> e.g. example@buddycloud.com
	 * @param name Name of the Entity (channelnode or item).
	 * @return The item or channelnode of the given channel or null if connection failed. Call the responses .getEntity() to retrieve the response body. If the body is null, check the statusline.
	 * @throws BCIOException 
	 */
	//public BCResponse getContent(String channel, String name) throws BCIOException;
	
	/**
	 * @param channel The channelname: <channel>@<server> e.g. example@buddycloud.com
	 * @param name Name of the Entity (channelnode or item).
	 * @param max Maximum number of entries.
	 * @param after Exclude all Entries before the given Date.
	 * @return The channelnodes Elements According to the given Parameters or null if connection failed. Call the Responses .getEntity() to retrieve the Response Body.If the Body is null, check the statusline.
	 * @throws BCIOException 
	 */
//	public BCResponse getContent(String channel, String name, int max, Date after) throws BCIOException;
	
	/**
	 * @param name Name of the Entity (channelnode or item).
	 * @param body The serialized Jason Representation. 
	 * @return The Contents URL or null if connection failed. Call the Responses .getEntity() to retrieve the Response Body. If the Body is null, check the statusline.
	 * @throws BCIOException 
	 */
//	public BCResponse postContent(String name, byte[] body) throws BCIOException;
	
	/**
	 * @param channel The channelname: <channel>@<server> e.g. example@buddycloud.com
	 * @param name Name of the Entity (channelnode or item).
	 * @param body The serialized Jason Representation. 
	 * @return The Contents URL or null if connection failed. Call the Responses .getEntity() to retrieve the Response Body. If the Body is null, check the statusline.
	 * @throws BCIOException 
	 */
//	public BCResponse postContent(String channel, String name, byte[] body) throws BCIOException;
	
	
	/**
	 * @param channel The channelname: <channel>@<server> e.g. example@buddycloud.com
	 * @param name Name of the channelnode
	 * @return The metadata for the given channelnode and the given channel or null if connection failed. Call the Responses .getEntity() to retrieve the Response Body. If the Body is null, check the statusline.
	 * @throws BCIOException 
	 */
	public BCMetaData getMetadata(String channel, String name) throws BCIOException;
	
	/**
	 * @param name Name of the channelnode
	 * @param body The serialized Jason Representation. 
	 * @return The metadata's URL or null if connection failed. Call the Responses .getEntity() to retrieve the Response Body. If the Body is null, check the statusline.
	 * @throws BCIOException 
	 */
	public BCMetaData postMetadata(String name, BCMetaData metadata) throws BCIOException;
	
	/**
	 * @param channel The channelname: <channel>@<server> e.g. example@buddycloud.com
	 * @param name Name of the channelnode
	 * @param body The serialized Jason Representation. 
	 * @return The metadata's URL or null if connection failed. Call the Responses .getEntity() to retrieve the Response Body. If the Body is null, check the statusline.
	 * @throws BCIOException 
	 */
	public BCMetaData postMetadata(String channel, String name, BCMetaData metadata) throws BCIOException;
	
	
	
	public List<BCItem> getPosts(String channelName, int max) throws BCIOException, JSONException;
	
	public List<BCItem> getPostsOlder(String channelName,Date date, int max) throws BCIOException, JSONException;
	
	public List<BCItem> getPostsNewer(String channelName,Date latest, int max) throws BCIOException, JSONException;
	
	//public BCItemList getPostsSync(String channelName, String sinceDate, int max) throws BCIOException;
	
	public Bitmap getImage(String imageURL) throws BCIOException;
	
	public Bitmap getAvatar(String channelName) throws BCIOException;
	
	public BCItem postPost(BCItem item) throws BCIOException;
	
	public BCItem postComment(BCItem item) throws BCIOException;
	
	public BCSubscribtion postSubscribe(String channelName) throws BCIOException;
	
	public BCItem postPost(String channelName, String content) throws BCIOException;
	
	public BCItem postComment(String channelName, String content, String replyTo)
			throws BCIOException;

	public BCSubscribtion postSubscribe(BCSubscribtion sub) throws BCIOException;

	public BCSubscribtion postSubscribe(String channelName, String node)
			throws BCIOException;
	
	public List<BCMetaData> searchChannel(String keyword, int max) throws BCIOException;
	
	//public BCResponse getSubscribers(String channel);
	
	//public BCResponse getSimilar(String channel, int max, int index);
	
	//public BCResponse sync(Date since, int max);
	
	//public BCResponse getMedia(String channel, String name);
	
	//public BCResponse postMedia(String channel, String name);
	
	//public BCResponse listMedia(String channel, int max, Date after);
	
	//public BCResponse getAvatar(String channel);
	
	//public BCResponse postAvatar(String channel);
	
	//public BCResponse createChannel(String name);
	
	//public BCResponse getSettings(String channel, String name);
	
	//public BCResponse postSettings(String channel, String name);
	
	//public BCResponse getSimilar(String channel, String name);
	
	/**
	 * 
	 */
	public void onSaveInstanceState();
	
	/**
	 * 
	 */
	public void onCreate();
	
	/**
	 * 
	 */
	public void onStart();
	
	/**
	 * 
	 */
	public void onRestart();
	
	/**
	 * 
	 */
	public void onResume();
	
	/**
	 * 
	 */
	public void onStop();
	
	/**
	 * 
	 */
	public void onPause();

	/**
	 * 
	 */
	public void onDestroy();





	
	
	
	
}
