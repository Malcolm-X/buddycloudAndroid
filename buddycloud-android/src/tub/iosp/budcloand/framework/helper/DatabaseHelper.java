package tub.iosp.budcloand.framework.helper;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import de.greenrobot.dao.LazyList;

import tub.iosp.budcloand.framework.database.BCDatabase;
import tub.iosp.budcloand.framework.database.CacheTimeFrame;
import tub.iosp.budcloand.framework.database.DefaultDatabase;
import tub.iosp.budcloand.framework.database.channelPostsScope;
import tub.iosp.budcloand.framework.exceptions.BCIOException;
import tub.iosp.budcloand.framework.types.BCItem;
import tub.iosp.budcloand.framework.types.BCMetaData;
import tub.iosp.budcloand.framework.types.BCSubscribtion;

public enum DatabaseHelper {
	INSTANCE;
	private BCDatabase db;
	private DatabaseHelper(){
	}
	
	public void init(Context context, CursorFactory fac){
		this.db = new DefaultDatabase(context, fac);
	}
	
	public List<BCItem> getPosts(String name) {
		List<BCItem> result = null;
		try {
			result = db.getPosts(name);
		} catch (BCIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public channelPostsScope getCachedChannel(String channel) {
		channelPostsScope ch = null;
		try {
			ch = db.getChannel(channel);
		} catch (BCIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		return ch;
		
	}

	public List<BCItem> getPosts(String channel, Date last) {
		List<BCItem> result = null;
		try {
			result = db.getPosts(channel, last);
		} catch (BCIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

//	public void storePosts(List<BCItem> posts_tmp) {
//		for
//		
//	}

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




	public void store(BCItem item) {
		try {
			db.store(item);
		} catch (BCIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void store(CacheTimeFrame frame) {
		try {
			db.store(frame);
		} catch (BCIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void delete(CacheTimeFrame frame) {
		try {
			db.delete(frame);
		} catch (BCIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void store(channelPostsScope channel) {
		try {
			db.store(channel);
		} catch (BCIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public List<BCItem> getComments(BCItem parent) {
		List<BCItem> result = null;
		try {
			result = db.getComments(parent.getChannel(),parent.getRemoteId());
		} catch (BCIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public List<BCSubscribtion> getSubscribtions(String user) {
		// TODO Auto-generated method stub
		return null;
	}

	public BCMetaData getMetadata(String channelName) {
		// TODO Auto-generated method stub
		return null;
	}

	public void store(BCMetaData newMeta) {
		// TODO Auto-generated method stub
		
	}

	public void delete(BCSubscribtion sub) {
		// TODO Auto-generated method stub
		
	}

	public void store(BCSubscribtion sub) {
		// TODO Auto-generated method stub
		
	}

}
