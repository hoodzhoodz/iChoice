package com.choice.c208sdkblelibrary.cmd.command;

import android.text.TextUtils;
import android.util.Log;

import com.choice.c208sdkblelibrary.ble.C208Ble;


public class C208ConnectCommand extends C208BaseCommand {

    private static final String TAG = "C208ConnectCommand";
    /**
     * 设备mac地址
     */
    private String address = "";

    public C208ConnectCommand(C208Ble c208Ble) {
        super(c208Ble);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void execute() {
        if (TextUtils.isEmpty(address)) {
            Log.d(TAG, "execute: mac地址为null");
            return;
        }
        c208Ble.connectDevice(address);
    }
}
