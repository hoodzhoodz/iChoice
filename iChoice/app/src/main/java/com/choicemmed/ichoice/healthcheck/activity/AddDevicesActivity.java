package com.choicemmed.ichoice.healthcheck.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.choicemmed.ichoice.framework.widget.MyCenterPopupView;
import com.choicemmed.ichoice.healthcheck.activity.wristpulse.DevicesSelectActivity;
import com.choicemmed.ichoice.healthcheck.adddevice.activity.DeviceSelectActivity;


import com.lxj.xpopup.XPopup;

import butterknife.OnClick;

import static com.choicemmed.ichoice.framework.utils.DevicesType.Device;

/**
 * 项目名称：iChoice
 * 类描述：选择设备界面
 * 创建人：114100
 * 创建时间：2019/4/8 11:22
 * 修改人：114100
 * 修改时间：2019/4/8 11:22
 * 修改备注：
 */
public class AddDevicesActivity extends BaseActivty {
    private static final int SYSTEM_WINDOW_PERMISSIONS_REQUEST_CODE = 0x01;
    @Override
    protected int contentViewID() {
        return R.layout.activity_add_devices;
    }

    @Override
    protected void initialize() {
        setTopTitle(getString(R.string.add_devices_title), true);
        setLeftBtnFinish();
        setRightBtn(true, R.drawable.ic_search, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(SearchDevicesActivity.class);
                IchoiceApplication.singleDialog(getString(R.string.stay), mContext);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestAlertWindowPermission();

    }


    private void requestAlertWindowPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(mContext)) {
            return;
        }
//        ToastUtils.showCustom(this,getString(R.string.tip_permission));
        MyCenterPopupView centerPopupView = new MyCenterPopupView(this);
        XPopup.get(this).asCustom(centerPopupView).show();
        centerPopupView.setSinglePopup("", getString(R.string.tip_permission)
                , getResources().getString(R.string.ok)
                , new MyCenterPopupView.PositiveClickListener() {
                    @Override
                    public void onPositiveClick() {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                        intent.setData(Uri.parse("package:com.choicemmed.ichoice"));
                        startActivityForResult(intent, SYSTEM_WINDOW_PERMISSIONS_REQUEST_CODE);
                    }
                });
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SYSTEM_WINDOW_PERMISSIONS_REQUEST_CODE) {
            if (!Settings.canDrawOverlays(mContext)) {
//                ToastUtils.showCustom(mContext, getString(R.string.need_system_window));
            }
        }
    }


    @OnClick({R.id.rl_add_devices_blood,R.id.rl_add_devices_ecg,R.id.rl_add_devices_pulse_oximeter,R.id.rl_add_devices_sleep_monitor,
    R.id.rl_add_devices_thermometer,R.id.rl_add_devices_scale,R.id.rl_add_devices_fitness_tracker})
    public void onClick(View v){
        Bundle bundle = new Bundle();
        switch (v.getId()){
            case R.id.rl_add_devices_blood:
                bundle.putInt(Device, DevicesType.BloodPressure);
                startActivityFinish(DevicesSelectActivity.class, bundle);
                break;
            case R.id.rl_add_devices_ecg:
                bundle.putInt(Device, DevicesType.Ecg);
                startActivityFinish(DevicesSelectActivity.class, bundle);
                break;
            case R.id.rl_add_devices_pulse_oximeter:
//                IchoiceApplication.singleDialog(DevicesSelectActivity.class,mContext);
                bundle.putInt(Device, DevicesType.PulseOximeter);
                startActivityFinish(DeviceSelectActivity.class, bundle);
                break;
            case R.id.rl_add_devices_sleep_monitor:
                bundle.putInt(Device, DevicesType.WristPluseOximeter);
                startActivityFinish(DevicesSelectActivity.class, bundle);
                break;
            case R.id.rl_add_devices_thermometer:
                bundle.putInt(Device, DevicesType.Thermometer);
                startActivityFinish(DevicesSelectActivity.class, bundle);
                break;
            case R.id.rl_add_devices_scale:
                IchoiceApplication.singleDialog(getString(R.string.stay), mContext);
                break;
            case R.id.rl_add_devices_fitness_tracker:
                IchoiceApplication.singleDialog(getString(R.string.stay), mContext);
                break;
                default:
        }

    }


}
