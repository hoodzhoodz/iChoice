package com.choicemmed.wristpulselibrary.base;

/**
 * @author Created by Jiang nan on 2020/1/11 10:54.
 * @description
 **/
public interface BleListener {
    void onFoundDevice(DeviceType deviceType, String address, String deviceName);
    void onFoundFail(DeviceType deviceType, int errorMsg);
    void onConnected(DeviceType deviceType);
    void onScanTimeout(DeviceType deviceType);
    void onError(DeviceType deviceType, int errorMsg);
    void onDisconnected(DeviceType deviceType);
    void onInitialized(DeviceType deviceType);
    void onCmdResponse(DeviceType deviceType, byte[] result);
    void onDataResponse(DeviceType deviceType, byte[] data);
}
