package com.choicemmed.ichoice.healthcheck.activity.pulseoximeter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.choicemmed.common.LogUtils;
import com.choicemmed.common.SharePreferenceUtil;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.utils.ActivityUtils;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.choicemmed.ichoice.healthcheck.fragment.pulseoximeter.OXRealCheckFragment;
import com.choicemmed.ichoice.healthcheck.fragment.pulseoximeter.OXRealHistoricalFragment;
import com.choicemmed.ichoice.healthcheck.fragment.pulseoximeter.OXSpotCheckFragment;
import com.choicemmed.ichoice.healthcheck.fragment.pulseoximeter.OXSpotHistoricalResultsFragment;

import com.choicemmed.ichoice.healthcheck.fragment.pulseoximeter.OxSpotHistoricalTrendFragment;
import com.choicemmed.ichoice.healthcheck.service.C208sBleConService;
import com.choicemmed.ichoice.healthcheck.service.C218RBleConService;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class C208sMeasureActivity extends BaseActivty {
    String TAG = "C208sMeasureActivity";
    @BindView(R.id.oxspotmeasurement_tab_textview)
    TextView tvSpotCheck;
    @BindView(R.id.oxspot_historicalresults_tab_textview)
    TextView tvHistoricalResults;
    @BindView(R.id.oxspot_historicaltrend_tab_textview)
    TextView tvHistoricalTrend;

    @BindView(R.id.oxspotmeasurement)
    LinearLayout lineSpotCheck;
    @BindView(R.id.oxspot_historicalresults)
    LinearLayout lineHistoricalResults;
    @BindView(R.id.oxspot_historicaltrend)
    LinearLayout lineHistoricalTrend;

    @BindView(R.id.fragment_result_ox_spot)
    FrameLayout fragment_result;
    @BindView(R.id.ll_point)
    LinearLayout ll_point;


    //    实时模式控件

    @BindView(R.id.ll_real)
    LinearLayout ll_real;
    @BindView(R.id.ll_ox_real)
    LinearLayout ll_ox_real;
    @BindView(R.id.ll_ox_his)
    LinearLayout ll_ox_his;
    @BindView(R.id.tv_ox_real)
    TextView tv_ox_real;
    @BindView(R.id.tv_ox_his)
    TextView tv_ox_his;
    private OXSpotCheckFragment oxSpotCheckFragment;
    private OXSpotHistoricalResultsFragment oxSpotHistoricalResultsFragment;
    private OxSpotHistoricalTrendFragment oxSpotHistoricalTrendFragment;
    private OXRealCheckFragment oxRealCheckFragment;
    private OXRealHistoricalFragment oxRealHistoricalFragment;
    private FragmentManager fragmentManager;
    private C208sBleConService.ScanBleBinder binder;
    private C218RBleConService.ScanBleBinder binder1;

    private String deviceType;
    private Bundle bundle;
    //    点测 1  实时 2
    private int workingMode;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: 绑定服务成功开始连接设备");
            if (getIntent().getExtras().getString(DevicesType.Device).equals("MD300CI218R")) {
                binder1 = (C218RBleConService.ScanBleBinder) service;
                binder1.setMode(workingMode);
                binder1.starConnectBle();
            } else {
                binder = (C208sBleConService.ScanBleBinder) service;
                binder.setMode(workingMode);
                binder.starConnectBle();
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceConnected: 服务断开");

        }
    };

    @Override
    protected int contentViewID() {
        return R.layout.activity_c208s_measure;
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            bundle = getIntent().getExtras();
            deviceType = bundle.getString(DevicesType.Device);
            Log.d(TAG, " deviceType   " + deviceType);
            if (("MD300CI218R").equals(deviceType)) {
                bindService(new Intent(this, C218RBleConService.class), serviceConnection, Context.BIND_AUTO_CREATE);
                Log.d(TAG, "C218RBleConService START");
            } else {
                bindService(new Intent(this, C208sBleConService.class), serviceConnection, Context.BIND_AUTO_CREATE);
                Log.d(TAG, "C208sBleConService START");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onStart");
    }

    @Override
    protected void initialize() {
        ActivityUtils.addActivity(this);
        setTopTitle(getString(R.string.pulse_qximeter), true);
        setLeftBtnFinish();
        setRightBtn(true, R.mipmap.setting, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(DevicesType.Device, getIntent().getExtras().getString(DevicesType.Device));
                        startActivity(DeviceSettingOXActivity.class, bundle);
                    }
                }
        );
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (workingMode != (int) SharePreferenceUtil.get(this, "ox_working_mode", 1)) {
            workingMode = (int) SharePreferenceUtil.get(this, "ox_working_mode", 1);
            if (binder != null) {
                binder.setMode(workingMode);
            } else if (binder1 != null) {
                binder1.setMode(workingMode);
            }
            LogUtils.d(TAG, "workingMode  " + workingMode + "");
            if (workingMode == 1) {
                ll_point.setVisibility(View.VISIBLE);
                ll_real.setVisibility(View.GONE);
                tvSpotCheck.performLongClick();
                lineSpotCheck.performClick();
            } else {
                ll_point.setVisibility(View.GONE);
                ll_real.setVisibility(View.VISIBLE);
                tv_ox_real.performLongClick();
                ll_ox_real.performClick();

            }

        }


    }

    private void setUnselected() {
        tvSpotCheck.setSelected(false);
        lineSpotCheck.setSelected(false);
        tvHistoricalResults.setSelected(false);
        lineHistoricalResults.setSelected(false);
        tvHistoricalTrend.setSelected(false);
        lineHistoricalTrend.setSelected(false);
        ll_ox_real.setSelected(false);
        ll_ox_his.setSelected(false);
        tv_ox_real.setSelected(false);
        tv_ox_his.setSelected(false);
    }

    @OnClick({R.id.oxspotmeasurement, R.id.oxspot_historicalresults, R.id.oxspot_historicaltrend, R.id.ll_ox_his, R.id.ll_ox_real,})
    public void onClick(View view) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        List<Fragment> fragments = fragmentManager.getFragments();
        switch (view.getId()) {
            case R.id.oxspotmeasurement:
                setUnselected();
                tvSpotCheck.setSelected(true);
                lineSpotCheck.setSelected(true);
                if (oxSpotCheckFragment != null && fragmentManager.findFragmentByTag("OXSpotCheckFragment") != null) {
                    LogUtils.d(TAG, "fragmentManager.findFragmentByTag != null");
                    return;
                }
                if (oxSpotCheckFragment == null) {
                    oxSpotCheckFragment = new OXSpotCheckFragment();
                }
                fragmentTransaction.add(R.id.fragment_result_ox_spot, oxSpotCheckFragment, "OXSpotCheckFragment");
                for (Fragment fragment : fragments) {
                    fragmentTransaction.remove(fragment);
                }
                fragmentTransaction.commit();
                break;
            case R.id.oxspot_historicalresults:
                setUnselected();
                tvHistoricalResults.setSelected(true);
                lineHistoricalResults.setSelected(true);
                oxSpotHistoricalResultsFragment = new OXSpotHistoricalResultsFragment();
                fragmentTransaction.add(R.id.fragment_result_ox_spot, oxSpotHistoricalResultsFragment);

                for (Fragment fragment : fragments) {
                    fragmentTransaction.remove(fragment);
                }
                fragmentTransaction.commit();
                break;
            case R.id.oxspot_historicaltrend:
                setUnselected();
                tvHistoricalTrend.setSelected(true);
                lineHistoricalTrend.setSelected(true);
                oxSpotHistoricalTrendFragment = new OxSpotHistoricalTrendFragment();
                fragmentTransaction.add(R.id.fragment_result_ox_spot, oxSpotHistoricalTrendFragment);
                for (Fragment fragment : fragments) {
                    fragmentTransaction.remove(fragment);
                }
                fragmentTransaction.commit();
                break;

            case R.id.ll_ox_real:
                setUnselected();
                ll_ox_real.setSelected(true);
                tv_ox_real.setSelected(true);
                if (oxRealCheckFragment != null && fragmentManager.findFragmentByTag("OXRealCheckFragment") != null) {
                    LogUtils.d(TAG, "fragmentManager.findFragmentByTag != null");
                    return;
                }
                if (oxRealCheckFragment == null) {
                    oxRealCheckFragment = new OXRealCheckFragment();
                }
                fragmentTransaction.add(R.id.fragment_result_ox_spot, oxRealCheckFragment, "OXRealCheckFragment");
                for (Fragment fragment : fragments) {
                    fragmentTransaction.remove(fragment);
                }
                fragmentTransaction.commit();
                break;
            case R.id.ll_ox_his:
                setUnselected();
                ll_ox_his.setSelected(true);
                tv_ox_his.setSelected(true);
                oxRealHistoricalFragment = new OXRealHistoricalFragment();
                fragmentTransaction.add(R.id.fragment_result_ox_spot, oxRealHistoricalFragment);
                for (Fragment fragment : fragments) {
                    fragmentTransaction.remove(fragment);
                }
                fragmentTransaction.commit();
                break;
            default:
        }

    }

    @Override
    protected void onDestroy() {
        try {
            if (binder != null) {
                binder.cancelConnect();
                binder.disconnect();
            } else if (binder1 != null) {
                binder1.cancelConnect();
                binder1.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        unbindService(serviceConnection);
        super.onDestroy();

    }
}