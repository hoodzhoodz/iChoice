package com.choice.c208sdkblelibrary.cmd.invoker;

import android.content.Context;
import android.util.Log;

import com.choice.c208sdkblelibrary.base.BleListener;
import com.choice.c208sdkblelibrary.base.DeviceType;
import com.choice.c208sdkblelibrary.ble.C218RBle;
import com.choice.c208sdkblelibrary.cmd.callback.C208BaseCallback;
import com.choice.c208sdkblelibrary.cmd.callback.C208BindDeviceCallback;
import com.choice.c208sdkblelibrary.cmd.callback.C208CancelConnectCallback;
import com.choice.c208sdkblelibrary.cmd.callback.C208ConnectCallback;
import com.choice.c208sdkblelibrary.cmd.callback.C208DisconnectCallBack;
import com.choice.c208sdkblelibrary.cmd.callback.C218RConnectAandDataResponseCallback;
import com.choice.c208sdkblelibrary.cmd.command.C218RBaseCommand;
import com.choice.c208sdkblelibrary.cmd.command.C218RConnectCommand;

import com.choice.c208sdkblelibrary.cmd.factory.C218RBindCommandFactory;
import com.choice.c208sdkblelibrary.cmd.factory.C218RCancelConnectCommandFactory;
import com.choice.c208sdkblelibrary.cmd.factory.C218RConnectCommandFactory;
import com.choice.c208sdkblelibrary.cmd.factory.C218RCreatCommandListener;
import com.choice.c208sdkblelibrary.cmd.factory.C218RSendRACPCommandFactory;
import com.choice.c208sdkblelibrary.cmd.factory.C218RdisconnectCommandFactory;
import com.choice.c208sdkblelibrary.device.C208;

import java.util.HashMap;

import static com.choice.c208sdkblelibrary.utils.ErrorCode.ERROR_BLE_DISCONNECT;

/**
 *
 */
public class C218RInvoker implements BleListener {
    private static final String TAG = "C218RInvoker";

    private C218RBle ble;
    private C208BaseCallback callback;
    private C218RCreatCommandListener factory;
    private C218RBaseCommand command;

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

    public C218RInvoker(Context context) {
        this.ble = new C218RBle(context, this);
    }

    /**
     * 扫描设备方法
     */
    public void bindDevice(C208BindDeviceCallback c208BindDeviceCallback) {
        this.callback = c208BindDeviceCallback;
        factory = new C218RBindCommandFactory();
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
        factory = new C218RConnectCommandFactory();
        command = factory.createCommand(this.ble, null);
        ((C218RConnectCommand) command).setAddress(macAddress);
        command.execute();
        previousCmd = CONNECT_DEVICE;
    }

    /**
     * 撤销连接
     */
    public void cancelConnectDevice(C208CancelConnectCallback c208CancelConnectCallback) {
        this.callback = c208CancelConnectCallback;
        factory = new C218RCancelConnectCommandFactory();
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
        factory = new C218RdisconnectCommandFactory();
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
        ((C218RConnectAandDataResponseCallback) callback).onSuccess();
    }

    public void sendCmd(String cmd) {
        factory = new C218RSendRACPCommandFactory();
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
        ((C218RConnectAandDataResponseCallback) callback).onSpotCheckResponse(deviceType, data);
    }

    @Override
    public void onRealTimeResponse(DeviceType deviceType, byte[] data) {
        ((C218RConnectAandDataResponseCallback) callback).onRealTimeResponse(deviceType, data);
    }

    @Override
    public void onRACPResponse(DeviceType deviceType, byte[] result) {
        ((C218RConnectAandDataResponseCallback) callback).onRACPResponse(deviceType, result);
    }

    @Override
    public void onReadFeature(DeviceType deviceType, HashMap<String, Boolean> map) {
        ((C218RConnectAandDataResponseCallback) callback).onReadFeature(deviceType, map);

    }

    public void stopScan() {
        ble.stopLeScan();
    }
}
