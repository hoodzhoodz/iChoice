package com.choicemmed.wristpulselibrary.base;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.choicemmed.wristpulselibrary.utils.ErrorCode;
import static com.choicemmed.wristpulselibrary.utils.ErrorCode.ERROR_BLE_NOT_SUPPORTED;
import static com.choicemmed.wristpulselibrary.utils.ErrorCode.ERROR_BLUETOOTH_NOT_OPEN;
import static com.choicemmed.wristpulselibrary.utils.ErrorCode.ERROR_BLUETOOTH_NOT_SUPPORTED;

/**
 * @author Created by Jiang nan on 2020/1/11 10:57.
 * @description
 **/


@SuppressLint("NewApi")
public abstract class BaseBle extends ScanCallback implements GattListener {
    private static final String TAG = "BaseBle";
    private Context mContext;
    public BleListener mBleListener;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothDevice;
    public BluetoothGatt mBluetoothGatt;
    public BluetoothLeScanner mBluetoothLeScanner;
    private static final long BLE_CONNECT_TIMEOUT = 10000;
    private static final long SCAN_PERIOD = 20000;
    public boolean foundDevice = false;
    protected static final int MSG_STOP_SCAN = 0;
    protected static final int MSG_CONNECT_TIMEOUT = 1;

    protected Handler bleHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_STOP_SCAN:
                    stopLeScan();
                    break;
                case MSG_CONNECT_TIMEOUT:
                    mBleListener.onError(getDeviceType(), ErrorCode.ERROR_CONNECT_TIMEOUT);
                    closeGatt();
                    //resetGatt();
                    break;
                default:
                    break;
            }
        }
    };


    public BaseBle(Context context, BleListener bleListener) {
        mContext = context;
        mBleListener = bleListener;
        initBluetoothAdapter();
    }

    /**
     初始化蓝牙设备适配器
     */
    private void initBluetoothAdapter() {
        final BluetoothManager bluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        }
    }

    private void closeGatt() {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.close();
        }
        mBluetoothDevice = null;
    }


    private class TestBleResult {
        public boolean isAvailable = true;
        public int errorMsg;
    }

    private TestBleResult testBle() {
        TestBleResult result = new TestBleResult();

        if (mBluetoothAdapter == null) {
            result.isAvailable = false;
            result.errorMsg = ERROR_BLUETOOTH_NOT_SUPPORTED;
            return result;
        }

        if (!mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            result.isAvailable = false;
            result.errorMsg = ERROR_BLE_NOT_SUPPORTED;
            return result;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            result.isAvailable = false;
            result.errorMsg = ERROR_BLUETOOTH_NOT_OPEN;
            return result;
        }

        return result;
    }

    /**
     * 获取设备类型
     *
     * @return 设备类型
     */
    protected abstract DeviceType getDeviceType();

    /**
     * 撤销连接超时回调
     */
    public void cancelConnect() {
        this.bleHandler.removeMessages(MSG_CONNECT_TIMEOUT);
        this.closeGatt();
    }

    /**
     * 断开设备蓝牙连接
     */
    public void disconnect() {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
        }
        mBluetoothDevice = null;
    }


    public void startLeScan() {
        if (!testBleConfig()) {
            return;
        }
        Message msg = bleHandler.obtainMessage();
        msg.what = MSG_STOP_SCAN;
        bleHandler.sendMessageDelayed(msg, SCAN_PERIOD);
        foundDevice = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBluetoothLeScanner.startScan(this);
        }
    }

    @SuppressLint("NewApi")
    public void stopLeScan() {
        if (mBluetoothLeScanner == null){
            return;
        }
        mBluetoothLeScanner.stopScan(this);
        bleHandler.removeMessages(MSG_STOP_SCAN);
    }

    private boolean testBleConfig() {
        TestBleResult result = testBle();
        if (!result.isAvailable) {
            mBleListener.onError(getDeviceType(), result.errorMsg);
            return false;
        }
        return true;
    }

    public void connectDevice(String address) {
        if (!testBleConfig()) {
            return;
        }
        Log.d(TAG, "开始连接……");
        if (address == null) {
            Log.d(TAG, "参数错误：address为空");
            return;
        }
        if (mBluetoothAdapter == null) {
            Log.d(TAG, "BluetoothAdapter未初始化");
            return;
        }
        try {
            resetGatt();
            mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(address);
            Log.d(TAG, "开始连接……" + mBluetoothDevice);
            mBluetoothGatt = mBluetoothDevice.connectGatt(mContext, false, GetGattCallback());
//            Message msgConnectTimeout = bleHandler.obtainMessage(MSG_CONNECT_TIMEOUT);
//            bleHandler.sendMessageDelayed(msgConnectTimeout, BLE_CONNECT_TIMEOUT);
        } catch (Exception e) {
            Log.d(TAG, "连接出错");
            e.printStackTrace();
        }
    }

    /**
     * 获取BluetoothGattCallback
     *
     * @return BluetoothGattCallback
     */
    protected abstract BluetoothGattCallback GetGattCallback();

    /**
     * 发送命令
     *
     * @param cmd 指令
     */
    public abstract void sendCmd(String cmd);

    public void resetGatt() {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
        }
        mBluetoothDevice = null;
    }

    @Override
    public void onError(DeviceType deviceType, int errorMsg) {
        bleHandler.removeMessages(MSG_CONNECT_TIMEOUT);
        mBleListener.onError(deviceType, errorMsg);
        resetGatt();
    }

    @Override
    public void onDisconnected(DeviceType deviceType) {
        bleHandler.removeMessages(MSG_CONNECT_TIMEOUT);
        mBleListener.onDisconnected(deviceType);
        resetGatt();
    }

    @Override
    public void onInitialized(DeviceType deviceType) {
        bleHandler.removeMessages(MSG_CONNECT_TIMEOUT);
        mBleListener.onInitialized(deviceType);
    }

    @Override
    public void onCmdResponse(DeviceType deviceType, byte[] data) {
        mBleListener.onCmdResponse(deviceType, data);
    }

    @Override
    public void onDataResponse(DeviceType deviceType, byte[] data) {
        mBleListener.onDataResponse(deviceType, data);
    }

    @Override
    public void onConnect(DeviceType deviceType) {
        mBleListener.onConnected(deviceType);
    }

    @Override
    public void onBleConnectSuccess() {
        bleHandler.removeMessages(MSG_CONNECT_TIMEOUT);
    }

}
