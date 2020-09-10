package com.choicemmed.cbp1k1sdkblelibrary.gatt;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.util.Log;

import com.choicemmed.cbp1k1sdkblelibrary.base.BaseGattCallback;
import com.choicemmed.cbp1k1sdkblelibrary.base.DeviceType;
import com.choicemmed.cbp1k1sdkblelibrary.base.GattListener;
import com.choicemmed.cbp1k1sdkblelibrary.utils.ByteUtils;
import com.choicemmed.cbp1k1sdkblelibrary.utils.ErrorCode;
import com.choicemmed.cbp1k1sdkblelibrary.utils.LogUtils;

import java.util.List;
import java.util.UUID;

import static com.choicemmed.cbp1k1sdkblelibrary.utils.ErrorCode.ERROR_CHARACTERISTIC_NOTIFICATION_FAIL;
import static com.choicemmed.cbp1k1sdkblelibrary.utils.ErrorCode.ERROR_FOUND_SERVICE_FAIL;
import static com.choicemmed.cbp1k1sdkblelibrary.utils.ErrorCode.ERROR_WRITE_CHARACTERISTIC_FAIL;
import static com.choicemmed.cbp1k1sdkblelibrary.utils.ErrorCode.ERROR_WRITE_DESCRIPTOR_FAIL;

/**
 * @author zhengzhong
 */
public class  BP2941GattCallback extends BaseGattCallback {

    /**
     * service
     */
    private static final String DEVICE_UUID_PREFIX = "0000fff000001000800000805f9b34fb";
    /**
     * notify*
     */
    private static final UUID CHARACTERISTIC_UUID_FFF1 = UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb");
    /**
     * write*
     */
    private static final UUID CHARACTERISTIC_UUID_FFF2 = UUID.fromString("0000fff2-0000-1000-8000-00805f9b34fb");

    private static final String TAG = "BP2941GattCallback";

    private static BluetoothGattService BMPService;

    public BP2941GattCallback(GattListener gattListener) {
        super(gattListener);
    }

    @Override
    protected DeviceType getDeviceType() {
        return DeviceType.BP2941;
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        if (status != BluetoothGatt.GATT_SUCCESS) {
            Log.i(TAG, "异常：改变蓝牙状态失败，status=" + status);
            gatt.close();
            //下位机主动断开蓝牙
            if (status == 8) {
//                onDisconnected();
            }

            if (status == 133){
                onError(ErrorCode.ERROR_GATT_EXCEPTION);
            }
            return;
        }
        switch (newState) {
            case BluetoothProfile.STATE_CONNECTED: {
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
            }
            case BluetoothProfile.STATE_DISCONNECTED: {
                Log.i(TAG, "蓝牙已断开");
                gatt.close();
                onDisconnected();
                break;
            }
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
        boolean foundService = false;
        List<BluetoothGattService> service = gatt.getServices();
        for (BluetoothGattService service1 : service) {
            String serviceUUID = service1.getUuid().toString();
            String serviceUUID4Compare = serviceUUID.toLowerCase().replace("-", "");
            LogUtils.i(TAG, "serviceUUID:" + serviceUUID4Compare);
            if (serviceUUID4Compare.equalsIgnoreCase(DEVICE_UUID_PREFIX)) {
                foundService = true;
                BluetoothGattCharacteristic characteristic = service1.getCharacteristic(CHARACTERISTIC_UUID_FFF1);
                if (setCharacteristicNotification(gatt, characteristic, true)) {
                    BMPService = service1;
                    Log.i(LogTag_BLE, "开始监听notify成功");
                } else {
                    Log.i(LogTag_BLE, "异常：开始监听Notify失败");
                    onError(ERROR_CHARACTERISTIC_NOTIFICATION_FAIL);
                }
            }
        }

        if (!foundService) {
            Log.i(LogTag_BLE, "异常：发现的服务中不包含血压数据服务");
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
            onDataReceived(data);
        }
    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorWrite(gatt, descriptor, status);
        if (status != BluetoothGatt.GATT_SUCCESS) {
            Log.i(LogTag_BLE, "异常:写描述符失败，status=" + status);
            onError(ERROR_WRITE_DESCRIPTOR_FAIL);
            return;
        }
        if (descriptor.getCharacteristic().getUuid().equals(CHARACTERISTIC_UUID_FFF1)) {
            Log.i(LogTag_BLE, "监听notify成功");
            onInitialized();
        }
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        if (status != BluetoothGatt.GATT_SUCCESS) {
            Log.i(LogTag_BLE, "异常：写特征状态失败，status=" + status);
            onError(ERROR_WRITE_CHARACTERISTIC_FAIL);
            return;
        }
        Log.i(LogTag_BLE, "写特征成功");
    }

    /**
     * 发送命令
     *
     * @param gatt
     * @param command
     */
    public static boolean sendCmd(BluetoothGatt gatt, String command) {
        if (gatt == null || BMPService == null){
            return false;
        }
        BluetoothGattCharacteristic write20 = BMPService
                .getCharacteristic(CHARACTERISTIC_UUID_FFF2);
        byte[] value = ByteUtils.cmdString2Bytes(command, true);
        Log.i(TAG, "sendCmd: value:" + ByteUtils.bytes2HexString(value));
        write20.setValue(value);
        return gatt.writeCharacteristic(write20);
    }
}
