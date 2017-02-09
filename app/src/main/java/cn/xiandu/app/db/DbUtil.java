/*
******************************* Copyright (c)*********************************\
**
**                 (c) Copyright 2015, 蒋朋, china, qd. sd
**                          All Rights Reserved
**
**                           By()
**                         
**-----------------------------------版本信息------------------------------------
** 版    本: V0.1
**
**------------------------------------------------------------------------------
********************************End of Head************************************\
*/

package cn.xiandu.app.db;

import cn.xiandu.app.dao.HomeBeanDao;
import cn.xiandu.app.dao.HomeDataDao;

/**
 * 一个工具类 DbUtils 获得 Helper
 *
 */
public class DbUtil {

    private static HomeDataHelper homeDataHelper;
    private static HomeBeanHelper homeBeanHelper;
    private static HomeDataDao getDriverDao() {
        return DbCore.getDaoSession().getHomeDataDao();
    }


    private static HomeBeanDao getBeanDao() {
        return DbCore.getDaoSession().getHomeBeanDao();
    }

    public static HomeDataHelper getHomeDataHelperHelper() {
        if (homeDataHelper == null) {
            homeDataHelper = new HomeDataHelper(getDriverDao());
        }
        return homeDataHelper;
    }


    public static HomeBeanHelper getHomeBeanHelperHelper() {
        if (homeBeanHelper == null) {
            homeBeanHelper = new HomeBeanHelper(getBeanDao());
        }
        return homeBeanHelper;
    }
}
