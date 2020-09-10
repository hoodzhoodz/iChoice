package com.choicemmed.ichoice.healthcheck.activity.bloodpressure;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.utils.ActivityUtils;
import com.choicemmed.ichoice.healthcheck.service.BpBleConService;
import com.choicemmed.ichoice.healthcheck.fragment.bloodpressure.BpHistoricalResultFragment;
import com.choicemmed.ichoice.healthcheck.fragment.bloodpressure.BpHistoricalTrendFragment;
import com.choicemmed.ichoice.healthcheck.fragment.bloodpressure.BpMeasureFragment;
import com.choicemmed.ichoice.initalization.activity.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ResultBpActivity extends BaseActivty {

    @BindView(R.id.tv_measurement)
    TextView tv_measurement;
    @BindView(R.id.tv_historical_results)
    TextView tv_historical_results;
    @BindView(R.id.tv_historical_trend)
    TextView tv_historical_trend;

    @BindView(R.id.bpmeasurement)
    LinearLayout bpmeasurement;
    @BindView(R.id.historical_results)
    LinearLayout historical_results;
    @BindView(R.id.historical_trend)
    LinearLayout historical_trend;

    @BindView(R.id.fragment_result_bp)
    FrameLayout fragment_result;

    private BpMeasureFragment bpMeasureFragment;
    private BpHistoricalResultFragment bpHistoricalResultFragment;
    private BpHistoricalTrendFragment bpHistoricalTrendFragment;

    private FragmentManager fManager;
    private BpBleConService.ScanBleBinder binder;
    private ServiceConnection bleService = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (BpBleConService.ScanBleBinder) service;
            binder.startConnectBle();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected int contentViewID() {
        return R.layout.activity_result_bp;
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            bindService(new Intent(this, BpBleConService.class), bleService, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initialize() {
        ActivityUtils.addActivity(this);
        setTopTitle(getResources().getString(R.string.Blood_Pressure), true);
        setLeftBtnFinish();
        setRightBtn(true, R.mipmap.setting, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(DeviceSettingbpActivity.class);
            }
        });
        fManager = getSupportFragmentManager();
        tv_measurement.performLongClick();
        bpmeasurement.performClick();
    }

    //重置所有文本的选中状态
    private void setSelected(){
        tv_historical_results.setSelected(false);
        tv_measurement.setSelected(false);
        tv_historical_trend.setSelected(false);
        bpmeasurement.setSelected(false);
        historical_trend.setSelected(false);
        historical_results.setSelected(false);
    }
    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if (bpMeasureFragment != null){
            fragmentTransaction.hide(bpMeasureFragment);
        }
        if (bpHistoricalResultFragment != null){
            fragmentTransaction.hide(bpHistoricalResultFragment);
        }
        if (bpHistoricalTrendFragment != null){
            fragmentTransaction.hide(bpHistoricalTrendFragment);
        }
    }


    @OnClick({R.id.bpmeasurement, R.id.historical_results,R.id.historical_trend})
    public void onClick(View v) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (v.getId()){
            case R.id.bpmeasurement:
                setSelected();
                tv_measurement.setSelected(true);
                bpmeasurement.setSelected(true);
                if (bpMeasureFragment == null){
                    bpMeasureFragment = new BpMeasureFragment();
                    fTransaction.add(R.id.fragment_result_bp,bpMeasureFragment);
                }else {
                    fTransaction.show(bpMeasureFragment);
                }
                break;
            case R.id.historical_results:
                setSelected();
                tv_historical_results.setSelected(true);
                historical_results.setSelected(true);
                if (bpHistoricalResultFragment == null){
                    bpHistoricalResultFragment = new BpHistoricalResultFragment();
                    fTransaction.add(R.id.fragment_result_bp,bpHistoricalResultFragment);
                }else {
                    fTransaction.show(bpHistoricalResultFragment);
                }
                break;
            case R.id.historical_trend:
                setSelected();
                tv_historical_trend.setSelected(true);
                historical_trend.setSelected(true);
                if (bpHistoricalTrendFragment == null){
                    bpHistoricalTrendFragment = new BpHistoricalTrendFragment();
                    fTransaction.add(R.id.fragment_result_bp,bpHistoricalTrendFragment);
                }else {
                    fTransaction.show(bpHistoricalTrendFragment);
                }
                break;
                default:
        }
        fTransaction.commit();
    }




    @Override
    protected void onDestroy() {
        try {
            binder.close();
            binder.cancelConnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
        unbindService(bleService);
    }
}
