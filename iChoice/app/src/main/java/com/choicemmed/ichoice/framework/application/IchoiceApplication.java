package com.choicemmed.ichoice.framework.application;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.entity.AppData;
import com.choicemmed.ichoice.healthcheck.db.MySQLiteOpenHelper;
import com.choicemmed.ichoice.healthcheck.fragment.ecg.analyzer.EcgAnalyzerResult;
import com.choicemmed.ichoice.healthcheck.fragment.ecg.bean.AnResult;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import okhttp3.OkHttpClient;
import pro.choicemmed.datalib.DaoMaster;
import pro.choicemmed.datalib.DaoSession;
import pro.choicemmed.datalib.EcgData;

/**
 * Created by gaofang on 2019/1/10.
 */

////////////////////////////////////////////////////////////////////
//                          _ooOoo_                               //
//                         o8888888o                              //
//                         88" . "88                              //
//                         (| ^_^ |)                              //
//                         O\  =  /O                              //
//                      ____/`---'\____                           //
//                    .'  \\|     |//  `.                         //
//                   /  \\|||  :  |||//  \                        //
//                  /  _||||| -:- |||||-  \                       //
//                  |   | \\\  -  /// |   |                       //
//                  | \_|  ''\---/''  |   |                       //
//                  \  .-\__  `-`  ___/-. /                       //
//                ___`. .'  /--.--\  `. . ___                     //
//              ."" '<  `.___\_<|>_/___.'  >'"".                  //
//            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
//            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
//      ========`-.____`-.___\_____/___.-`____.-'========         //
//                           `=---='                              //
//      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
//         佛祖保佑       永无BUG     永不修改                        //
////////////////////////////////////////////////////////////////////
public class IchoiceApplication extends Application {
    private static final String TAG = "IchoiceApplication";
    private MySQLiteOpenHelper helper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static IchoiceApplication application;
    private static AppData appData = new AppData();
    private static Context mContext;
    public static String type = "A12";
    public static String localelan = "";
    public static AnResult thisResult;
    public static EcgAnalyzerResult ecgAnalyzerResult;
    public static EcgData ecgData;
    /**
     * 用于标记最近的睡眠数据
     */
    public final static String KEY_Current_Record = "KEY_Current_Record";

    /**
     * 用来存储全局变量
     */
    private Map<String, Object> map = new HashMap<String, Object>();
    @Override
    public void onCreate() {
        super.onCreate();
        initBase();
        initOkGo();
        initDatabase();
        Fresco.initialize(this);
        mContext = getApplicationContext();
    }

    public static IchoiceApplication getInstance() {
        return application;
    }

    public static AppData getAppData() {
        return IchoiceApplication.appData;
    }


    private void initBase() {
        application = this;
    }

    private void initOkGo() {
        OkHttpClient.Builder builder  = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志

        //超时时间设置，默认60秒
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())
                .setCacheMode(CacheMode.NO_CACHE)//全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)////全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(2);//全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
    }
    /**
     * 设置greenDao
     */
    private void initDatabase() {
        helper = new MySQLiteOpenHelper(this, "ichoice-db",
                null);
        mDaoMaster = new DaoMaster(helper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static Context  getmContext() {
        return mContext;
    }

    public static void singleDialog(String title, Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.tip_dialog,null);
        TextView msg_title = view.findViewById(R.id.msg_title);
        msg_title.setText(title);
        Button btn_ok = view.findViewById(R.id.btn_ok);
        final Dialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(true)
                .create();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
