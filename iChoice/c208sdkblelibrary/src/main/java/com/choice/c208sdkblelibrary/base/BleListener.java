package com.choice.c208sdkblelibrary.base;

import java.util.HashMap;

public interface BleListener {
    void onFoundDevice(DeviceType deviceType, String address, String deviceName);

    void onScanTimeout(DeviceType deviceType);

    void onError(DeviceType deviceType, int errorMsg);

    void onDisconnected(DeviceType deviceType);

    void onInitialized(DeviceType deviceType);

    void onCmdResponse(DeviceType deviceType, byte[] result);

    void onDataResponse(DeviceType deviceType, byte[] data);

    void onSpotCheckResponse(DeviceType deviceType, byte[] data);

    void onRealTimeResponse(DeviceType deviceType, byte[] data);

    void onRACPResponse(DeviceType deviceType, byte[] result);

    void onReadFeature(DeviceType deviceType, HashMap<String, Boolean> map);
}
