package com.choicemmed.cbp1k1sdkblelibrary.device;

/**
 * @author Name: ZhengZhong on 2018/1/19 16:22
 *         Email: zheng_zhong@163.com
 * @version V1.0.0
 */
public class BP2941 {

    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 设备mac地址
     */
    private String macAddress;

    public BP2941() {

    }

    public BP2941(String deviceName, String macAddress) {
        this.deviceName = deviceName;
        this.macAddress = macAddress;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @Override
    public String toString() {
        return "BP2941{" +
                "deviceName='" + deviceName + '\'' +
                ", macAddress='" + macAddress + '\'' +
                '}';
    }
}
