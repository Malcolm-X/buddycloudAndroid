package tub.iosp.budcloand.framework.control.database;

import java.util.Date;
import java.util.List;

import tub.iosp.budcloand.framework.exceptions.BCIOException;
import tub.iosp.budcloand.framework.model.BCItem;
import tub.iosp.budcloand.framework.model.BCMetaData;
import tub.iosp.budcloand.framework.model.BCSubscribtion;
import tub.iosp.budcloand.framework.model.CacheTimeFrame;
import tub.iosp.budcloand.framework.model.channelPostsScope;

/**
 * The Interface BCDatabase. This is the interface for all databases. 
 * Classes implementing this Interface are supposed to be threadsafe
 */
public interface BCDatabase {

	
	
	
	/**
	 * Gets the subscriptions for a given User.
	 *
	 * @param user the user's jid
	 * @return the subscription list
	 * @throws BCIOException on any error, use to implement custom error handling
	 */
	public List<BCSubscribtion> getSubscribed(String user) throws BCIOException;

	/**
	 * Gets the metadata for a given channel.
	 *
	 * @param channelName the channel's jid
	 * @return the metadatalist
	 * @throws BCIOException on any error, use to implement custom error handling
	 */
	public BCMetaData getMetadata(String channelName) throws BCIOException;
	
	/**
	 * Gets all posts for a given channel.
	 *
	 * @param channelName the channel's jid
	 * @return the posts list
	 * @throws BCIOException on any error, use to implement custom error handling
	 */
	public List<BCItem> getPosts(String channelName) throws BCIOException;
	
	/**
	 * Gets the comments on  for a given parent post on the given channel.
	 *
	 * @param postID the parent post's id
	 * @param channel the channel's jid
	 * @return the comments list
	 * @throws BCIOException on any error, use to implement custom error handling
	 */
	public List<BCItem> getComments(String postID, String channel) throws BCIOException;
	
	/**
	 * Store an Entity into the database.
	 *
	 * @param channel the channel
	 * @return true, if successful
	 * @throws BCIOException on any error, use to implement custom error handling
	 */
	public boolean store(channelPostsScope channel) throws BCIOException;
	
	/**
	 * Store an Entity into the database.
	 *
	 * @param subscribtion the subscription
	 * @return true, if successful
	 * @throws BCIOException on any error, use to implement custom error handling
	 */
	public boolean store(BCMetaData subscribtion) throws BCIOException;
	
	/**
	 * Store an Entity into the database.
	 *
	 * @param subscribtion the subscription
	 * @return true, if successful
	 * @throws BCIOException on any error, use to implement custom error handling
	 */
	public boolean store(BCSubscribtion subscribtion) throws BCIOException;
	
	/**
	 * Store an Entity into the database.
	 *
	 * @param item the item
	 * @return true, if successful
	 * @throws BCIOException on any error, use to implement custom error handling
	 */
	public boolean store(BCItem item) throws BCIOException;
	
	/**
	 * Store item list.
	 *
	 * @param list the list
	 * @return true, if successful
	 * @throws BCIOException on any error, use to implement custom error handling
	 */
	public boolean storeItemList(List<BCItem> list) throws BCIOException;
	
	/**
	 * Store meta data list.
	 *
	 * @param list the list
	 * @return true, if successful
	 * @throws BCIOException on any error, use to implement custom error handling.
	 */
	public boolean storeMetaDataList(List<BCMetaData> list) throws BCIOException;
	
	/**
	 * Store subscribtion list.
	 *
	 * @param list the list
	 * @return true, if successful
	 * @throws BCIOException on any error, use to implement custom error handling.
	 */
	public boolean storeSubscribtionList(List<BCSubscribtion> list) throws BCIOException;
	
	/**
	 * Close the database, if needed.
	 *
	 * @throws BCIOException on any error, use to implement custom error handling.
	 */
	void close() throws BCIOException;

	/**
	 * Gets the channels cache_time_frame list
	 *
	 * @param name the channel's jid
	 * @return the channels posts scopes
	 * @throws BCIOException on any error, use to implement custom error handling.
	 */
	public channelPostsScope getChannel(String name) throws BCIOException;

	/**
	 * Start new session, if needed.
	 *
	 * @throws BCIOException on any error, use to implement custom error handling.
	 */
	void startNewSession() throws BCIOException;

	/**
	 * Gets all posts before the given date for the given channel.
	 *
	 * @param channel the channel's jid
	 * @param last the date
	 * @return the posts list
	 * @throws BCIOException on any error, use to implement custom error handling.
	 */
	public List<BCItem> getPosts(String channel, Date last) throws BCIOException;

	/**
	 * Store an Entity into the database.
	 *
	 * @param frame the frame
	 * @return true, if successful
	 * @throws BCIOException on any error, use to implement custom error handling.
	 */
	public boolean store(CacheTimeFrame frame) throws BCIOException;

	/**
	 * Delete an entity from the db.
	 *
	 * @param frame the frame
	 * @return true, if successful
	 * @throws BCIOException on any error, use to implement custom error handling.
	 */
	public boolean delete(CacheTimeFrame frame) throws BCIOException;

	/**
	 * Delete an entity from the db.
	 *
	 * @param sub the subscription to delete
	 * @return true, if successful
	 * @throws BCIOException on any error, use to implement custom error handling.
	 */
	public boolean delete(BCSubscribtion sub) throws BCIOException;

	/**
	 * delete all subscriptions.
	 *
	 * @return true, if successful
	 * @throws BCIOException on any error, use to implement custom error handling.
	 */
	public boolean dropSubscriptions() throws BCIOException;

	/**
	 * Reset the db.
	 *
	 * @return true, if successful
	 * @throws BCIOException on any error, use to implement custom error handling.
	 */
	public boolean resetDB() throws BCIOException;
	
	
	
	
}
