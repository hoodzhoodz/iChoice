package com.choicemmed.ichoice.healthcheck.activity.ecg;

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
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.utils.ActivityUtils;
import com.choicemmed.ichoice.healthcheck.activity.pulseoximeter.DeviceSettingOXActivity;
import com.choicemmed.ichoice.healthcheck.activity.wristpulse.DeviceSettingTempActivity;
import com.choicemmed.ichoice.healthcheck.fragment.ecg.EcgHistoryFragment;
import com.choicemmed.ichoice.healthcheck.fragment.ecg.EcgMeasureFragment;
import com.choicemmed.ichoice.healthcheck.fragment.pulseoximeter.OXSpotCheckFragment;
import com.choicemmed.ichoice.healthcheck.fragment.pulseoximeter.OXSpotHistoricalResultsFragment;
import com.choicemmed.ichoice.healthcheck.fragment.pulseoximeter.OxSpotHistoricalTrendFragment;
import com.choicemmed.ichoice.healthcheck.service.C208BleConService;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class EcgCheckActivity extends BaseActivty {
    private String TAG = this.getClass().getSimpleName();
    @BindView(R.id.ll_measurement)
    LinearLayout ll_measurement;
    @BindView(R.id.tv_measurement)
    TextView tv_measurement;
    @BindView(R.id.ll_history)
    LinearLayout ll_history;
    @BindView(R.id.tv_history)
    TextView tv_history;
    private Fragment currentFragment;
    private Fragment ecgMeasureFragment;
    private Fragment ecgHistoryFragment;

    @Override
    protected int contentViewID() {
        return R.layout.activity_ecg;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void initialize() {
        setTopTitle(getString(R.string.ecg), true);
        setLeftBtnFinish();

        setRightBtn(true, R.mipmap.setting, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(DeviceSettingEcgActivity.class);
                    }
                }
        );
        ll_measurement.performClick();
    }


    @OnClick({R.id.ll_measurement, R.id.ll_history})
    public void onClick(View view) {
        if (check(view))
            return;
        switch (view.getId()) {
            case R.id.ll_measurement:
                LogUtils.d(TAG, "click");
                ll_measurement.setSelected(true);
                tv_measurement.setSelected(true);
                ll_history.setSelected(false);
                tv_history.setSelected(false);
                if (ecgMeasureFragment == null) {
                    ecgMeasureFragment = new EcgMeasureFragment();
                }
                switchFragment(ecgMeasureFragment);
                break;
            case R.id.ll_history:
                LogUtils.d(TAG, "click");
                ll_measurement.setSelected(false);
                tv_measurement.setSelected(false);
                ll_history.setSelected(true);
                tv_history.setSelected(true);
                if (ecgHistoryFragment == null) {
                    ecgHistoryFragment = new EcgHistoryFragment();
                }
                switchFragment(ecgHistoryFragment);
                break;
        }
    }

    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            if (currentFragment != null)
                fragmentTransaction.hide(currentFragment);
            fragmentTransaction.add(R.id.fl_ecg, targetFragment, targetFragment.getClass().getName());
        } else {
            fragmentTransaction
                    .hide(currentFragment)
                    .show(targetFragment);
        }
        currentFragment = targetFragment;
        fragmentTransaction.commit();
    }


    private boolean check(View view) {
        if (view.isSelected()) {
            return true;
        }
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
