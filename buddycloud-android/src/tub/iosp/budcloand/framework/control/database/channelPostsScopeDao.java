package tub.iosp.budcloand.framework.control.database;

import java.util.ArrayList;
import java.util.List;

import tub.iosp.budcloand.framework.model.CacheTimeFrame;
import tub.iosp.budcloand.framework.model.channelPostsScope;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.SqlUtils;

// TODO: Auto-generated Javadoc
// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table CACHED_CHANNELS.
*/
public class channelPostsScopeDao extends AbstractDao<channelPostsScope, Long> {

    /** The Constant TABLENAME. */
    public static final String TABLENAME = "CACHED_CHANNELS";

    /**
     * Properties of entity channelPostsScope.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        
        /** The Constant Id. */
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        
        /** The Constant Name. */
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        
        /** The Constant TimeFrameId. */
        public final static Property TimeFrameId = new Property(2, Long.class, "timeFrameId", false, "TIME_FRAME_ID");
    };

    /** The dao session. */
    private DaoSession daoSession;


    /**
     * Instantiates a new channel posts scope dao.
     *
     * @param config the config
     */
    public channelPostsScopeDao(DaoConfig config) {
        super(config);
    }
    
    /**
     * Instantiates a new channel posts scope dao.
     *
     * @param config the config
     * @param daoSession the dao session
     */
    public channelPostsScopeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /**
     * Creates the underlying database table.
     *
     * @param db the db
     * @param ifNotExists the if not exists
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'CACHED_CHANNELS' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'NAME' TEXT," + // 1: name
                "'TIME_FRAME_ID' INTEGER);"); // 2: timeFrameId
    }

    /**
     * Drops the underlying database table.
     *
     * @param db the db
     * @param ifExists the if exists
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'CACHED_CHANNELS'";
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
    protected void bindValues(SQLiteStatement stmt, channelPostsScope entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        Long timeFrameId = entity.getTimeFrameId();
        if (timeFrameId != null) {
            stmt.bindLong(3, timeFrameId);
        }
    }

    /* (non-Javadoc)
     * @see de.greenrobot.dao.AbstractDao#attachEntity(java.lang.Object)
     */
    @Override
    protected void attachEntity(channelPostsScope entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
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
     * @return the channel posts scope
     * @inheritdoc
     */
    @Override
    public channelPostsScope readEntity(Cursor cursor, int offset) {
        channelPostsScope entity = new channelPostsScope( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2) // timeFrameId
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
    public void readEntity(Cursor cursor, channelPostsScope entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTimeFrameId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
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
    protected Long updateKeyAfterInsert(channelPostsScope entity, long rowId) {
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
    public Long getKey(channelPostsScope entity) {
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
    
    /** The select deep. */
    private String selectDeep;

    /**
     * Gets the select deep.
     *
     * @return the select deep
     */
    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getCacheTimeFrameDao().getAllColumns());
            builder.append(" FROM CACHED_CHANNELS T");
            builder.append(" LEFT JOIN CACHE_TIME_FRAME T0 ON T.'TIME_FRAME_ID'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    /**
     * Load current deep.
     *
     * @param cursor the cursor
     * @param lock the lock
     * @return the channel posts scope
     */
    protected channelPostsScope loadCurrentDeep(Cursor cursor, boolean lock) {
        channelPostsScope entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        CacheTimeFrame cacheTimeFrame = loadCurrentOther(daoSession.getCacheTimeFrameDao(), cursor, offset);
        entity.setCacheTimeFrame(cacheTimeFrame);

        return entity;    
    }

    /**
     * Load deep.
     *
     * @param key the key
     * @return the channel posts scope
     */
    public channelPostsScope loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /**
     * Reads all available rows from the given cursor and returns a list of new ImageTO objects.
     *
     * @param cursor the cursor
     * @return the list
     */
    public List<channelPostsScope> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<channelPostsScope> list = new ArrayList<channelPostsScope>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    /**
     * Load deep all and close cursor.
     *
     * @param cursor the cursor
     * @return the list
     */
    protected List<channelPostsScope> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /**
     * A raw-style query where you can pass any WHERE clause and arguments.
     *
     * @param where the where
     * @param selectionArg the selection arg
     * @return the list
     */
    public List<channelPostsScope> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
