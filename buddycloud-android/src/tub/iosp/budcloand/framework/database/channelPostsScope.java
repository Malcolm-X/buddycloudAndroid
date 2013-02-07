package tub.iosp.budcloand.framework.database;

import tub.iosp.budcloand.framework.database.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table CACHED_CHANNELS.
 */
public class channelPostsScope {

    private Long id;
    private String name;
    private Long timeFrameId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient channelPostsScopeDao myDao;

    private CacheTimeFrame cacheTimeFrame;
    private Long cacheTimeFrame__resolvedKey;


    public channelPostsScope() {
    }

    public channelPostsScope(Long id) {
        this.id = id;
    }

    public channelPostsScope(Long id, String name, Long timeFrameId) {
        this.id = id;
        this.name = name;
        this.timeFrameId = timeFrameId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getChannelPostsScopeDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTimeFrameId() {
        return timeFrameId;
    }

    public void setTimeFrameId(Long timeFrameId) {
        this.timeFrameId = timeFrameId;
    }

    /** To-one relationship, resolved on first access. */
    public CacheTimeFrame getCacheTimeFrame() {
        if (cacheTimeFrame__resolvedKey == null || !cacheTimeFrame__resolvedKey.equals(timeFrameId)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CacheTimeFrameDao targetDao = daoSession.getCacheTimeFrameDao();
            cacheTimeFrame = targetDao.load(timeFrameId);
            cacheTimeFrame__resolvedKey = timeFrameId;
        }
        return cacheTimeFrame;
    }

    public void setCacheTimeFrame(CacheTimeFrame cacheTimeFrame) {
        this.cacheTimeFrame = cacheTimeFrame;
        timeFrameId = cacheTimeFrame == null ? null : cacheTimeFrame.getId();
        cacheTimeFrame__resolvedKey = timeFrameId;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
