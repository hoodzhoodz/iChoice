package com.choicemmed.ichoice.framework.utils;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.net.ConnectivityManager;

/**
 * 权限请求类
 * Created by gaofang on 2019/1/10.
 */

public class PermissionsUtils {
    private static final String TAG = PermissionsUtils.class.getSimpleName();

    public static boolean isNetworkConnected(Context context){
        ConnectivityManager manager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager.getActiveNetworkInfo()!=null){
            return manager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

    public static boolean isBluetoothPermission(Context context){
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
            return false;
        }else {
            return true;
        }
    }

}
