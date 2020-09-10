package com.choice.c208sdkblelibrary.cmd.command;

import android.text.TextUtils;
import android.util.Log;

import com.choice.c208sdkblelibrary.ble.C208sBle;
import com.choice.c208sdkblelibrary.ble.C218RBle;


public class C218RConnectCommand extends C218RBaseCommand {

    private static final String TAG = "C218RConnectCommand";
    /**
     * 设备mac地址
     */
    private String address = "";

    public C218RConnectCommand(C218RBle c218RBle) {
        super(c218RBle);
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
        c218RBle.connectDevice(address);
    }
}
