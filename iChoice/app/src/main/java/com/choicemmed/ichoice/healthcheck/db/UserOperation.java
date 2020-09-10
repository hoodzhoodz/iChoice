package com.choicemmed.ichoice.healthcheck.db;

import android.content.Context;

import com.choicemmed.ichoice.framework.base.BaseDb;

import java.util.List;

import pro.choicemmed.datalib.UserProfileInfo;
import pro.choicemmed.datalib.UserProfileInfoDao;

/**
 * @author Created by Jiang nan on 2019/11/21 10:48.
 * @description
 **/
public class UserOperation extends BaseDb {

    public UserOperation(Context context){
        dao = getDaoSession(context).getUserProfileInfoDao();
    }

    public UserProfileInfo queryByUserId(String userid){
        List<UserProfileInfo> userProfileInfos = dao.queryBuilder().where(UserProfileInfoDao.Properties.UserId.eq(userid)).build().list();
        if (userProfileInfos == null || userProfileInfos.isEmpty()){
            return null;
        }
        return userProfileInfos.get(0);
    }

    public  void upDateUser(UserProfileInfo userProfileInfo){
        try {
            dao.update(userProfileInfo);
        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
