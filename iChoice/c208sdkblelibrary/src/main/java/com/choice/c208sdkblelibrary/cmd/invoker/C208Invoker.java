package com.choice.c208sdkblelibrary.cmd.invoker;

import android.content.Context;
import android.util.Log;

import com.choice.c208sdkblelibrary.base.BleListener;
import com.choice.c208sdkblelibrary.base.DeviceType;
import com.choice.c208sdkblelibrary.ble.C208Ble;
import com.choice.c208sdkblelibrary.cmd.callback.C208BaseCallback;
import com.choice.c208sdkblelibrary.cmd.callback.C208BindDeviceCallback;
import com.choice.c208sdkblelibrary.cmd.callback.C208CancelConnectCallback;
import com.choice.c208sdkblelibrary.cmd.callback.C208ConnectCallback;
import com.choice.c208sdkblelibrary.cmd.callback.C208DisconnectCallBack;
import com.choice.c208sdkblelibrary.cmd.callback.C208SendPasswordCallback;
import com.choice.c208sdkblelibrary.cmd.command.C208BaseCommand;
import com.choice.c208sdkblelibrary.cmd.command.C208ConnectCommand;
import com.choice.c208sdkblelibrary.cmd.factory.C208BindCommandFactory;
import com.choice.c208sdkblelibrary.cmd.factory.C208CancelConnectCommandFactory;
import com.choice.c208sdkblelibrary.cmd.factory.C208ConnectCommandFactory;
import com.choice.c208sdkblelibrary.cmd.factory.C208CreatCommandListener;
import com.choice.c208sdkblelibrary.cmd.factory.C208SendPasswordCommandFactory;
import com.choice.c208sdkblelibrary.cmd.factory.C208disconnectCommandFactory;
import com.choice.c208sdkblelibrary.cmd.parse.C208ParseUtils;
import com.choice.c208sdkblelibrary.device.C208;
import com.choice.c208sdkblelibrary.utils.ByteUtils;
import com.choice.c208sdkblelibrary.utils.ErrorCode;

import java.util.HashMap;
import java.util.Map;

import static com.choice.c208sdkblelibrary.utils.ErrorCode.ERROR_BLE_DISCONNECT;

public class C208Invoker implements BleListener {
    private static final String TAG = "C208Invoker";

    private C208Ble ble;
    private C208BaseCallback callback;
    private C208CreatCommandListener factory;
    private C208BaseCommand command;

    private static int previousCmd;

    /**
     * 绑定设备
     */
    private static final int BIND_DEVICE = 0x01;
    /**
     * 连接设备
     */
    private static final int CONNECT_DEVICE = 0x02;
    /**
     * 断开设备蓝牙连接
     */
    private static final int DISCONNECT_DEVICE = 0x03;
    /**
     * 撤销连接设备事件
     */
    private static final int CANCEL_CONNECT_DEVICE = 0x04;
    /**
     * 发送密码
     */
    private static final int SEND_PASSWORD_DEVICE = 0x05;

    /**
     * 发送密码成功结果指令标识
     */
    private static final String SEND_PASSWORD_SUCCESS = "03b100";
    /**
     * 发送密码失败结果指令标识
     */
    private static final String SEND_PASSWORD_FAILD = "03b101";
    /**
     * 测量结果指令标识
     */
    private static final String MEASURE_RESULT = "04";

    public C208Invoker(Context context) {
        this.ble = new C208Ble(context, this);
    }

    /**
     * 扫描设备方法
     */
    public void bindDevice(C208BindDeviceCallback c208BindDeviceCallback) {
        this.callback = c208BindDeviceCallback;
        factory = new C208BindCommandFactory();
        command = factory.createCommand(this.ble);
        command.execute();
        previousCmd = BIND_DEVICE;
    }

    /**
     * 通过设备mac地址连接设备
     *
     * @param macAddress            设备mac
     * @param connectDeviceCallback 回调接口
     */
    public void connectDevice(C208ConnectCallback connectDeviceCallback, String macAddress) {
        Log.d(TAG, "connectDevice: ");
        this.callback = connectDeviceCallback;
        factory = new C208ConnectCommandFactory();
        command = factory.createCommand(this.ble);
        ((C208ConnectCommand) command).setAddress(macAddress);
        command.execute();
        previousCmd = CONNECT_DEVICE;
    }

    /**
     * 撤销连接
     */
    public void cancelConnectDevice(C208CancelConnectCallback c208CancelConnectCallback) {
        this.callback = c208CancelConnectCallback;
        factory = new C208CancelConnectCommandFactory();
        command = factory.createCommand(this.ble);
        command.execute();
        previousCmd = CANCEL_CONNECT_DEVICE;
    }

    /**
     * 断开设备蓝牙连接
     *
     * @param c208DisconnectCallBack 回调接口
     */
    public void disConnectDevice(C208DisconnectCallBack c208DisconnectCallBack) {
        this.callback = c208DisconnectCallBack;
        factory = new C208disconnectCommandFactory();
        command = factory.createCommand(this.ble);
        command.execute();
        previousCmd = DISCONNECT_DEVICE;
    }

    /**
     * 发送密码
     *
     * @param c208SendPasswordCallback 回调接口
     */
    public void sendPassword(C208SendPasswordCallback c208SendPasswordCallback) {

        this.callback = c208SendPasswordCallback;
        factory = new C208SendPasswordCommandFactory();
        command = factory.createCommand(ble);
        command.execute();
        previousCmd = SEND_PASSWORD_DEVICE;
    }

    @Override
    public void onFoundDevice(DeviceType deviceType, String address, String deviceName) {
        if (previousCmd == BIND_DEVICE) {
            ((C208BindDeviceCallback) callback).onFoundDevice(deviceType, new C208(address, deviceName));
        }
    }

    @Override
    public void onScanTimeout(DeviceType deviceType) {
        if (previousCmd == BIND_DEVICE) {
            ((C208BindDeviceCallback) callback).onScanTimeout(deviceType);
        }
    }

    @Override
    public void onError(DeviceType deviceType, int errorMsg) {
        callback.onError(deviceType, errorMsg);
    }

    @Override
    public void onDisconnected(DeviceType deviceType) {
        switch (previousCmd) {
            case BIND_DEVICE:
                callback.onError(deviceType, ERROR_BLE_DISCONNECT);
                break;
            case CONNECT_DEVICE:
                callback.onError(deviceType, ERROR_BLE_DISCONNECT);
                break;
            case DISCONNECT_DEVICE:
                ((C208DisconnectCallBack) callback).onSuccess();
                break;
            case CANCEL_CONNECT_DEVICE:
                ((C208CancelConnectCallback) callback).onSuccess();
                break;
            case SEND_PASSWORD_DEVICE:
                callback.onError(deviceType, ERROR_BLE_DISCONNECT);
            default:
        }
    }

    @Override
    public void onInitialized(DeviceType deviceType) {
        if (previousCmd == CONNECT_DEVICE) {
            ((C208ConnectCallback) callback).onSuccess();
            Log.d(TAG, "onInitialized: ");
            factory = new C208SendPasswordCommandFactory();
            command = factory.createCommand(ble);
            command.execute();
            previousCmd = SEND_PASSWORD_DEVICE;
        }

    }

    @Override
    public void onCmdResponse(DeviceType deviceType, byte[] result) {
        String result1 = ByteUtils.bytes2HexString(result);
        Log.d(TAG, "onDataResponse: " + result1);
        switch (result1) {
            case SEND_PASSWORD_SUCCESS:
                ((C208ConnectCallback) callback).onSuccess();
                break;
            case SEND_PASSWORD_FAILD:
                ((C208ConnectCallback) callback).onError(DeviceType.MD300C208, ErrorCode.ERROR_CONNECT_TIMEOUT);
                return;
            default:
        }
    }

    @Override
    public void onDataResponse(DeviceType deviceType, byte[] data) {
        String result = ByteUtils.bytes2HexString(data);
        Log.d(TAG, "onDataResponse: " + result);
        Map<String, String> map = C208ParseUtils.parseData(result);
        if (map == null) {
            return;
        }
        ((C208ConnectCallback) callback).onMeasureResult(DeviceType.MD300C208, Integer.parseInt(map.get("ox")), Float.parseFloat(map.get("pi")), Integer.parseInt(map.get("pr")));

    }

    @Override
    public void onSpotCheckResponse(DeviceType deviceType, byte[] data) {

    }

    @Override
    public void onRealTimeResponse(DeviceType deviceType, byte[] data) {

    }

    @Override
    public void onRACPResponse(DeviceType deviceType, byte[] result) {

    }

    @Override
    public void onReadFeature(DeviceType deviceType, HashMap<String, Boolean> map) {

    }

    public void stopScan() {
        ble.stopLeScan();
    }
}
