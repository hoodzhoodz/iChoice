package com.choicemmed.ichoice.healthcheck.db;

import android.content.Context;
import android.database.Cursor;

import com.choicemmed.common.LogUtils;
import com.choicemmed.ichoice.framework.base.BaseDb;

import java.util.ArrayList;
import java.util.List;

import pro.choicemmed.datalib.Cbp1k1Data;
import pro.choicemmed.datalib.Cbp1k1DataDao;
import pro.choicemmed.datalib.OxSpotData;

/**
 * @author Created by Jiang nan on 2019/11/21 10:20.
 * @description
 **/
public class Cbp1k1Operation extends BaseDb {
    private static final String TAG = Cbp1k1Operation.class.getSimpleName();
    private Context context;
    public Cbp1k1Operation(Context context) {
        dao = getDaoSession(context).getCbp1k1DataDao();
        this.context = context;
    }

    public List<Cbp1k1Data> queryByUserId(String date, String userId, int offset, int rows) {
        List<Cbp1k1Data> cbp1k1DataList = dao.queryBuilder()
                .where(Cbp1k1DataDao.Properties.UserId.eq(userId), Cbp1k1DataDao.Properties.MeasureDateTime.like(date + "%"))
                .orderDesc(Cbp1k1DataDao.Properties.MeasureDateTime)
                .offset(offset)
                .limit(rows)
                .build()
                .list();
        return cbp1k1DataList;
    }

    public List<Cbp1k1Data> queryById(String uuid) {
        return   dao.queryBuilder().where(Cbp1k1DataDao.Properties.Id.eq(uuid)).build().list();
    }

    public List<Cbp1k1Data> queryBySyncState(String userId) {
        return dao.queryBuilder().where(Cbp1k1DataDao.Properties.UserId.eq(userId)).orderAsc(Cbp1k1DataDao.Properties.MeasureDateTime).build().list();
    }

    public long insertCbp1k1ByUser(Cbp1k1Data cbp1k1Data){
        return dao.insert(cbp1k1Data);
    }

    @Override
    public List<Cbp1k1Data> queryAll(){
        return  dao.queryBuilder().orderAsc(Cbp1k1DataDao.Properties.MeasureDateTime).build().list();
    }

    public Cbp1k1Data queryByNowDate(String userId, String beginTime, String endTime) {
        String sql = "WHERE MEASURE_DATE_TIME BETWEEN\""
                + beginTime
                + "\" AND\""
                + endTime
                + "\" AND USER_ID= "
                + userId
                + " GROUP BY substr(MEASURE_DATE_TIME,1,10)"
                + "ORDER BY MEASURE_DATE_TIME DESC";
        List<Cbp1k1Data> cbp1k1DataList = dao.queryRaw(sql);
        if (cbp1k1DataList == null || cbp1k1DataList.isEmpty()) {
            return null;
        }
        return cbp1k1DataList.get(0);
    }



    public List<Cbp1k1Data> queryToMonth(String userId, String beginTime, String endTime){
        String sql = "WHERE MEASURE_DATE_TIME BETWEEN\""
                + beginTime
                + "\" AND\""
                + endTime
                + "\" AND USER_ID= "
                + userId
                + " GROUP BY substr(MEASURE_DATE_TIME,1,18) "
                + "ORDER BY MEASURE_DATE_TIME DESC";
        List<Cbp1k1Data> cbp1k1DataList = dao.queryRaw(sql);
        if (cbp1k1DataList == null || cbp1k1DataList.isEmpty()) {
            return null;
        }
        return cbp1k1DataList;
    }

    public List<Cbp1k1Data> queryToDay(String userId, String beginTime, String endTime){
        String sql = "WHERE MEASURE_DATE_TIME BETWEEN\""
                + beginTime
                + "\" AND\""
                + endTime
                + "\" AND USER_ID= "
                + userId
                + " GROUP BY substr(MEASURE_DATE_TIME,1,18) "
                + "ORDER BY MEASURE_DATE_TIME DESC";
        List<Cbp1k1Data> cbp1k1DataList = dao.queryRaw(sql);
        if (cbp1k1DataList == null || cbp1k1DataList.isEmpty()) {
            return null;
        }
        return cbp1k1DataList;
    }

    public List<Cbp1k1Data> queryAVGDataByThreeHour(String userId) {
        List<Cbp1k1Data> list = new ArrayList<>();
        String sql = "SELECT " +
                "ID," +
                "substr(MEASURE_DATE_TIME,1,11) || case 2-length(substr(MEASURE_DATE_TIME,12,2)/3*3) when 1 then '0' else '' end || (substr(MEASURE_DATE_TIME,12,2)/3*3) || ':00:00' MEASURE_DATE_TIME," +
                "CAST(avg(SYSTOLIC) as int) SYSTOLIC," +
                "CAST(avg(DIASTOLIC) as int) DIASTOLIC," +
                "CAST(avg(PULSE_RATE) as int) PULSE_RATE," +
                "DEVICE_ID," +
                "SYNC_STATE," +
                "CREATE_TIME," +
                "LOG_DATE_TIME" +
                " FROM " + "CBP1K1_DATA" +
                " WHERE USER_ID = " + userId +
                " GROUP BY substr(MEASURE_DATE_TIME,1,11) || case 2-length(substr(MEASURE_DATE_TIME,12,2)/3*3) when 1 then '0' else '' end || (substr(MEASURE_DATE_TIME,12,2)/3*3) || ':00:00'\n" +
                " ORDER BY MEASURE_DATE_TIME ASC";
        Cursor c = dao.getDatabase().rawQuery(sql, null);
        try {
            if (c.moveToFirst()) {

                do {
                    Cbp1k1Data cbp1k1Data = new Cbp1k1Data();
                    cbp1k1Data.setSystolic(c.getInt(c.getColumnIndex("SYSTOLIC")));
                    cbp1k1Data.setPulseRate(c.getInt(c.getColumnIndex("PULSE_RATE")));
                    cbp1k1Data.setDiastolic(c.getInt(c.getColumnIndex("DIASTOLIC")));
                    cbp1k1Data.setId(c.getString(c.getColumnIndex("ID")));
                    cbp1k1Data.setMeasureDateTime(c.getString(c.getColumnIndex("MEASURE_DATE_TIME")));
                    LogUtils.d(TAG, cbp1k1Data.toString());
                    list.add(cbp1k1Data);
                } while (c.moveToNext());
            }
        } finally {
            c.close();
        }
        return list;

    }

    public List<Cbp1k1Data> queryAVGDataByDay(String userId) {
        List<Cbp1k1Data> list = new ArrayList<>();
        String sql = "SELECT " +
                "ID," +
                "substr(MEASURE_DATE_TIME,1,11)|| '00:00:00' MEASURE_DATE_TIME," +
                "CAST(avg(SYSTOLIC) as int) SYSTOLIC," +
                "CAST(avg(DIASTOLIC) as int) DIASTOLIC," +
                "CAST(avg(PULSE_RATE) as int) PULSE_RATE," +
                "DEVICE_ID," +
                "SYNC_STATE," +
                "CREATE_TIME," +
                "LOG_DATE_TIME" +
                " FROM " + "CBP1K1_DATA" +
                " WHERE USER_ID = " + userId +
                " GROUP BY substr(MEASURE_DATE_TIME,1,11)|| '00:00:00'" +
                " ORDER BY MEASURE_DATE_TIME ASC";
        Cursor c = dao.getDatabase().rawQuery(sql, null);
        try {
            if (c.moveToFirst()) {

                do {
                    Cbp1k1Data cbp1k1Data = new Cbp1k1Data();
                    cbp1k1Data.setSystolic(c.getInt(c.getColumnIndex("SYSTOLIC")));
                    cbp1k1Data.setPulseRate(c.getInt(c.getColumnIndex("PULSE_RATE")));
                    cbp1k1Data.setDiastolic(c.getInt(c.getColumnIndex("DIASTOLIC")));
                    cbp1k1Data.setId(c.getString(c.getColumnIndex("ID")));
                    cbp1k1Data.setMeasureDateTime(c.getString(c.getColumnIndex("MEASURE_DATE_TIME")));
                    LogUtils.d(TAG, cbp1k1Data.toString());
                    list.add(cbp1k1Data);
                } while (c.moveToNext());
            }
        } finally {
            c.close();
        }
        return list;

    }
    public List<Cbp1k1Data> queryByDate(String date, String userId) {
        return dao.queryBuilder().where(Cbp1k1DataDao.Properties.MeasureDateTime.like("%" + date + "%")
                , Cbp1k1DataDao.Properties.UserId.eq(userId)).orderDesc(Cbp1k1DataDao.Properties.MeasureDateTime).build().list();
    }

}
