package com.choicemmed.common;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.pm.PackageManager;


public class BleUtils {
    public static TestBleResult testBle(Context context) {
        TestBleResult result = new TestBleResult();
        final BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            result.isAvailable = false;
            result.errorMsg = context.getResources().getString(R.string.ble_adapter_not_available);
            return result;
        }
        if (!mBluetoothAdapter.isEnabled()) {
            result.isAvailable = false;
            result.errorMsg = context.getResources().getString(R.string.ble_not_turned_on);
            return result;
        }
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            result.isAvailable = false;
            result.errorMsg = context.getResources().getString(R.string.ble_not_supported);
            return result;
        }
        return result;
    }

    public static class TestBleResult {
        public boolean isAvailable = true;
        public String errorMsg;
    }
}
