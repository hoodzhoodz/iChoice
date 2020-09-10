package com.choicemmed.wristpulselibrary.gatt;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.util.Log;

import com.choicemmed.wristpulselibrary.base.BaseGattCallback;
import com.choicemmed.wristpulselibrary.base.DeviceType;
import com.choicemmed.wristpulselibrary.base.GattListener;
import com.choicemmed.wristpulselibrary.utils.ByteUtils;
import com.choicemmed.wristpulselibrary.utils.ErrorCode;

import java.util.UUID;

import static com.choicemmed.wristpulselibrary.utils.ErrorCode.ERROR_BLE_DISCONNECT;
import static com.choicemmed.wristpulselibrary.utils.ErrorCode.ERROR_WRITE_CHARACTERISTIC_FAIL;
import static com.choicemmed.wristpulselibrary.utils.ErrorCode.ERROR_WRITE_DESCRIPTOR_FAIL;

/**
 * @author Created by Jiang nan on 2020/1/11 11:25.
 * @description
 **/
public class W314b4GattCallback  extends BaseGattCallback {
    private static final String TAG = "BleGattCallback";

    private  final UUID Service_UUID = UUID.
            fromString("0000fee9-0000-1000-8000-00805f9b34fb");
    private  final UUID Characteristic_UUID_WRITE= UUID
            .fromString("D44BC439-ABFD-45A2-B575-925416129600");
    private  final UUID Characteristic_UUID_NOTIFY_01 = UUID
            .fromString("D44BC439-ABFD-45A2-B575-925416129601");
    private  final UUID Characteristic_UUID_NOTIFY_02 = UUID
            .fromString("D44BC439-ABFD-45A2-B575-925416129602");
    private  final UUID Characteristic_UUID_NOTIFY_03 = UUID
            .fromString("D44BC439-ABFD-45A2-B575-925416129603");
    private  final UUID Characteristic_UUID_NOTIFY_04 = UUID
            .fromString("D44BC439-ABFD-45A2-B575-925416129604");
    private  final UUID Characteristic_UUID_NOTIFY_05 = UUID
            .fromString("D44BC439-ABFD-45A2-B575-925416129605");
    private  final UUID Characteristic_UUID_NOTIFY_06 = UUID
            .fromString("D44BC439-ABFD-45A2-B575-925416129606");
    private  final UUID Characteristic_UUID_NOTIFY_07 = UUID
            .fromString("D44BC439-ABFD-45A2-B575-925416129607");

    BluetoothGattService gattService;

    public W314b4GattCallback(GattListener mGattListener) {
        super(mGattListener);
    }

    @Override
    protected DeviceType getDeviceType() {
        return DeviceType.W314B4;
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        if (status != BluetoothGatt.GATT_SUCCESS) {
            gatt.disconnect();
            gatt.close();
            if (status == 133){
                onError(ErrorCode.ERROR_GATT_EXCEPTION);
            }
            return;
        }

        switch (newState) {
            case BluetoothProfile.STATE_CONNECTED:
                //蓝牙连接成功
                onBleConnectSuccess();
                Log.i(TAG, "蓝牙已连接");
                if (gatt.discoverServices()) {
                    Log.i(TAG, "发现服务启动");
                } else {
                    Log.i(TAG, "异常：开始发现服务失败");
                    onError(ErrorCode.ERROR_FOUND_SERVICE_FAIL);
                }
                break;

            case BluetoothProfile.STATE_DISCONNECTED:
                Log.i(TAG, "蓝牙已断开");
                gatt.close();
                onDisconnected();
                onError(ErrorCode.ERROR_BLE_DISCONNECT);
                break;

            default:
        }
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);
        if (status != BluetoothGatt.GATT_SUCCESS) {
            Log.i(LogTag_BLE, "异常：发现服务失败，status=" + status);
            onError(ErrorCode.ERROR_FOUND_SERVICE_FAIL);
            return;
        }
        //指定uuid的service
        gattService = gatt.getService(Service_UUID);
        if (gattService != null) {
            BluetoothGattCharacteristic characteristic = gattService.getCharacteristic(Characteristic_UUID_NOTIFY_01);
            Log.d(TAG, "serviceUUID 服务发现 ");
            if (setCharacteristicNotification(gatt, characteristic, true)) {
                Log.d(TAG, "开始监听notify1成功");
            } else {
                Log.d(TAG, "异常：开始监听Notify1失败");
            }
        }
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);
        final byte[] data = characteristic.getValue();
        if (data != null & data.length > 0) {
            Log.d(TAG, "<---cmd" + ByteUtils.bytes2HexString(data));
            onDataReceived(data);
        }
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicWrite(gatt, characteristic, status);
        if (status != BluetoothGatt.GATT_SUCCESS) {
            Log.d(TAG, "异常：写特征状态失败，status=" + status);
            onError(ERROR_WRITE_DESCRIPTOR_FAIL);
            return;
        }
        Log.d(TAG, "写特征成功");
    }


    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicRead(gatt, characteristic, status);
        if (status != BluetoothGatt.GATT_SUCCESS) {
            Log.d(TAG, "异常：读特征状态失败，status=" + status);
            return;
        }
        Log.d(TAG, "读特征成功");
        final byte[] data = characteristic.getValue();
        Log.d("读取蓝牙数据", "" + ByteUtils.bytes2HexString(data));
    }
    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorWrite(gatt, descriptor, status);
        if (status != BluetoothGatt.GATT_SUCCESS) {
            Log.d(TAG, "异常:写描述符失败，status=" + status);
            onError(ERROR_WRITE_CHARACTERISTIC_FAIL);
            return;
        }
        BluetoothGattService service = descriptor.getCharacteristic()
                .getService();
        if (descriptor.getCharacteristic().getUuid().equals(Characteristic_UUID_NOTIFY_01)) {
            BluetoothGattCharacteristic characteristic = service
                    .getCharacteristic(Characteristic_UUID_NOTIFY_02);
            Log.d(TAG, "监听notify1成功");
            if (setCharacteristicNotification(gatt, characteristic, true)) {
                Log.d(TAG, "开始监听notify2成功");
            } else {
                Log.d(TAG, "异常：开始监听Notify2失败");
            }
        } else if (descriptor.getCharacteristic().getUuid().equals(Characteristic_UUID_NOTIFY_02)) {
            BluetoothGattCharacteristic characteristic = service
                    .getCharacteristic(Characteristic_UUID_NOTIFY_03);
            Log.d(TAG, "监听notify2成功");
            if (setCharacteristicNotification(gatt, characteristic, true)) {
                Log.d(TAG, "开始监听notify3成功");
            } else {
                Log.d(TAG, "异常：开始监听Notify3失败");
            }
        } else if (descriptor.getCharacteristic().getUuid().equals(Characteristic_UUID_NOTIFY_03)) {
            BluetoothGattCharacteristic characteristic = service
                    .getCharacteristic(Characteristic_UUID_NOTIFY_04);
            Log.d(TAG, "监听notify3成功");
            if (setCharacteristicNotification(gatt, characteristic, true)) {
                Log.d(TAG, "开始监听notify4成功");
            } else {
                Log.d(TAG, "异常：开始监听Notify4失败");
            }
        } else if (descriptor.getCharacteristic().getUuid().equals(Characteristic_UUID_NOTIFY_04)) {
            BluetoothGattCharacteristic characteristic = service
                    .getCharacteristic(Characteristic_UUID_NOTIFY_05);
            Log.d(TAG, "监听notify4成功");
            if (setCharacteristicNotification(gatt, characteristic, true)) {
                Log.d(TAG, "开始监听notify5成功");
            } else {
                Log.d(TAG, "异常：开始监听Notify5失败");
            }
        } else if (descriptor.getCharacteristic().getUuid().equals(Characteristic_UUID_NOTIFY_05)) {
            BluetoothGattCharacteristic characteristic = service
                    .getCharacteristic(Characteristic_UUID_NOTIFY_06);
            Log.d(TAG, "监听notify5成功");
            if (setCharacteristicNotification(gatt, characteristic, true)) {
                Log.d(TAG, "开始监听notify6成功");
            } else {
                Log.d(TAG, "异常：开始监听Notify6失败");
            }
        } else if (descriptor.getCharacteristic().getUuid().equals(Characteristic_UUID_NOTIFY_06)) {
            BluetoothGattCharacteristic characteristic = service
                    .getCharacteristic(Characteristic_UUID_NOTIFY_07);
            Log.d(TAG, "监听notify6成功");
            if (setCharacteristicNotification(gatt, characteristic, true)) {
                Log.d(TAG, "开始监听notify7成功");
            } else {
                Log.d(TAG, "异常：开始监听Notify7失败");
            }
        }  else if (descriptor.getCharacteristic().getUuid()
                .equals(Characteristic_UUID_NOTIFY_07)) {
            Log.d(TAG, "监听notify7成功");
            onInitialized();
            onConnect();
        }
    }


    /**
     * 发送命令
     */
    public  boolean sendCmd(BluetoothGatt gatt, String command) {
        BluetoothGattCharacteristic write20 = null;
        try {
            write20 = gattService
                    .getCharacteristic(Characteristic_UUID_WRITE);
            Log.d("cmd",command);
            byte[] value = ByteUtils.hexString2Bytes(command);
            write20.setValue(value);
            return gatt.writeCharacteristic(write20);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
