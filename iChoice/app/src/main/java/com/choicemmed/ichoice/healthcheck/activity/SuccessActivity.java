package com.choicemmed.ichoice.healthcheck.activity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.choicemmed.common.LogUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.utils.ActivityUtils;
import com.choicemmed.ichoice.healthcheck.activity.bloodpressure.ResultBpActivity;
import com.choicemmed.ichoice.healthcheck.activity.ecg.EcgCheckActivity;
import com.choicemmed.ichoice.healthcheck.activity.pulseoximeter.C208sMeasureActivity;
import com.choicemmed.ichoice.healthcheck.activity.pulseoximeter.ResultPOSpotCheckActivity;
import com.choicemmed.ichoice.healthcheck.activity.wristpulse.InfraredThermometerActivity;
import com.choicemmed.ichoice.healthcheck.activity.wristpulse.ResultWpoW314Activity;
import com.choicemmed.ichoice.healthcheck.activity.wristpulse.ResultWpoW628Activity;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.choicemmed.ichoice.healthcheck.fragment.pulseoximeter.OxWorkingModeDialogFragment;

import butterknife.OnClick;

public class SuccessActivity extends BaseActivty implements OxWorkingModeDialogFragment.Listerner {
    private OxWorkingModeDialogFragment dialogFragment;
    private String deviceType;
    private Bundle bundle;
    @Override
    protected int contentViewID() {
        return R.layout.activity_success;
    }

    @Override
    protected void initialize() {
        bundle = getIntent().getExtras();
        deviceType = bundle.getString(DevicesType.Device);
        switch (deviceType){
            case DevicesType.DEVICE_CBP1K1:
                setTopTitle(getString(R.string.Blood_Pressure), true);
                setLeftBtnToActivity(ResultBpActivity.class);
                break;
            case DevicesType.DEVICE_W314:
                setTopTitle(getString(R.string.wrist_pulse_oximeter), true);
                setLeftBtnToActivity(ResultWpoW314Activity.class);
                break;
            case DevicesType.DEVICE_W628:
                setTopTitle(getString(R.string.wrist_pulse_oximeter), true);
                setLeftBtnToActivity(ResultWpoW628Activity.class);
                break;
            case DevicesType.DEVICE_CFT308:
                setTopTitle(getString(R.string.infrared_temperature), true);
                setLeftBtnToActivity(InfraredThermometerActivity.class, bundle);
                break;
            case "OX200":
            case "MD300C208":
            case "MD300C228":
                setTopTitle(getString(R.string.pulse_oximeter), true);
                btnLeft.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_back));
                btnLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityUtils.removeAllExceptMain();
                        startActivity(ResultPOSpotCheckActivity.class, bundle);
                    }
                });

                break;

            case "MD300CI218":
            case "MD300CI218R":
            case "MD300C208S":
            case "MD300C228S":
            case "MD300I-G":
                setTopTitle(getString(R.string.pulse_oximeter), true);
                btnLeft.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_back));
                btnLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        change();
                    }
                });
                break;
            case DevicesType.A12_DEVICE_NAME:
            case DevicesType.P10_NEW_DEVICE_NAME:
            case DevicesType.P10_OLD_DEVICE_NAME:
                setTopTitle(getString(R.string.ecg), true);
                setLeftBtnToActivity(EcgCheckActivity.class, bundle);
                break;
            default:

        }

    }

    @OnClick({R.id.success})
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.success:
                switch (deviceType){
                    case DevicesType.DEVICE_CBP1K1:
                        startActivityFinish(ResultBpActivity.class);
                        break;
                    case DevicesType.DEVICE_W314:
                        startActivityFinish(ResultWpoW314Activity.class);
                        break;
                    case DevicesType.DEVICE_W628:
                        startActivityFinish(ResultWpoW628Activity.class);
                        break;
                    case DevicesType.DEVICE_CFT308:
                        startActivityFinish(InfraredThermometerActivity.class, bundle);
                        break;
                    case "OX200":
                    case "MD300C208":
                    case "MD300C228":
                        ActivityUtils.removeAllExceptMain();
                        startActivity(ResultPOSpotCheckActivity.class, bundle);
                        break;
                    case "MD300CI218":
                    case "MD300C208S":
                    case "MD300C228S":
                    case "MD300I-G":
                    case "MD300CI218R":
                        dialogFragment = new OxWorkingModeDialogFragment();
                        dialogFragment.setCancelable(false);
                        dialogFragment.setListerner(this);
                        dialogFragment.show(getSupportFragmentManager(), "DeviceSettingOXActivity");
                        break;
                    case DevicesType.A12_DEVICE_NAME:
                    case DevicesType.P10_NEW_DEVICE_NAME:
                    case DevicesType.P10_OLD_DEVICE_NAME:
                        ActivityUtils.removeAllExceptMain();
                        startActivity(EcgCheckActivity.class, bundle);
                        break;
//                        startActivityFinish(InfraredThermometerActivity.class, bundle);

                    default:
                }
                break;
                default:
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            return true;
        }else {
            return super.dispatchKeyEvent(event);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void change() {
        ActivityUtils.removeAllExceptMain();
        LogUtils.d("SuccessActivity", deviceType);
        startActivity(C208sMeasureActivity.class, bundle);
    }
}
