package com.choice.c208sdkblelibrary.cmd.callback;

import com.choice.c208sdkblelibrary.base.DeviceType;

public interface C208ConnectCallback extends C208BaseCallback {
    /**
     * 设备连接成功
     */
    void onSuccess();

    /**
     * 测量结果
     *
     * @param deviceType 设备类型
     * @param ox         血氧
     * @param pi         pi值
     * @param pulseRate  脉率
     */
    void onMeasureResult(DeviceType deviceType, int ox, float pi, int pulseRate);
}
