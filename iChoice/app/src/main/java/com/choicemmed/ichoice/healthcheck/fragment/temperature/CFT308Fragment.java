package com.choicemmed.ichoice.healthcheck.fragment.temperature;


import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.choicemmed.common.FormatUtils;
import com.choicemmed.common.LogUtils;
import com.choicemmed.common.SharePreferenceUtil;
import com.choicemmed.common.StringUtils;
import com.choicemmed.common.ToastUtils;
import com.choicemmed.common.UuidUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseFragment;
import com.choicemmed.ichoice.healthcheck.adapter.CFT308HistoryAdapter;

import com.choicemmed.ichoice.healthcheck.fragment.wristpulse.TipsDialogFragment;
import com.choicemmed.ichoice.healthcheck.model.BleConnectListener;
import com.choicemmed.ichoice.healthcheck.service.BleConService;

import com.choicemmed.ichoice.healthcheck.view.CircleProgress;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pro.choicemmed.datalib.CFT308Data;
import pro.choicemmed.datalib.CFT308DataDao;


import static com.choicemmed.ichoice.framework.base.BaseDb.getDaoSession;


public class CFT308Fragment extends BaseFragment implements BleConnectListener, View.OnClickListener {
    @BindView(R.id.cp)
    CircleProgress circleProgress;
    @BindView(R.id.bt_tips)
    ImageView bt_tips;
    @BindView(R.id.temp_left)
    TextView temp_left;
    @BindView(R.id.temp_right)
    TextView temp_right;
    @BindView(R.id.tv_unit)
    TextView tv_unit;
    @BindView(R.id.tv_current)
    TextView tv_current;
    @BindView(R.id.rl)
    RecyclerView recyclerView;
    @BindView(R.id.bt_age)
    ImageView bt_age;
    @BindView(R.id.text_age)
    TextView text_age;
    @BindView(R.id.ll_today)
    LinearLayout ll_today;
    @BindView(R.id.rl_today_no_data)
    LinearLayout rl_today_no_data;
    private List<CFT308Data> cft308DataList;
    private CFT308HistoryAdapter cft308HistoryAdapter;
    public static final String TAG = "CFT308Fragment";
    private BleConService.ScanBleBinder binder;
    String dateTimeString;
    private int unit = 1;
    private float currentTempLow;
    private float currentTempHigh;
    private float currentTempHigher;
    //    1 :0-3个月   2 :3-36月  3: 36＋
    private int age = 1;
    private ServiceConnection bleService = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.d(TAG, "ServiceConnected");
            binder = (BleConService.ScanBleBinder) service;
            binder.setListener(CFT308Fragment.this);
            binder.init();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.d(TAG, "ServiceDisconnected");
        }
    };

    @Override
    protected int contentViewID() {
        return R.layout.fragment_cft308;
    }

    @Override
    protected void initialize() {
        try {
            getActivity().bindService(new Intent(getContext(), BleConService.class), bleService, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.d(TAG, "initialize");
    }

    @Override
    public void onStart() {
        super.onStart();
        bt_tips.setOnClickListener(this);
        age = (int) SharePreferenceUtil.get(getActivity(), "temp_age", 3);
        initAge();
        if ((int) SharePreferenceUtil.get(getActivity(), "temp_unit", 1) == 1) {
            unit = 1;
            tv_unit.setText(getString(R.string.temp_unit));
        } else {
            unit = 2;
            tv_unit.setText(getString(R.string.temp_unit1));
        }
        LogUtils.d(TAG, "onStart");
        dateTimeString = FormatUtils.getDateTimeString(Calendar.getInstance().getTime(), FormatUtils.template_Date);
        cft308DataList = getDaoSession(getActivity()).getCFT308DataDao().queryBuilder().where(CFT308DataDao.Properties.UserId.eq(IchoiceApplication.getAppData().userProfileInfo.getUserId()), CFT308DataDao.Properties.MeasureTime.like(dateTimeString + "%")).orderDesc(CFT308DataDao.Properties.MeasureTime).list();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        cft308HistoryAdapter = new CFT308HistoryAdapter(getActivity(), cft308DataList);
        recyclerView.setAdapter(cft308HistoryAdapter);
        if (cft308DataList.isEmpty()) {
            rl_today_no_data.setVisibility(View.VISIBLE);
            ll_today.setVisibility(View.GONE);
        } else {
            rl_today_no_data.setVisibility(View.GONE);
            ll_today.setVisibility(View.VISIBLE);
            temp_left.setTextSize(92);
            temp_right.setTextSize(64);
            if (unit == 1) {
                temp_left.setText(cft308DataList.get(0).getTemp().substring(0, cft308DataList.get(0).getTemp().length() - 2));
                temp_right.setText(cft308DataList.get(0).getTemp().substring(cft308DataList.get(0).getTemp().length() - 2));
            } else {
                String f = String.valueOf((Float.parseFloat(cft308DataList.get(0).getTemp()) * 9 / 5) + 32);
                DecimalFormat df = new DecimalFormat("#.0");
                df.setRoundingMode(RoundingMode.DOWN);
                f = df.format(Float.parseFloat(f));
                if (!StringUtils.isEmpty(f)) {
                    if (Float.parseFloat(f) >= 100) {
                        temp_left.setTextSize(80);
                        temp_right.setTextSize(56);

                    }
                    temp_left.setText(f.substring(0, f.length() - 2));
                    temp_right.setText(f.substring(f.length() - 2));
                }

            }

            changeColor(cft308DataList.get(0).getTemp());

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.d(TAG, "onPause");
    }

    @Override
    public void onBindDeviceSuccess(BluetoothDevice mDevice) {


    }

    @Override
    public void onError(String msg) {
        ToastUtils.showCustom(getActivity(), msg, 3000);
    }

    @Override
    public void onConnectedDeviceSuccess() {

    }

    @Override
    public void onDataResponse(final String value) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                temp_left.setTextSize(92);
                temp_right.setTextSize(64);
                changeColor(value);
                circleProgress.reset();
                circleProgress.setValue((float) (((Float.parseFloat(value) - 32) * 100) / 10.9));
                if (unit == 1) {
                    temp_left.setText(value.substring(0, value.length() - 2));
                    temp_right.setText(value.substring(value.length() - 2));

                } else {
                    String f = String.valueOf((Float.parseFloat(value) * 9 / 5) + 32);
                    DecimalFormat df = new DecimalFormat("#.0");
                    df.setRoundingMode(RoundingMode.DOWN);
                    f = df.format(Float.parseFloat(f));
                    LogUtils.d(TAG, f);
                    if (!StringUtils.isEmpty(f)) {
                        if (Float.parseFloat(f) >= 100) {
                            temp_left.setTextSize(80);
                            temp_right.setTextSize(56);

                        }
                        temp_left.setText(f.substring(0, f.length() - 2));
                        temp_right.setText(f.substring(f.length() - 2));
                    }

                }
                CFT308Data cft308Data = new CFT308Data();
                cft308Data.setUserId(IchoiceApplication.getAppData().userProfileInfo.getUserId());
                cft308Data.setAccountKey(IchoiceApplication.getAppData().user.getToken());
                cft308Data.setUuid(UuidUtils.getUuid());
                String timeString = FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime);
                cft308Data.setMeasureTime(timeString);
                cft308Data.setTemp(value);
                getDaoSession(getActivity()).getCFT308DataDao().insertOrReplace(cft308Data);
                cft308DataList = getDaoSession(getActivity()).getCFT308DataDao().queryBuilder().where(CFT308DataDao.Properties.UserId.eq(IchoiceApplication.getAppData().userProfileInfo.getUserId()), CFT308DataDao.Properties.MeasureTime.like(dateTimeString + "%")).orderDesc(CFT308DataDao.Properties.MeasureTime).list();
                cft308HistoryAdapter = new CFT308HistoryAdapter(getActivity(), cft308DataList);
                recyclerView.setAdapter(cft308HistoryAdapter);
                if (cft308DataList.isEmpty()) {
                    rl_today_no_data.setVisibility(View.VISIBLE);
                    ll_today.setVisibility(View.GONE);
                } else {
                    rl_today_no_data.setVisibility(View.GONE);
                    ll_today.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void changeColor(String value) {
        float temp = Float.valueOf(value);
        if (age == 1) {
            if (temp > currentTempHigher) {
                circleProgress.resetPintColor(3);
                temp_left.setTextColor(getResources().getColor(R.color.red_e86060));
                temp_right.setTextColor(getResources().getColor(R.color.red_e86060));
                tv_unit.setTextColor(getResources().getColor(R.color.red_e86060));
                tv_current.setTextColor(getResources().getColor(R.color.red_e86060));

            } else {
                circleProgress.resetPintColor(1);
                temp_left.setTextColor(getResources().getColor(R.color.blue_8c9eff));
                temp_right.setTextColor(getResources().getColor(R.color.blue_8c9eff));
                tv_unit.setTextColor(getResources().getColor(R.color.blue_8c9eff));
                tv_current.setTextColor(getResources().getColor(R.color.blue_8c9eff));
            }
        } else {
            if (temp > currentTempHigh) {
                if (temp <= currentTempHigher) {
                    circleProgress.resetPintColor(2);
                    temp_left.setTextColor(getResources().getColor(R.color.red1));
                    temp_right.setTextColor(getResources().getColor(R.color.red1));
                    tv_unit.setTextColor(getResources().getColor(R.color.red1));
                    tv_current.setTextColor(getResources().getColor(R.color.red1));
                } else {
                    circleProgress.resetPintColor(3);
                    temp_left.setTextColor(getResources().getColor(R.color.red_e86060));
                    temp_right.setTextColor(getResources().getColor(R.color.red_e86060));
                    tv_unit.setTextColor(getResources().getColor(R.color.red_e86060));
                    tv_current.setTextColor(getResources().getColor(R.color.red_e86060));
                }
            } else {
                circleProgress.resetPintColor(1);
                temp_left.setTextColor(getResources().getColor(R.color.blue_8c9eff));
                temp_right.setTextColor(getResources().getColor(R.color.blue_8c9eff));
                tv_unit.setTextColor(getResources().getColor(R.color.blue_8c9eff));
                tv_current.setTextColor(getResources().getColor(R.color.blue_8c9eff));
            }
        }
    }

    @OnClick({R.id.bt_tips, R.id.bt_age})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_tips:
                if (isFastDoubleClick()) {
                    return;
                }
                TipsDialogFragment dialogFragment = new TipsDialogFragment();
                dialogFragment.setCancelable(false);
                dialogFragment.show(getFragmentManager(), "CFT308Fragment");
                break;
            case R.id.bt_age:
                calculatingAge();
                break;
        }

    }

    private void initAge() {
        switch (age) {
            case 1:
                bt_age.setBackgroundResource(R.mipmap.temp_bt1);
                text_age.setText(R.string.tips_one_to_three);
                currentTempLow = 35.8f;
                currentTempHigh = 37.4f;
                currentTempHigher = 37.4f;
                break;
            case 2:
                bt_age.setBackgroundResource(R.mipmap.temp_bt2);
                text_age.setText(R.string.tips_three_to_thirth_six);
                currentTempLow = 35.4f;
                currentTempHigh = 37.6f;
                currentTempHigher = 38.5f;
                break;
            case 3:
                bt_age.setBackgroundResource(R.mipmap.temp_bt3);
                text_age.setText(R.string.tips_thirth_six_to_more);
                currentTempLow = 35.4f;
                currentTempHigh = 37.7f;
                currentTempHigher = 39.4f;
                break;

        }
    }

    private void calculatingAge() {
        switch (age) {
            case 3:
                age = 1;
                break;
            case 2:
                age = 3;
                break;
            case 1:
                age = 2;
                break;
        }
        initAge();
        SharePreferenceUtil.put(getActivity(), "temp_age", age);
        LogUtils.d(TAG, "age  " + age);
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.d(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unbindService(bleService);
    }

    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = SystemClock.uptimeMillis();
        if (time - lastClickTime < 400) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

}