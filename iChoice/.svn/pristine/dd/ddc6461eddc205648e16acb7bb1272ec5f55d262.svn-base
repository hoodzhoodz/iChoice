package com.choicemmed.cbp1k1sdkblelibrary.cmd.invoker;

import android.content.Context;
import android.util.Log;

import com.choicemmed.cbp1k1sdkblelibrary.base.BleListener;
import com.choicemmed.cbp1k1sdkblelibrary.base.DeviceType;
import com.choicemmed.cbp1k1sdkblelibrary.ble.BP2941Ble;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.callback.BP2941BaseCallback;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.callback.BP2941BindDeviceCallback;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.callback.BP2941CancelConnectDeviceCallback;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.callback.BP2941ConnectDeviceCallback;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.callback.BP2941DisconnectDeviceCallback;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.callback.BP2941GetDeviceBatteryPowerCallback;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.callback.BP2941PowerOffCallback;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.callback.BP2941StartMeasureCallback;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.callback.BP2941StopMeasureCallback;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.command.BP2941BaseCommand;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.command.BP2941ConnectDeviceCommand;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.factory.BP2941ACKFailCommandFactory;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.factory.BP2941ACKSuccessCommandFactory;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.factory.BP2941BindCommandFactory;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.factory.BP2941CancelConnectDeviceCommandFactory;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.factory.BP2941CheckDateCommandFactory;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.factory.BP2941ConnectDeviceCommandFactory;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.factory.BP2941CreateCommandListener;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.factory.BP2941DisconnectDeviceCommandFactory;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.factory.BP2941PowerOffCommandFactory;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.factory.BP2941StartMeasureCommandFactory;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.factory.BP2941StopMeasureCommandFactory;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.parse.BP2941ParseUtils;
import com.choicemmed.cbp1k1sdkblelibrary.device.BP2941;
import com.choicemmed.cbp1k1sdkblelibrary.utils.ByteUtils;

import java.util.Map;

import static com.choicemmed.cbp1k1sdkblelibrary.utils.ErrorCode.ERROR_BLE_DISCONNECT;


/**
 * @author Name: ZhengZhong on 2018/1/19 16:28
 *         Email: zheng_zhong@163.com
 * @version V1.0.0
 */
public class BP2941Invoker implements BleListener {

    private static final String TAG = "BP2941Invoker";
    private BP2941Ble ble;
    private BP2941BaseCommand command;
    private BP2941CreateCommandListener factory;
    private BP2941BaseCallback callback;
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
     * 校验时间
     */
    private static final int CHECK_DATE = 0x05;
    /**
     * 获取设备电池电量
     */
    private static final int GET_DEVICE_BATTERY_POWER = 0x06;
    /**
     * 开始测量
     */
    private static final int START_MEASURE = 0x07;
    /**
     * 停止测量
     */
    private static final int STOP_MEASURE = 0x08;
    /**
     * 关机
     */
    private static final int POWER_OFF = 0x09;

    /**
     * 下位机返回ACK成功指令
     */
    private static final String CMD_ACK_SUCCESS = "00010102";
    /**
     * 下位机返回ACK失败指令
     */
    private static final String CMD_ACK_FAIL = "00010001";

    /**
     * 测量结果指令标识
     */
    private static final String MEASURE_RESULT = "04";
    /**
     * 中间压指令标识
     */
    private static final String MEASURE_MIDDLE = "03";


    public BP2941Invoker(Context context) {
        this.ble = new BP2941Ble(context, this);
    }


    /**
     * 扫描设备方法
     */
    public void bindDevice(BP2941BindDeviceCallback bindDeviceCallback) {
        this.callback = bindDeviceCallback;
        factory = new BP2941BindCommandFactory();
        command = factory.createCommand(ble);
        command.execute();
        previousCmd = BIND_DEVICE;
    }

    /**
     * 通过设备mac地址连接设备
     *
     * @param macAddress            设备mac
     * @param connectDeviceCallback 回调接口
     */
    public void connectDevice(String macAddress, BP2941ConnectDeviceCallback connectDeviceCallback) {
        this.callback = connectDeviceCallback;
        factory = new BP2941ConnectDeviceCommandFactory();
        command = factory.createCommand(ble);
        ((BP2941ConnectDeviceCommand) command).setAddress(macAddress);
        command.execute();
        previousCmd = CONNECT_DEVICE;
    }

    /**
     * 撤销连接
     */
    public void cancleConnectDevice(BP2941CancelConnectDeviceCallback cancelConnectDeviceCallback) {
        this.callback = cancelConnectDeviceCallback;
        factory = new BP2941CancelConnectDeviceCommandFactory();
        command = factory.createCommand(ble);
        command.execute();
        previousCmd = CANCEL_CONNECT_DEVICE;
    }

    /**
     * 开始测量
     *
     * @param startMeasureCallback 回调接口
     */
    public void startMeasure(BP2941StartMeasureCallback startMeasureCallback) {

        this.callback = startMeasureCallback;
        factory = new BP2941StartMeasureCommandFactory();
        command = factory.createCommand(ble);
        command.execute();
        previousCmd = START_MEASURE;
    }

    /**
     * 停止测量
     *
     * @param stopMeasureCallback 回调接口
     */
    public void stopMeasure(BP2941StopMeasureCallback stopMeasureCallback) {

        this.callback = stopMeasureCallback;
        factory = new BP2941StopMeasureCommandFactory();
        command = factory.createCommand(ble);
        command.execute();
        previousCmd = STOP_MEASURE;
    }

    /**
     * 关机
     *
     * @param powerOffCallback 回调接口
     */
    public void powerOff(BP2941PowerOffCallback powerOffCallback) {

        this.callback = powerOffCallback;
        factory = new BP2941PowerOffCommandFactory();
        command = factory.createCommand(ble);
        command.execute();
        previousCmd = POWER_OFF;
    }

    /**
     * 断开设备蓝牙连接
     *
     * @param disconnectDeviceCallback 回调接口
     */
    public void disconnectDevice(BP2941DisconnectDeviceCallback disconnectDeviceCallback) {
        this.callback = disconnectDeviceCallback;
        factory = new BP2941DisconnectDeviceCommandFactory();
        command = factory.createCommand(ble);
        command.execute();
        previousCmd = DISCONNECT_DEVICE;
    }


    @Override
    public void onFoundDevice(DeviceType deviceType, String address, String deviceName) {
        if (previousCmd == BIND_DEVICE) {
            ((BP2941BindDeviceCallback) callback).onFoundDevice(deviceType, new BP2941(deviceName, address));
        }
    }

    @Override
    public void onScanTimeout(DeviceType deviceType) {
        if (previousCmd == BIND_DEVICE) {
            ((BP2941BindDeviceCallback) callback).onScanTimeout(deviceType);
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
            case CHECK_DATE:
                callback.onError(deviceType, ERROR_BLE_DISCONNECT);
                break;
            case DISCONNECT_DEVICE:
                ((BP2941DisconnectDeviceCallback) callback).onSuccess();
                break;
            case CANCEL_CONNECT_DEVICE:
                ((BP2941CancelConnectDeviceCallback) callback).onSuccess();
                break;
            case GET_DEVICE_BATTERY_POWER:
                callback.onError(deviceType, ERROR_BLE_DISCONNECT);
                break;
            case START_MEASURE:
                callback.onError(deviceType, ERROR_BLE_DISCONNECT);
                break;
            case STOP_MEASURE:
                callback.onError(deviceType, ERROR_BLE_DISCONNECT);
                break;
            default:
        }
    }

    @Override
    public void onInitialized(DeviceType deviceType) {
        if (previousCmd == CONNECT_DEVICE) {
            ((BP2941ConnectDeviceCallback) callback).onSuccess();
            //发送校验时间命令
            factory = new BP2941CheckDateCommandFactory();
            command = factory.createCommand(ble);
            command.execute();
            previousCmd = CHECK_DATE;
        }
    }

    @Override
    public void onCmdResponse(DeviceType deviceType, byte[] result) {

    }


    @Override
    public void onDataResponse(DeviceType deviceType, byte[] data) {
        String result = ByteUtils.bytes2HexString(data);
        Log.d(TAG, "onDataResponse: " + result);
        switch (result) {
            case CMD_ACK_SUCCESS:
                ackProcessCallback();
                return;
            case CMD_ACK_FAIL:
                command.execute();
                return;
            default:
        }

        //中间压或者测量结果
        boolean isMiddleOrResult = result.startsWith(MEASURE_MIDDLE) || result.startsWith(MEASURE_RESULT);
        //连接或者开始测量命令
        boolean isConnectOrCheckDate = previousCmd == START_MEASURE || previousCmd == CHECK_DATE;
        if (isMiddleOrResult && isConnectOrCheckDate) {
            if (!BP2941ParseUtils.checkCRC(data)) {
                Log.e(TAG, "onDataResponse: 数据检验和校验失败！");
                command = new BP2941ACKFailCommandFactory().createCommand(ble);
                command.execute();
                return;
            }
            Map<String, Integer> map = BP2941ParseUtils.parseData(result);
            if (map == null) {
                return;
            }
            switch (previousCmd) {
                case START_MEASURE:
                    ((BP2941StartMeasureCallback) callback).onMeasureResult(deviceType
                            , map.get(BP2941ParseUtils.SYSTOLIC_PRESSURE)
                            , map.get(BP2941ParseUtils.DIASTOLIC_PRESSURE)
                            , map.get(BP2941ParseUtils.PULSE_RATE));
                    break;
                case CHECK_DATE:
                    ((BP2941ConnectDeviceCallback) callback).onMeasureResult(deviceType
                            , map.get(BP2941ParseUtils.SYSTOLIC_PRESSURE)
                            , map.get(BP2941ParseUtils.DIASTOLIC_PRESSURE)
                            , map.get(BP2941ParseUtils.PULSE_RATE));
                    break;
                default:
            }

            Log.d(TAG, "onDataResponse: 高压："
                    + map.get(BP2941ParseUtils.SYSTOLIC_PRESSURE)
                    + " 低压: " + map.get(BP2941ParseUtils.DIASTOLIC_PRESSURE)
                    + " 脉率：" + map.get(BP2941ParseUtils.PULSE_RATE));
            command = new BP2941ACKSuccessCommandFactory().createCommand(ble);
            command.execute();

        }

    }

    /**
     * ack应答通过，回调指定业务成功接口
     */
    private void ackProcessCallback() {
        switch (previousCmd) {
            case GET_DEVICE_BATTERY_POWER:
                ((BP2941GetDeviceBatteryPowerCallback) callback).onSuccess();
                break;
            case POWER_OFF:
                ((BP2941PowerOffCallback) callback).onSuccess();
                break;
            case START_MEASURE:
                ((BP2941StartMeasureCallback) callback).onSuccess();
                break;
            case STOP_MEASURE:
                ((BP2941StopMeasureCallback) callback).onSuccess();
                break;
            default:
        }
    }


    public void stopScan(){
        ble.stopLeScan();
    }

}
