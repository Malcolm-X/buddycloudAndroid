package tub.iosp.budcloand.framework.database;

import java.io.Closeable;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import de.greenrobot.dao.Query;
import de.greenrobot.dao.QueryBuilder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import tub.iosp.budcloand.framework.database.BCItemDao.Properties;
import tub.iosp.budcloand.framework.exceptions.BCIOException;
import tub.iosp.budcloand.framework.types.BCItem;
import tub.iosp.budcloand.framework.types.BCMetaData;
import tub.iosp.budcloand.framework.types.BCSubscribtion;

public class DefaultDatabase implements BCDatabase{
	
	private DaoMaster master;
	private SQLiteOpenHelper helper;
	private SQLiteDatabase db;
	private DaoSession session;
	private BCItemDao daoItem;
	private BCMetaDataDao daoMeta;
	private CacheTimeFrameDao daoFrame;
	private BCSubscribtionDao daoSubscribtion;
	private channelPostsScopeDao daoChannel;
	private Query<BCSubscribtion> query_subscribtion_channel;
	private Query<BCItem> query_item_channel;
	private Query<BCItem> query_item_channel_post;
	private Query<BCItem> query_item_channel_post_until;
	private Query<BCItem> query_item_channel_comment;
	private Query<BCItem> query_item_channel_comment_id;
	private Query<BCMetaData> query_metadata_channel;
	private Query<channelPostsScope> query_channels;
	
	public DefaultDatabase(Context context, CursorFactory factory){
		this.helper = new DaoMaster.DevOpenHelper(context, "tub_iosp_budcloand_framework_database.db", factory);
		this.db = helper.getWritableDatabase();
		this.master = new DaoMaster(db);
		this.session = master.newSession();
		this.initQueries(session);
	}
	
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
				.where(tub.iosp.budcloand.framework.database.channelPostsScopeDao.Properties.Name.eq(""))
				.build();
		this.query_subscribtion_channel = qbSubscribtion
				.where(tub.iosp.budcloand.framework.database.BCSubscribtionDao.Properties.ChannelAddress.eq(""))
				.build();
		this.query_metadata_channel = qbMeta
				.where(tub.iosp.budcloand.framework.database.BCMetaDataDao.Properties.Channel.eq(""))
				.build();
		this.query_item_channel = qbItem
				.where(tub.iosp.budcloand.framework.database.BCItemDao.Properties.Channel.eq(""))
				.orderDesc(tub.iosp.budcloand.framework.database.BCItemDao.Properties.Updated)
				.build();
		qbItem = daoItem.queryBuilder();
		this.query_item_channel_post = qbItem
				.where(tub.iosp.budcloand.framework.database.BCItemDao.Properties.Channel.eq(""),tub.iosp.budcloand.framework.database.BCItemDao.Properties.ItemType.eq("post"))
				.orderDesc(tub.iosp.budcloand.framework.database.BCItemDao.Properties.Updated)
				.build();
		qbItem = daoItem.queryBuilder();
		this.query_item_channel_comment = qbItem
				.where(tub.iosp.budcloand.framework.database.BCItemDao.Properties.Channel.eq(""),tub.iosp.budcloand.framework.database.BCItemDao.Properties.ItemType.eq("comment"))
				.orderAsc(tub.iosp.budcloand.framework.database.BCItemDao.Properties.Updated)
				.build();
		qbItem = daoItem.queryBuilder();
		this.query_item_channel_comment_id = qbItem
				.where(tub.iosp.budcloand.framework.database.BCItemDao.Properties.Channel.eq(""), tub.iosp.budcloand.framework.database.BCItemDao.Properties.ReplyTo.eq(""), tub.iosp.budcloand.framework.database.BCItemDao.Properties.ItemType.eq("comment"))
				.orderAsc(tub.iosp.budcloand.framework.database.BCItemDao.Properties.Updated)
				.build();
		qbItem = daoItem.queryBuilder();
		this.query_item_channel_post_until = qbItem
				.where(tub.iosp.budcloand.framework.database.BCItemDao.Properties.Channel.eq(""), tub.iosp.budcloand.framework.database.BCItemDao.Properties.Updated.ge((long)0),tub.iosp.budcloand.framework.database.BCItemDao.Properties.ItemType.eq("post"),qbItem.or(tub.iosp.budcloand.framework.database.BCItemDao.Properties.ReplyTo.isNotNull(),tub.iosp.budcloand.framework.database.BCItemDao.Properties.ReplyTo.notEq("")))
				.orderAsc(tub.iosp.budcloand.framework.database.BCItemDao.Properties.Updated)
				.build();
		qbItem = daoItem.queryBuilder();
	
	}
	@Override
	public void startNewSession() throws BCIOException{
		this.session = master.newSession();
		this.initQueries(session);
	}
//	public void startSession(){
//		if(this.session != null) return;
//		this.session = master.newSession();
//		this.ready = true;
//	}
	
//	public void closeSession(){
//		this.ready = false;
//		this.session.;
//	}
	@Override
	public channelPostsScope getChannel(String name) throws BCIOException{
		this.query_channels.setParameter(0, name);
		return query_channels.unique();
	}
	@Override
	public List<BCSubscribtion> getSubscribed(String channelName) throws BCIOException {
		//TODO: create real Exception
		this.query_subscribtion_channel.setParameter(0, channelName);
		return query_subscribtion_channel.listLazy();
	}

	

//	@Override
//	public Cursor getSubscribedCursor(String channelName, Object session) throws BCIOException{
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public BCMetaData getMetadata(String channelName) throws BCIOException{
		this.query_metadata_channel.setParameter(0, channelName);
		return query_metadata_channel.unique();
	}

	@Override
	public List<BCItem> getPosts(String channelName) throws BCIOException{
		this.query_item_channel_post.setParameter(0, channelName);
		return this.query_item_channel_post.listLazy();
	}

//	@Override
//	public Cursor getPostsCursor(String channelName, Object session) throws BCIOException{
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public BCPostList getPostsBefore(String channelName, String date, Object session) throws BCIOException{
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public Cursor getPostsBeforeCursor(String channelName, String date, Object session) throws BCIOException{
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public BCPostList getPostsAfter(String channelName, String date, Object session) throws BCIOException{
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public Cursor getPostsAfterCursor(String channelName, String date, Object session) throws BCIOException{
//		// TODO Auto-generated method stub
//		return null;
//	}

	

	@Override
	public List<BCItem> getComments(String channel, String postID) throws BCIOException{
		this.query_item_channel_comment_id.setParameter(0, channel);
		
		this.query_item_channel_comment_id.setParameter(1, postID);
		return query_item_channel_comment_id.listLazy();
	}

//	@Override
//	public BCPost getComments(BCPost post) throws BCIOException{
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public void close() throws BCIOException{
		// TODO Auto-generated method stub
		
	}


	
	@Override
	public boolean store(BCSubscribtion toStore) throws BCIOException{
		toStore.setCached(new Date(System.currentTimeMillis()));
		daoSubscribtion.insertOrReplace(toStore);
		return true;
	}

	@Override
	public boolean store(BCMetaData toStore) throws BCIOException{
		toStore.setCached(new Date(System.currentTimeMillis()));
		this.daoMeta.insertOrReplace(toStore);
		return true;
	}

	@Override
	public boolean store(BCItem toStore) throws BCIOException{
		toStore.setCached(new Date(System.currentTimeMillis()));
		this.daoItem.insertOrReplace(toStore);
		return true;
	}

	@Override
	public boolean storeItemList(List<BCItem> list) throws BCIOException{
		for(BCItem toStore : list){
			this.store(toStore);
		}
		return true;
	}

	@Override
	public boolean storeMetaDataList(List<BCMetaData> list) throws BCIOException{
		for(BCMetaData toStore : list){
			this.store(toStore);
		}
		return true;
	}

	@Override
	public boolean storeSubscribtionList(List<BCSubscribtion> list) throws BCIOException{
		for(BCSubscribtion toStore : list){
			this.store(toStore);
		}
		return true;
	}

	@Override
	public List<BCItem> getPosts(String channel, Date last) throws BCIOException {
		this.query_item_channel_post.setParameter(0, channel);
		this.query_item_channel_post_until.setParameter(1, last);
		return this.query_item_channel_post.listLazy();
	}




	@Override
	public boolean store(channelPostsScope toStore) throws BCIOException {
		this.daoChannel.insertOrReplace(toStore);
		return true;
	}

	@Override
	public boolean store(CacheTimeFrame frame) throws BCIOException {
		this.daoFrame.insertOrReplace(frame);
		return true;
		
	}

	@Override
	public boolean delete(CacheTimeFrame frame) throws BCIOException {
		daoFrame.delete(frame);
		return true;
		
	}





}
