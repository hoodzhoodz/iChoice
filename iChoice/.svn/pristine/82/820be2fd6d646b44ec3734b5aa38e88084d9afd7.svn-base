package com.choice.c208sdkblelibrary.gatt;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.choice.c208sdkblelibrary.base.BaseGattCallback;
import com.choice.c208sdkblelibrary.base.DeviceType;
import com.choice.c208sdkblelibrary.base.GattListener;
import com.choice.c208sdkblelibrary.utils.ByteUtils;
import com.choice.c208sdkblelibrary.utils.ErrorCode;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static com.choice.c208sdkblelibrary.utils.ErrorCode.ERROR_FOUND_SERVICE_FAIL;


public class C208sGattCallback extends BaseGattCallback {


    //SN服务
    private static final UUID Service_UUID_SpO2_SN = UUID
            .fromString("0000180a-0000-1000-8000-00805f9b34fb");
    //SN特征
    private static final UUID CDsn = UUID
            .fromString("00002a25-0000-1000-8000-00805f9b34fb");
    //电池服务
    private static final UUID Service_UUID_SpO2_Battery = UUID
            .fromString("0000180f-0000-1000-8000-00805f9b34fb");
    //读取电池特征
    private static final UUID CD19 = UUID
            .fromString("00002a19-0000-1000-8000-00805f9b34fb");

    //对表服务
    private static final UUID currentTimeService = UUID
            .fromString("00001805-0000-1000-8000-00805f9b34fb");
    //对表特征
    private static final UUID currentTime = UUID
            .fromString("00002a2b-0000-1000-8000-00805f9b34fb");

    //读取数据服务及通道
    private static final UUID Service_UUID = UUID
            .fromString("00001822-0000-1000-8000-00805f9b34fb");
    //Feature
    private static final UUID CD60 = UUID
            .fromString("00002a60-0000-1000-8000-00805f9b34fb");
    //RACP
    private static final UUID CD52 = UUID
            .fromString("00002a52-0000-1000-8000-00805f9b34fb");
    //点测
    private static final UUID CD5E = UUID
            .fromString("00002a5e-0000-1000-8000-00805f9b34fb");
    //连续
    private static final UUID CD5F = UUID
            .fromString("00002a5f-0000-1000-8000-00805f9b34fb");

    private static final String TAG = "C208sGattCallback";
    private static final int BATTERY_SERVICE = 0x01;
    private static final int SN_SERVICE = 0x02;
    private static final int CURRENT_TIME_SERVICE_FINISH = 0x03;
    private static final int PULSE_OX_FEATURE_SERVICE = 0x04;
    private BluetoothGatt gatt;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            BluetoothGattService service = (BluetoothGattService) msg.obj;
            switch (msg.what) {
                case BATTERY_SERVICE://电池电量服务
                    readCharacteristic(gatt, service, CD19);
                    break;
                case SN_SERVICE://SN服务
                    readCharacteristic(gatt, service, CDsn);
                    break;
                case CURRENT_TIME_SERVICE_FINISH://对表服务
                    Log.d(TAG, "对表服务延时结束！");
                    obtainBatteryService();
                    break;
                case PULSE_OX_FEATURE_SERVICE://血氧脉率服务
                    readCharacteristic(gatt, service, CD60);
                    break;
                default:
                    Log.d(TAG, "default:");
                    break;
            }
        }
    };
    private boolean supportedRACPState = true;//RACP
    private boolean supportedPIState = true;//PI
    private boolean supportedOngoningState = true;//测量进行中
    private boolean supportedDataSource = true;//数据来源
    private boolean supportedSPID = true;//信号不规则
    private boolean supportedLPD = true;//弱灌注
    private boolean supportedSUTU = true;//手指脱落


    private void readCharacteristic(BluetoothGatt gatt, BluetoothGattService service, UUID uuid) {
        if (service == null) {
            Log.d(TAG, "发现服务失败！");
            return;
        }
        BluetoothGattCharacteristic characteristicBattery = service.getCharacteristic(uuid);
        if (characteristicBattery == null) {
            Log.d(TAG, "获取服务特征失败：uuid:" + uuid);
            return;
        }
        if (gatt.readCharacteristic(characteristicBattery))
            Log.d(TAG, "读特征成功！uuid= " + uuid);
        else
            Log.d(TAG, "读特征失败！uuid= " + uuid);

    }


    public C208sGattCallback(GattListener gattListener) {
        super(gattListener);
    }

    @Override
    protected DeviceType getDeviceType() {
        return DeviceType.C208S;
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
            case BluetoothProfile.STATE_CONNECTED: {
                Log.d(TAG, "蓝牙已连接");
                onBleConnectSuccess();
                if (gatt.discoverServices()) {
                    Log.d(TAG, "发现服务启动");
                } else {
                    Log.d(TAG, "异常：开始发现服务失败");
                    gatt.close();
                    onError(ERROR_FOUND_SERVICE_FAIL);
                }
                break;
            }
            case BluetoothProfile.STATE_DISCONNECTED: {
                Log.d(TAG, "蓝牙已断开");
                gatt.close();
                onDisconnected();
                break;
            }
        }
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);
        this.gatt = gatt;
        if (status == BluetoothGatt.GATT_SUCCESS) {
            //initHandler();
            //监听时间校验特征
            inspectionTimeCompleted = false;
            BluetoothGattService serviceCurrentTime = discoverService(gatt, currentTimeService.toString());
            if (serviceCurrentTime == null) {
                Log.d(TAG, "发现校验时间服务失败！");
                obtainBatteryService();
                return;
            }
            BluetoothGattCharacteristic currentTimeCharacteristic = serviceCurrentTime.getCharacteristic(currentTime);
            if (setCharacteristicNotification(gatt, currentTimeCharacteristic, true)) {
                Log.d(TAG, "开始监听校验时间特征成功！");
                Message msg = new Message();
                msg.what = CURRENT_TIME_SERVICE_FINISH;
                handler.sendMessageDelayed(msg, 5000);
            } else
                Log.d(TAG, "开始监听校验时间特征失败！");

        } else {
            Log.d(TAG, "发现服务GATT异常： " + status);
        }
    }


    /**
     * 查找指定UUID的服务
     *
     * @param gatt        Gatt
     * @param serviceUUID 需要服务的UUID
     * @return 对应UUID的Service
     */
    private static BluetoothGattService discoverService(BluetoothGatt gatt, String serviceUUID) {

        List<BluetoothGattService> services = gatt.getServices();
        for (BluetoothGattService service : services) {

            if (service.getUuid().toString().equals(serviceUUID))
                return service;

        }
        return null;
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        Log.d(TAG, "onCharacteristicRead   :" + characteristic.getUuid());
        if (characteristic.getUuid().equals(CD19)) {//读电池电量成功
            Log.d(TAG, "读电池电量成功");
//            onDataReceivedBattery(characteristic.getValue());
            BluetoothGattService service = discoverService(gatt, Service_UUID_SpO2_SN.toString());//发现SN服务
            if (service == null) {
                Log.d(TAG, "发现SN服务失败！");
                return;
            }
            sendReadServiceMessage(SN_SERVICE, service, 0);
        }

        if (characteristic.getUuid().equals(CDsn)) {//读SN成功
            Log.d(TAG, "读取SN成功！");
//            onDataReceivedSN(characteristic.getValue());
            BluetoothGattService service = discoverService(gatt, Service_UUID.toString());//发现血氧服务
            if (service == null) {
                Log.d(TAG, "发现血氧服务失败！");
                return;
            }
            sendReadServiceMessage(PULSE_OX_FEATURE_SERVICE, service, 0);
        }

        if (characteristic.getUuid().equals(CD60)) {//读血氧特征值成功
            Log.d(TAG, "读取血氧服务信息特征成功！");
            checkSpo2ServiceList(characteristic.getValue());
            sendFeature();//回转解析出的Feature
            BluetoothGattService service = discoverService(gatt, Service_UUID.toString());//发现血氧服务
            if (service == null) {
                Log.d(TAG, "发现血氧服务失败！");
                return;
            }
            BluetoothGattCharacteristic spotCheckCharacteristic = service.getCharacteristic(CD5E);
            if (spotCheckCharacteristic != null) {
                Log.d(TAG, "获取到点测特征！");
                if (setCharacteristicIndication(gatt, spotCheckCharacteristic, true)) {
                    Log.d(TAG, "开始监听点测特征成功！");
                } else {
                    Log.d(TAG, "开始监听点测特征失败！");
                }
            } else {
                BluetoothGattCharacteristic continuousCharacteristic = service.getCharacteristic(CD5F);
                if (setCharacteristicNotification(gatt, continuousCharacteristic, true)) {
                    Log.d(TAG, "开始监听实时特征成功！");
                } else {
                    Log.d(TAG, "开始监听实时特征失败！");
                }
            }
        }
    }

    private void sendFeature() {
        HashMap<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("RACP", supportedRACPState);
        map.put("PI", supportedPIState);
        map.put("OnGoning", supportedOngoningState);
        map.put("DataSource", supportedDataSource);
        map.put("SPID", supportedSPID);
        map.put("LPD", supportedLPD);
        map.put("SUTU", supportedSUTU);
        onDataReceivedFeature(map);
    }


    /**
     * 发送
     *
     * @param what
     * @param service
     * @param delayTimes
     */
    private void sendReadServiceMessage(int what, BluetoothGattService service, int delayTimes) {
        Message msg = new Message();
        msg.what = what;
        msg.obj = service;
        handler.sendMessageDelayed(msg, delayTimes);
    }

    private void checkSpo2ServiceList(byte[] data) {
        //4f 00    20 00    24 08 00
        parseCD60SupportedFeatures(data);
        parseCD60SupportedMeasureStatus(data);
        parseCD60SupportedSensorStatus(data);

    }

    /**
     * 解析血氧服务Feature特征中DeviceAndSensorStatusSupport部分
     *
     * @param data Cd60特征数据
     */
    private void parseCD60SupportedSensorStatus(byte[] data) {
        if (!supportedRACPState)
            return;
        String feature = ByteUtils.hexStringReverse(ByteUtils.bytes2HexString(data).substring(8, 14));
        String binary = ByteUtils.hexString2binaryString(feature);
        StringBuilder builder = new StringBuilder(binary);
        builder.reverse();
        Log.d(TAG, "builder:" + builder);
        char[] bits = builder.toString().toCharArray();
        if (bits[2] != '1') {
            supportedSPID = false;
            Log.d(TAG, "检测到设备不支持“信号不规则”检测");
        }
        if (bits[5] != '1') {
            supportedLPD = false;
            Log.d(TAG, "检测到设备不支持“弱灌注”检测");
        }
        if (bits[11] != '1') {
            supportedSUTU = false;
            Log.d(TAG, "检测到设备不支持“手指脱落”检测");
        }
    }

    /**
     * 解析血氧服务Feature特征中MeasureStatusSupport部分
     *
     * @param data Cd60特征数据
     */
    private void parseCD60SupportedMeasureStatus(byte[] data) {
        if (!supportedRACPState)
            return;
        String feature = ByteUtils.hexStringReverse(ByteUtils.bytes2HexString(data).substring(4, 8));
        Log.d(TAG, "feature:" + feature);
        String binary = ByteUtils.hexString2binaryString(feature);
        Log.d(TAG, "binary:" + binary);
        StringBuilder builder = new StringBuilder(binary);
        builder.reverse();
        Log.d(TAG, "builder:" + builder);
        char[] bits = builder.toString().toCharArray();
        if (bits[5] != '1') {
            supportedOngoningState = false;
            Log.d(TAG, "检测到设备不支持“测量中”");
        }
        if (bits[9] != '1') {
            supportedDataSource = false;
            Log.d(TAG, "检测到设备不支持“数据来源自设备存储”");
        }
    }

    /**
     * 解析血氧服务Feature特征中SupportedFeatures部分
     *
     * @param data Cd60特征数据
     */
    private void parseCD60SupportedFeatures(byte[] data) {
//        43002000240800
        Log.d(TAG, ByteUtils.bytes2HexString(data));
        String feature = ByteUtils.hexStringReverse(ByteUtils.bytes2HexString(data).substring(0, 2));
        String binary = ByteUtils.hexString2binaryString(feature);
        Log.d(TAG, "feature:" + feature);
        Log.d(TAG, "binary:" + binary);
        StringBuilder builder = new StringBuilder(binary);
        builder.reverse();
        Log.d(TAG, "builder:" + builder);
        char[] bits = builder.toString().toCharArray();
        if (bits[0] != '1' || bits[1] != '1') {
//            onError("设备异常");
            Log.d(TAG, "设备异常:不支持测量状态项或设备与传感器项");
            return;
        }

        if (bits[6] != '1') {
            supportedPIState = false;
            Log.d(TAG, "检测到设备不支持PI值");
        }

        if (bits[2] != '1' || bits[3] != '1') {
            supportedRACPState = false;
            Log.d(TAG, "检测到设备不支持点测存储");
        } else {
            Log.d(TAG, "检测到设备支持点测存储");
        }
    }
    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);
        byte[] data = characteristic.getValue();
        String spotCheckData = ByteUtils.bytes2HexString(data);
        Log.d(TAG, "onCharacteristicChanged: " + spotCheckData);
        if (data == null || data.length <= 0) {
            Log.d(TAG, "接收到异常数据！" + spotCheckData);
            return;
        }
        if (characteristic.getUuid().equals(currentTime)) {//校验时间命令回复
            Log.d(TAG, "时间校验成功：" + ByteUtils.bytes2HexString(data));
            handler.removeMessages(CURRENT_TIME_SERVICE_FINISH);
            if (!inspectionTimeCompleted)
                obtainBatteryService();
        }
        if (characteristic.getUuid().equals(CD5E)) {
            //            2760004200e407061b0e0d232000200003
            onSpotCheckResponse(data);
        }
        if (characteristic.getUuid().equals(CD5F)) {

            onRealTimeResponse(data);
        }
        if (characteristic.getUuid().equals(CD52)) {
            onRACPResponse(data);
        }
    }


    private boolean inspectionTimeCompleted = false;

    private void obtainBatteryService() {
        inspectionTimeCompleted = true;
        BluetoothGattService serviceBattery = discoverService(gatt, Service_UUID_SpO2_Battery.toString());//发现电池服务
        if (serviceBattery != null) {
            Log.d(TAG, "发现读电池电量服务");
            sendReadServiceMessage(BATTERY_SERVICE, serviceBattery, 500);
            return;
        }
        Log.d(TAG, "发现读电池电量服务失败！");
        BluetoothGattService serviceSN = discoverService(gatt, Service_UUID_SpO2_SN.toString());//发现SN服务
        if (serviceSN != null) {
            Log.d(TAG, "发现SN服务成功！");
            sendReadServiceMessage(SN_SERVICE, serviceSN, 500);
        }
    }

    @Override
    public void onDescriptorWrite(final BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorWrite(gatt, descriptor, status);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            BluetoothGattService service = descriptor.getCharacteristic().getService();
            if (descriptor.getCharacteristic().getUuid().equals(currentTime)) {
                Log.d(TAG, "监听校验时间特征成功！");
                if (sendCmd(gatt, service.getCharacteristic(currentTime), obtainCurrentTimeCommand()))
                    Log.d(TAG, "发送校验时间命令成功！");
                else
                    Log.d(TAG, "发送校验时间命令失败！");
            }


            if (descriptor.getCharacteristic().getUuid().equals(CD5E)) {
                Log.d(TAG, "点测血氧特征2A5E监听成功!");
                BluetoothGattCharacteristic characteristic = service.getCharacteristic(CD5F);
                if (setCharacteristicNotification(gatt, characteristic, true))
                    Log.d(TAG, "开始监听连续血氧特征2A5F！");
                else
                    Log.d(TAG, "连续血氧特征2A5F监听失败！");
            }

            if (descriptor.getCharacteristic().getUuid().equals(CD5F)) {
                Log.d(TAG, "连续血氧特征2A5F监听成功!");
//                if (supportedRACPState) {
                BluetoothGattCharacteristic characteristic = service.getCharacteristic(CD52);
                if (setCharacteristicIndication(gatt, characteristic, true))
                    Log.d(TAG, "开始监听RACP！");
                else
                    Log.d(TAG, "开始监听RACP失败！");
//                } else {
//                    Log.d(TAG, "初始化完成！");
//                    onInitialized();
//                }
            }
            if (descriptor.getCharacteristic().getUuid().equals(CD52)) {
                Log.d(TAG, "RACP监听成功！");
                onInitialized();
            }
        } else {
            Log.d(TAG, "GATT异常：" + status);
        }

    }


    /**
     * 获取校验时间命令
     *
     * @return 校验时间命令
     */
    private String obtainCurrentTimeCommand() {
        Calendar calendar = Calendar.getInstance();
        //年
        String year = Integer.toHexString(calendar.get(Calendar.YEAR));
        year = ByteUtils.coverByte(year, "0");
        year = ByteUtils.hexStringReverse(year);

        //月
        String month = Integer.toHexString(calendar.get(Calendar.MONTH) + 1);
        month = ByteUtils.coverByte(month, "0");

        //日
        String day = Integer.toHexString(calendar.get(Calendar.DAY_OF_MONTH));
        day = ByteUtils.coverByte(day, "0");


        //时
        String hour = Integer.toHexString(calendar.get(Calendar.HOUR_OF_DAY));
        hour = ByteUtils.coverByte(hour, "0");


        //时
        String minute = Integer.toHexString(calendar.get(Calendar.MINUTE));
        minute = ByteUtils.coverByte(minute, "0");

        //时
        String second = Integer.toHexString(calendar.get(Calendar.SECOND));
        second = ByteUtils.coverByte(second, "0");

        //周
        int temp = calendar.get(Calendar.DAY_OF_WEEK);
        if (temp == Calendar.SUNDAY)
            temp = 7;
        else
            temp = temp - 1;
        String week = Integer.toHexString(temp);
        week = ByteUtils.coverByte(week, "0");
        return String.format(Locale.getDefault(), "%s%s%s%s%s%s%s%s", year, month, day, hour, minute, second, week, "0000");
    }


    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicWrite(gatt, characteristic, status);
        Log.d(TAG, "---onCharacteristicWrite完成---");
    }

    /**
     * 发送命令
     *
     * @param gatt           Gatt
     * @param characteristic 特征
     * @param command        命令
     * @return 命令发送情况
     */
    public static boolean sendCmd(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, String command) {
        Log.d(TAG, "发送命令：" + command);
        byte[] cmd = ByteUtils.cmdString2Bytes(command, false);
        characteristic.setValue(cmd);
        return gatt.writeCharacteristic(characteristic);
    }

    /**
     * 发送RACP命令
     *
     * @param gatt    GATT
     * @param command 命令
     * @return 命令发送情况
     */
    public static boolean sendCmd(BluetoothGatt gatt, String command) {
        BluetoothGattService service = discoverService(gatt, Service_UUID.toString());
        if (service == null)
            return false;
        BluetoothGattCharacteristic RACPCharacteristic = service.getCharacteristic(CD52);
        byte[] value = ByteUtils.cmdString2Bytes(command, false);
        Log.d(TAG, "发送RACP命令：" + command);
        RACPCharacteristic.setValue(value);
        return gatt.writeCharacteristic(RACPCharacteristic);
    }
}
