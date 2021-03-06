package com.choicemmed.wristpulselibrary.cmd.listener;


import com.choicemmed.wristpulselibrary.entity.Device;

/**
 * @author Created by Jiang nan on 2019/10/23 10:17.
 * @description
 **/
public interface W314Listener {

    /**
     * 绑定 成功
     **/
    void onBindDeviceSuccess(Device mDevice);

    /**
     * 绑定失败
     */
    void onBindDeviceFail(int message);
    /**
     * 错误
     *
     * @param message*/
    void onError(int message);

    /**
     * 连接 成功
     **/
    void onConnectedDeviceSuccess();

    /**
     * 断开连接
     **/
    void onDisconnected();

    /**
     * 状态改变
     **/
    void onStateChanged(int bleState, int state);

    /**
     * 返回全部数据，全部开关机时间
     * @param recordData
     * @param recordTime
     */
    void onRecordDataResponse(String recordData,String recordTime);

    /**
     * 返回实时血氧数据
     * @param realTimeSpoData
     */
    void onRealTimeSpoData(int realTimeSpoData);

    /**
     * 返回实时脉率数据
     * @param realTimePRData
     */
    void onRealTimePRData(int realTimePRData);


    void exitSuccess();
    void onRealTimeNone();
    void onRealTimeWaveData(Float realTimeWaveData);
}
