package com.choicemmed.ichoice.healthcheck.activity.wristpulse;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.choicemmed.cbp1k1sdkblelibrary.utils.ErrorCode;
import com.choicemmed.common.FormatUtils;
import com.choicemmed.common.LogUtils;
import com.choicemmed.common.UuidUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.healthcheck.activity.FailureActivity;
import com.choicemmed.ichoice.healthcheck.activity.SuccessActivity;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.choicemmed.ichoice.healthcheck.presenter.SaveDeviceInfoPresenter;
import com.choicemmed.ichoice.healthcheck.view.ISaveDeviceInfoView;
import com.choicemmed.wristpulselibrary.cmd.invoker.W628Invoker;
import com.choicemmed.wristpulselibrary.cmd.listener.W628Listener;
import com.choicemmed.wristpulselibrary.entity.Device;

import java.util.Date;

import pro.choicemmed.datalib.DeviceDisplay;
import pro.choicemmed.datalib.DeviceInfo;

public class SearchDeviceW628Activity extends BaseActivty implements W628Listener , ISaveDeviceInfoView {
    public static final String TAG = "SearchDeviceW628Activity";
    private Handler timeHanlder;
    private SaveDeviceInfoPresenter mPresenter;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private final int REQUESTCODE_BLE = 1;
    private W628Invoker w628Invoker;

    @Override
    protected int contentViewID() {
        return R.layout.activity_search_device_w628;
    }

    @Override
    protected void initialize() {
        setTopTitle(getString(R.string.wrist_pulse_oximeter),true);
        setLeftBtnFinish();
        w628Invoker = new W628Invoker(this, this);
        mPresenter = new SaveDeviceInfoPresenter(this, this);
        if (hasPermission()) {
            w628Invoker.bindDevice();
        }
    }

    private boolean hasPermission() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUESTCODE_BLE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUESTCODE_BLE) {
            w628Invoker.bindDevice();
        }
    }

    @Override
    public void onBindDeviceSuccess(Device mDevice) {
        saveW628(mDevice);
        Bundle bundle = new Bundle();
        bundle.putString(DevicesType.Device, DevicesType.DEVICE_W628);
        startActivityFinish(SuccessActivity.class, bundle);
    }

    private void saveW628(Device mDevice) {
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setUserId(IchoiceApplication.getAppData().userProfileInfo.getUserId() + "");
        deviceInfo.setCreateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
        deviceInfo.setLastUpdateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
        deviceInfo.setDeviceType(DevicesType.WristPluseOximeter);
        deviceInfo.setTypeName(getString(R.string.wrist_pulse_oximeter) + " " + getString(R.string.w628));
        deviceInfo.setBluetoothId(mDevice.getDeviceMacAddress());
        deviceInfo.setDeviceName(mDevice.getDeviceName());
        mPresenter.callModelSaveDeviceInfo(deviceInfo);
        LogUtils.d(TAG, "deviceInfo:" + deviceInfo.toString());
    }

    @Override
    public void onBindDeviceFail(int message) {
        toFailureActivity();
    }

    @Override
    public void onError(int message) {
        switch (message){
            //未打开蓝牙
            case ErrorCode.ERROR_BLUETOOTH_NOT_OPEN:
                openBluetoothBle();
                break;
            //连接超时
            case ErrorCode.ERROR_CONNECT_TIMEOUT:
                //gatt异常
            case ErrorCode.ERROR_GATT_EXCEPTION:
                toFailureActivity();
                break;
            //下位机主动断开连接
            case ErrorCode.ERROR_BLE_DISCONNECT:

                break;
            default:
        }
    }

    private void openBluetoothBle() {
        BluetoothManager bluetoothManager = (BluetoothManager) this.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
            LogUtils.d(TAG, "检测到蓝牙未打开：重新开启蓝牙！");
            bluetoothAdapter.enable();//不弹对话框直接开启蓝牙
        }
    }

    @Override
    public void onConnectedDeviceSuccess() {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onStateChanged(int bleState, int state) {

    }

    @Override
    public void onRecordDataResponse(String recordData, String recordTime, long timeDiff) {

    }

    @Override
    public void onRealTimeSpoData(int realTimeSpoData) {

    }

    @Override
    public void onRealTimePRData(int realTimePRData) {

    }

    @Override
    public void onRealTimePIData(float realTimePIDate) {

    }


    @Override
    public void onRealTimeRRData(int realTimeRRDate) {

    }

    @Override
    public void exitSuccess() {

    }

    @Override
    public void onRealTimeNone() {

    }

    @Override
    public void onRealTimeWaveData(Float realTimeWaveData) {

    }

    @Override
    public void saveDeviceInfoFinish() {
        DeviceDisplay deviceDisplay = new DeviceDisplay();
        deviceDisplay.setUserId(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        deviceDisplay.setCreateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
        deviceDisplay.setLastUpdateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
        deviceDisplay.setID(UuidUtils.getUuid());
        deviceDisplay.setWristPluseOximeter(1);
        mPresenter.callModelSaveBindDeviceInfo(deviceDisplay, DevicesType.WristPluseOximeter);
        IchoiceApplication.getAppData().deviceDisplay = deviceDisplay;
    }

    @Override
    public void saveOrUpdateDeviceDisplayFinish() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        timeHanlder = new Handler();
        timeHanlder.postDelayed(new Runnable() {
            @Override
            public void run() {
                w628Invoker.stopScan();
                toFailureActivity();
            }
        },11000);
    }


    private void toFailureActivity() {
        Bundle bundle = new Bundle();
        bundle.putString(DevicesType.Device, DevicesType.DEVICE_W628);
        startActivityFinish(FailureActivity.class, bundle);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeHanlder != null){
            timeHanlder.removeCallbacksAndMessages(null);
        }
        w628Invoker.stopScan();
    }
}
