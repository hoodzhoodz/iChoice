package com.choicemmed.wristpulselibrary.base;

/**
 * @author Created by Jiang nan on 2020/1/11 10:54.
 * @description
 **/
public interface GattListener {
    void onError(DeviceType deviceType, int errorMsg);
    void onConnect(DeviceType deviceType);
    void onDisconnected(DeviceType deviceType);
    void onInitialized(DeviceType deviceType);
    void onCmdResponse(DeviceType deviceType, byte[] data);
    void onDataResponse(DeviceType deviceType, byte[] data);
    void onBleConnectSuccess();

}
