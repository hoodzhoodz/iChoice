package com.choicemmed.cbp1k1sdkblelibrary.base;

/**
 * Created by Yu Baoxiang on 2015/4/13.
 */
public interface GattListener {
    void onError(DeviceType deviceType, int errorMsg);
    void onDisconnected(DeviceType deviceType);
    void onInitialized(DeviceType deviceType);
    void onCmdResponse(DeviceType deviceType, byte[] data);
    void onDataResponse(DeviceType deviceType, byte[] data);
    void onBleConnectSuccess();

}
