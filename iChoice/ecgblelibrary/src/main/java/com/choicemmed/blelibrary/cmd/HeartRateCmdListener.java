package com.choicemmed.blelibrary.cmd;

import com.choicemmed.blelibrary.base.DeviceType;

/**
 * Created by Yu Baoxiang on 2015/6/24.
 */
public interface HeartRateCmdListener {
    void onFoundDevice(DeviceType deviceType, String address, String deviceName);

    void onScanTimeout(DeviceType deviceType);

    void onError(DeviceType deviceType, String errorMsg);

    void onDisconnected(DeviceType deviceType);

    void onLoadBegin();

    void onRecordInfoResponse(String recordInfo, String measureTime, int ecgHeartRate, int ecgResult, String ecgErrorCode);

    boolean onEcgDataResponse(String ecgData);

    void onLoadEnd(boolean success, String errorMsg, String type);
}
