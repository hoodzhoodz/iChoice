package com.choicemmed.ichoice.healthcheck.db;

import android.content.Context;
import android.util.Log;

import com.choicemmed.ichoice.framework.base.BaseDb;

import java.util.List;

import pro.choicemmed.datalib.W314B4Data;
import pro.choicemmed.datalib.W314B4DataDao;

/**
 * @author Created by Jiang nan on 2020/1/17 11:10.
 * @description
 **/
public class W314B4Operation extends BaseDb {
    public static final String TAG = "W314B4Operation";
    private Context context;

    public W314B4Operation(Context context) {
        dao = getDaoSession(context).getW314B4DataDao();
        this.context = context;
    }

    public long insertW314B4(W314B4Data w314B4Data){
        return dao.insert(w314B4Data);
    }

    public List<W314B4Data> queryByUser(String userId){
        return dao.queryBuilder().where(W314B4DataDao.Properties.UserId.eq(userId)).orderAsc(W314B4DataDao.Properties.StartDate).build().list();
    }

    public W314B4Data queryByNowDate(String userId, String DateTime){
        List<W314B4Data> w314B4DataList = dao.queryBuilder().where(W314B4DataDao.Properties.UserId.eq(userId),
                W314B4DataDao.Properties.StartDate.like(DateTime + "%"))
                .orderDesc(W314B4DataDao.Properties.StartDate)
                .build()
                .list();
        if (w314B4DataList.isEmpty()){
            return null;
        }
        return w314B4DataList.get(0);
    }

    public W314B4Data queryByNow(String userId){
        List<W314B4Data> w314B4DataList = dao.queryBuilder().where(W314B4DataDao.Properties.UserId.eq(userId))
                .orderDesc(W314B4DataDao.Properties.StartDate)
                .build()
                .list();
        if (w314B4DataList.isEmpty()){
            return null;
        }
        return w314B4DataList.get(0);
    }


    public List<W314B4Data> queryByUserDate(String userId, String DateTime){
        return dao.queryBuilder().where(W314B4DataDao.Properties.UserId.eq(userId),
                W314B4DataDao.Properties.StartDate.like(DateTime + "%"))
                .orderDesc(W314B4DataDao.Properties.StartDate)
                .build()
                .list();
    }

    public W314B4Data queryByUserUuid(String userId, String uuid){
        List<W314B4Data> w314B4DataList = dao.queryBuilder().where(W314B4DataDao.Properties.UserId.eq(userId),
                W314B4DataDao.Properties.Uuid.like(uuid + "%"))
                .orderDesc(W314B4DataDao.Properties.StartDate)
                .build()
                .list();
        if (w314B4DataList.isEmpty()){
            return null;
        }
        return w314B4DataList.get(0);
    }

    public List<W314B4Data> queryUpLoadFalse(){
        List<W314B4Data> w314B4DataList = dao.queryBuilder()
                .where(W314B4DataDao.Properties.UpLoadFlag.eq("false"))
                .orderDesc(W314B4DataDao.Properties.StartDate)
                .build()
                .list();
        return w314B4DataList;
    }

    public void setUpLoadTrue(String uuid){
        List<W314B4Data> w314B4DataList = dao.queryBuilder().where(W314B4DataDao.Properties.Uuid.eq(uuid))
                .build().list();
        if (w314B4DataList.size() == 0){
            return;
        }
        W314B4Data w314B4Data = w314B4DataList.get(0);
        w314B4Data.setUpLoadFlag("true");
        dao.update(w314B4Data);
    }

}
