package com.choicemmed.cbp1k1sdkblelibrary.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCallback;
import android.content.Context;

import com.choicemmed.cbp1k1sdkblelibrary.base.BaseBle;
import com.choicemmed.cbp1k1sdkblelibrary.base.BleListener;
import com.choicemmed.cbp1k1sdkblelibrary.base.DeviceType;
import com.choicemmed.cbp1k1sdkblelibrary.gatt.BP2941GattCallback;
import com.choicemmed.cbp1k1sdkblelibrary.utils.BleScanThreadUtils;
import com.choicemmed.cbp1k1sdkblelibrary.utils.LogUtils;

/**
 * @author zhengzhong
 */
public class BP2941Ble extends BaseBle {

    private static final String DEVICE_NAME = "BP2941";
    private static final String DEVICE_UUID = "FFF0";

    public BP2941Ble(Context context, BleListener bleListener) {
        super(context, bleListener);
    }

    @Override
    protected DeviceType getDeviceType() {

        return DeviceType.BP2941;
    }

    @Override
    protected BluetoothGattCallback GetGattCallback() {
        return new BP2941GattCallback(this);
    }

    @Override
    public void sendCmd(String cmd) {
        BP2941GattCallback.sendCmd(mBluetoothGatt, cmd);
    }

    @Override
    public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
        final String str = bytes2HexString(scanRecord);
        BleScanThreadUtils.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!foundDevice && device != null && device.getName() != null && str.contains(DEVICE_UUID) && device.getName().equalsIgnoreCase(DEVICE_NAME)) {
                        synchronized (BP2941Ble.this) {
                            if (foundDevice) {
                                return;
                            }
                            foundDevice = true;
                            LogUtils.d("onLeScan", "已扫描到蓝牙设备 " + device.getAddress() + " DeviceName: " + device.getName());
                            mBleListener.onFoundDevice(getDeviceType(), device.getAddress(), device.getName());
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
