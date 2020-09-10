package com.choicemmed.ichoice.healthcheck.db;

import android.content.Context;

import com.choicemmed.ichoice.framework.base.BaseDb;

import java.util.List;

import pro.choicemmed.datalib.W314B4Data;
import pro.choicemmed.datalib.W314B4DataDao;
import pro.choicemmed.datalib.W628Data;
import pro.choicemmed.datalib.W628DataDao;

/**
 * @anthor by jiangnan
 * @Date on 2020/3/3.
 */
public class W628Operation extends BaseDb {
    private Context context;

    public W628Operation(Context context) {
        this.context = context;
        dao = getDaoSession(context).getW628DataDao();
    }

    public long insertW628(W628Data w628Data){
        return dao.insert(w628Data);
    }

    public List<W628Data> queryByUser(String UserId){
        return dao.queryBuilder().where(W628DataDao.Properties.UserId.eq(UserId)).orderAsc(W628DataDao.Properties.StartDate).build().list();
    }

    public List<W628Data> queryByUserDate(String userId, String DateTime){
        return dao.queryBuilder().where(W628DataDao.Properties.UserId.eq(userId),
                W628DataDao.Properties.StartDate.like(DateTime + "%"))
                .orderDesc(W628DataDao.Properties.StartDate)
                .build()
                .list();
    }


    public W628Data queryByUserUuid(String userId, String uuid){
        List<W628Data> w628DataList = dao.queryBuilder().where(W628DataDao.Properties.UserId.eq(userId),
                W628DataDao.Properties.Uuid.like(uuid + "%"))
                .orderDesc(W628DataDao.Properties.StartDate)
                .build()
                .list();
        if (w628DataList.isEmpty()){
            return null;
        }
        return w628DataList.get(0);
    }

    public W628Data queryByNow(String userId) {
        List<W628Data> w628DataList = dao.queryBuilder().where(W628DataDao.Properties.UserId.eq(userId))
                .orderDesc(W628DataDao.Properties.StartDate)
                .build()
                .list();
        if (w628DataList.isEmpty()){
            return null;
        }
        return w628DataList.get(0);
    }

    public List<W628Data> queryUpLoadFalse(){
        List<W628Data> w628DataList = dao.queryBuilder()
                .where(W628DataDao.Properties.UpLoadFlag.eq("false"))
                .orderDesc(W628DataDao.Properties.StartDate)
                .build()
                .list();
        return w628DataList;
    }

    public void setUpLoadTrue(String uuid){
        List<W628Data> w628DataList = dao.queryBuilder().where(W628DataDao.Properties.Uuid.eq(uuid)).build().list();
        if (w628DataList.isEmpty()){
            return;
        }
        W628Data w628Data = w628DataList.get(0);
        w628Data.setUpLoadFlag("true");
        dao.update(w628Data);
    }
}
