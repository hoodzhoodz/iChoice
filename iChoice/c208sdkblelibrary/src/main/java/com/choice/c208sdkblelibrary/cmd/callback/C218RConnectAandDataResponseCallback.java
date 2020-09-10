package com.choice.c208sdkblelibrary.cmd.callback;

import com.choice.c208sdkblelibrary.base.DeviceType;

import java.util.HashMap;

public interface C218RConnectAandDataResponseCallback extends C208ConnectCallback {
    void onSpotCheckResponse(DeviceType deviceType, byte[] data);

    void onRealTimeResponse(DeviceType deviceType, byte[] data);

    void onRACPResponse(DeviceType deviceType, byte[] result);

    void onReadFeature(DeviceType deviceType, HashMap<String, Boolean> map);
}
