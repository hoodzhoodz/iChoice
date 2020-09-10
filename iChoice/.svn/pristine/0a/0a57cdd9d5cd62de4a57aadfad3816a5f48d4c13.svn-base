package com.choice.c208sdkblelibrary.base;

import java.util.HashMap;

public interface GattListener {
    void onError(DeviceType deviceType, int errorMsg);

    void onDisconnected(DeviceType deviceType);

    void onInitialized(DeviceType deviceType);

    void onCmdResponse(DeviceType deviceType, byte[] data);

    void onDataResponse(DeviceType deviceType, byte[] data);

    void onBleConnectSuccess();

    void onRACPResponse(DeviceType deviceType, byte[] data);

    void onSpotCheckResponse(DeviceType deviceType, byte[] data);

    void onRealTimeResponse(DeviceType deviceType, byte[] data);

    void onReadFeature(DeviceType deviceType, HashMap<String, Boolean> map);
}
