package com.choicemmed.ichoice.healthcheck.model;


import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.choicemmed.common.BleUtils;
import com.choicemmed.common.LogUtils;
import com.choicemmed.common.StringUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.choicemmed.wristpulselibrary.utils.BleScanThreadUtils;

import java.util.List;

import static com.choicemmed.ichoice.framework.utils.DevicesType.A12_DEVICE_NAME;
import static com.choicemmed.ichoice.framework.utils.DevicesType.P10_NEW_DEVICE_NAME;
import static com.choicemmed.ichoice.framework.utils.DevicesType.P10_OLD_DEVICE_NAME;


@SuppressLint("NewApi")
public class BleConnectInvoker extends ScanCallback {
    private static String TAG = "BleConnectInvoker";
    private static final long SCAN_PERIOD = 20000;
    public boolean foundDevice = false;
    protected static final int MSG_STOP_SCAN = 0;
    protected static final int MSG_CONNECT_TIMEOUT = 1;
    private int deviceType;
    private String deviceDeatailName = "";
    private String deviceDeatailName1;
    private Context context;
    private BleConnectListener bleConnectListener;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private String ECG_DEVICE_UUID = "0000FEE900001000800000805F9B34FB";


    protected Handler bleHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_STOP_SCAN:
                    stopLeScan();
                    bleConnectListener.onError(context.getResources().getString(R.string.ble_connect_timeout));
                    break;
                default:
                    break;
            }
        }
    };

    public BleConnectInvoker(BleConnectListener bleConnectListener, Context context, int deviceType, String deviceDeatailName) {
        this.deviceDeatailName = deviceDeatailName;
        this.deviceType = deviceType;
        this.context = context;
        this.bleConnectListener = bleConnectListener;
        if (deviceDeatailName.equals(P10_OLD_DEVICE_NAME)) {
            ECG_DEVICE_UUID = "FF00";
        }
        LogUtils.d(TAG, "deviceDeatailName  " + this.deviceDeatailName + "  ECG_DEVICE_UUID  " + ECG_DEVICE_UUID);
    }


    @Override
    public void onScanResult(int callbackType, final ScanResult result) {
        super.onScanResult(callbackType, result);
        BleScanThreadUtils.getInstance().execute(new Runnable() {
            @Override
            public void run() {
//                try {
                final BluetoothDevice device = result.getDevice();
                final String str = bytes2HexString(result.getScanRecord().getBytes());
                LogUtils.d(TAG, str);
                if ((deviceType == DevicesType.Thermometer && device != null && !StringUtils.isEmpty(device.getName()) && device.getName().equalsIgnoreCase(deviceDeatailName)) ||
                        (deviceType == DevicesType.Ecg && device != null && !StringUtils.isEmpty(device.getName()) && (device.getName().contains(deviceDeatailName) || device.getName().contains(P10_NEW_DEVICE_NAME)) && str.contains(ECG_DEVICE_UUID))) {
                    LogUtils.d(TAG, "device.getName()  " + device.getName() + "   device.getAddress()  " + device.getAddress());
                        synchronized (BleConnectInvoker.this) {
                            if (foundDevice) {
                                return;
                            }
                            foundDevice = true;
                            bleConnectListener.onBindDeviceSuccess(device);
                            stopLeScan();
                        }
                    }

//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        });
    }

    @Override
    public void onBatchScanResults(List<ScanResult> results) {
        super.onBatchScanResults(results);
    }

    @Override
    public void onScanFailed(int errorCode) {
        super.onScanFailed(errorCode);
    }

    public void bindDevice() {
        BleUtils.TestBleResult bleResult = BleUtils.testBle(context);
        if (!bleResult.isAvailable) {
            bleConnectListener.onError(bleResult.errorMsg);
        } else {
            Message msg = bleHandler.obtainMessage();
            msg.what = MSG_STOP_SCAN;
            bleHandler.sendMessageDelayed(msg, SCAN_PERIOD);
            foundDevice = false;
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
            bluetoothLeScanner.startScan(this);
        }
    }

    public void stopLeScan() {
        if (bluetoothLeScanner == null) {
            return;
        }
        bluetoothLeScanner.stopScan(this);
        bleHandler.removeMessages(MSG_STOP_SCAN);
    }

    private static String bytes2HexString(byte[] a) {
        int len = a.length;
        byte[] b = new byte[len];
        for (int k = 0; k < len; k++) {
            b[k] = a[a.length - 1 - k];
        }
        String ret = "";
        for (int i = 0; i < len; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }
}
