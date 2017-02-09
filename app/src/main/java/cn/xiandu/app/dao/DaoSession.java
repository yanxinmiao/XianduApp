package cn.xiandu.app.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import cn.xiandu.app.bean.ChannelData;
import cn.xiandu.app.bean.HomeBean;
import cn.xiandu.app.bean.HomeData;

import cn.xiandu.app.dao.ChannelDataDao;
import cn.xiandu.app.dao.HomeBeanDao;
import cn.xiandu.app.dao.HomeDataDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig channelDataDaoConfig;
    private final DaoConfig homeBeanDaoConfig;
    private final DaoConfig homeDataDaoConfig;

    private final ChannelDataDao channelDataDao;
    private final HomeBeanDao homeBeanDao;
    private final HomeDataDao homeDataDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        channelDataDaoConfig = daoConfigMap.get(ChannelDataDao.class).clone();
        channelDataDaoConfig.initIdentityScope(type);

        homeBeanDaoConfig = daoConfigMap.get(HomeBeanDao.class).clone();
        homeBeanDaoConfig.initIdentityScope(type);

        homeDataDaoConfig = daoConfigMap.get(HomeDataDao.class).clone();
        homeDataDaoConfig.initIdentityScope(type);

        channelDataDao = new ChannelDataDao(channelDataDaoConfig, this);
        homeBeanDao = new HomeBeanDao(homeBeanDaoConfig, this);
        homeDataDao = new HomeDataDao(homeDataDaoConfig, this);

        registerDao(ChannelData.class, channelDataDao);
        registerDao(HomeBean.class, homeBeanDao);
        registerDao(HomeData.class, homeDataDao);
    }
    
    public void clear() {
        channelDataDaoConfig.clearIdentityScope();
        homeBeanDaoConfig.clearIdentityScope();
        homeDataDaoConfig.clearIdentityScope();
    }

    public ChannelDataDao getChannelDataDao() {
        return channelDataDao;
    }

    public HomeBeanDao getHomeBeanDao() {
        return homeBeanDao;
    }

    public HomeDataDao getHomeDataDao() {
        return homeDataDao;
    }

}
