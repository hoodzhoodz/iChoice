package com.choice.c208sdkblelibrary.device;

public class C208 {
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 设备mac地址
     */
    private String macAddress;

    public C208() {

    }

    public C208(String deviceName, String macAddress) {
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
        return deviceName + "{" +
                "deviceName='" + deviceName + '\'' +
                ", macAddress='" + macAddress + '\'' +
                '}';
    }
}
