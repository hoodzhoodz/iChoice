package com.choicemmed.blelibrary.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCallback;
import android.content.Context;
import android.util.Log;

import com.choicemmed.blelibrary.base.BaseBle;
import com.choicemmed.blelibrary.base.BleListener;
import com.choicemmed.blelibrary.base.DeviceType;
import com.choicemmed.blelibrary.gatt.HeartRateGattCallback;
import com.choicemmed.blelibrary.utils.FormatUtils;

import java.util.Locale;

/**
 * Created by Yu Baoxiang on 2015/5/13.
 */
public class HeartRateBle extends BaseBle {
    private static final String A12_DEVICE_NAME = "A12";
    private static final String A12_DEVICE_UUID = "0000FEE900001000800000805F9B34FB";
    private static final String P10_DEVICE_NAME = "P10-B";
    private static final String P10_DEVICE_UUID = "FF00";
    private static final String HR1_DEVICE_NAME = "HR1";
    private static final String HR1_DEVICE_UUID = "FF00";

    @Override
    public void onLeScan(final BluetoothDevice device, int rssi, final byte[] scanRecord) {
        final String str = bytes2HexString(scanRecord);
        new Thread() {
            @Override
            public void run() {
                try {
                    if (foundDevice || device == null || device.getName() == null) {
                        return;
                    }
                    if (isDeviceName(device, A12_DEVICE_NAME) && str.contains(A12_DEVICE_UUID)) {
                        setDeviceType(DeviceType.A12b);
                        FormatUtils.type = "A12";
                        findDevice(device);
                    }
                    if (isDeviceName(device, P10_DEVICE_NAME) && str.contains(P10_DEVICE_UUID)) {
                        setDeviceType(DeviceType.P10b);
                        FormatUtils.type = "P10b";
                        findDevice(device);
                    }
                    if (isDeviceName(device, P10_DEVICE_NAME) && str.contains(A12_DEVICE_UUID)) {
                        setDeviceType(DeviceType.P10ba);
                        FormatUtils.type = "P10ba";
                        findDevice(device);
                    }
                    if (isDeviceName(device, HR1_DEVICE_NAME) && str.contains(HR1_DEVICE_UUID)) {
                        setDeviceType(DeviceType.HR1);
                        FormatUtils.type = "HR1";
                        findDevice(device);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private boolean isDeviceName(BluetoothDevice device, String deviceName) {
        return device.getName().toUpperCase(Locale.getDefault()).contains(deviceName);
    }

    private void findDevice(BluetoothDevice device) {
        Log.d(LogTag_BLE, "搜索到" + device.getName() + "设备，address：" + device.getAddress());
        mBleListener.onFoundDevice(getDeviceType(), device.getAddress(), device.getName());
        foundDevice = true;
        bleHandler.removeMessages(MSG_STOPSCAN);
        bleHandler.obtainMessage(MSG_STOPSCAN).sendToTarget();
    }

    public HeartRateBle(Context context, BleListener bleListener) {
        super(context, bleListener);
    }


    @Override
    protected BluetoothGattCallback GetGattCallback() {
        return new HeartRateGattCallback(this);
    }

    @Override
    public void sendCmd(String cmd) {
        HeartRateGattCallback.sendCmd(mBluetoothGatt, cmd);
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
