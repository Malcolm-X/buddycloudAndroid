package tub.iosp.budcloand.framework.control.database;

import tub.iosp.budcloand.framework.model.BCSubscribtion;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.Property;

// TODO: Auto-generated Javadoc
// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table BCSUBSCRIBTION.
*/
public class BCSubscribtionDao extends AbstractDao<BCSubscribtion, Long> {

    /** The Constant TABLENAME. */
    public static final String TABLENAME = "BCSUBSCRIBTION";

    /**
     * Properties of entity BCSubscribtion.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        
        /** The Constant Id. */
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        
        /** The Constant Subscriber. */
        public final static Property Subscriber = new Property(1, String.class, "subscriber", false, "SUBSCRIBER");
        
        /** The Constant ChannelAddress. */
        public final static Property ChannelAddress = new Property(2, String.class, "channelAddress", false, "CHANNEL_ADDRESS");
        
        /** The Constant MemberType. */
        public final static Property MemberType = new Property(3, String.class, "memberType", false, "MEMBER_TYPE");
        
        /** The Constant Cached. */
        public final static Property Cached = new Property(4, java.util.Date.class, "cached", false, "CACHED");
    };


    /**
     * Instantiates a new bC subscribtion dao.
     *
     * @param config the config
     */
    public BCSubscribtionDao(DaoConfig config) {
        super(config);
    }
    
    /**
     * Instantiates a new bC subscribtion dao.
     *
     * @param config the config
     * @param daoSession the dao session
     */
    public BCSubscribtionDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     *
     * @param db the db
     * @param ifNotExists the if not exists
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'BCSUBSCRIBTION' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'SUBSCRIBER' TEXT," + // 1: subscriber
                "'CHANNEL_ADDRESS' TEXT," + // 2: channelAddress
                "'MEMBER_TYPE' TEXT," + // 3: memberType
                "'CACHED' INTEGER);"); // 4: cached
    }

    /**
     * Drops the underlying database table.
     *
     * @param db the db
     * @param ifExists the if exists
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'BCSUBSCRIBTION'";
        db.execSQL(sql);
    }

    /**
     * Bind values.
     *
     * @param stmt the stmt
     * @param entity the entity
     * @inheritdoc
     */
    @Override
    protected void bindValues(SQLiteStatement stmt, BCSubscribtion entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String subscriber = entity.getSubscriber();
        if (subscriber != null) {
            stmt.bindString(2, subscriber);
        }
 
        String channelAddress = entity.getChannelAddress();
        if (channelAddress != null) {
            stmt.bindString(3, channelAddress);
        }
 
        String memberType = entity.getMemberType();
        if (memberType != null) {
            stmt.bindString(4, memberType);
        }
 
        java.util.Date cached = entity.getCached();
        if (cached != null) {
            stmt.bindLong(5, cached.getTime());
        }
    }

    /**
     * Read key.
     *
     * @param cursor the cursor
     * @param offset the offset
     * @return the long
     * @inheritdoc
     */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /**
     * Read entity.
     *
     * @param cursor the cursor
     * @param offset the offset
     * @return the bC subscribtion
     * @inheritdoc
     */
    @Override
    public BCSubscribtion readEntity(Cursor cursor, int offset) {
        BCSubscribtion entity = new BCSubscribtion( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // subscriber
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // channelAddress
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // memberType
            cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)) // cached
        );
        return entity;
    }
     
    /**
     * Read entity.
     *
     * @param cursor the cursor
     * @param entity the entity
     * @param offset the offset
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, BCSubscribtion entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSubscriber(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setChannelAddress(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMemberType(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCached(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
     }
    
    /**
     * Update key after insert.
     *
     * @param entity the entity
     * @param rowId the row id
     * @return the long
     * @inheritdoc
     */
    @Override
    protected Long updateKeyAfterInsert(BCSubscribtion entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /**
     * Gets the key.
     *
     * @param entity the entity
     * @return the key
     * @inheritdoc
     */
    @Override
    public Long getKey(BCSubscribtion entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /**
     * Checks if is entity updateable.
     *
     * @return true, if is entity updateable
     * @inheritdoc
     */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
