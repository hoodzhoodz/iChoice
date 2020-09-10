package com.choicemmed.ichoice.healthcheck.db;

import android.content.Context;
import com.choicemmed.ichoice.framework.base.BaseDb;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;
import pro.choicemmed.datalib.DeviceInfo;
import pro.choicemmed.datalib.DeviceInfoDao;

/**
 * @author Created by Jiang nan on 2019/11/26 10:18.
 * @description
 **/
public class DeviceOperation extends BaseDb {
    private static final String TAG = DeviceOperation.class.getSimpleName();
    private Context context;

    public DeviceOperation(Context context) {
        dao = getDaoSession(context).getDeviceInfoDao();
        this.context = context;
    }

    public List<DeviceInfo> queryByDeviceType(String userId, int deviceType) {
        return dao.queryBuilder().where(DeviceInfoDao.Properties.UserId.eq(userId), DeviceInfoDao.Properties.DeviceType.eq(deviceType)).build().list();
    }

    public DeviceInfo queryByDeviceTypeName(String userId, int deviceType, String TypeName){
        List<DeviceInfo> deviceInfos  = dao.queryBuilder().where(DeviceInfoDao.Properties.UserId.eq(userId),DeviceInfoDao.Properties.DeviceType.eq(deviceType), DeviceInfoDao.Properties.TypeName.eq(TypeName)).build().list();
        if (deviceInfos.size() > 0){
            return deviceInfos.get(0);
        }else{
            return null;
        }
    }

    public String queryByDeviceName(String userId, int deviceType){
        List<DeviceInfo> deviceInfos = dao.queryBuilder().where(DeviceInfoDao.Properties.UserId.eq(userId), DeviceInfoDao.Properties.DeviceType.eq(deviceType))
                .build().list();
        if (deviceInfos.size() > 0){
            return deviceInfos.get(0).getTypeName();
        }else{
            return null;
        }
    }

    public void intertDv(DeviceInfo device) {
        dao.insert(device);
    }

    public void upDateDv(DeviceInfo de) {
        dao.update(de);
    }

    public void deleteDv(DeviceInfo dv) {
        dao.delete(dv);
    }

    public List<DeviceInfo> queryAllDv(String userId) {
        return dao.queryRaw("where USER_ID = ?", new String[]{userId});
//        return dao.queryBuilder().build().list();
    }

    public List<DeviceInfo> queryByUserId(String userId) {
        return dao.queryBuilder().where(DeviceInfoDao.Properties.UserId.eq(userId)).build().list();
    }

    public List<DeviceInfo> queryByUserIdType(String userId, int deviceType ) {
        return dao.queryBuilder().where(DeviceInfoDao.Properties.UserId.eq(userId), DeviceInfoDao.Properties.DeviceType.eq(deviceType)).build().list();
    }


    public boolean queryByMac(String bluethoothId) {
        QueryBuilder queryBuilder = dao.queryBuilder();
        queryBuilder.where(DeviceInfoDao.Properties.BluetoothId.eq(bluethoothId));
        List<DeviceInfo> list = queryBuilder.list();
        if (list.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean queryByTypeName(String typeName){
        QueryBuilder queryBuilder = dao.queryBuilder();
        queryBuilder.where(DeviceInfoDao.Properties.TypeName.eq(typeName));
        List<DeviceInfo> list = queryBuilder.list();
        if (list.size() != 0){
            return true;
        }else {
            return false;
        }
    }
}
