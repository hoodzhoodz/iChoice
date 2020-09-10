package com.choicemmed.ichoice.framework.base;
import android.content.Context;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;
import pro.choicemmed.datalib.DaoSession;
import pro.choicemmed.datalib.DaoMaster;

@SuppressWarnings("unchecked")
public class BaseDb {
    private static final String TAG = "BaseDb";
    public static final String DB_NAME = "ichoice-db";
    protected volatile static DaoMaster daoMaster;
    protected volatile static DaoSession daoSession;

    protected AbstractDao dao;

    /**
     * 取得DaoMaster
     *
     * @param context 上下文对象
     * @return DaoMaster
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            synchronized (DaoMaster.class) {
                if (daoMaster == null) {
                    DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
                    daoMaster = new DaoMaster(helper.getWritableDatabase());
                }
            }
        }

        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context 上下文对象
     * @return DaoSession
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            synchronized (DaoSession.class) {
                if (daoSession == null) {
                    if (daoMaster == null) {
                        daoMaster = getDaoMaster(context);
                    }
                    daoSession = daoMaster.newSession();

                }
            }

        }
        return daoSession;
    }

    public <T> long insert(T t) {
        return dao.insert(t);
    }

    public <T> void insertList(List<T> list) {
        dao.insertOrReplaceInTx(list);
    }

    public <T> long insertOrReplace(T t) {
        return dao.insertOrReplace(t);
    }

    public <T> void delete(T t) {
        dao.delete(t);
    }

    public void deletAll() {
        dao.deleteAll();
    }

    public <T> void update(T t) {
        dao.update(t);
    }

    public <T> void updateList(List<T> list) {
        dao.updateInTx(list);
    }

    public <T> List<T> query(WhereCondition cond, WhereCondition... condMore) {
        return dao.queryBuilder().where(cond, condMore).build().list();
    }

    public <T> List<T> queryAll() {
        return dao.queryBuilder().build().list();
    }


}
