package com.choicemmed.cbp1k1sdkblelibrary.cmd.callback;

import com.choicemmed.cbp1k1sdkblelibrary.base.DeviceType;

/**
 * @author Name: ZhengZhong on 2018/1/23 10:22
 *         Email: zheng_zhong@163.com
 * @version V1.0.0
 */
public interface BP2941ConnectDeviceCallback extends BP2941BaseCallback{
    /**
     * 设备连接成功
     */
    void onSuccess();
    /**
     * 测量结果
     *
     * @param deviceType        设备类型
     * @param systolicPressure  收缩压
     * @param diastolicPressure 舒张压
     * @param pulseRate         脉率
     */
    void onMeasureResult(DeviceType deviceType, int systolicPressure, int diastolicPressure, int pulseRate);

}
