package com.choice.c208sdkblelibrary.cmd.callback;

import com.choice.c208sdkblelibrary.base.DeviceType;
import com.choice.c208sdkblelibrary.cmd.callback.C208BaseCallback;
import com.choice.c208sdkblelibrary.device.C208;

public interface C208BindDeviceCallback extends C208BaseCallback {
    /**
     * 扫描到的设备
     *
     * @param deviceType 设备类型
     * @param c208       设备信息
     */
    void onFoundDevice(DeviceType deviceType, C208 c208);

    /**
     * 扫描超时回调
     *
     * @param deviceType 设备类型
     */
    void onScanTimeout(DeviceType deviceType);
}
