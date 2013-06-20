/**
 * 
 */
package tub.iosp.budcloand.framework.control.http;

import java.util.Date;
import java.util.List;

import org.json.JSONException;

import tub.iosp.budcloand.framework.exceptions.BCIOException;
import tub.iosp.budcloand.framework.model.BCItem;
import tub.iosp.budcloand.framework.model.BCMetaData;
import tub.iosp.budcloand.framework.model.BCSubscribtion;
import android.graphics.Bitmap;
import android.util.Pair;

// TODO: Auto-generated Javadoc

/**
 * This is an Interface for the Buddycloud Http Sessions. Implementations of the interface can be used to exchange data with a given buddycloud apiserver
 * using a specified set of userdata.
 * @author Malcolm-X
 *
 */
public interface BCSession {

	//public boolean login();
	
	/**
	 * Gets the user.
	 *
	 * @return the user's jid
	 */
	public String getUser();
	
	/**
	 * Get all Channels the user subscribed to. Use to verify the supplied login data
	 *
	 * @return The subscription or null if connection failed.
	 * @throws BCIOException if something went wrong while exchanging data, use to implement custom error handling
	 * @throws JSONException if the servers response cannot be parsed
	 */
	public List<BCSubscribtion> getSubscribed() throws BCIOException, JSONException;


	
	
	/**
	 * Gets the metadata for a specific channelnode.
	 *
	 * @param channel The channelname (jid): "<channel>@<server>" e.g. "example@buddycloud.com"
	 * @param name Name of the channelnode: e.g. "/posts"
	 * @return The metadata for the given channelnode and the given channel or null if connection failed.
	 * @throws BCIOException if something went wrong while exchanging data, use to implement custom error handling
	 */
	public BCMetaData getMetadata(String channel, String name) throws BCIOException;
	
	/**
	 * Post metadata to a channelnode on the users homechannel.
	 *
	 * @param name Name of the channelnode
	 * @param metadata the metadata
	 * @return The metadata if transmission was successful
	 * @throws BCIOException if something went wrong while exchanging data, use to implement custom error handling
	 */
	public BCMetaData postMetadata(String name, BCMetaData metadata) throws BCIOException;
	
	/**
	 * Post metadata to a specific channelnode on the given channel.
	 *
	 * @param channel The channelname: <channel>@<server> e.g. example@buddycloud.com
	 * @param name Name of the channelnode
	 * @param metadata the metadata
	 * @return The metadata if transmission was successful
	 * @throws BCIOException if something went wrong while exchanging data, use to implement custom error handling
	 */
	public BCMetaData postMetadata(String channel, String name, BCMetaData metadata) throws BCIOException;
	
	
	
	/**
	 * Gets the posts from a channel.
	 *
	 * @param channelName The channelname: <channel>@<server> e.g. example@buddycloud.com
	 * @param max the maximum number of items that will be downloaded. note that this is not the number of actual posts downloaded. Set 0 to ignore
	 * @return the posts
	 * @throws BCIOException if something went wrong while exchanging data, use to implement custom error handling
	 * @throws JSONException if the servers response cannot be parsed
	 */
	public List<BCItem> getPosts(String channelName, int max) throws BCIOException, JSONException;
	
	/**
	 * Gets the posts that are older than the given post from a channel .
	 *
	 * @param post the post that specifies the after
	 * @param max the maximum number of items to download. Note that this is not the actual number of posts downloaded. Set 0 to ignore
	 * @return the posts older than the given post
	 * @throws BCIOException if something went wrong while exchanging data, use to implement custom error handling
	 * @throws JSONException if the servers response cannot be parsed
	 */
	public List<BCItem> getPostsOlder(BCItem post, int max) throws BCIOException, JSONException;
 	
	/**
	 * Gets the posts older than the given post from a channel.
	 *
	 * @param channelName the channel's jid.
	 * @param remoteId the remoteid of the post that specifies the after
	 * @param max the max number of items to download. Note that this is not the number of posts, set 0 to ignore
	 * @return the posts older the given post
	 * @throws BCIOException if something went wrong while exchanging data, use to implement custom error handling
	 * @throws JSONException if the servers response cannot be parsed
	 */
	public List<BCItem> getPostsOlder(String channelName,String remoteId, int max) throws BCIOException, JSONException;
	
	/**
	 * Gets the posts before that given date.
	 *
	 * @param channelName the channel's jid
	 * @param latest the date for new posts
	 * @param max the maximum number of posts, set 0 to ignore
	 * @return the posts newer than the date
	 * @throws BCIOException if something went wrong while exchanging data, use to implement custom error handling
	 * @throws JSONException if the servers response cannot be parsed
	 */
	public List<BCItem> getPostsNewer(String channelName,Date latest, int max) throws BCIOException, JSONException;
	
	//public BCItemList getPostsSync(String channelName, String sinceDate, int max) throws BCIOException;
	
	/**
	 * Gets the image from the media server.
	 *
	 * @param imageURL the image url
	 * @return the image
	 * @throws BCIOException if something went wrong while exchanging data, use to implement custom error handling
	 */
	public Bitmap getImage(String imageURL) throws BCIOException;
	
	/**
	 * Gets the avatar from the media server.
	 *
	 * @param channelName the channel's jid
	 * @return the avatar
	 * @throws BCIOException if something went wrong while exchanging data, use to implement custom error handling
	 */
	public Bitmap getAvatar(String channelName) throws BCIOException;
	
	/**
	 * Post post to a channel.
	 *
	 * @param item the post
	 * @return the post, must contain all fields needed to parse and the channelname
	 * @throws BCIOException if something went wrong while exchanging data, use to implement custom error handling
	 */
	public BCItem postPost(BCItem item) throws BCIOException;
	
	/**
	 * Post comment to a channel.
	 *
	 * @param item the item
	 * @return the comment must contain all fields needed to parse and the channelname
	 * @throws BCIOException if something went wrong while exchanging data, use to implement custom error handling
	 */
	public BCItem postComment(BCItem item) throws BCIOException;
	
	/**
	 * Post new post to a channel.
	 *
	 * @param channelName the channel's jid
	 * @param content the content of the post
	 * @return the new post
	 * @throws BCIOException if something went wrong while exchanging data, use to implement custom error handling
	 */
	public BCItem postPost(String channelName, String content) throws BCIOException;
	
	/**
	 * Post new comment to a channel.
	 *
	 * @param channelName the channel's jid
	 * @param content the content of the comment
	 * @param replyTo the remote id for the parent post
	 * @return the new created comment
	 * @throws BCIOException if something went wrong while exchanging data, use to implement custom error handling
	 */
	public BCItem postComment(String channelName, String content, String replyTo)
			throws BCIOException;
	
	/**
	 * Subscribe to the given channel.
	 *
	 * @param channelName the channel's jid
	 * @return the the subscription
	 * @throws BCIOException if something went wrong while exchanging data, use to implement custom error handling
	 */
	public BCSubscribtion postSubscribe(String channelName) throws BCIOException;
	
	

	/**
	 * Subscribe to the channel(node) specified in the Sibscription.
	 *
	 * @param sub the subscription to be created
	 * @return the created subscription
	 * @throws BCIOException if something went wrong while exchanging data, use to implement custom error handling
	 */
	public BCSubscribtion postSubscribe(BCSubscribtion sub) throws BCIOException;

	/**
	 * Subscribe to a specific node on the given channel.
	 *
	 * @param channelName the channel's jid
	 * @param node the node e.g. "/posts"
	 * @return the new subscription
	 * @throws BCIOException if something went wrong while exchanging data, use to implement custom error handling
	 */
	public BCSubscribtion postSubscribe(String channelName, String node)
			throws BCIOException;
	
	/**
	 * Search for channels with a given keyword.
	 *
	 * @param keyword a keyword used for the search
	 * @param max the max numer of searchresults, set 0 to ignore
	 * @return the list
	 * @throws BCIOException if something went wrong while exchanging data, use to implement custom error handling
	 */
	public List<BCMetaData> searchChannel(String keyword, int max) throws BCIOException;
	
	/**
	 * Search for channels with a given keyword.
	 *
	 * @param keyword a keyword used for the search
	 * @param offset the offset for the results
	 * @param max the max number of searchresults, set 0 to ignore
	 * @return the list
	 */
	public List<BCMetaData> searchChannel(String keyword, int offset, int max);
	/**
	 * Gets the number of new posts for all subscribed channels.
	 *
	 * @param since sync all posts since this date
	 * @param max the maximum number of posts per channel to count, set 0 to ignore param
	 * @return A list of Pairs: first= channel-jid, second = the number of new posts
	 * @throws BCIOException if something went wrong while exchanging data, use to implement custom error handling
	 * @throws JSONException if the servers response cannot be parsed
	 */
	public List<Pair<String, Integer>> getSyncCount(Date since, int max) throws BCIOException, JSONException;
	
	/**
	 * Gets all new posts for all subscribed channels. 
	 *
	 * @param since sync all posts since this date
	 * @param max the maximum number of posts per channel, set 0 to ignore
	 * @return A list of Pairs: first= channel-jid, second = the list of new posts 
	 * @throws BCIOException if something went wrong while exchanging data, use to implement custom error handling
	 * @throws JSONException if the servers response cannot be parsed
	 */
	public List<Pair<String, List<BCItem>>> getSync(Date since, int max)
			throws BCIOException, JSONException;
	
	






	
	
	
	
}
