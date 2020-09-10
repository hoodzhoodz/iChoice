package com.choicemmed.wristpulselibrary.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.choicemmed.wristpulselibrary.base.BaseBle;
import com.choicemmed.wristpulselibrary.base.BleListener;
import com.choicemmed.wristpulselibrary.base.DeviceType;
import com.choicemmed.wristpulselibrary.gatt.W628GattCallback;
import com.choicemmed.wristpulselibrary.utils.BleScanThreadUtils;
import com.choicemmed.wristpulselibrary.utils.ByteUtils;

/**
 * @anthor by jiangnan
 * @Date on 2020/3/2.
 */
public class W628Ble extends BaseBle {

    public static final String TAG = "W628Ble";
    private static final String DEVICE_NAME = "W628";
    private static final String DEVICE_UUID = "0000FEE900001000800000805F9B34FB";

    private W628GattCallback w628GattCallback;
    public W628Ble(Context context, BleListener bleListener) {
        super(context, bleListener);
    }

    @Override
    protected DeviceType getDeviceType() {
        return DeviceType.W628;
    }

    @Override
    protected BluetoothGattCallback GetGattCallback() {
        w628GattCallback = new W628GattCallback(this);
        return w628GattCallback;
    }

    @Override
    public void sendCmd(String cmd) {
        if (cmd == null || mBluetoothGatt == null){
            return;
        }
        try {
            w628GattCallback.sendCmd(mBluetoothGatt, cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        super.onScanResult(callbackType, result);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final BluetoothDevice device = result.getDevice();
            final String str = ByteUtils.ScanBytes2HexString(result.getScanRecord().getBytes());
            BleScanThreadUtils.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        if ((foundDevice && device == null && (device.getName() == null))){
                            return;
                        }
                        if (str.contains(DEVICE_UUID) && device.getName().equalsIgnoreCase(DEVICE_NAME)){
                            synchronized (W628Ble.this){
                                if (foundDevice){
                                    return;
                                }
                                foundDevice = true;
                                Log.d(TAG, "found device name : " + device.getName() + " address : " + device.getAddress());
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
    }
}
