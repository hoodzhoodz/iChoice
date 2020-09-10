package com.choicemmed.wristpulselibrary.cmd.invoker;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.choicemmed.wristpulselibrary.base.BleListener;
import com.choicemmed.wristpulselibrary.base.DeviceType;
import com.choicemmed.wristpulselibrary.ble.W314b4Ble;
import com.choicemmed.wristpulselibrary.cmd.command.BaseCommand;
import com.choicemmed.wristpulselibrary.cmd.command.ConnectDeviceCommand;
import com.choicemmed.wristpulselibrary.cmd.factory.ICommandCreator;
import com.choicemmed.wristpulselibrary.cmd.factory.IConnectDeviceCommandFactory;
import com.choicemmed.wristpulselibrary.cmd.factory.IDisconnectDeviceCommandFactory;
import com.choicemmed.wristpulselibrary.cmd.factory.IScanBleCommandFactory;
import com.choicemmed.wristpulselibrary.cmd.listener.W314Listener;
import com.choicemmed.wristpulselibrary.entity.Device;
import com.choicemmed.wristpulselibrary.utils.ByteUtils;
import com.choicemmed.wristpulselibrary.utils.FormatUtils;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Created by Jiang nan on 2020/1/11 13:07.
 * @description
 **/
public class W314b4Invoker {
    private static final String TAG = "W314b4Invoker";
    private W314b4Ble w314b4Ble;
    private BaseCommand baseCommand;
    private W314Listener mlistener;
    private final Context mContext;
    private Device mdevice;
    private static volatile StringBuffer previousResponse = new StringBuffer();
    /**
     * 回传的所有开关机时间
     */
    private volatile AtomicInteger pacCount = new AtomicInteger(0);
    /**
     * 蓝牙空闲状态
     */
    public static final int BLUETOOTH_FREE = 0x01;

    /**
     * 蓝牙扫描中状态
     */
    public static final int BLUETOOTH_SCANNING = 0x02;
    /**
     * 发现设备状态
     */
    public static final int BLUETOOTH_FOUND_DEVICE = 0x03;
    /**
     * 连接设备中状态
     */
    public static final int BLUETOOTH_CONNECTING_DEVICE = 0x04;
    /**
     * 获取设备信息中状态
     */
    public static final int BLUETOOTH_GETTING_DEVICE_INFO = 0x05;
    /**
     * 蓝牙连接成功状态
     */
    public static final int BLUETOOTH_CONNECT_SUCCESS = 0x07;
    /**
     * 同步命令状态
     */
    public static final int BLUETOOTH_SYNC = 0x08;

    public static final int BLUETOOTH_QUERY = 0x09;

    public static final int BLUETOOTH_ALL_DATA = 0xA;

    public static final  int BLUETOOTH_SYNC_QUIT = 0xB;

    public static final int BLUETOOTH_REALTIME_START =0xC;

    public static final int BLUETOOTH_REALTIME_QUIT =0xC;

    public static final int BLUETOOTH_ON_OFF_TIME =0xD;

    public static final int BLUETOOTH_CLEAR_ORDER =0xE;

    private static int bleState;
    /**
     * 查询记录返回的有效数据长度*
     */
    public int validLength;
    /**
     * 有效的血氧脉率数据帧数*
     */
    public int validCount;
    /**
     * 有效的开关机数据帧数*
     */
    public int validTimeCount;
    /**
     * 当前发送的有效数据索引  默认是0 *
     */
    public int curPosition = 0;
    /**
     * 当前开关机的有效数据索引  默认是0 *
     */
    public int curTimePosition = 0;
    /**
     * 查询开关机返回的有效数据长度*
     */
    public int validTimeLength;

    /**
     * 绑定、连接设备标志false-绑定设备，true-连接设备
     */
    private static boolean bindOrConnectState = false;

    /**
     * 回传的所有的血氧脉率数据
     */
    private static volatile StringBuffer historyData = new StringBuffer();
    /**
     * 回传的所有开关机时间
     */
    private static volatile StringBuffer historyTime = new StringBuffer();
    private String timeLower = "";
    private String timeHigh = "";
    /**
     * 同步命令
     */
    private final static String FD_CMD = "55aafd0000fd";
    /**
     * 查询命令（有多少帧数据和时间）
     */
    private final static String FE_CMD = "55aafe0000fe";
    /**
     * 传输全部血氧数据
     */
    private final static String FA_CMD = "55aafa0000fa";
    /**
     * 传输全部开关机时间数据
     */
    private final static String FB_CMD = "55aafb0000fb";
    /**
     * 退出同步
     */
    private final static String F3_CMD = "55aaf30000f3";

    /**
     * 清空命令
     */
    private final static String F4_CMD = "55aaf40000f4";
    /**
     * 时间设置低位
     */
    private final static String F7_CMD = "55aaf7";
    /**
     * 时间设置高位
     */
    private final static String F8_CMD = "55aaf8";

    /**
     * 开始实时数据
     */
    private final static String RT_CMD = "55aa48000048";
    /**
     * 退出实时数据
     */
    private final static String RQ_CMD = "55aa54000054";
    private static String previousCmd = "";
    private static final int MSG_SEND_CMD = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SEND_CMD:
                    Bundle data = msg.getData();
                    String cmd = data.getString("Command");
                    Log.d(TAG, "\n 向下位机发送的命令------>" + cmd);
                    if (w314b4Ble != null && cmd != null) {
                        previousCmd = cmd;
                        previousResponse.setLength(0);
                        w314b4Ble.sendCmd(cmd);
                    }
                    break;
                    default:
            }
        }
    };

    public W314b4Invoker(final W314Listener mlistener, Context mContext) {
        this.mlistener = mlistener;
        this.mContext = mContext;
        mdevice = new Device();
        w314b4Ble = new W314b4Ble(mContext, new BleListener() {
            @Override
            public void onFoundDevice(DeviceType deviceType, String address, String deviceName) {
                changeState(BLUETOOTH_FOUND_DEVICE);
                mdevice.setDeviceMacAddress(address);
                mdevice.setDeviceName(deviceName);
                changeState(BLUETOOTH_CONNECTING_DEVICE);
                mlistener.onBindDeviceSuccess(mdevice);
            }

            @Override
            public void onFoundFail(DeviceType deviceType, int errorMsg) {
                mlistener.onBindDeviceFail(errorMsg);
            }

            @Override
            public void onConnected(DeviceType deviceType) {
                if (!bindOrConnectState) {
                    changeState(BLUETOOTH_GETTING_DEVICE_INFO);
                }
                mlistener.onConnectedDeviceSuccess();
                changeState(BLUETOOTH_CONNECT_SUCCESS);
            }

            @Override
            public void onScanTimeout(DeviceType deviceType) {

            }

            @Override
            public void onError(DeviceType deviceType, int errorMsg) {
                Log.d(TAG, "onError: " + errorMsg);
                mlistener.onError(errorMsg);
                changeState(BLUETOOTH_FREE);
            }

            @Override
            public void onDisconnected(DeviceType deviceType) {
                Log.d(TAG, "onDisconnected....断开");
                changeState(BLUETOOTH_FREE);
                mlistener.onDisconnected();
            }

            @Override
            public void onInitialized(DeviceType deviceType) {

            }

            @Override
            public void onCmdResponse(DeviceType deviceType, byte[] result) {

            }

            @Override
            public void onDataResponse(DeviceType deviceType, byte[] data) {
                String response = ByteUtils.bytes2HexString(data);
                previousResponse.append(response);
                Log.d(TAG, "onCmdResponse下位机回传数据------->" + response);
                if (previousCmd.equalsIgnoreCase(FD_CMD) && previousResponse.length() == (38 * 2)) {
                    Log.d(TAG, "------>开始解析同步数据...");
                    byte[] bytes = ByteUtils.hexString2Bytes(previousResponse.toString());
                    int count = bytes[5] & 0xff << 8 + bytes[6] & 0xff;
                    String softVersion = (bytes[17] & 0xff) + "." + (bytes[18] & 0xff) + "." + (bytes[19] & 0xff);
                    String protocolVersion = (bytes[20] & 0xff) + "." + (bytes[21] & 0xff) + "." + (bytes[22] & 0xff);
                    Log.d(TAG, "\n ------>软件版本号:  " + softVersion + "  协议版本号:  " + protocolVersion);
                    onQuery();

                } else if (previousCmd.equalsIgnoreCase(FE_CMD) && previousResponse.length() == 38 * 2) {
                    Log.d(TAG, "\n ------>开始解析查询数据...");
                    byte[] bytes = ByteUtils.hexString2Bytes(previousResponse.toString());
                    validCount = ((bytes[7] & 0xff) << 24) + ((bytes[8] & 0xff) << 16) + ((bytes[9] & 0xff) << 8) + (bytes[10] & 0xff);
                    validTimeCount = ((bytes[11] & 0xff) << 24) + ((bytes[12] & 0xff) << 16) + ((bytes[13] & 0xff) << 8) + (bytes[14] & 0xff);
                    Log.d(TAG, "\n ------>总共有" + validCount + "帧血氧数据需要上传以及  " + validTimeCount + "帧开关机数据需要同步");
                    if (validCount > 0) {
                        //有血氧脉率数据 取全部数据
                        onAllData();
                    } else {
                        getTimeData();
                    }

                } else if (previousCmd.contains(FA_CMD) && response.startsWith("55aa") && response.length() >= 14) {
                    byte[] bytes = ByteUtils.hexString2Bytes(response);
                    Log.d(TAG, "-------->previousResponse删除前所有的数据..." + response);
                    validLength = ((bytes[5] & 0xff) << 8) + (bytes[6] & 0xff);
                    Log.d(TAG, "\n ------>有效数据长度是..." + validLength);
                    Log.d(TAG, "validLength--->" + validLength);
                    previousResponse.setLength(0);

                } else if (previousCmd.contains(FA_CMD) && previousResponse.length() >= (validLength * 2 + 2)) {
                    // 至少收到一帧数据包 解析 解析一帧扔掉后继续解析不够一帧数据 留着等待下次触发的时候继续解析
                    previousResponse.delete((validLength * 2), (validLength * 2) + 2);
                    historyData = historyData.append(previousResponse);
                    curPosition++;
                    Log.d(TAG, "\n ------>开始解析第" + curPosition + "帧数据接收数据");
                    Log.d(TAG, "curPosition--->" + curPosition);
                    if (curPosition >= validCount) {
                        previousCmd = "";
                        previousResponse.setLength(0);
                        curPosition = 0;
                        if (validTimeCount > 0) {
                            onOnOffTime();
                            Log.d(TAG, "\n ------>获取开关机的数据...");
                        }
                    }
                } else if (previousCmd.equalsIgnoreCase(FB_CMD) && response.startsWith("55aa") && response.length() >= 14) {
                    byte[] bytes = ByteUtils.hexString2Bytes(response);
                    validTimeLength = ((bytes[5] & 0xff) << 8) + (bytes[6] & 0xff);
                    Log.d(TAG, "开关机有效数据长度是: " + response);
                    previousResponse.setLength(0);
                } else if (previousCmd.contains(FB_CMD) && previousResponse.length() >= (validTimeLength * 2) + 2) {
                    previousResponse.delete((validTimeLength * 2), (validTimeLength * 2) + 2);
                    Log.d(TAG, "\n ------>开始解析第" + curTimePosition + "帧开关机数据接收数据");
                    Log.d(TAG, "time--->" + previousResponse.toString());
                    curTimePosition++;
                    historyTime.append(previousResponse.toString());
                    if (curTimePosition >= validTimeCount) {
                        mlistener.onRecordDataResponse(historyData.toString(), historyTime.toString());
                        previousCmd = "";
                        previousResponse.setLength(0);
                        resetDataIndex();
                        onClearOrder();
                    }

                } else if (previousCmd.contains(F4_CMD) && previousResponse.length() == (38 * 2)) {
                    //计算校验和
                    String subResponse = previousResponse.substring(4, previousResponse.length() - 2);
                    String sum = ByteUtils.makeChecksum(subResponse);
                    if (sum.equalsIgnoreCase(previousResponse.substring(previousResponse.length() - 2, previousResponse.length()))) {
                        Log.d(TAG, "\n ------>清除数据成功..");
                        setTime();
                        sendCmd(F7_CMD + timeLower + ByteUtils.makeChecksum("f7" + timeLower), 1000);
                    } else {
                        Log.d(TAG, "\n ------>清除数据失败..");
                    }
                } else if (previousCmd.contains(F7_CMD) && previousResponse.length() == (38 * 2)) {
                    String subResponse = previousResponse.substring(4, previousResponse.length() - 2);
                    String sum = ByteUtils.makeChecksum(subResponse);
                    if (sum.equalsIgnoreCase(previousResponse.substring(previousResponse.length() - 2, previousResponse.length()))) {
                        Log.d(TAG, "\n ------>低位设置时间成功..");
                        sendCmd(F8_CMD + timeHigh + ByteUtils.makeChecksum("f8" + timeHigh), 1000);
                    } else {
                        Log.d(TAG, "\n ------>低位设置时间失败..");
                    }

                } else if (previousCmd.contains(F8_CMD) && previousResponse.length() == (38 * 2)) {
                    String subResponse = previousResponse.substring(4, previousResponse.length() - 2);
                    String sum = ByteUtils.makeChecksum(subResponse);
                    if (sum.equalsIgnoreCase(previousResponse.substring(previousResponse.length() - 2, previousResponse.length()))) {
                        Log.d(TAG, "\n ------>高位设置时间成功..");
                        onSyncQuit();
                    } else {
                        Log.d(TAG, "\n ------>高位设置时间失败..");
                    }

                } else if (previousCmd.contains(F3_CMD) && previousResponse.length() == (38 * 2)) {
                    //计算校验和
                    String subResponse = previousResponse.substring(4, previousResponse.length() - 2);
                    String sum = ByteUtils.makeChecksum(subResponse);
                    if (sum.equalsIgnoreCase(previousResponse.substring(previousResponse.length() - 2, previousResponse.length()))) {
                        Log.d(TAG, "\n ------>退出同步成功..");
                        resetDataIndex();
                        mlistener.exitSuccess();

                    } else {
                        Log.d(TAG, "\n ------>退出同步失败..");
                    }
                } else if (previousCmd.contains(RT_CMD)) {
                    while (previousResponse.length() >= 6) {
                        //先找包头
                        int headIndex = findHead(previousResponse.toString());
                        if (headIndex != -1) {
                            pacCount.addAndGet(1);
                            previousResponse.delete(0, headIndex);
                            if (previousResponse.length() >= 6) {
                                String temp6Bytes = previousResponse.substring(0, 6);
                                previousResponse.delete(0, 6);

                                byte[] bytes = ByteUtils.hexString2Bytes(temp6Bytes);
                                String strHead = Integer.toBinaryString(bytes[0] & 0xff);
                                if (strHead.substring(1, 2).equals("0")) {
                                    //有手指
                                    String str1 = String.format("%08d", Integer.parseInt(Integer.toBinaryString(bytes[1] & 0xff)));
                                    int b0 = Integer.parseInt(str1.substring(str1.length() - 1, str1.length()));
                                    int b1 = Integer.parseInt(str1.substring(str1.length() - 2, str1.length() - 1));
                                    int b2 = Integer.parseInt(str1.substring(str1.length() - 3, str1.length() - 2));
                                    int HR = Integer.parseInt(str1.substring(str1.length() - 4, str1.length() - 3));
                                    int realTimeResult = bytes[2] & 0xff;
                                    if ((b0 == b1) && (b1 == b2) && (b2 == 0)) {
                                        //血氧波形数据
                                        Log.d(TAG, "\n ------>血氧波形数据.." + realTimeResult);
                                        mlistener.onRealTimeWaveData(realTimeResult * 1.0f);

                                    } else if ((b2 == 0) && (b1 == 0) && (b0 == 1)) {
                                        Log.d(TAG, "\n ------>血氧   数据.." + realTimeResult);
                                        mlistener.onRealTimeSpoData(realTimeResult);
                                        pacCount = new AtomicInteger(0);
                                    } else if ((b2 == 0) && (b1 == 1) && (b0 == 0)) {
                                        if (HR == 1) {
                                            realTimeResult += 128;
                                        }
                                        Log.d(TAG, "\n ------>脉率   数据.." + realTimeResult);
                                        mlistener.onRealTimePRData(realTimeResult);
                                    }
                                } else {
                                    //没有手指
                                    mlistener.onRealTimeNone();
                                }
                            }
                        }
                    }
                }
            }
        });

    }


    /**
     * 设置时间
     */
    private void setTime() {
        String time = FormatUtils.getDateTimeString(Calendar.getInstance().getTime(), FormatUtils.sleep_DbDateTime);
        String[] split = time.split("-");
        int year = Integer.parseInt(split[0]) - 2000;
        int month = Integer.parseInt(split[1]);
        int day = Integer.parseInt(split[2]);
        int hour = Integer.parseInt(split[3]);
        int minute = Integer.parseInt(split[4]);
        int second = Integer.parseInt(split[5]);

        byte b1, b2, b3, b4;
        b1 = (byte) (((month & 0x3) << 6) + (year & 0x3f));
        b2 = (byte) (((hour & 1) << 7) + ((day & 0x1f) << 2) + ((month >> 2) & 0x3));

        b3 = (byte) (((minute & 0xf) << 4) + ((hour >> 1) & 0xf));
        b4 = (byte) (((second & 0x3f) << 2) + ((minute >> 4) & 0x3));
        timeLower = String.format("%02x", b2) + String.format("%02x", b1);
        timeHigh = String.format("%02x", b4) + String.format("%02x", b3);
    }

    private void getTimeData() {
        if (validTimeCount > 0) {
           onOnOffTime();
        } else {
            onSyncQuit();
        }
    }


    private void resetDataIndex() {
        curPosition = 0;
        curTimePosition = 0;
        validCount = 0;
        validTimeCount = 0;
        validLength = 0;
        validTimeLength = 0;
        historyData.setLength(0);
        historyTime.setLength(0);
    }


    private synchronized void changeState(int state) {
        if (state == bleState) {
            return;
        }
        Log.d(TAG, "onStateChanged" + bleState + " new state " + state);
        bleState = state;
        mlistener.onStateChanged(bleState, state);
    }

    public BaseCommand getBaseCommand(ICommandCreator iCommandCreator) {
        return iCommandCreator.createCommand(w314b4Ble);
    }

    /**
     * 绑定设备方法
     */
    public void bindDevice() {
        bindOrConnectState = false;
        baseCommand = getBaseCommand(new IScanBleCommandFactory());
        baseCommand.execute();
        changeState(BLUETOOTH_SCANNING);
    }

    public void connectDevice(Device device) {
        bindOrConnectState = true;
        ConnectDeviceCommand connectDeviceCommand =
                (ConnectDeviceCommand)getBaseCommand(new IConnectDeviceCommandFactory());
        connectDeviceCommand.setAddress(device.getDeviceMacAddress());
        connectDeviceCommand.execute();
        changeState(BLUETOOTH_CONNECTING_DEVICE);
    }

    public void stopScan(){
        w314b4Ble.stopLeScan();
    }

    /**
     * 断开连接
     */
    public void disconnectDevice() {
        baseCommand = getBaseCommand(new IDisconnectDeviceCommandFactory());
        baseCommand.execute();
    }

    public void onRealTimeStart(){
        sendCmd(RT_CMD,1000);
        changeState(BLUETOOTH_REALTIME_START);
    }

    public void onRealTimeQuit(int during){
        sendCmd(RQ_CMD,during);
        changeState(BLUETOOTH_REALTIME_QUIT);
    }

    public void onSync(){
        sendCmd(FD_CMD,1000);
        changeState(BLUETOOTH_SYNC);
    }

    public void onQuery(){
        sendCmd(FE_CMD,2000);
        changeState(BLUETOOTH_QUERY);
    }

    public void onAllData(){
        sendCmd(FA_CMD,1000);
        changeState(BLUETOOTH_ALL_DATA);
    }

    public void onOnOffTime(){
        sendCmd(FB_CMD,1000);
        changeState(BLUETOOTH_ON_OFF_TIME);
    }

    public void onSyncQuit(){
        sendCmd(F3_CMD,1000);
        changeState(BLUETOOTH_SYNC_QUIT);
    }

    public void onClearOrder(){
        sendCmd(F4_CMD,1000);
        changeState(BLUETOOTH_CLEAR_ORDER);
    }

    private synchronized int findHead(String response) {
        if (response == null || response.isEmpty()) {
            return -1;
        }
        int index = 0;
        String temp = response;
        char firstChar = temp.charAt(0);
        while (firstChar < '8') {
            if (temp.length() > 2) {
                temp = temp.substring(2, temp.length());
                index += 2;
                firstChar = temp.charAt(0);
            } else {
                return -1;
            }
        }
        return index;
    }

    public void sendCmd(String cmd, int during) {
        Log.d(TAG, "cmd---->" + cmd);
        Message msg = Message.obtain();
        Bundle data = new Bundle();
        data.putString("Command", cmd);
        msg.setData(data);
        msg.what = MSG_SEND_CMD;
        handler.sendMessageDelayed(msg, during);
    }

}
