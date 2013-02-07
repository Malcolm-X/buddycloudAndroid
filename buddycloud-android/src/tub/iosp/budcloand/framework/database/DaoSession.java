package tub.iosp.budcloand.framework.database;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.IdentityScopeType;

import tub.iosp.budcloand.framework.database.CacheTimeFrame;
import tub.iosp.budcloand.framework.database.channelPostsScope;
import tub.iosp.budcloand.framework.types.BCSubscribtion;
import tub.iosp.budcloand.framework.types.BCItem;
import tub.iosp.budcloand.framework.types.BCMetaData;

import tub.iosp.budcloand.framework.database.CacheTimeFrameDao;
import tub.iosp.budcloand.framework.database.channelPostsScopeDao;
import tub.iosp.budcloand.framework.database.BCSubscribtionDao;
import tub.iosp.budcloand.framework.database.BCItemDao;
import tub.iosp.budcloand.framework.database.BCMetaDataDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig cacheTimeFrameDaoConfig;
    private final DaoConfig channelPostsScopeDaoConfig;
    private final DaoConfig bCSubscribtionDaoConfig;
    private final DaoConfig bCItemDaoConfig;
    private final DaoConfig bCMetaDataDaoConfig;

    private final CacheTimeFrameDao cacheTimeFrameDao;
    private final channelPostsScopeDao channelPostsScopeDao;
    private final BCSubscribtionDao bCSubscribtionDao;
    private final BCItemDao bCItemDao;
    private final BCMetaDataDao bCMetaDataDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        cacheTimeFrameDaoConfig = daoConfigMap.get(CacheTimeFrameDao.class).clone();
        cacheTimeFrameDaoConfig.initIdentityScope(type);

        channelPostsScopeDaoConfig = daoConfigMap.get(channelPostsScopeDao.class).clone();
        channelPostsScopeDaoConfig.initIdentityScope(type);

        bCSubscribtionDaoConfig = daoConfigMap.get(BCSubscribtionDao.class).clone();
        bCSubscribtionDaoConfig.initIdentityScope(type);

        bCItemDaoConfig = daoConfigMap.get(BCItemDao.class).clone();
        bCItemDaoConfig.initIdentityScope(type);

        bCMetaDataDaoConfig = daoConfigMap.get(BCMetaDataDao.class).clone();
        bCMetaDataDaoConfig.initIdentityScope(type);

        cacheTimeFrameDao = new CacheTimeFrameDao(cacheTimeFrameDaoConfig, this);
        channelPostsScopeDao = new channelPostsScopeDao(channelPostsScopeDaoConfig, this);
        bCSubscribtionDao = new BCSubscribtionDao(bCSubscribtionDaoConfig, this);
        bCItemDao = new BCItemDao(bCItemDaoConfig, this);
        bCMetaDataDao = new BCMetaDataDao(bCMetaDataDaoConfig, this);

        registerDao(CacheTimeFrame.class, cacheTimeFrameDao);
        registerDao(channelPostsScope.class, channelPostsScopeDao);
        registerDao(BCSubscribtion.class, bCSubscribtionDao);
        registerDao(BCItem.class, bCItemDao);
        registerDao(BCMetaData.class, bCMetaDataDao);
    }
    
    public void clear() {
        cacheTimeFrameDaoConfig.getIdentityScope().clear();
        channelPostsScopeDaoConfig.getIdentityScope().clear();
        bCSubscribtionDaoConfig.getIdentityScope().clear();
        bCItemDaoConfig.getIdentityScope().clear();
        bCMetaDataDaoConfig.getIdentityScope().clear();
    }

    public CacheTimeFrameDao getCacheTimeFrameDao() {
        return cacheTimeFrameDao;
    }

    public channelPostsScopeDao getChannelPostsScopeDao() {
        return channelPostsScopeDao;
    }

    public BCSubscribtionDao getBCSubscribtionDao() {
        return bCSubscribtionDao;
    }

    public BCItemDao getBCItemDao() {
        return bCItemDao;
    }

    public BCMetaDataDao getBCMetaDataDao() {
        return bCMetaDataDao;
    }

}

