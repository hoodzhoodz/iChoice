package com.choice.c208sdkblelibrary.gatt;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.util.Log;

import com.choice.c208sdkblelibrary.base.BaseGattCallback;
import com.choice.c208sdkblelibrary.base.DeviceType;
import com.choice.c208sdkblelibrary.base.GattListener;
import com.choice.c208sdkblelibrary.utils.ByteUtils;
import com.choice.c208sdkblelibrary.utils.ErrorCode;

import java.util.List;
import java.util.UUID;

import static com.choice.c208sdkblelibrary.utils.ErrorCode.ERROR_CHARACTERISTIC_NOTIFICATION_FAIL;
import static com.choice.c208sdkblelibrary.utils.ErrorCode.ERROR_FOUND_SERVICE_FAIL;
import static com.choice.c208sdkblelibrary.utils.ErrorCode.ERROR_WRITE_CHARACTERISTIC_FAIL;
import static com.choice.c208sdkblelibrary.utils.ErrorCode.ERROR_WRITE_DESCRIPTOR_FAIL;

public class C208GattCallback extends BaseGattCallback {
    private static final String TAG = "C208GattCallback";

    private static BluetoothGattService BMPService;
    /**
     * service
     */
    private static final String DEVICE_UUID_PREFIX = "ba11f08c5f140b0d108000";
    /**
     * notify*
     */
    private static final UUID CHARACTERISTIC_UUID_CD01 = UUID.fromString("0000cd01-0000-1000-8000-00805f9b34fb");
    /**
     * notify*
     */
    private static final UUID CHARACTERISTIC_UUID_CD02 = UUID.fromString("0000cd02-0000-1000-8000-00805f9b34fb");
    /**
     * notify*
     */
    private static final UUID CHARACTERISTIC_UUID_CD03 = UUID.fromString("0000cd03-0000-1000-8000-00805f9b34fb");
    /**
     * notify*
     */
    private static final UUID CHARACTERISTIC_UUID_CD04 = UUID.fromString("0000cd04-0000-1000-8000-00805f9b34fb");
    /**
     * write*
     */
    private static final UUID CHARACTERISTIC_UUID_CD20 = UUID.fromString("0000cd20-0000-1000-8000-00805f9b34fb");
    private BluetoothGattCharacteristic CD01Characteristic;
    private BluetoothGattCharacteristic CD02Characteristic;
    private BluetoothGattCharacteristic CD03Characteristic;
    private BluetoothGattCharacteristic CD04Characteristic;

    public C208GattCallback(GattListener mGattListener) {
        super(mGattListener);
    }

    @Override
    protected DeviceType getDeviceType() {
        return DeviceType.MD300C208;
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        if (status != BluetoothGatt.GATT_SUCCESS) {
            Log.i(TAG, "异常：改变蓝牙状态失败，status=" + status);
            gatt.close();
            //下位机主动断开蓝牙
            if (status == 8) {
                onDisconnected();
            }
            if (status == 133) {
                onError(ErrorCode.ERROR_GATT_EXCEPTION);
            }
            return;
        }
        switch (newState) {
            case BluetoothProfile
                    .STATE_CONNECTED:
                //蓝牙连接成功
                onBleConnectSuccess();
                Log.i(TAG, "蓝牙已连接");
                if (gatt.discoverServices()) {
                    Log.i(TAG, "发现服务启动");
                } else {
                    Log.i(TAG, "异常：开始发现服务失败");
                    onError(ERROR_FOUND_SERVICE_FAIL);
                }
                break;
            case BluetoothProfile.STATE_DISCONNECTED:
                Log.i(TAG, "蓝牙已断开");
                gatt.close();
                onDisconnected();
                break;
            default:
        }
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);
        if (status != BluetoothGatt.GATT_SUCCESS) {
            Log.i(TAG, "异常：发现服务失败，status=" + status);
            onError(ERROR_FOUND_SERVICE_FAIL);
            return;
        }
        boolean foundService = false;
        List<BluetoothGattService> service = gatt.getServices();
        for (BluetoothGattService service1 : service) {
            String serviceUUID = service1.getUuid().toString();
            String serviceUUID4Compare = serviceUUID.toLowerCase().replace("-", "");
            Log.i(TAG, "serviceUUID:" + serviceUUID4Compare);
            if (serviceUUID4Compare.contains(DEVICE_UUID_PREFIX)) {
                foundService = true;
                List<BluetoothGattCharacteristic> characteristics = service1.getCharacteristics();
                for (BluetoothGattCharacteristic characteristic : characteristics) {
                    if (characteristic.getUuid().equals(CHARACTERISTIC_UUID_CD01)) {
                        CD01Characteristic = characteristic;
                        if (setCharacteristicNotification(gatt, characteristic, true)) {
                            BMPService = service1;
                            Log.i(TAG, "1开始监听notify成功");
                        } else {
                            Log.i(TAG, "异常：1开始监听Notify失败");
                            onError(ERROR_CHARACTERISTIC_NOTIFICATION_FAIL);
                        }
                        break;
                    }

                }
                break;
            }

        }
        if (!foundService) {
            Log.i(TAG, "异常：发现的服务中不包含血氧数据服务");
            onError(ERROR_FOUND_SERVICE_FAIL);
        }
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);
        byte[] data = characteristic.getValue();
        if (data == null) {
            Log.e(TAG, "onCharacteristicChanged: 数据异常，返回数据为null");
            return;
        }
        if ((data.length > 0)) {
            Log.e(TAG, "onCharacteristicChanged: ");
            if (characteristic.getUuid().equals(CHARACTERISTIC_UUID_CD04)) {
                onDataReceived(data);
            } else {
                onCommandReceived(data);
            }

        }
    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorWrite(gatt, descriptor, status);
        if (status != BluetoothGatt.GATT_SUCCESS) {
            Log.i(TAG, "异常:写描述符失败，status=" + status);
            onError(ERROR_WRITE_DESCRIPTOR_FAIL);
            return;
        }
        if (descriptor.getCharacteristic().getUuid().equals(CHARACTERISTIC_UUID_CD01)) {
            Log.i(TAG, "1监听notify成功");
            if (setCharacteristicNotification(gatt, BMPService.getCharacteristic(CHARACTERISTIC_UUID_CD02), true)) {
                Log.i(TAG, "2开始监听notify成功");
            } else {
                Log.i(TAG, "2异常：开始监听Notify失败");
                onError(ERROR_CHARACTERISTIC_NOTIFICATION_FAIL);
            }
        } else if (descriptor.getCharacteristic().getUuid().equals(CHARACTERISTIC_UUID_CD02)) {
            Log.i(TAG, "2监听notify成功");
            if (setCharacteristicNotification(gatt, BMPService.getCharacteristic(CHARACTERISTIC_UUID_CD03), true)) {
                Log.i(TAG, "3开始监听notify成功");
            } else {
                Log.i(TAG, "3异常：开始监听Notify失败");
                onError(ERROR_CHARACTERISTIC_NOTIFICATION_FAIL);
            }
        } else if (descriptor.getCharacteristic().getUuid().equals(CHARACTERISTIC_UUID_CD03)) {
            Log.i(TAG, "3监听notify成功");
            if (setCharacteristicNotification(gatt, BMPService.getCharacteristic(CHARACTERISTIC_UUID_CD04), true)) {
                Log.i(TAG, "4开始监听notify成功");
            } else {
                Log.i(TAG, "4异常：开始监听Notify失败");
                onError(ERROR_CHARACTERISTIC_NOTIFICATION_FAIL);
            }
        } else if (descriptor.getCharacteristic().getUuid().equals(CHARACTERISTIC_UUID_CD04)) {
            Log.i(TAG, "4监听notify成功");
            onInitialized();
        }
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        if (status != BluetoothGatt.GATT_SUCCESS) {
            Log.i(TAG, "异常：写特征状态失败，status=" + status);
            onError(ERROR_WRITE_CHARACTERISTIC_FAIL);
            return;
        }
        Log.i(TAG, "写特征成功");
    }

    /**
     * 发送命令
     *
     * @param gatt
     * @param command
     */
    public static boolean sendCmd(BluetoothGatt gatt, String command) {
        BluetoothGattCharacteristic write20 = BMPService
                .getCharacteristic(CHARACTERISTIC_UUID_CD20);
        byte[] value = ByteUtils.cmdString2Bytes(command, true);
        Log.i(TAG, "sendCmd: value:" + ByteUtils.bytes2HexString(value));
        write20.setValue(value);
        write20.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
        return gatt.writeCharacteristic(write20);
    }
}
