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
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import de.greenrobot.dao.Query;
import de.greenrobot.dao.QueryBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class DefaultDatabase.
 */
public class DefaultDatabase implements BCDatabase{
	
	/** The daomaster. */
	private DaoMaster master;
	
	/** The SQLiteOpenHelper. */
	private SQLiteOpenHelper helper;
	
	/** The db. */
	private SQLiteDatabase db;
	
	/** The session. */
	private DaoSession session;
	
	/** The dao item. */
	private BCItemDao daoItem;
	
	/** The dao meta. */
	private BCMetaDataDao daoMeta;
	
	/** The dao frame. */
	private CacheTimeFrameDao daoFrame;
	
	/** The dao subscribtion. */
	private BCSubscribtionDao daoSubscribtion;
	
	/** The dao channel. */
	private channelPostsScopeDao daoChannel;
	
	/** The query_subscribtion_channel. */
	private Query<BCSubscribtion> query_subscribtion_channel;
	
	/** The query_item_channel_post. */
	private Query<BCItem> query_item_channel_post;
	
	/** The query_item_channel_post_until. */
	private Query<BCItem> query_item_channel_post_until;
	
	/** The query_item_channel_comment_id. */
	private Query<BCItem> query_item_channel_comment_id;
	
	/** The query_metadata_channel. */
	private Query<BCMetaData> query_metadata_channel;
	
	/** The query_channels. */
	private Query<channelPostsScope> query_channels;
	
	/**
	 * Instantiates a new default database.
	 *
	 * @param context the context
	 * @param factory the factory
	 */
	public DefaultDatabase(Context context, CursorFactory factory){
		this.helper = new DaoMaster.DevOpenHelper(context, "tub_iosp_budcloand_framework_database.db", factory);
		this.db = helper.getWritableDatabase();
		this.master = new DaoMaster(db);
		this.session = master.newSession();
		this.initQueries(session);
	}
	
	/**
	 * Inits the queries.
	 *
	 * @param session the session
	 */
	private void initQueries(DaoSession session){
		
		this.daoSubscribtion = session.getBCSubscribtionDao();
		this.daoMeta = session.getBCMetaDataDao();
		this.daoItem = session.getBCItemDao();
		this.daoChannel = session.getChannelPostsScopeDao();
		this.daoFrame = session.getCacheTimeFrameDao();
		
		QueryBuilder<BCItem> qbItem = daoItem.queryBuilder();
		QueryBuilder<BCMetaData> qbMeta = daoMeta.queryBuilder();
		QueryBuilder<BCSubscribtion> qbSubscribtion = daoSubscribtion.queryBuilder();
		QueryBuilder<channelPostsScope> qbChannel = daoChannel.queryBuilder();
		
		this.query_channels = qbChannel
				.where(tub.iosp.budcloand.framework.control.database.channelPostsScopeDao.Properties.Name.eq(""))
				.build();
		this.query_subscribtion_channel = qbSubscribtion
				.where(tub.iosp.budcloand.framework.control.database.BCSubscribtionDao.Properties.Subscriber.eq(""))
				.build();
		this.query_metadata_channel = qbMeta
				.where(tub.iosp.budcloand.framework.control.database.BCMetaDataDao.Properties.Channel.eq(""))
				.build();

		qbItem = daoItem.queryBuilder();
		this.query_item_channel_post = qbItem
				.where(tub.iosp.budcloand.framework.control.database.BCItemDao.Properties.Channel.eq(""),tub.iosp.budcloand.framework.control.database.BCItemDao.Properties.ItemType.eq("post"))
				.orderDesc(tub.iosp.budcloand.framework.control.database.BCItemDao.Properties.Updated)
				.build();
		qbItem = daoItem.queryBuilder();
		this.query_item_channel_comment_id = qbItem
				.where(tub.iosp.budcloand.framework.control.database.BCItemDao.Properties.Channel.eq(""), tub.iosp.budcloand.framework.control.database.BCItemDao.Properties.ReplyTo.eq(""), tub.iosp.budcloand.framework.control.database.BCItemDao.Properties.ItemType.eq("comment"))
				.orderAsc(tub.iosp.budcloand.framework.control.database.BCItemDao.Properties.Updated)
				.build();
		qbItem = daoItem.queryBuilder();
		this.query_item_channel_post_until = qbItem
				.where(tub.iosp.budcloand.framework.control.database.BCItemDao.Properties.Channel.eq(""), tub.iosp.budcloand.framework.control.database.BCItemDao.Properties.Updated.ge((long)0),tub.iosp.budcloand.framework.control.database.BCItemDao.Properties.ItemType.eq("post"),qbItem.or(tub.iosp.budcloand.framework.control.database.BCItemDao.Properties.ReplyTo.isNotNull(),tub.iosp.budcloand.framework.control.database.BCItemDao.Properties.ReplyTo.notEq("")))
				.orderAsc(tub.iosp.budcloand.framework.control.database.BCItemDao.Properties.Updated)
				.build();
		qbItem = daoItem.queryBuilder();
	/*
	 *	not used, but might use later
	 *	this.query_item_channel = qbItem
	 *			.where(tub.iosp.budcloand.framework.control.database.BCItemDao.Properties.Channel.eq(""))
	 *			.orderDesc(tub.iosp.budcloand.framework.control.database.BCItemDao.Properties.Updated)
	 *			.build();
	 *	qbItem = daoItem.queryBuilder();
	 *	this.query_item_channel_comment = qbItem
	 *			.where(tub.iosp.budcloand.framework.control.database.BCItemDao.Properties.Channel.eq(""),tub.iosp.budcloand.framework.control.database.BCItemDao.Properties.ItemType.eq("comment"))
	 *			.orderAsc(tub.iosp.budcloand.framework.control.database.BCItemDao.Properties.Updated)
	 *			.build();
	 */
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.database.BCDatabase#startNewSession()
	 */
	@Override
	synchronized public void startNewSession() throws BCIOException{
		this.session = master.newSession();
		this.initQueries(session);
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.database.BCDatabase#getChannel(java.lang.String)
	 */
	@Override
	public channelPostsScope getChannel(String name) throws BCIOException{
		synchronized (this.query_channels) {
			this.query_channels.setParameter(0, name);
			return query_channels.unique();
		}
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.database.BCDatabase#getSubscribed(java.lang.String)
	 */
	@Override
	public List<BCSubscribtion> getSubscribed(String user) throws BCIOException {
		synchronized (this.query_subscribtion_channel) {
			this.query_subscribtion_channel.setParameter(0, user);
			return query_subscribtion_channel.listLazy();
		}
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.database.BCDatabase#getMetadata(java.lang.String)
	 */
	@Override
	public BCMetaData getMetadata(String channelName) throws BCIOException{
		synchronized (this.query_metadata_channel) {
			this.query_metadata_channel.setParameter(0, channelName);
			return query_metadata_channel.unique();
		}
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.database.BCDatabase#getPosts(java.lang.String)
	 */
	@Override
	public List<BCItem> getPosts(String channelName) throws BCIOException{
		synchronized (this.query_item_channel_post) {
			this.query_item_channel_post.setParameter(0, channelName);
			return this.query_item_channel_post.listLazy();
		}
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.database.BCDatabase#getComments(java.lang.String, java.lang.String)
	 */
	@Override
	public List<BCItem> getComments(String channel, String postID) throws BCIOException{
		synchronized (this.query_item_channel_comment_id) {
			this.query_item_channel_comment_id.setParameter(0, channel);
			this.query_item_channel_comment_id.setParameter(1, postID);
			return query_item_channel_comment_id.listLazy();
		}
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.database.BCDatabase#close()
	 */
	@Override
	public void close() throws BCIOException{
		// not used in this db, closes automaticly
	}
	
	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.database.BCDatabase#store(tub.iosp.budcloand.framework.model.BCSubscribtion)
	 */
	@Override
	public boolean store(BCSubscribtion toStore) throws BCIOException{
		toStore.setCached(new Date(System.currentTimeMillis()));
		daoSubscribtion.insertOrReplace(toStore);
		return true;
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.database.BCDatabase#store(tub.iosp.budcloand.framework.model.BCMetaData)
	 */
	@Override
	public boolean store(BCMetaData toStore) throws BCIOException{
		toStore.setCached(new Date(System.currentTimeMillis()));
		this.daoMeta.insertOrReplace(toStore);
		return true;
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.database.BCDatabase#store(tub.iosp.budcloand.framework.model.BCItem)
	 */
	@Override
	public boolean store(BCItem toStore) throws BCIOException{
		toStore.setCached(new Date(System.currentTimeMillis()));
		this.daoItem.insertOrReplace(toStore);
		return true;
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.database.BCDatabase#storeItemList(java.util.List)
	 */
	@Override
	public boolean storeItemList(List<BCItem> list) throws BCIOException{
		for(BCItem toStore : list){
			this.store(toStore);
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.database.BCDatabase#storeMetaDataList(java.util.List)
	 */
	@Override
	public boolean storeMetaDataList(List<BCMetaData> list) throws BCIOException{
		for(BCMetaData toStore : list){
			this.store(toStore);
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.database.BCDatabase#storeSubscribtionList(java.util.List)
	 */
	@Override
	public boolean storeSubscribtionList(List<BCSubscribtion> list) throws BCIOException{
		for(BCSubscribtion toStore : list){
			this.store(toStore);
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.database.BCDatabase#getPosts(java.lang.String, java.util.Date)
	 */
	@Override
	public List<BCItem> getPosts(String channel, Date last) throws BCIOException {
		synchronized(this.query_item_channel_post){
			this.query_item_channel_post.setParameter(0, channel);
			this.query_item_channel_post_until.setParameter(1, last);
			return this.query_item_channel_post.listLazy();
		}
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.database.BCDatabase#store(tub.iosp.budcloand.framework.model.channelPostsScope)
	 */
	@Override
	public boolean store(channelPostsScope toStore) throws BCIOException {
		this.daoChannel.insertOrReplace(toStore);
		return true;
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.database.BCDatabase#store(tub.iosp.budcloand.framework.model.CacheTimeFrame)
	 */
	@Override
	public boolean store(CacheTimeFrame frame) throws BCIOException {
		this.daoFrame.insertOrReplace(frame);
		return true;
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.database.BCDatabase#delete(tub.iosp.budcloand.framework.model.CacheTimeFrame)
	 */
	@Override
	public boolean delete(CacheTimeFrame frame) throws BCIOException {
		daoFrame.delete(frame);
		return true;
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.database.BCDatabase#delete(tub.iosp.budcloand.framework.model.BCSubscribtion)
	 */
	@Override
	public boolean delete(BCSubscribtion sub) throws BCIOException {
		daoSubscribtion.delete(sub);
		return true;
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.database.BCDatabase#dropSubscriptions()
	 */
	@Override
	public boolean dropSubscriptions() throws BCIOException {
		this.daoSubscribtion.deleteAll();
		return true;
	}

	/* (non-Javadoc)
	 * @see tub.iosp.budcloand.framework.control.database.BCDatabase#resetDB()
	 */
	@Override
	public boolean resetDB() throws BCIOException {
		this.daoChannel.deleteAll();
		this.daoItem.deleteAll();
		this.daoSubscribtion.deleteAll();
		this.daoFrame.deleteAll();
		this.daoMeta.deleteAll();
		return true;
	}
}
