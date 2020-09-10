package com.choicemmed.ichoice.healthcheck.activity.wristpulse;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.choicemmed.common.FormatUtils;
import com.choicemmed.common.LogUtils;
import com.choicemmed.common.StringUtils;
import com.choicemmed.common.UuidUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.base.BaseActivty;
import com.choicemmed.ichoice.healthcheck.activity.FailureActivity;
import com.choicemmed.ichoice.healthcheck.activity.SuccessActivity;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.choicemmed.ichoice.healthcheck.presenter.SaveDeviceInfoPresenter;
import com.choicemmed.ichoice.healthcheck.model.BleConnectInvoker;
import com.choicemmed.ichoice.healthcheck.model.BleConnectListener;
import com.choicemmed.ichoice.healthcheck.view.ISaveDeviceInfoView;

import java.util.Date;

import pro.choicemmed.datalib.DeviceDisplay;
import pro.choicemmed.datalib.DeviceInfo;

import static com.choicemmed.ichoice.framework.utils.DevicesType.A12_DEVICE_NAME;
import static com.choicemmed.ichoice.framework.utils.DevicesType.P10_NEW_DEVICE_NAME;
import static com.choicemmed.ichoice.framework.utils.DevicesType.P10_OLD_DEVICE_NAME;

public class SearchDeviceActivity extends BaseActivty implements BleConnectListener, ISaveDeviceInfoView {
    public static final String TAG = "SearchDeviceActivity";
    private int deviceType;
    private String typeName;
    private String deviceName;
    private SaveDeviceInfoPresenter mPresenter;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private final int REQUESTCODE_BLE = 1;
    private BleConnectInvoker bleConnectInvoker;

    @Override
    protected int contentViewID() {
        return R.layout.activity_search_device;
    }

    @Override
    protected void initialize() {
        mPresenter = new SaveDeviceInfoPresenter(this, this);
        deviceName = getIntent().getExtras().getString(DevicesType.Device);
        setLeftBtnFinish();
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }
        String deviceDetailName = "";
        if (!StringUtils.isEmpty(deviceName) && deviceName.equals("CFT308")) {
            deviceType = DevicesType.Thermometer;
            setTopTitle(getString(R.string.infrared_temperature), true);
            deviceDetailName = "Bluetooth BP";


        }
        if (!StringUtils.isEmpty(deviceName) && (deviceName.equals(A12_DEVICE_NAME)
                || deviceName.equals(P10_NEW_DEVICE_NAME) || deviceName.equals(P10_OLD_DEVICE_NAME))) {
            deviceDetailName = getIntent().getExtras().getString(DevicesType.Device);
            deviceType = DevicesType.Ecg;
            setTopTitle(getString(R.string.ecg), true);
        }

        LogUtils.d(TAG, "deviceDetailName: " + deviceDetailName);
        bleConnectInvoker = new BleConnectInvoker(this, this, deviceType, deviceDetailName);
        if (hasPermission()) {
            bleConnectInvoker.bindDevice();
        }
    }

    private boolean hasPermission() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUESTCODE_BLE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUESTCODE_BLE) {
            bleConnectInvoker.bindDevice();
        }
    }


    @Override
    public void onBindDeviceSuccess(BluetoothDevice mDevice) {
        saveData(mDevice);
        Bundle bundle = new Bundle();
        bundle.putString(DevicesType.Device, deviceName);
        startActivityFinish(SuccessActivity.class, bundle);
    }

    private void saveData(BluetoothDevice mDevice) {
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setUserId(IchoiceApplication.getAppData().userProfileInfo.getUserId() + "");
        deviceInfo.setCreateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
        deviceInfo.setLastUpdateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
        deviceInfo.setDeviceType(deviceType);
        if (deviceType == DevicesType.Thermometer) {
            typeName = getString(R.string.therometer) + " CFT-308";
        } else if (deviceType == DevicesType.Ecg) {
            typeName = getString(R.string.ecg) + " " + mDevice.getName();
        }
        deviceInfo.setTypeName(typeName);
        deviceInfo.setBluetoothId(mDevice.getAddress());
        deviceInfo.setDeviceName(mDevice.getName());
        mPresenter.callModelSaveDeviceInfo(deviceInfo);
        LogUtils.d(TAG, "deviceInfo:" + deviceInfo.toString());
    }

    @Override
    public void onError(String message) {
        toFailureActivity(message);
    }

    @Override
    public void onConnectedDeviceSuccess() {

    }

    @Override
    public void onDataResponse(String map) {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    private void toFailureActivity(String message) {
        Bundle bundle = new Bundle();
        bundle.putString(DevicesType.Device, deviceName);
        bundle.putString("Message", message);

        startActivityFinish(FailureActivity.class, bundle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bleConnectInvoker != null) {
            bleConnectInvoker.stopLeScan();
        }

    }

    @Override
    public void saveDeviceInfoFinish() {
        DeviceDisplay deviceDisplay = new DeviceDisplay();
        deviceDisplay.setUserId(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        deviceDisplay.setCreateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
        deviceDisplay.setLastUpdateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
        deviceDisplay.setID(UuidUtils.getUuid());
        if (deviceType == DevicesType.Thermometer) {
            deviceDisplay.setThermometer(1);
        } else if (deviceType == DevicesType.Ecg) {
            deviceDisplay.setEcg(1);
        }
        mPresenter.callModelSaveBindDeviceInfo(deviceDisplay, deviceType);
        IchoiceApplication.getAppData().deviceDisplay = deviceDisplay;
    }

    @Override
    public void saveOrUpdateDeviceDisplayFinish() {

    }
}
