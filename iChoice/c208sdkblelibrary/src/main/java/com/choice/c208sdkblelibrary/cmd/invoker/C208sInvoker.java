package com.choice.c208sdkblelibrary.cmd.invoker;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.choice.c208sdkblelibrary.base.BleListener;
import com.choice.c208sdkblelibrary.base.DeviceType;
import com.choice.c208sdkblelibrary.ble.C208sBle;
import com.choice.c208sdkblelibrary.cmd.callback.C208BaseCallback;
import com.choice.c208sdkblelibrary.cmd.callback.C208BindDeviceCallback;
import com.choice.c208sdkblelibrary.cmd.callback.C208CancelConnectCallback;
import com.choice.c208sdkblelibrary.cmd.callback.C208ConnectCallback;
import com.choice.c208sdkblelibrary.cmd.callback.C208DisconnectCallBack;
import com.choice.c208sdkblelibrary.cmd.callback.C208sConnectAandDataResponseCallback;
import com.choice.c208sdkblelibrary.cmd.command.C208sBaseCommand;
import com.choice.c208sdkblelibrary.cmd.command.C208sConnectCommand;

import com.choice.c208sdkblelibrary.cmd.factory.C208sBindCommandFactory;
import com.choice.c208sdkblelibrary.cmd.factory.C208sCancelConnectCommandFactory;
import com.choice.c208sdkblelibrary.cmd.factory.C208sConnectCommandFactory;
import com.choice.c208sdkblelibrary.cmd.factory.C208sCreatCommandListener;
import com.choice.c208sdkblelibrary.cmd.factory.C208sSendRACPCommandFactory;
import com.choice.c208sdkblelibrary.cmd.factory.C208sdisconnectCommandFactory;
import com.choice.c208sdkblelibrary.device.C208;
import com.choice.c208sdkblelibrary.utils.ByteUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static com.choice.c208sdkblelibrary.utils.ErrorCode.ERROR_BLE_DISCONNECT;

/**
 *
 */
public class C208sInvoker implements BleListener {
    private static final String TAG = "C208sInvoker";

    private C208sBle ble;
    private C208BaseCallback callback;
    private C208sCreatCommandListener factory;
    private C208sBaseCommand command;

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

    public C208sInvoker(Context context) {
        this.ble = new C208sBle(context, this);
    }

    /**
     * 扫描设备方法
     */
    public void bindDevice(C208BindDeviceCallback c208BindDeviceCallback) {
        this.callback = c208BindDeviceCallback;
        factory = new C208sBindCommandFactory();
        command = factory.createCommand(this.ble, null);
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
        factory = new C208sConnectCommandFactory();
        command = factory.createCommand(this.ble, null);
        ((C208sConnectCommand) command).setAddress(macAddress);
        command.execute();
        previousCmd = CONNECT_DEVICE;
    }

    /**
     * 撤销连接
     */
    public void cancelConnectDevice(C208CancelConnectCallback c208CancelConnectCallback) {
        this.callback = c208CancelConnectCallback;
        factory = new C208sCancelConnectCommandFactory();
        command = factory.createCommand(this.ble, null);
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
        factory = new C208sdisconnectCommandFactory();
        command = factory.createCommand(this.ble, null);
        command.execute();
        previousCmd = DISCONNECT_DEVICE;
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
        }
    }

    @Override
    public void onInitialized(DeviceType deviceType) {
        Log.d(TAG, "onInitialized: ");
        ((C208sConnectAandDataResponseCallback) callback).onSuccess();
    }

    public void sendCmd(String cmd) {
        factory = new C208sSendRACPCommandFactory();
        command = factory.createCommand(ble, cmd);
        command.execute();
    }

    @Override
    public void onCmdResponse(DeviceType deviceType, byte[] result) {

    }

    @Override
    public void onDataResponse(DeviceType deviceType, byte[] data) {

    }

    @Override
    public void onSpotCheckResponse(DeviceType deviceType, byte[] data) {
        ((C208sConnectAandDataResponseCallback) callback).onSpotCheckResponse(deviceType, data);
    }

    @Override
    public void onRealTimeResponse(DeviceType deviceType, byte[] data) {
        ((C208sConnectAandDataResponseCallback) callback).onRealTimeResponse(deviceType, data);
    }

    @Override
    public void onRACPResponse(DeviceType deviceType, byte[] result) {
        ((C208sConnectAandDataResponseCallback) callback).onRACPResponse(deviceType, result);
    }

    @Override
    public void onReadFeature(DeviceType deviceType, HashMap<String, Boolean> map) {
        ((C208sConnectAandDataResponseCallback) callback).onReadFeature(deviceType, map);

    }


    public void stopScan() {
        ble.stopLeScan();
    }
}
