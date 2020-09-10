package com.choicemmed.ichoice.healthcheck.activity.wristpulse;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.utils.ActivityUtils;
import com.choicemmed.ichoice.healthcheck.activity.SuccessActivity;
import com.choicemmed.ichoice.healthcheck.fragment.wristpulse.W314B4HistoricalRecordFragment;
import com.choicemmed.ichoice.healthcheck.fragment.wristpulse.W314B4RealTimeFragment;
import com.choicemmed.ichoice.healthcheck.fragment.wristpulse.W314B4SleepFragment;
import com.choicemmed.ichoice.healthcheck.fragment.wristpulse.W628HistoricalRecordFragment;
import com.choicemmed.ichoice.healthcheck.fragment.wristpulse.W628RealTimeFragment;
import com.choicemmed.ichoice.healthcheck.fragment.wristpulse.W628SleepFragment;
import com.choicemmed.ichoice.initalization.activity.MainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ResultWpoW628Activity extends BaseActivty {

    @BindView(R.id.tv_sleep)
    TextView tvSleep;
    @BindView(R.id.tv_real_time)
    TextView tvRealtime;
    @BindView(R.id.tv_wpo_historical_trend)
    TextView tvHistoricaltrend;

    @BindView(R.id.line_sleep)
    LinearLayout lineSleep;
    @BindView(R.id.line_real_time)
    LinearLayout lineRealTime;
    @BindView(R.id.line_wpo_historical_trend)
    LinearLayout lineHistoricalTrend;

    @BindView(R.id.fragment_result_wpo)
    FrameLayout fragment_result;

    private W628SleepFragment w628SleepFragment;
    private W628RealTimeFragment w628RealTimeFragment;
    private W628HistoricalRecordFragment w628HistoricalRecordFragment;
    private FragmentManager fManager;

    @Override
    protected int contentViewID() {
        return R.layout.activity_result_device_wpo;
    }

    @Override
    protected void initialize() {
        setTopTitle(getResources().getString(R.string.wrist_pulse_oximeter), true);
        setLeftBtnFinish();
        setRightBtn(true, R.mipmap.setting, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("DeviceType","W628");
                startActivity(DeviceSettingWpoActivity.class, bundle);
            }
        });
        ActivityUtils.addActivity(this);
        fManager = getSupportFragmentManager();
        tvSleep.performLongClick();
        lineSleep.performClick();
    }


    //重置所有文本的选中状态
    private void setSelected(){
        tvSleep.setSelected(false);
        tvRealtime.setSelected(false);
        tvHistoricaltrend.setSelected(false);
        lineSleep.setSelected(false);
        lineRealTime.setSelected(false);
        lineHistoricalTrend.setSelected(false);
    }

    @OnClick({R.id.line_sleep, R.id.line_real_time, R.id.line_wpo_historical_trend})
    public void onClick(View view) {
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        switch (view.getId()){
            case R.id.line_sleep:
                setSelected();
                tvSleep.setSelected(true);
                lineSleep.setSelected(true);
                w628SleepFragment = new W628SleepFragment();
                fragmentTransaction.add(R.id.fragment_result_wpo, w628SleepFragment);
                break;
            case R.id.line_real_time:
                setSelected();
                tvRealtime.setSelected(true);
                lineRealTime.setSelected(true);
                w628RealTimeFragment = new W628RealTimeFragment();
                fragmentTransaction.add(R.id.fragment_result_wpo, w628RealTimeFragment);
                break;
            case R.id.line_wpo_historical_trend:
                setSelected();
                tvHistoricaltrend.setSelected(true);
                lineHistoricalTrend.setSelected(true);
                w628HistoricalRecordFragment = new W628HistoricalRecordFragment();
                fragmentTransaction.add(R.id.fragment_result_wpo, w628HistoricalRecordFragment);
                break;
            default:
        }
        List<Fragment> fragments = fManager.getFragments();
        for (Fragment fragment: fragments){
            fragmentTransaction.remove(fragment);
        }
        fragmentTransaction.commit();
    }
}
