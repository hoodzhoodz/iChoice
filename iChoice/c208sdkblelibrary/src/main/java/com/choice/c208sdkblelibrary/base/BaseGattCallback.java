package com.choice.c208sdkblelibrary.base;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;

import java.util.HashMap;
import java.util.UUID;

public abstract class BaseGattCallback extends BluetoothGattCallback {

    private static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
    private GattListener mGattListener;

    public BaseGattCallback(GattListener mGattListener) {
        this.mGattListener = mGattListener;
    }

    protected abstract DeviceType getDeviceType();

    protected void onError(int errorMsg) {
        if (mGattListener != null) {
            mGattListener.onError(getDeviceType(), errorMsg);
        }
    }

    protected void onDisconnected() {
        if (mGattListener != null) {
            mGattListener.onDisconnected(getDeviceType());
        }
    }

    protected void onInitialized() {
        if (mGattListener != null) {
            mGattListener.onInitialized(getDeviceType());
        }
    }

    protected void onDataReceivedFeature(HashMap<String, Boolean> map) {
        if (mGattListener != null) {
            mGattListener.onReadFeature(getDeviceType(), map);
        }
    }
    protected void onDataReceived(byte[] data) {
        if (mGattListener != null) {
            mGattListener.onDataResponse(getDeviceType(), data);
        }
    }

    protected void onRACPResponse(byte[] data) {
        if (mGattListener != null) {
            mGattListener.onRACPResponse(getDeviceType(), data);
        }
    }

    protected void onRealTimeResponse(byte[] data) {
        if (mGattListener != null) {
            mGattListener.onRealTimeResponse(getDeviceType(), data);
        }
    }

    protected void onSpotCheckResponse(byte[] data) {
        if (mGattListener != null) {
            mGattListener.onSpotCheckResponse(getDeviceType(), data);
        }
    }
    protected void onBleConnectSuccess() {
        if (mGattListener != null) {
            mGattListener.onBleConnectSuccess();
        }
    }

    protected void onCommandReceived(byte[] data) {
        if (mGattListener != null) {
            mGattListener.onCmdResponse(getDeviceType(), data);
        }
    }


    protected static boolean setCharacteristicNotification(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, boolean enabled) {
        if (gatt == null || characteristic == null) {
            return false;
        }
        if (!gatt.setCharacteristicNotification(characteristic, enabled)) {
            return false;
        }

        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString(CLIENT_CHARACTERISTIC_CONFIG));
        if (descriptor != null) {
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            gatt.writeDescriptor(descriptor);
        }
        return true;
    }

    protected static boolean setCharacteristicIndication(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, boolean enabled) {
        if (gatt == null || characteristic == null) {
            return false;
        }
        if (!gatt.setCharacteristicNotification(characteristic, enabled)) {
            return false;
        }

        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString(CLIENT_CHARACTERISTIC_CONFIG));
        if (descriptor != null) {
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
            gatt.writeDescriptor(descriptor);
        }
        return true;
    }
}
