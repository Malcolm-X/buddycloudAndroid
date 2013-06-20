package tub.iosp.budcloand.framework.control.database;

import java.util.Date;
import java.util.List;

import tub.iosp.budcloand.framework.exceptions.BCIOException;
import tub.iosp.budcloand.framework.model.BCItem;
import tub.iosp.budcloand.framework.model.BCMetaData;
import tub.iosp.budcloand.framework.model.BCSubscribtion;
import tub.iosp.budcloand.framework.model.CacheTimeFrame;
import tub.iosp.budcloand.framework.model.channelPostsScope;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * Singleton implementation of the Database Controler.
 */
public enum DatabaseHelper {
	
	/** The instance. */
	INSTANCE;
	
	/** the tag for logging*/
	private final static String TAG = "DatabaseHelper";
	
	/** The db. */
	private BCDatabase db;
	
	/**
	 * Instantiates a new database helper.
	 */
	private DatabaseHelper(){
	}
	
	/**
	 * Inits the db.
	 *
	 * @param context the context
	 * @param fac a cursorfactory, can be null
	 */
	public void init(Context context, CursorFactory fac){
		this.db = new DefaultDatabase(context, fac);
	}
	
	/**
	 * Gets the posts for the given channel jid
	 *
	 * @param name the channels jid
	 * @return the posts list
	 */
	public List<BCItem> getPosts(String name) {
		List<BCItem> result = null;
		try {
			result = db.getPosts(name);
		} catch (BCIOException e) {
			Log.e(TAG,"error accured:",e);
		}
		return result;
	}

	/**
	 * Gets a cached channel's timeframe list.
	 *
	 * @param channel the channel's jid
	 * @return the cached channel's timeframe list
	 */
	public channelPostsScope getCachedChannel(String channel) {
		channelPostsScope ch = null;
		try {
			ch = db.getChannel(channel);
		} catch (BCIOException e) {
			Log.e(TAG,"error accured:",e);
		}
		return ch;
		
	}

	/**
	 * Gets the posts before the given date for the given channel.
	 *
	 * @param channel the channel's jid
	 * @param last the date
	 * @return the posts lists
	 */
	public List<BCItem> getPosts(String channel, Date last) {
		List<BCItem> result = null;
		try {
			result = db.getPosts(channel, last);
		} catch (BCIOException e) {
			Log.e(TAG,"error accured:",e);
		}
		return result;
	}


	/**
	 * Creates the CacheTimeFrame and the timeframe lists for this channel
	 * and store everything. Call whenever you need to create a new channel.
	 *
	 * @param channelName the channel's jid
	 * @param first the date of the first most recent cached entry
	 * @param last the oldes cache entry
	 * @return the channel posts scope
	 */
	public channelPostsScope addChannel(String channelName, Date first,
			Date last) {
		channelPostsScope ch = new channelPostsScope();
		ch.setName(channelName);
		CacheTimeFrame frame = new CacheTimeFrame();
		frame.setFirst(first);
		frame.setLast(last);
		store(frame);
		ch.setTimeFrameId(frame.getId());
		ch.setCacheTimeFrame(frame);
		store(ch);
		return ch;
	}




	/**
	 * Store.
	 *
	 * @param item the item
	 * @return true, if successful
	 */
	public boolean store(BCItem item) {
		try {
			return db.store(item);
		} catch (BCIOException e) {
			Log.e(TAG,"error accured:",e);
			return false;
		}
	}

	/**
	 * Store.
	 *
	 * @param frame the frame
	 * @return true, if successful
	 */
	public boolean store(CacheTimeFrame frame) {
		try {
			return db.store(frame);
		} catch (BCIOException e) {
			Log.e(TAG,"error accured:",e);
			return false;
		}
	}

	/**
	 * Delete.
	 *
	 * @param frame the frame
	 * @return true, if successful
	 */
	public boolean delete(CacheTimeFrame frame) {
		try {
			return db.delete(frame);
		} catch (BCIOException e) {
			Log.e(TAG,"error accured:",e);
			return false;
		}
	}

	/**
	 * Store.
	 *
	 * @param channel the channel timeframe list
	 * @return true, if successful
	 */
	public boolean store(channelPostsScope channel) {
		try {
			return db.store(channel);
		} catch (BCIOException e) {
			Log.e(TAG,"error accured:",e);
			return false;
		}
	}


	/**
	 * Gets the comments for a post.
	 *
	 * @param parent the parent
	 * @return the comments
	 */
	public List<BCItem> getComments(BCItem parent) {
		List<BCItem> result = null;
		try {
			result = db.getComments(parent.getChannel(),parent.getRemoteId());
		} catch (BCIOException e) {
			Log.e(TAG,"error accured:",e);
		}
		return result;
	}

	/**
	 * Gets the subscribers for a user.
	 *
	 * @param user the user's jid
	 * @return the subscription list
	 */
	public List<BCSubscribtion> getSubscribed(String user) {
		try {
			return this.db.getSubscribed(user);
		} catch (BCIOException e) {
			Log.e(TAG,"error accured:",e);
			return null;
		}
		
	}

	/**
	 * Gets the metadata for a channel.
	 *
	 * @param channelName the channel's jid
	 * @return the metadata
	 */
	public BCMetaData getMetadata(String channelName)  {
		
		try {
			return this.db.getMetadata(channelName);
		} catch (BCIOException e) {
			Log.e(TAG,"error accured:",e);
			return null;
		}
		
	}

	/**
	 * Store.
	 *
	 * @param newMeta the new meta
	 * @return true, if successful
	 */
	public boolean store(BCMetaData newMeta)  {
		try {
			return this.db.store(newMeta);
		} catch (BCIOException e) {
			Log.e(TAG,"error accured:",e);
			return false;
		}
		
	}

	/**
	 * Delete.
	 *
	 * @param sub the sub
	 * @return true, if successful
	 */
	public boolean delete(BCSubscribtion sub) {
		try {
			return this.db.delete(sub);
		} catch (BCIOException e) {
			Log.e(TAG,"error accured:",e);
			return false;
		}
	}

	/**
	 * Store.
	 *
	 * @param sub the sub
	 * @return true, if successful
	 */
	public boolean store(BCSubscribtion sub) {
		try {
			return this.db.store(sub);
		} catch (BCIOException e) {
			Log.e(TAG,"error accured:",e);
			return false;
		}
		
	}

	/**
	 * Drop subscriptions.
	 *
	 * @return true, if successful
	 */
	public boolean dropSubscriptions() {
		try {
			return this.db.dropSubscriptions();
		} catch (BCIOException e) {
			Log.e(TAG,"error accured:",e);
			return false;
		}
	}

	/**
	 * Reset db.
	 *
	 * @return true, if successful
	 */
	public boolean resetDB() {
		try {
			db.resetDB();
			
			return true;
		} catch (BCIOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG,"error accured:",e);;
			return false;
		}
		
	}

}
