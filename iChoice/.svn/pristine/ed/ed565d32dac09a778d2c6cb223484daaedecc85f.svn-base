package com.choicemmed.blelibrary.base;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.choicemmed.blelibrary.R;

/**
 * Created by Yu Baoxiang on 2015/3/27.
 */
public abstract class BaseBle implements GattListener, BluetoothAdapter.LeScanCallback {
    protected static final String LogTag_BLE = "BLELog";

    private Context mContext;
    protected BleListener mBleListener;

    private DeviceType deviceType;
    private BluetoothAdapter mBluetoothAdapter;

    private BluetoothDevice mBluetoothDevice;
    protected BluetoothGatt mBluetoothGatt;

    private static final long BLE_CONNECT_TIMEOUT = 10000;

    private static final long SCAN_PERIOD = 20000;
    protected boolean foundDevice = false;

    protected static final int MSG_STOPSCAN = 0;
    protected static final int MSG_CONNECTTIMEOUT = 1;


    protected Handler bleHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_STOPSCAN:
                    stopLeScan();
                    break;
                case MSG_CONNECTTIMEOUT:
                    mBleListener.onError(getDeviceType(), mContext.getString(R.string.error_connect_timeout));
                    resetGatt();
                    break;
                default:
                    break;
            }
        }
    };

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public BaseBle(Context context, BleListener bleListener) {
        mContext = context;
        mBleListener = bleListener;
        initBluetoothAdapter();
    }

    private void initBluetoothAdapter() {
        final BluetoothManager bluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
    }

    private class TestBleResult {
        public boolean isAvailable = true;
        public String errorMsg;
    }

    private TestBleResult testBle() {
        TestBleResult result = new TestBleResult();

        if (mBluetoothAdapter == null) {
            result.isAvailable = false;
            result.errorMsg = mContext.getString(R.string.error_bluetooth_not_supported);
            return result;
        }

        if (!mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            result.isAvailable = false;
            result.errorMsg = mContext.getString(R.string.error_ble_not_supported);
            return result;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            result.isAvailable = false;
            result.errorMsg = mContext.getString(R.string.error_bluetooth_not_open);
            return result;
        }

        return result;
    }


    public void startLeScan() {
        TestBleResult result = testBle();
        if (!result.isAvailable) {
            mBleListener.onError(getDeviceType(), result.errorMsg);
            return;
        }

        Message msg = bleHandler.obtainMessage();
        msg.what = MSG_STOPSCAN;
        bleHandler.sendMessageDelayed(msg, SCAN_PERIOD);

        foundDevice = false;
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.startLeScan(this);
        }
    }

    public void stopLeScan() {
        mBluetoothAdapter.stopLeScan(this);
        if (!foundDevice) {
            mBleListener.onScanTimeout(getDeviceType());
        }
    }

    public void connectDevice(String address) {
        Log.d(LogTag_BLE, "开始连接……" + address);
        if (address == null) {
            Log.d(LogTag_BLE, "参数错误：address为空");
            return;
        }
        if (mBluetoothAdapter == null) {
            Log.d(LogTag_BLE, "BluetoothAdapter未初始化");
            return;
        }
        try {
            resetGatt();
            mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(address);
            mBluetoothGatt = mBluetoothDevice.connectGatt(mContext, false, GetGattCallback());

            Message msgConnectTimeout = bleHandler.obtainMessage(MSG_CONNECTTIMEOUT);
            bleHandler.sendMessageDelayed(msgConnectTimeout, BLE_CONNECT_TIMEOUT);
        } catch (Exception e) {
            Log.d(LogTag_BLE, "连接出错");
            e.printStackTrace();
        }
    }

    protected abstract BluetoothGattCallback GetGattCallback();

    public abstract void sendCmd(String cmd);

    public void resetGatt() {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
            mBluetoothGatt.close();
        }
        mBluetoothDevice = null;
    }

    @Override
    public void onError(DeviceType deviceType, String errorMsg) {
        //mBleListener.onError(deviceType, errorMsg);
        mBleListener.onError(deviceType, mContext.getString(R.string.error_device_exception) + "1");
        resetGatt();
    }

    @Override
    public void onDisconnected(DeviceType deviceType) {
        mBleListener.onDisconnected(deviceType);
        resetGatt();
    }

    @Override
    public void onInitialized(DeviceType deviceType) {
        mBleListener.onInitialized(deviceType);
        bleHandler.removeMessages(MSG_CONNECTTIMEOUT);
    }

    @Override
    public void onCmdResponse(DeviceType deviceType, byte[] data) {
        mBleListener.onCmdResponse(deviceType, data);
    }
}
