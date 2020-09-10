package com.choicemmed.ichoice.healthcheck.activity.wristpulse;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.healthcheck.activity.ConnectDeviceActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.choicemmed.ichoice.framework.utils.DevicesType.A12_DEVICE_NAME;
import static com.choicemmed.ichoice.framework.utils.DevicesType.BloodPressure;
import static com.choicemmed.ichoice.framework.utils.DevicesType.Device;
import static com.choicemmed.ichoice.framework.utils.DevicesType.Ecg;

import static com.choicemmed.ichoice.framework.utils.DevicesType.P10_NEW_DEVICE_NAME;
import static com.choicemmed.ichoice.framework.utils.DevicesType.P10_OLD_DEVICE_NAME;
import static com.choicemmed.ichoice.framework.utils.DevicesType.Thermometer;
import static com.choicemmed.ichoice.framework.utils.DevicesType.WristPluseOximeter;


public class DevicesSelectActivity extends BaseActivty {
    @BindView(R.id.rl_wrist_pluse_oximeter)
    RelativeLayout rl_wrist_pluse_oximeter;
    @BindView(R.id.rl_temperature)
    RelativeLayout rl_temperature;
    @BindView(R.id.rl_bp)
    RelativeLayout rl_bp;
    @BindView(R.id.ll_ecg)
    LinearLayout ll_ecg;

    @Override
    protected int contentViewID() {
        return R.layout.activity_devices_select_wpo;
    }

    @Override
    protected void initialize() {
        if (getIntent().getExtras().getInt(Device) == WristPluseOximeter) {
            rl_wrist_pluse_oximeter.setVisibility(View.VISIBLE);
            setTopTitle(getString(R.string.wrist_pulse_oximeter), true);
        } else if (getIntent().getExtras().getInt(Device) == Thermometer) {
            rl_temperature.setVisibility(View.VISIBLE);
            setTopTitle(getString(R.string.temperature), true);
        } else if (getIntent().getExtras().getInt(Device) == BloodPressure) {
            rl_bp.setVisibility(View.VISIBLE);
            setTopTitle(getResources().getString(R.string.Blood_Pressure), true);
        } else if (getIntent().getExtras().getInt(Device) == Ecg) {
            ll_ecg.setVisibility(View.VISIBLE);
            setTopTitle(getResources().getString(R.string.ecg), true);
        }
        setLeftBtnFinish();
    }

    @OnClick({R.id.w314b4, R.id.w628, R.id.fl_temperature, R.id.cbp111, R.id.cbp1K1, R.id.ecg_a12, R.id.ecg_a12_new, R.id.ecg_p10, R.id.ecg_p10_new})
    public void onClick(View v){
        Bundle bundle = new Bundle();
        switch (v.getId()){
            case R.id.w314b4:
                bundle.putString(Device, "W314B4");
                startActivityFinish(ConnectDeviceActivity.class, bundle);
                break;
            case R.id.w628:
                bundle.putString(Device, "W628");
                startActivityFinish(ConnectDeviceActivity.class, bundle);
                break;
            case R.id.fl_temperature:
                bundle.putString(Device, "CFT308");
                startActivityFinish(ConnectDeviceActivity.class, bundle);
                break;
            case R.id.cbp1K1:
                bundle.putString(Device, "bp");
                startActivityFinish(ConnectDeviceActivity.class, bundle);
                break;
            case R.id.cbp111:
                IchoiceApplication.singleDialog(getString(R.string.stay), mContext);
                break;
            case R.id.ecg_a12:
            case R.id.ecg_a12_new:
                bundle.putString(Device, A12_DEVICE_NAME);
                startActivityFinish(ConnectDeviceActivity.class, bundle);
                break;
            case R.id.ecg_p10:
                bundle.putString(Device, P10_OLD_DEVICE_NAME);
                startActivityFinish(ConnectDeviceActivity.class, bundle);
                break;
            case R.id.ecg_p10_new:
                bundle.putString(Device, P10_NEW_DEVICE_NAME);
                startActivityFinish(ConnectDeviceActivity.class, bundle);
                break;
            default:
        }

    }
}
