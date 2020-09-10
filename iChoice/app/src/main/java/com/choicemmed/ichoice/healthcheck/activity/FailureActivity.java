package com.choicemmed.ichoice.healthcheck.activity;



import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.healthcheck.activity.bloodpressure.SearchDeviceCbp1k1Activity;
import com.choicemmed.ichoice.healthcheck.activity.wristpulse.SearchDeviceActivity;
import com.choicemmed.ichoice.healthcheck.activity.wristpulse.SearchDeviceW314b4Activity;
import com.choicemmed.ichoice.healthcheck.activity.wristpulse.SearchDeviceW628Activity;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.choicemmed.ichoice.initalization.activity.MainActivity;
import com.choicemmed.ichoice.healthcheck.adddevice.activity.ox.SearchDeviceOXActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class FailureActivity extends BaseActivty {
    @BindView(R.id.tv_fail)
    TextView tv_fail;
    private String deviceType;
    private Bundle bundle;
    @Override
    protected int contentViewID() {
        return R.layout.activity_failure;
    }

    @Override
    protected void initialize() {
        bundle = getIntent().getExtras();
        deviceType = bundle.getString(DevicesType.Device);
        switch (deviceType){
            case DevicesType.DEVICE_CBP1K1:
                tv_fail.setText(R.string.connect_failure_blood_pressure);
                setTopTitle(getString(R.string.Blood_Pressure), true);
                setLeftBtnToActivity(MainActivity.class);
                break;
            case DevicesType.DEVICE_W314:
            case DevicesType.DEVICE_W628:
                tv_fail.setText(R.string.connect_failure_wrist_pulse_oximeter);
                setTopTitle(getString(R.string.wrist_pulse_oximeter), true);
                setLeftBtnToActivity(MainActivity.class);
                break;
            case DevicesType.DEVICE_CFT308:
                setTopTitle(getString(R.string.infrared_temperature), true);
                setLeftBtnToActivity(ConnectDeviceActivity.class, bundle);
                break;
            case DevicesType.A12_DEVICE_NAME:
            case DevicesType.P10_NEW_DEVICE_NAME:
            case DevicesType.P10_OLD_DEVICE_NAME:
                setTopTitle(getString(R.string.ecg), true);
                setLeftBtnToActivity(ConnectDeviceActivity.class, bundle);
                break;
            case "OX200":
            case "MD300C208":
            case "MD300C228":
            case "MD300CI218":
            case "MD300C208S":
            case "MD300C228S":
            case "MD300I-G":
            case "MD300CI218R":
                tv_fail.setText(R.string.connect_failure_pulse_oximeter);
                setTopTitle(getString(R.string.pulse_oximeter), true);
                setLeftBtnToActivity(SearchDeviceOXActivity.class, bundle);
//                setLeftBtnToActivity(MainActivity.class);
                break;
            default:
        }
    }

    @OnClick(R.id.btn_connect)
    public void onClick(View view) {
        switch (deviceType){
            case DevicesType.DEVICE_CBP1K1:
                startActivity(SearchDeviceCbp1k1Activity.class);
                break;
            case DevicesType.DEVICE_W314:
                startActivity(SearchDeviceW314b4Activity.class);
                break;
            case DevicesType.DEVICE_W628:
                startActivity(SearchDeviceW628Activity.class);
                break;
            case DevicesType.DEVICE_CFT308:
            case DevicesType.A12_DEVICE_NAME:
            case DevicesType.P10_NEW_DEVICE_NAME:
            case DevicesType.P10_OLD_DEVICE_NAME:
                startActivityFinish(SearchDeviceActivity.class, bundle);
                break;
            case "OX200":
            case "MD300C208":
            case "MD300C228":
            case "MD300CI218":
            case "MD300CI218R":
            case "MD300C208S":
            case "MD300C228S":
            case "MD300I-G":
                startActivityFinish(SearchDeviceOXActivity.class, bundle);
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
