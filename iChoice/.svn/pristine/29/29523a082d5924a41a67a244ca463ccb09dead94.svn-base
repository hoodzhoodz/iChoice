package com.choicemmed.ichoice.healthcheck.db;

import android.content.Context;

import com.choicemmed.ichoice.framework.base.BaseDb;

import java.util.List;

import pro.choicemmed.datalib.DeviceDisplay;
import pro.choicemmed.datalib.DeviceDisplayDao;

/**
 * @author Created by Jiang nan on 2019/11/26 11:33.
 * @description
 **/
public class DeviceDisplayOperation extends BaseDb {
    private static final String TAG = DeviceOperation.class.getSimpleName();
    private Context context;

    public DeviceDisplayOperation(Context context) {
        dao = getDaoSession(context).getDeviceDisplayDao();
        this.context = context;
    }

    public List<DeviceDisplay> queryByUserIds(String userId) {
        List<DeviceDisplay> list = dao.queryBuilder().where(DeviceDisplayDao.Properties.UserId.eq(userId)).build().list();
        return list;
    }

    public DeviceDisplay queryByUserId(String userId) {
        List<DeviceDisplay> list = dao.queryBuilder().where(DeviceDisplayDao.Properties.UserId.eq(userId)).build().list();
        if (list.size() > 0){
            return list.get(0);
        }else {
            return null;
        }
    }

    public void updateDeviceDisplay(DeviceDisplay deviceDisplay){
        dao.update(deviceDisplay);
    }



}