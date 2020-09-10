package com.choice.c208sdkblelibrary.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCallback;
import android.content.Context;
import android.util.Log;

import com.choice.c208sdkblelibrary.base.BaseBle;
import com.choice.c208sdkblelibrary.base.BleListener;
import com.choice.c208sdkblelibrary.base.DeviceType;
import com.choice.c208sdkblelibrary.gatt.C218RGattCallback;
import com.choice.c208sdkblelibrary.utils.BleScanThreadUtils;

public class C218RBle extends BaseBle {
    private static final String DEVICE_UUID = "6E400001B5A3F393E0A9E50E24DCCA9E";

    public C218RBle(Context context, BleListener bleListener) {
        super(context, bleListener);
    }

    @Override
    protected DeviceType getDeviceType() {
        return DeviceType.MD300C218R;
    }

    @Override
    protected BluetoothGattCallback GetGattCallback() {
        return new C218RGattCallback(this);
    }

    @Override
    public void sendCmd(String cmd) {
        C218RGattCallback.sendCmd(mBluetoothGatt, cmd);
    }

    @Override
    public void onLeScan(final BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
        final String str = bytes2HexString(bytes);
        Log.d("onLeScan", "str " + str);
        BleScanThreadUtils.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!foundDevice && bluetoothDevice != null && bluetoothDevice.getName() != null && str.contains(DEVICE_UUID)) {
                        synchronized (C218RBle.this) {
                            if (foundDevice) {
                                return;
                            }
                            foundDevice = true;
                            Log.d("onLeScan", "已扫描到蓝牙设备 " + bluetoothDevice.getAddress() + " DeviceName: " + bluetoothDevice.getName());
                            mBleListener.onFoundDevice(getDeviceType(), bluetoothDevice.getName(), bluetoothDevice.getAddress());
                            stopLeScan();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

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
