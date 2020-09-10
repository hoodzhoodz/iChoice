package com.choicemmed.ichoice.healthcheck.model;


import android.bluetooth.BluetoothDevice;

import com.choicemmed.wristpulselibrary.entity.Device;

import java.util.Map;

public interface BleConnectListener {

    /**
     * 绑定 成功
     **/
    void onBindDeviceSuccess(BluetoothDevice mDevice);

    /**
     * 错误
     *
     * @param message
     */
    void onError(String message);

    /**
     * 连接 成功
     **/
    void onConnectedDeviceSuccess();

    void onDataResponse(String s);

}
