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
import com.choicemmed.wristpulselibrary.gatt.W314b4GattCallback;
import com.choicemmed.wristpulselibrary.utils.BleScanThreadUtils;
import com.choicemmed.wristpulselibrary.utils.ByteUtils;

/**
 * @author Created by Jiang nan on 2020/1/11 11:23.
 * @description
 **/
public class W314b4Ble extends BaseBle {
    private static final java.lang.String TAG = "W314b4Ble";

    private static final String DEVICE_NAME = "314B4";
    private static final String DEVICE_UUID = "0000FEE900001000800000805F9B34FB";
    private W314b4GattCallback w314b4GattCallback;
    public W314b4Ble(Context context, BleListener bleListener) {
        super(context, bleListener);
    }

    @Override
    protected DeviceType getDeviceType() {
        return DeviceType.W314B4;
    }

    @Override
    protected BluetoothGattCallback GetGattCallback() {
        w314b4GattCallback = new W314b4GattCallback(this);
        return w314b4GattCallback;
    }


    @Override
    public void sendCmd(String cmd) {
        if (cmd == null || mBluetoothGatt == null){
            return;
        }
        try {
            w314b4GattCallback.sendCmd(mBluetoothGatt, cmd);
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
                            synchronized (W314b4Ble.this){
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

    @Override
    public void onScanFailed(int errorCode) {
        super.onScanFailed(errorCode);
        Log.e(TAG,"搜索失败");
        mBleListener.onFoundFail(getDeviceType(),errorCode);
    }

}
