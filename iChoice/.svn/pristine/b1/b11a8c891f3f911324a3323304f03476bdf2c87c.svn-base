package com.choicemmed.ichoice.healthcheck.fragment.wristpulse;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.choicemmed.common.FormatUtils;
import com.choicemmed.common.LogUtils;
import com.choicemmed.common.ThreadManager;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseFragment;
import com.choicemmed.ichoice.framework.utils.MethodsUtils;
import com.choicemmed.ichoice.framework.utils.ORECalculate;
import com.choicemmed.ichoice.framework.utils.PermissionsUtils;
import com.choicemmed.ichoice.framework.view.IBaseView;
import com.choicemmed.ichoice.healthcheck.activity.wristpulse.ReportWpoActivity;
import com.choicemmed.ichoice.healthcheck.db.W314B4Operation;
import com.choicemmed.ichoice.healthcheck.presenter.WristDataPresenter;
import com.choicemmed.ichoice.healthcheck.service.W314BleConService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.choicemmed.datalib.W314B4Data;

/**
*@anthor by jiangnan
*@Date on "2020/1/15".
*/
public class W314B4SleepFragment extends BaseFragment implements IBaseView {
    public static final String TAG = "W314B4SleepFragment";
    @BindView(R.id.sleep_time)
    TextView sleep_times;
    @BindView(R.id.desaturation_event)
    TextView desaturation_event;
    @BindView(R.id.start_time)
    TextView start_time;
    @BindView(R.id.end_time)
    TextView end_time;
    @BindView(R.id.image_normal)
    ImageView image_normal;
    @BindView(R.id.image_mild)
    ImageView image_mild;
    @BindView(R.id.image_moderate)
    ImageView image_moderate;
    @BindView(R.id.image_severe)
    ImageView image_severe;
    @BindView(R.id.btn_sync)
    Button btn_sync;
    private int oreTimes;
    private int hours;
    private int minute;
    private int seconds;
    private int minSpo;
    private float draw_Ahi = 0;
//    private ProgressDialog progressDialog = null;
    private W314B4Operation w314B4Operation = new W314B4Operation(getContext());
    private W314B4Data w314B4Data;
    private W314BleConService.ScanBleBinder binder;
    private WristDataPresenter mWristDataPresenter;
    private Handler mHandler = new Handler();
    private ServiceConnection bleService = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (W314BleConService.ScanBleBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshData();
            upLoadSleepData();
        }
    };
    private BroadcastReceiver broadcastReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            btn_sync.setEnabled(true);
//            progressDialog.dismiss();
        }
    };
    private BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            btn_sync.setEnabled(true);
//            progressDialog.dismiss();
            MethodsUtils.showErrorTip(getContext(),getString(R.string.connect_fail));
        }
    };


    @Override
    protected int contentViewID() {
        return R.layout.fragment_wpo_sleep;
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            getActivity().bindService(new Intent(getContext(), W314BleConService.class), bleService, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initialize() {
        mWristDataPresenter = new WristDataPresenter(getContext(),this);
        IntentFilter intentFilter = new IntentFilter("W314BleConService:onRecordDataResponse");
        getActivity().registerReceiver(broadcastReceiver,intentFilter);
        IntentFilter intentFilter1 = new IntentFilter("W314BleConService:exitSuccess");
        getActivity().registerReceiver(broadcastReceiver1,intentFilter1);
        IntentFilter intentFilter2 = new IntentFilter("W314BleConService:connectFail");
        getActivity().registerReceiver(broadcastReceiver2,intentFilter2);
        refreshData();
        upLoadSleepData();
    }

    /**
     * 开启子线程搜索需要上传的睡眠数据
     */
    private void upLoadSleepData() {
        ThreadManager.execute(new Runnable() {
            @Override
            public void run() {
                searchUploadData();
            }
        });
    }

    private void searchUploadData() {
        try {
            List<W314B4Data> recordHistory = w314B4Operation.queryUpLoadFalse();
            if (recordHistory == null || recordHistory.isEmpty()) {
                return;
            }
            upLoadData(recordHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void upLoadData(List<W314B4Data> records) {
        for (final W314B4Data record : records) {
            mWristDataPresenter.sendWristDataRequest(record.getAccountKey(), record.getUuid(), record.getStartDate(), record.getEndDate(), record.getSeries(), 0);
        }
    }


    private void refreshData() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        w314B4Data = w314B4Operation.queryByNow(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        if (w314B4Data == null){
            return;
        }
        Log.d(TAG, "refreshData: "+ w314B4Data.toString());
        start_time.setText(w314B4Data.getStartDate());
        end_time.setText(w314B4Data.getEndDate());
        Date startDate = FormatUtils.parseDate(w314B4Data.getStartDate(), FormatUtils.sleep_DbDateTime);
        Date endDate = FormatUtils.parseDate(w314B4Data.getEndDate(), FormatUtils.sleep_DbDateTime);
        if (startDate != null && endDate != null) {
            hours = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60));
            minute = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60) % 60);
            seconds = (int) ((endDate.getTime() - startDate.getTime()) / (1000) % 60);
        }
        oreTimes = ORECalculate.oreCountTimes(w314B4Data.getSeries());
        draw_Ahi = (int) (oreTimes * 60 * 60 * 1000 / (endDate.getTime() - startDate.getTime()));

        minSpo = ORECalculate.getMinSpo(w314B4Data.getSeries());
        oreLevel(draw_Ahi, minSpo);
        sleep_times.setText(hours + ":" + minute);
        desaturation_event.setText(oreTimes+"");

    }

    private void oreLevel(float ahi, int minOl) {
        if (ahi > 30 || minOl < 80) {
            //重度
            image_normal.setVisibility(View.GONE);
            image_mild.setVisibility(View.GONE);
            image_moderate.setVisibility(View.GONE);
            image_severe.setVisibility(View.VISIBLE);

        } else if ((ahi > 15 && ahi <= 30) || (minOl >= 80 && minOl < 85)) {
            //中度
            image_severe.setVisibility(View.GONE);
            image_normal.setVisibility(View.GONE);
            image_mild.setVisibility(View.GONE);
            image_moderate.setVisibility(View.VISIBLE);
        } else if ((ahi >= 5 && ahi <= 15) || (minOl >= 85 && minOl <= 90)) {
            //轻度
            image_severe.setVisibility(View.GONE);
            image_normal.setVisibility(View.GONE);
            image_moderate.setVisibility(View.GONE);
            image_mild.setVisibility(View.VISIBLE);
        }else if (ahi < 5 || minOl > 90){
            //正常
            image_severe.setVisibility(View.GONE);
            image_mild.setVisibility(View.GONE);
            image_moderate.setVisibility(View.GONE);
            image_normal.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.btn_sync,R.id.btn_report})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sync:
                if (!PermissionsUtils.isNetworkConnected(getContext())) {
                    MethodsUtils.showErrorTip(getActivity(),getString(R.string.no_signal));
                    return;
                }
                if (!PermissionsUtils.isBluetoothPermission(getContext())) {
                    LogUtils.d(TAG, "检测到蓝牙未打开：重新开启蓝牙！");
                    MethodsUtils.showErrorTip(getActivity(),getString(R.string.not_bluetooth));
                    return;
                }
                btn_sync.setEnabled(false);
//                progressDialog = ProgressDialog.show(getContext(), "", getString(R.string.syning), true);
                binder.onSyncData();
                break;
            case R.id.btn_report:
                if (!PermissionsUtils.isNetworkConnected(getContext())) {
                    MethodsUtils.showErrorTip(getActivity(),getString(R.string.no_signal));
                    return;
                }
                if (w314B4Data == null){
                    MethodsUtils.showErrorTip(getContext(),getString(R.string.no_data));
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("TYPE","W314");
                bundle.putString("UUID",w314B4Data.getUuid());
                startActivity(ReportWpoActivity.class, bundle);
                break;
                default:
        }
    }


    @Override
    public void onSuccess() {
        Log.d(TAG, "onSuccess: 上传成功");
    }

    @Override
    public void onError(String msg) {
        Log.d(TAG, "onError: 上传失败"+ msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
        getActivity().unregisterReceiver(broadcastReceiver1);
        getActivity().unregisterReceiver(broadcastReceiver2);
        mHandler.removeCallbacks(null);
        try {
            binder.cancelConnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getActivity().unbindService(bleService);
    }
}
