package com.choicemmed.ichoice.healthcheck.service;

import android.app.AlertDialog;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.choicemmed.cbp1k1sdkblelibrary.base.DeviceType;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.callback.BP2941CancelConnectDeviceCallback;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.callback.BP2941ConnectDeviceCallback;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.callback.BP2941PowerOffCallback;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.invoker.BP2941Invoker;
import com.choicemmed.cbp1k1sdkblelibrary.device.BP2941;
import com.choicemmed.cbp1k1sdkblelibrary.utils.ErrorCode;
import com.choicemmed.common.FormatUtils;
import com.choicemmed.common.LogUtils;
import com.choicemmed.common.ThreadManager;
import com.choicemmed.common.UuidUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.healthcheck.db.Cbp1k1Operation;
import com.choicemmed.ichoice.healthcheck.db.DeviceOperation;
import com.choicemmed.ichoice.healthcheck.db.UserOperation;
import com.choicemmed.ichoice.framework.utils.DevicesType;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import pro.choicemmed.datalib.Cbp1k1Data;
import pro.choicemmed.datalib.DeviceInfo;
import pro.choicemmed.datalib.UserProfileInfo;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
/**
*Created by
 * @author Jiangnan
 * @Date 2019/12/1.
*/
public class BpBleConService extends Service {
    private static final String TAG = "BpBleConService";
    private List<DeviceInfo> list;
    private ScanBleBinder binder = new ScanBleBinder();

    private BP2941 bp2941;
    private BP2941Invoker bp2941Invoker;
    private Subscription bp2941Subscription;
    private Handler handler;
    private AlertDialog dialog;
    public BpBleConService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "-------onBind------");
       return binder;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        Log.d(TAG, "-----unbindService----- ");
        super.unbindService(conn);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "-------onCreate-----");
        initInvoker();
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
        public void startConnectBle(){
            LogUtils.d(TAG, "----startConnectBle-----");
            init();
        }

        public void cancelConnect() {
            LogUtils.d(TAG, "关闭Gatt,取消所有待连接！");
            bp2941Invoker.cancleConnectDevice(new BP2941CancelConnectDeviceCallback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(DeviceType deviceType, int errorMsg) {

                }
            });

        }

        public void close(){
            closeCBP1K1Device();
        }
    }

    private void init() {
        list = new DeviceOperation(this).queryByUserIdType(IchoiceApplication.getAppData().userProfileInfo.getUserId(), DevicesType.BloodPressure);
        if (list.isEmpty()){
            return;
        }
        openBluetoothBle();
        LogUtils.d(TAG, "devices：" + list.toString());
        searchDv(list);
        LogUtils.d(TAG, "结束了：searchDv");

    }

    private void initInvoker() {
        bp2941Invoker = new BP2941Invoker(this);
    }

    private void searchDv(List<DeviceInfo> list) {
        if (list.size() != 0) {
            for (final DeviceInfo deviceInfo : list) {
                ThreadManager.execute(new Runnable() {
                    @Override
                    public void run() {
                        bp2941 = new BP2941();
                        bp2941.setDeviceName(deviceInfo.getDeviceName());
                        bp2941.setMacAddress(deviceInfo.getBluetoothId());
                        connectBp2941(bp2941);
                    }
                });
            }
        } else {
            LogUtils.d(TAG, "没有绑定设备");
        }
    }

    private void connectBp2941(final BP2941 bp2941) {
        bp2941Invoker.connectDevice(bp2941.getMacAddress(), new BP2941ConnectDeviceCallback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccessBp2941: BP2941连接成功！");
            }

            @Override
            public void onMeasureResult(DeviceType deviceType, int systolicPressure, int diastolicPressure, int pulseRate) {
                saveCBP1K1(systolicPressure,diastolicPressure,pulseRate);
            }

            @Override
            public void onError(DeviceType deviceType, int errorMsg) {
                Log.d(TAG, "onErrorBp2941: " + errorMsg);
                switch (errorMsg){
                    //未打开蓝牙
                    case ErrorCode.ERROR_BLUETOOTH_NOT_OPEN:
                        openBluetoothBle();
                        break;
                    //连接超时
                    case ErrorCode.ERROR_CONNECT_TIMEOUT:
                        //下位机主动断开连接
                    case ErrorCode.ERROR_BLE_DISCONNECT:
                        //gatt异常
                    case ErrorCode.ERROR_GATT_EXCEPTION:
                        connectDelayBp2941(1);
                        break;
                    default:
                }

            }

        });

    }

    private void openBluetoothBle() {
        BluetoothManager bluetoothManager = (BluetoothManager) BpBleConService.this.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
            LogUtils.d(TAG, "检测到蓝牙未打开：重新开启蓝牙！");
            bluetoothAdapter.enable();//不弹对话框直接开启蓝牙
        }
    }

    private void connectDelayBp2941(int delayTime) {
        bp2941Subscription = Observable
                .timer(delayTime, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Log.d(TAG, "call: ");
                        connectBp2941(bp2941);
                    }
                });
    }

    private void  saveCBP1K1(int systolicPressure, int diastolicPressure, int pulseRate) {
        Cbp1k1Data cbp1k1Data = new Cbp1k1Data();
        Cbp1k1Operation cbp1k1Operation = new Cbp1k1Operation(this);
        UserOperation userOperation = new UserOperation(this);
        UserProfileInfo userProfileInfo = userOperation.queryByUserId(IchoiceApplication.getAppData().userProfileInfo.getUserId());
        if (userProfileInfo != null) {

            cbp1k1Data.setId(UuidUtils.getUuid());
            cbp1k1Data.setUserId(userProfileInfo.getUserId());
            cbp1k1Data.setDeviceName(bp2941.getDeviceName());
            cbp1k1Data.setDeviceId("Blood Pressure bp.bp");
            cbp1k1Data.setLogDateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
            cbp1k1Data.setMeasureDateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
            cbp1k1Data.setCreateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
            cbp1k1Data.setLastUpdateTime(FormatUtils.getDateTimeString(new Date(), FormatUtils.template_DbDateTime));
            cbp1k1Data.setSystolic(systolicPressure);
            cbp1k1Data.setDiastolic(diastolicPressure);
            cbp1k1Data.setPulseRate(pulseRate);
            cbp1k1Data.setSyncState(0);
            cbp1k1Operation.insertCbp1k1ByUser(cbp1k1Data);

            Intent intent = new Intent("onMeasureResult");
            intent.putExtra("result",true);
            sendBroadcast(intent);
            handler = new Handler(getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    showMeasureFinishDialog();
                }
            });
        }
    }

    private void showMeasureFinishDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(getApplication())) {
            return;
        }
        View view = LayoutInflater.from(getApplication()).inflate(R.layout.popup_center_tip,null);
        TextView titleView = view.findViewById(R.id.tv_title);
        TextView contentVIew = view.findViewById(R.id.tv_content);
        TextView cancelButtonView = view.findViewById(R.id.btn_negative);
        TextView confirmButtonView = view.findViewById(R.id.btn_positive);
        contentVIew.setText(getString(R.string.disconnect_dialogMsg));
        cancelButtonView.setText(getString(R.string.dialog_disconnect));
        confirmButtonView.setText(getString(R.string.dialog_not));

        cancelButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeCBP1K1Device();
                dialog.dismiss();
                if (handler!=null){
                    handler.removeCallbacksAndMessages(null);
                }

            }
        });

        confirmButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (handler!=null){
                    handler.removeCallbacksAndMessages(null);
                }

            }
        });

        dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(true)
                .create();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 10000);

        //适配8.0弹窗行为变更
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dialog.getWindow().setType((WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY));

        } else {
            dialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
        }

        dialog.show();
    }

    public void closeCBP1K1Device () {
        bp2941Invoker.powerOff(new BP2941PowerOffCallback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: ");
            }

            @Override
            public void onError(DeviceType deviceType, int errorMsg) {
                Log.d(TAG, "onError: ");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
    }
}
