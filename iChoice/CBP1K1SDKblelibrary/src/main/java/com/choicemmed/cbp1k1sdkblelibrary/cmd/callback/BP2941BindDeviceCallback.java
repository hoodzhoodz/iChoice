package com.choicemmed.cbp1k1sdkblelibrary.cmd.callback;

import com.choicemmed.cbp1k1sdkblelibrary.base.DeviceType;
import com.choicemmed.cbp1k1sdkblelibrary.device.BP2941;

/**
 * @author Name: ZhengZhong on 2018/1/23 10:22
 *         Email: zheng_zhong@163.com
 * @version V1.0.0
 */
public interface BP2941BindDeviceCallback extends BP2941BaseCallback {
    /**
     * 扫描到的设备
     *
     * @param deviceType 设备类型
     * @param bp2941     设备信息
     */
    void onFoundDevice(DeviceType deviceType, BP2941 bp2941);

    /**
     * 扫描超时回调
     *
     * @param deviceType 设备类型
     */
    void onScanTimeout(DeviceType deviceType);


}
