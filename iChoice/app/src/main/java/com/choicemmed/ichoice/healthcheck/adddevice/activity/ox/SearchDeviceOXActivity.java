package com.choicemmed.ichoice.healthcheck.adddevice.activity.ox;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;

import com.choice.c208sdkblelibrary.base.DeviceType;
import com.choice.c208sdkblelibrary.cmd.callback.C208BindDeviceCallback;
import com.choice.c208sdkblelibrary.cmd.invoker.C208Invoker;
import com.choice.c208sdkblelibrary.cmd.invoker.C208sInvoker;
import com.choice.c208sdkblelibrary.cmd.invoker.C218RInvoker;
import com.choice.c208sdkblelibrary.device.C208;
import com.choicemmed.common.FormatUtils;
import com.choicemmed.common.LogUtils;
import com.choicemmed.common.UuidUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.choicemmed.ichoice.healthcheck.activity.FailureActivity;
import com.choicemmed.ichoice.healthcheck.activity.SuccessActivity;
import com.choicemmed.ichoice.healthcheck.presenter.SaveDeviceInfoPresenter;
import com.choicemmed.ichoice.healthcheck.view.ISaveDeviceInfoView;

import java.util.Date;

import pro.choicemmed.datalib.DeviceDisplay;
import pro.choicemmed.datalib.DeviceInfo;


public class SearchDeviceOXActivity extends BaseActivty implements ISaveDeviceInfoView, C208BindDeviceCallback {
    private static final String TAG = "SearchDeviceOXSActivity";
    private static final int REQUEST_LOCATION_PERMISSION_CODE = 0x01;
    private C208Invoker c208Invoker;
    private C208sInvoker c208sInvoker;
    private C218RInvoker c218RInvoker;
    private String address = "";
    private C208 c208Device;
    private int deviceType = 1;
    private String deviceName = "";
    private SaveDeviceInfoPresenter mSaveDeviceInfoPresenter;
    private Handler timeHanlder;
    private Bundle mBundle;

    @Override
    protected int contentViewID() {
        return R.layout.activity_search_device_ox_standard;
    }

    @Override
    protected void initialize() {
        setTopTitle(getResources().getString(R.string.pulse_oximeter), true);
        setLeftBtnFinish();

        ImageView imageView = findViewById(R.id.activity_search_device_ox_standard_imageView);
//        Intent intent = getIntent();
//        DeviceIconvo deviceIconvo = (DeviceIconvo) intent.getSerializableExtra("deviceIconvo");
//        imageView.setImageResource(deviceIconvo.getIconImagePositive());
//        this.deviceName = deviceIconvo.getIconName();
        mBundle = getIntent().getExtras();
        deviceName = mBundle.getString(DevicesType.Device);
        switch (deviceName) {
            case "OX200":
                imageView.setImageResource(R.mipmap.add_device_pulse_oximeter_ox200_positive);
                break;
            case "MD300C208":
                imageView.setImageResource(R.mipmap.add_device_pulse_oximeter_md300c208_positive);
                break;
            case "MD300C228":
                imageView.setImageResource(R.mipmap.add_device_pulse_oximeter_md300c228_positive);
                break;
            case "MD300CI218":
            case "MD300CI218R":
                imageView.setImageResource(R.mipmap.add_device_pulse_oximeter_md300ci218_positive);
                break;
            case "MD300C208S":
                imageView.setImageResource(R.mipmap.add_device_pulse_oximeter_md300c208s_positive);
                break;
            case "MD300C228S":
                imageView.setImageResource(R.mipmap.add_device_pulse_oximeter_md300c228s_positive);
                break;
            case "MD300I-G":
                imageView.setImageResource(R.mipmap.add_device_pulse_oximeter_md300i_g_positive);
                break;
            default:
        }

        autoRequestPermission();
        mSaveDeviceInfoPresenter = new SaveDeviceInfoPresenter(this, (ISaveDeviceInfoView) this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        timeHanlder = new Handler();
        timeHanlder.postDelayed(new Runnable() {
            @Override
            public void run() {
                toFailureActivity();
            }
        }, 11000);
    }

    private void toFailureActivity() {
        Bundle bundle = new Bundle();
        bundle.putString(DevicesType.Device, deviceName);
        startActivityFinish(FailureActivity.class, bundle);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION_CODE:
                //申请权限成功连接设备
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    bindDevice(deviceName);
                }
                break;
            default:
        }
    }

    /**
     * 动态申请位置信息权限
     */
    private void autoRequestPermission() {

        //sdk版本低于23无需申请位置信息权限
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            bindDevice(deviceName);
            return;
        }
        //判断是否有位置信息权限，有-不处理，无-申请
        if (ContextCompat.checkSelfPermission(this
                , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION_CODE);
            return;
        }
        bindDevice(deviceName);
    }

    private void bindDevice(String deviceName) {
        switch (deviceName) {
            case "MD300CI218":
            case "MD300C208S":
            case "MD300C228S":
            case "MD300I-G":
                c208sInvoker = new C208sInvoker(this);
                c208sInvoker.bindDevice(this);
                break;
            case "MD300CI218R":
                c218RInvoker = new C218RInvoker(this);
                c218RInvoker.bindDevice(this);
                break;
            case "OX200":
            case "MD300C208":
            case "MD300C228":
                c208Invoker = new C208Invoker(this);
                c208Invoker.bindDevice(this);
                break;
        }
    }
    private void saveC208Device() {
        deviceType = DevicesType.PulseOximeter;
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setUserId(IchoiceApplication.getAppData().userProfileInfo.getUserId() + "");
        deviceInfo.setCreateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
        deviceInfo.setLastUpdateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
        deviceInfo.setDeviceType(deviceType);
        deviceInfo.setTypeName(getString(R.string.pulse_qximeter) + " " + deviceName);
        deviceInfo.setBluetoothId(c208Device.getMacAddress());
        deviceInfo.setDeviceName(deviceName);
        mSaveDeviceInfoPresenter.callModelSaveDeviceInfo(deviceInfo);
        LogUtils.d(TAG, "deviceInfo:" + deviceInfo.toString());

    }

    @Override
    public void saveDeviceInfoFinish() {
        DeviceDisplay deviceDisplay = new DeviceDisplay();
        deviceDisplay.setUserId(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        deviceDisplay.setCreateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
        deviceDisplay.setLastUpdateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
        deviceDisplay.setID(UuidUtils.getUuid());
        deviceDisplay.setPulseOximeter(1);
        mSaveDeviceInfoPresenter.callModelSaveBindDeviceInfo(deviceDisplay, deviceType);
        IchoiceApplication.getAppData().deviceDisplay = deviceDisplay;
    }

    @Override
    public void saveOrUpdateDeviceDisplayFinish() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (c208sInvoker != null) {
            c208sInvoker.stopScan();
        } else if (c208Invoker != null) {
            c208Invoker.stopScan();
        } else if (c218RInvoker != null) {
            c218RInvoker.stopScan();
        }
        if (timeHanlder != null) {
            timeHanlder.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onFoundDevice(DeviceType deviceType, C208 c208) {
        Log.d(TAG, "onFoundDevice--: 发现设备" + c208.toString());
        address = c208.getMacAddress();
        c208Device = c208;
        saveC208Device();
//                connectDevice();
        Bundle bundle = new Bundle();
        bundle.putString(DevicesType.Device, SearchDeviceOXActivity.this.deviceName);
        startActivityFinish(SuccessActivity.class, bundle);
    }

    @Override
    public void onScanTimeout(DeviceType deviceType) {
        Log.d(TAG, "onScanTimeout: 绑定超时");
        //跳转失败重试界面
        toFailureActivity();
    }

    @Override
    public void onError(DeviceType deviceType, int errorMsg) {
        Log.d(TAG, "onError: error:" + errorMsg);
        //跳转失败重试界面
        toFailureActivity();
    }
}
