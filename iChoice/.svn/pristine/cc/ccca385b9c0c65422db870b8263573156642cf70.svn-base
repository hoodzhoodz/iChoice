package com.choicemmed.wristpulselibrary.cmd.command;

import android.text.TextUtils;


import com.choicemmed.wristpulselibrary.base.BaseBle;

/**
 * @author Created by Jiang nan on 2020/1/11 12:17.
 * @description
 **/
public class ConnectDeviceCommand extends BaseCommand {
    private String address;

    public ConnectDeviceCommand(BaseBle baseBle) {
        super(baseBle);
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public void execute() {
        if (!TextUtils.isEmpty(address)) {
            baseBle.connectDevice(address);
        } else {
            throw new RuntimeException("macAddress is empty or null");
        }
    }
}
