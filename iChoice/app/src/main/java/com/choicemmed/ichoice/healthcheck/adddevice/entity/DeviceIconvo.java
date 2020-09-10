package com.choicemmed.ichoice.healthcheck.adddevice.entity;

import java.io.Serializable;

public class DeviceIconvo implements Serializable {
    String iconName;
    int iconImage;
    int iconImagePositive;
    int iconImageConnect;

    public DeviceIconvo(String iconName, int iconImage, int iconImagePositive, int iconImageConnect) {
        this.iconName = iconName;
        this.iconImage = iconImage;
        this.iconImagePositive = iconImagePositive;
        this.iconImageConnect = iconImageConnect;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public int getIconImage() {
        return iconImage;
    }

    public void setIconImage(int iconImage) {
        this.iconImage = iconImage;
    }

    public int getIconImagePositive() {
        return iconImagePositive;
    }

    public void setIconImagePositive(int iconImagePositive) {
        this.iconImagePositive = iconImagePositive;
    }

    public int getIconImageConnect() {
        return iconImageConnect;
    }

    public void setIconImageConnect(int iconImageConnect) {
        this.iconImageConnect = iconImageConnect;
    }

}
