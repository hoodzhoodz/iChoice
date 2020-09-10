package com.choicemmed.ichoice.healthcheck.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.choice.c208sdkblelibrary.base.DeviceType;
import com.choice.c208sdkblelibrary.cmd.callback.C208CancelConnectCallback;
import com.choice.c208sdkblelibrary.cmd.callback.C208DisconnectCallBack;
import com.choice.c208sdkblelibrary.cmd.callback.C208sConnectAandDataResponseCallback;
import com.choice.c208sdkblelibrary.cmd.invoker.C208sInvoker;
import com.choice.c208sdkblelibrary.device.C208;
import com.choice.c208sdkblelibrary.utils.ByteUtils;
import com.choice.c208sdkblelibrary.utils.ErrorCode;
import com.choicemmed.common.FormatUtils;
import com.choicemmed.common.LogUtils;
import com.choicemmed.common.ThreadManager;
import com.choicemmed.common.UuidUtils;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.framework.utils.DevicesType;
import com.choicemmed.ichoice.healthcheck.db.DeviceOperation;
import com.choicemmed.ichoice.healthcheck.db.UserOperation;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import pro.choicemmed.datalib.DeviceInfo;
import pro.choicemmed.datalib.OxRealTimeData;
import pro.choicemmed.datalib.OxSpotData;
import pro.choicemmed.datalib.UserProfileInfo;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

import static com.choicemmed.ichoice.framework.base.BaseDb.getDaoSession;

public class C208sBleConService extends Service {
    public static final String TAG = "C208sBleConService";
    private List<DeviceInfo> list;
    Subscription c208Subscription;
    C208 c208s;
    C208sInvoker c208sInvoker;
    C208sBleConService.ScanBleBinder binder = new ScanBleBinder();
    private HashMap<String, Boolean> feature = new HashMap<String, Boolean>();
    private UserProfileInfo userProfileInfo;
    private boolean timestampPresent = true;
    private boolean spotCheckPI = true;
    private boolean deviceClockSet = true;
    private boolean spotCheckRR = true;
    //    workingMode 1 点测，2 实时。
    private int workingMode;

    public void setWorkingMode(int workingMode) {
        this.workingMode = workingMode;
    }

    private String lastCMD = "";


    private static final String reportNumberOfStoredRecordsCmd = "040100";
    private static final String deleteStoredRecordsCmd = "020100";
    private static final String reportStoredRecordsCmd = "010100";
    private int numberOfRecords;
    private List<OxSpotData> history = new ArrayList<>();


    private String measureBeginTime;
    private String measureEndTime;
    private int countNum = 0;
    /**
     * 缓存实时数据，多线程情况下StringBuffer线程安全
     */
    private StringBuffer realTimeData = new StringBuffer();

    private long realModeBeginTime = 0;
    private boolean spo2PrFastPresent = true;
    private boolean spo2PrSlowPresent = true;
    private boolean continuousPI = true;
    private boolean continuousRR = true;
    private int storeLastOrderNum = -1;

    /**
     * 连续手指脱落次数
     */
    private int continuationFingerOutTimes;

    private void initSpotCheckState() {
        timestampPresent = true;
        spotCheckPI = true;
        deviceClockSet = true;
        spotCheckRR = true;
    }

    public C208sBleConService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return binder;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        UserOperation userOperation = new UserOperation(this);
        userProfileInfo = userOperation.queryByUserId(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        if (userProfileInfo != null) {
            c208sInvoker = new C208sInvoker(this);
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public class ScanBleBinder extends Binder {
        ScanBleBinder getService() {
            Log.d(TAG, "getService");
            return ScanBleBinder.this;
        }

        public void starConnectBle() {
            LogUtils.d(TAG, "----startConnectBle-----");
            init();
        }

        public void setMode(int workingMode) {
            setWorkingMode(workingMode);
        }
        public void cancelConnect() {
            LogUtils.d(TAG, "关闭Gatt,取消所有待连接！");
            c208sInvoker.cancelConnectDevice(new C208CancelConnectCallback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(DeviceType deviceType, int errorMsg) {

                }
            });

        }

        public void disconnect() {
            LogUtils.d(TAG, "断开蓝牙连接");
            c208sInvoker.disConnectDevice(new C208DisconnectCallBack() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(DeviceType deviceType, int errorMsg) {

                }
            });
        }
    }

    private void init() {
        list = new DeviceOperation(this).queryByDeviceType(IchoiceApplication.getAppData().userProfileInfo.getUserId(), DevicesType.PulseOximeter);
        if (list.isEmpty()) {
            LogUtils.d(TAG, "devices：没有血氧设备");
            return;
        }
        openBluetoothBle();
        LogUtils.d(TAG, "devices：" + list.toString());
        searchDevice(list);
        LogUtils.d(TAG, "结束了：searchDevice");
    }

    private void openBluetoothBle() {
        BluetoothManager bluetoothManager = (BluetoothManager) C208sBleConService.this.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
            LogUtils.d(TAG, "检测到蓝牙未打开：重新开启蓝牙！");
            bluetoothAdapter.enable();//不弹对话框直接开启蓝牙
        }
    }

    private void searchDevice(List<DeviceInfo> list) {
        if (list.size() != 0) {
            final DeviceInfo deviceInfo = list.get(0);
            ThreadManager.execute(new Runnable() {
                @Override
                public void run() {
                    c208s = new C208();
                    c208s.setDeviceName(deviceInfo.getDeviceName());
                    c208s.setMacAddress(deviceInfo.getBluetoothId());
                    LogUtils.d(TAG, "绑定设备" + deviceInfo.getDeviceName());
                    connectC208(c208s);
                }
            });

        } else {
            LogUtils.d(TAG, "没有绑定设备");
        }
    }

    private void connectC208(final C208 c208s) {
        c208sInvoker.connectDevice(new C208sConnectAandDataResponseCallback() {
            @Override
            public void onSpotCheckResponse(DeviceType deviceType, byte[] data) {
                if (workingMode == 1) {
                    analysisPointData(data);
                }
            }

            @Override
            public void onRealTimeResponse(DeviceType deviceType, byte[] data) {
                if (workingMode == 2) {
//                2c 6200 3f00 2000 000035
                    String continuousData = ByteUtils.bytes2HexString(data);
                    LogUtils.d(TAG, "连续血氧数据:" + continuousData);
                    initContinuousState();

                    String flag = continuousData.substring(0, 2);
                    String binaryFlag = ByteUtils.hexString2binaryString(flag);
                    StringBuilder builder = new StringBuilder(binaryFlag);
                    builder.reverse();
                    char[] continuousFlag = builder.toString().toCharArray();

                    if (continuousFlag[2] != '1' || continuousFlag[3] != '1') {
                        LogUtils.d(TAG, "连续血氧数据不支持“测量状态”或“设备与传感器”");
                        LogUtils.d(TAG, "异常的连续血氧数据");
                        return;
                    }
                    if (continuousFlag[0] != '1') {
                        spo2PrFastPresent = false;
                        LogUtils.d(TAG, "连续血氧不支持快速血氧！");
                    }
                    if (continuousFlag[1] != '1') {
                        spo2PrSlowPresent = false;
                        LogUtils.d(TAG, "连续血氧不支持慢速血氧");
                    }
                    if (continuousFlag[4] != '1') {
                        continuousPI = false;
                        LogUtils.d(TAG, "连续血氧不支持PI");
                    }
                    if (continuousFlag[5] != '1') {
                        continuousRR = false;
                        Log.d(TAG, "实时数据不支持呼吸率");
                    }
                    //数据公式为：数据=尾数 X（10^指数）解析血氧脉率
                    byte oxHigh = data[2];
                    byte oxLow = data[1];
                    //右移4位屏蔽尾数影响
                    int oxIndex = (oxHigh >> 4);
                    //左移4位屏蔽掉SFloat中的指数位
                    byte oxLeftResult = (byte) (oxHigh << 4);
                    //右移4位屏蔽掉因左移放大的倍数
                    byte oxRightResult = (byte) (oxLeftResult >> 4);
                    //拼接后的尾数
                    short oxMantissa = (short) ((oxRightResult << 8) | (oxLow & 0x00ff));
                    int oxValue = (int) (oxMantissa * Math.pow(10, oxIndex));

                    byte prHigh = data[4];
                    byte prLow = data[3];
                    //右移4位屏蔽尾数影响
                    int prIndex = (prHigh >> 4);
                    //左移4位屏蔽掉SFloat中的指数位
                    byte prLeftResult = (byte) (prHigh << 4);
                    //右移4位屏蔽掉因左移放大的倍数
                    byte prRightResult = (byte) (prLeftResult >> 4);
                    //拼接后的尾数
                    short prMantissa = (short) ((prRightResult << 8) | (prLow & 0x00ff));
                    int prValue = (int) (prMantissa * Math.pow(10, prIndex));

                    LogUtils.d(TAG, "连续血氧：" + oxValue + " 脉率：" + prValue);

                    String DSS;
                    String dataSource;
                    byte high = data[0];
                    byte low = data[0];
                    int RR = 0;
                    //都支持
                    if (spo2PrFastPresent && spo2PrSlowPresent) {
                        dataSource = ByteUtils.hexStringReverse(continuousData.substring(26, 30));
                        DSS = ByteUtils.hexStringReverse(continuousData.substring(30, 36));
                        if (continuousPI) {
                            high = data[19];
                            low = data[18];

                        }
                        if (continuousRR) {
                            RR = data[17] & 0xff;
                        }

                        //只支持一个
                    } else if (spo2PrFastPresent || spo2PrSlowPresent) {
                        dataSource = ByteUtils.hexStringReverse(continuousData.substring(18, 22));
                        DSS = ByteUtils.hexStringReverse(continuousData.substring(22, 28));
                        if (continuousPI) {
                            high = data[15];
                            low = data[14];

                        }
                        if (continuousRR) {
                            RR = data[13] & 0xff;
                        }

                    } else {//都不支持
                        if (continuousRR) {
                            RR = data[9] & 0xff;
                        }
                        if (continuousPI) {
                            high = data[11];
                            low = data[10];

                        }
                        dataSource = ByteUtils.hexStringReverse(continuousData.substring(10, 14));
                        DSS = ByteUtils.hexStringReverse(continuousData.substring(14, 20));
                    }


                    //解析设备与传感器
                    StringBuilder DSSBuilder = new StringBuilder(ByteUtils.hexString2binaryString(DSS));
                    DSSBuilder.reverse();
                    char[] deviceSensorStatus = DSSBuilder.toString().toCharArray();
                    if (deviceSensorStatus[2] == '1') {
                        LogUtils.d(TAG, "信号不规则！");
                    }
                    if (deviceSensorStatus[5] == '1') {
                        LogUtils.d(TAG, "弱灌注！");
                    }

                    //测量状态
                    StringBuilder dataSourceBuilder = new StringBuilder(ByteUtils.hexString2binaryString(dataSource));
                    dataSourceBuilder.reverse();
                    char[] measurementStatus = dataSourceBuilder.toString().toCharArray();
                    if (measurementStatus[9] == '1') {
                        LogUtils.d(TAG, "数据来自历史记录！");
                    } else {
                        LogUtils.d(TAG, "数据来自当前连续测！");
                    }

                    float piValue = 0;
                    //解析PI
                    if (continuousPI) {
                        //右移4位屏蔽尾数影响
                        int index = (high >> 4);
                        //左移4位屏蔽掉SFloat中的指数位
                        byte leftResult = (byte) (high << 4);
                        //右移4位屏蔽掉因左移放大的倍数
                        byte rightResult = (byte) (leftResult >> 4);
                        //拼接后的尾数
                        short mantissa = (short) ((rightResult << 8) | (low & 0x00ff));
                        piValue = (float) (mantissa * Math.pow(10, index));
                        LogUtils.d(TAG, "连续血氧的pi:" + new DecimalFormat("0.0").format(piValue));
                    }

                    //实时波形从第一个血氧脉率有效值的点开始计数，
                    if (realModeBeginTime == 0 && oxValue >= 60 && oxValue <= 100 && prValue >= 40 && prValue <= 250) {
                        realModeBeginTime = System.currentTimeMillis();
                        measureBeginTime = FormatUtils.getDateTimeString(realModeBeginTime, FormatUtils.template_DbDateTime);
                        beginLoopSave();
                    }


                    //计数开始后存储血氧脉率数据、绘制血氧脉率波形
                    if (realModeBeginTime != 0) {

                        if (deviceSensorStatus[11] == '1') {
                            continuationFingerOutTimes++;
                            //当连续三次手指脱落时，分开存储
                            if (continuationFingerOutTimes >= 3 && oxValue >= 60 && oxValue <= 100 && prValue >= 40 && prValue <= 250) {
                                // TODO: 2018/5/25  手指脱落记录下一条
                                Log.e(TAG, "检测到连续三次手指脱落！");
                                continuationFingerOutTimes = 0;
                                realModeBeginTime = 0;
                                return;

                            }

                            LogUtils.d(TAG, "手指脱落！ 连续次数" + continuationFingerOutTimes);
                        } else if (oxValue >= 60 && oxValue <= 100 && prValue >= 40 && prValue <= 250) {
                            continuationFingerOutTimes = 0;
                        }
                        long timeStamp = System.currentTimeMillis() - realModeBeginTime;
                        int storeOrderNum = (int) Math.rint((timeStamp) / 1000f);
                        Log.d(TAG, "绘制波形的数据: 序号：" + storeOrderNum + "  时间戳：" + (timeStamp));
                        if (storeOrderNum - storeLastOrderNum != 1) {
                            Log.e(TAG, "onRealTimeResponse: 数据丢失 beforeIndex= " + storeLastOrderNum + " nowIndex= " + storeOrderNum);
                        }
                        //实数数据存储

                        Log.d(TAG, "存储的数据: 序号：" + storeOrderNum + "  时间戳：" + (timeStamp));
                        if (storeLastOrderNum != storeOrderNum && oxValue >= 60 && oxValue <= 100 && prValue >= 30 && prValue <= 250) {
                            //西班牙语数据格式转换
                            realTimeData.append(String.format(Locale.getDefault(), "|%s,%s,%s,%s,%s", storeOrderNum, oxValue, prValue, getFloatWithLocal(new DecimalFormat("0.0").format(piValue)), RR));
                            Log.d(TAG, "onRealTimeResponse: " + String.format(Locale.getDefault(), "|%s,%s,%s,%s,%s", storeOrderNum, oxValue, prValue, getFloatWithLocal(new DecimalFormat("0.0").format(piValue)), RR));
                            Intent intent = new Intent("onOxRealtimeData");
                            if (continuousPI) {
                                intent.putExtra("pi", getFloatWithLocal(new DecimalFormat("0.0").format(piValue)));
                            }
                            if (continuousRR) {
                                intent.putExtra("rr", RR);
                            }
                            intent.putExtra("spo2", oxValue);
                            intent.putExtra("pr", prValue);

                            sendBroadcast(intent);
                        }
                        storeLastOrderNum = storeOrderNum;
                    }
                } else {
                    continuationFingerOutTimes = 0;
                    realModeBeginTime = 0;
                    if (timer != null) {
                        timer.cancel();
                    }
                }
            }
            @Override
            public void onRACPResponse(DeviceType deviceType, byte[] result) {
                String data = ByteUtils.bytes2HexString(result);
                Log.d(TAG, "RACP通道回复: " + data);
                if (lastCMD.equals(reportNumberOfStoredRecordsCmd)) {
                    String OpCode = data.substring(0, 2);
                    if (Integer.parseInt(OpCode, 16) != 5) {
                        Log.d(TAG, "读历史记录条数指令回复异常：OpCode=" + Integer.parseInt(OpCode, 16));
                        return;
                    }
                    String number = ByteUtils.hexStringReverse(data.substring(4, 8));
                    numberOfRecords = Integer.parseInt(number, 16);
                    Log.d(TAG, "存储数据条数：" + numberOfRecords);
                    if (numberOfRecords == 0) {
                        return;
                    }
                    lastCMD = reportStoredRecordsCmd;
                    c208sInvoker.sendCmd(reportStoredRecordsCmd);
                    return;
                }

                if (lastCMD.equals(reportStoredRecordsCmd)) {
                    String OpCode = data.substring(0, 2);
                    if (Integer.parseInt(OpCode, 16) != 6) {
                        Log.d(TAG, "获取历史记录指令回复异常：OpCode=" + Integer.parseInt(OpCode, 16));
                        return;
                    }
                    String responseValue = data.substring(6, 8);
                    if (Integer.parseInt(responseValue, 16) != 1) {
                        Log.d(TAG, "获取历史记录失败！" + Integer.parseInt(responseValue, 16));
                        return;
                    }

                    if (history.size() != numberOfRecords) {
                        Log.d(TAG, "回传数据丢失！正确应回传条数：" + numberOfRecords + " 收到回传数据条数：" + history.size());
                        return;
                    }
                    saveHistoryData(history);
                    lastCMD = deleteStoredRecordsCmd;
                    c208sInvoker.sendCmd(deleteStoredRecordsCmd);
                    Log.d(TAG, "获取历史记录成功！");
                    return;

                }
                if (lastCMD.equals(deleteStoredRecordsCmd)) {
                    String OpCode = data.substring(0, 2);
                    if (Integer.parseInt(OpCode, 16) != 6) {
                        Log.d(TAG, "删除历史记录指令回复异常：OpCode=" + Integer.parseInt(OpCode, 16));
                        return;
                    }
                    String responseValue = data.substring(6, 8);
                    if (Integer.parseInt(responseValue, 16) != 1) {
                        Log.d(TAG, "删除历史记录失败！" + Integer.parseInt(responseValue, 16));
                        return;
                    }
                    Log.d(TAG, "删除历史记录成功！");
                    lastCMD = "";
                }
            }

            @Override
            public void onReadFeature(DeviceType deviceType, HashMap<String, Boolean> map) {
                feature = map;
            }

            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccessC208: C208连接成功！");
                if (feature.get("RACP")) {
                    history.clear();
                    lastCMD = reportNumberOfStoredRecordsCmd;
                    c208sInvoker.sendCmd(reportNumberOfStoredRecordsCmd);
                }

            }

            @Override
            public void onMeasureResult(DeviceType deviceType, int ox, float pi, int pulseRate) {

            }


            @Override
            public void onError(DeviceType deviceType, int errorMsg) {
                Log.d(TAG, "onErrorC208s: " + errorMsg);
                continuationFingerOutTimes = 0;
                realModeBeginTime = 0;
                if (timer != null) {
                    timer.cancel();
                }
                switch (errorMsg) {
                    //未打开蓝牙
                    case ErrorCode.ERROR_BLUETOOTH_NOT_OPEN:
                        openBluetoothBle();
                        break;
                    case ErrorCode.ERROR_CONNECT_TIMEOUT://连接超时
                    case ErrorCode.ERROR_BLE_DISCONNECT://下位机主动断开连接
                    case ErrorCode.ERROR_GATT_EXCEPTION://gatt异常
                        connectDelayC208(1);
                        break;
                    default:
                }

            }
        }, c208s.getMacAddress());

    }

    private void analysisPointData(byte[] data) {

        initSpotCheckState();
        String spotCheckData = ByteUtils.bytes2HexString(data);
        Log.d(TAG, "点测血氧数据:" + spotCheckData);

        //解析flag
        String flag = spotCheckData.substring(0, 2);
        String binaryFlag = ByteUtils.hexString2binaryString(flag);
        StringBuilder builder = new StringBuilder(binaryFlag);
        builder.reverse();
        char[] spotCheckFlag = builder.toString().toCharArray();
        if (spotCheckFlag[1] != '1' || spotCheckFlag[2] != '1') {
            Log.d(TAG, "点测数据不支持“测量状态”或“设备与传感器”");
            Log.d(TAG, "异常的点测数据");
            return;
        }
        if (spotCheckFlag[0] != '1') {
            timestampPresent = false;
            Log.d(TAG, "点测数据不支持时间戳！");
        }
        if (spotCheckFlag[3] != '1') {
            spotCheckPI = false;
            Log.d(TAG, "不支持PI");
        }
        if (spotCheckFlag[4] != '0') {
            deviceClockSet = false;
            Log.d(TAG, "未校验时间点测数据");
        }
        if (spotCheckFlag[5] != '1') {
            spotCheckRR = false;
            Log.d(TAG, "点测数据不支持呼吸率");
        }
        String ox = ByteUtils.hexStringReverse(spotCheckData.substring(2, 6));
        String pr = ByteUtils.hexStringReverse(spotCheckData.substring(6, 10));
        if (ox == null || pr == null)
            return;

        //数据公式为：数据=尾数 X（10^指数）解析血氧脉率
        byte oxHigh = data[2];
        byte oxLow = data[1];
        int oxIndex = (oxHigh >> 4);//右移4位屏蔽尾数影响
        byte oxLeftResult = (byte) (oxHigh << 4);//左移4位屏蔽掉SFloat中的指数位
        byte oxRightResult = (byte) (oxLeftResult >> 4);//右移4位屏蔽掉因左移放大的倍数
        short oxMantissa = (short) ((oxRightResult << 8) | (oxLow & 0x00ff));//拼接后的尾数
        int oxValue = (int) (oxMantissa * Math.pow(10, oxIndex));

        byte prHigh = data[4];
        byte prLow = data[3];
        int prIndex = (prHigh >> 4);//右移4位屏蔽尾数影响
        byte prLeftResult = (byte) (prHigh << 4);//左移4位屏蔽掉SFloat中的指数位
        byte prRightResult = (byte) (prLeftResult >> 4);//右移4位屏蔽掉因左移放大的倍数
        short prMantissa = (short) ((prRightResult << 8) | (prLow & 0x00ff));//拼接后的尾数
        int prValue = (int) (prMantissa * Math.pow(10, prIndex));

//2f 6200 3e00 e40707020d2d3b 2000 00000c 2af0
        Log.d(TAG, "血氧：" + oxValue + " 脉率：" + prValue);
        String measureDate = FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime);
        //解析时间戳
        if (timestampPresent) {
            String timeStamp = spotCheckData.substring(10, 24);
            //year
            String year = String.format(Locale.ENGLISH, "%s", Integer.parseInt(ByteUtils.hexStringReverse(timeStamp.substring(0, 4)), 16));
            //month
            String month = String.format(Locale.ENGLISH, "%02d", Integer.parseInt(timeStamp.substring(4, 6), 16));
            //day
            String day = String.format(Locale.ENGLISH, "%02d", Integer.parseInt(timeStamp.substring(6, 8), 16));
            //hour
            String hour = String.format(Locale.ENGLISH, "%02d", Integer.parseInt(timeStamp.substring(8, 10), 16));
            //minute
            String minute = String.format(Locale.ENGLISH, "%02d", Integer.parseInt(timeStamp.substring(10, 12), 16));
            //second
            String second = String.format(Locale.ENGLISH, "%02d", Integer.parseInt(timeStamp.substring(12, 14), 16));
            measureDate = String.format(Locale.ENGLISH, "%s-%s-%s %s:%s:%s", year, month, day, hour, minute, second);
            Log.d(TAG, "measureDate：" + measureDate);
        }

        //解析设备与传感器
        String DSS;
        if (timestampPresent)
            DSS = ByteUtils.hexStringReverse(spotCheckData.substring(28, 34));
        else
            DSS = ByteUtils.hexStringReverse(spotCheckData.substring(14, 20));
        StringBuilder DSSBuilder = new StringBuilder(ByteUtils.hexString2binaryString(DSS));
        DSSBuilder.reverse();
        char[] deviceSensorStatus = DSSBuilder.toString().toCharArray();
        if (deviceSensorStatus[2] == '1')
            Log.d(TAG, "信号不规则！");
        if (deviceSensorStatus[5] == '1')
            Log.d(TAG, "弱灌注！");
        if (deviceSensorStatus[11] == '1')
            Log.d(TAG, "手指脱落！");

        int RRValue = 0;
        if (spotCheckRR) {//解析PI
            byte high = 00;
            byte low;
            if (timestampPresent) {
                low = data[16];
            } else {
                low = data[9];
            }
            int index = (high >> 4);//右移4位屏蔽尾数影响
            byte leftResult = (byte) (high << 4);//左移4位屏蔽掉SFloat中的指数位
            byte rightResult = (byte) (leftResult >> 4);//右移4位屏蔽掉因左移放大的倍数
            short mantissa = (short) ((rightResult << 8) | (low & 0x00ff));//拼接后的尾数
            RRValue = (int) (mantissa * Math.pow(10, index));
            Log.d(TAG, "RRValue:" + RRValue);
        }


        double PIValue = 0.0;
        if (spotCheckPI) {//解析PI
            byte high;
            byte low;
            if (timestampPresent) {
                low = data[17];
                high = data[18];
            } else {
                low = data[10];
                high = data[11];
            }
            int index = (high >> 4);//右移4位屏蔽尾数影响
            byte leftResult = (byte) (high << 4);//左移4位屏蔽掉SFloat中的指数位
            byte rightResult = (byte) (leftResult >> 4);//右移4位屏蔽掉因左移放大的倍数
            short mantissa = (short) ((rightResult << 8) | (low & 0x00ff));//拼接后的尾数
            PIValue = mantissa * Math.pow(10, index);

            Log.d(TAG, "pi:" + new DecimalFormat("0.0").format(PIValue));
        }

        //解析测量状态
        String dataSource;
        if (timestampPresent)
            dataSource = ByteUtils.hexStringReverse(spotCheckData.substring(24, 28));
        else
            dataSource = ByteUtils.hexStringReverse(spotCheckData.substring(10, 14));
        StringBuilder dataSourceBuilder = new StringBuilder(ByteUtils.hexString2binaryString(dataSource));
        dataSourceBuilder.reverse();
        char[] measurementStatus = dataSourceBuilder.toString().toCharArray();
        OxSpotData oxSpotData = new OxSpotData();
        oxSpotData.setId(UuidUtils.getUuid());
        oxSpotData.setUserId(userProfileInfo.getUserId());
        oxSpotData.setDeviceName(c208s.getDeviceName());
        oxSpotData.setLogDateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
        oxSpotData.setMeasureDateTime(measureDate);
        oxSpotData.setCreateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
        oxSpotData.setLastUpdateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
        oxSpotData.setBloodOxygen(oxValue);
        oxSpotData.setPulseRate(prValue);
        oxSpotData.setPi((float) PIValue);
        oxSpotData.setRR(RRValue);
        oxSpotData.setSyncState(0);

        if (measurementStatus[9] == '1') {
            Log.d(TAG, "数据来自历史记录！" + oxSpotData.toString());
            history.add(oxSpotData);
        } else {
            Log.d(TAG, "数据来自当前测量！" + oxSpotData.toString());
            saveData(oxSpotData);
        }
        Log.d(TAG, "-------------------------分隔符---------------------------\\n");
    }

    private void saveData(OxSpotData oxSpotData) {
        getDaoSession(this).getOxSpotDataDao().insertOrReplace(oxSpotData);
        Intent intent = new Intent("onOxSpotMeasureResult");
        intent.putExtra("oxResult", true);
        sendBroadcast(intent);
    }

    private void saveHistoryData(List<OxSpotData> history) {
        getDaoSession(this).getOxSpotDataDao().insertOrReplaceInTx(history);
    }

    private void connectDelayC208(int delayTime) {
        c208Subscription = Observable.timer(delayTime, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d(TAG, "call: ");
                connectC208(c208s);
            }
        });


    }

    private void initContinuousState() {
        spo2PrFastPresent = true;
        spo2PrSlowPresent = true;
        continuousPI = true;
        continuousRR = true;
    }

    private Timer timer;
    private String uuid;
    /**
     * 定时存储任务，每隔十秒钟存储一次实时数据
     */
    private void beginLoopSave() {
        Log.d(TAG, "beginLoopSave: 开始存储!");
        Intent intent = new Intent("beginSaveAndChart");
        sendBroadcast(intent);
        storeLastOrderNum = -1;
        uuid = UuidUtils.getUuid();
        countNum = 0;
        continuationFingerOutTimes = 0;
        //移除之前定时任务
        if (timer != null) {
            timer.cancel();
        }
        if (realTimeData.length() != 0) {
            realTimeData.delete(0, realTimeData.length());
        }
        timer = new Timer("实时模式数据存储定时器");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //有数据才存储
                if (realTimeData.length() == 0) {
                    return;
                }
                //去除第一个|
                if (countNum == 0 && realTimeData.toString().startsWith("|")) {
                    realTimeData.delete(0, 1);
                }
                String series = realTimeData.substring(0, realTimeData.length());
                String currentTime = FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime);
                OxRealTimeData oxRealTimeData = new OxRealTimeData();
                oxRealTimeData.setUserId(userProfileInfo.getUserId());
                oxRealTimeData.setId(uuid);
                oxRealTimeData.setDeviceName(c208s.getDeviceName());
                oxRealTimeData.setLogDateTime(currentTime);
                oxRealTimeData.setMeasureDateStartTime(measureBeginTime);
                oxRealTimeData.setMeasureDateEndTime(currentTime);
                oxRealTimeData.setLastUpdateTime(currentTime);
                oxRealTimeData.setCreateTime(currentTime);
                oxRealTimeData.setSeries(series);
                getDaoSession(getApplicationContext()).getOxRealTimeDataDao().insertOrReplace(oxRealTimeData);
                Log.d(TAG, "run: 开始时间：" + measureBeginTime + "  实时数据入库" + oxRealTimeData.getSeries());
                //记录存储的次数
                countNum++;
            }
        }, 0, 5000);
    }

    /**
     * 获取本地显示的小数转化为double处理
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static float getFloatWithLocal(String str) {
        if (str.contains(".")) {
            return Float.valueOf(str);
        }
        NumberFormat format = NumberFormat.getNumberInstance(Locale
                .getDefault());// 获取当前区域数字格式
        try {
            return format.parse(str).floatValue();
        } catch (ParseException e) {
            e.printStackTrace();
            return Float.NaN;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG, "C208sBleConService Destroy");
        if (timer != null) {
            timer.cancel();
        }
    }
}
