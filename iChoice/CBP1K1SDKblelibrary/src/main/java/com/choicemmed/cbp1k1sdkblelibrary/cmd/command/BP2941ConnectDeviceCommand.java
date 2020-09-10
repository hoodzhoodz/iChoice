package com.choicemmed.cbp1k1sdkblelibrary.cmd.command;

import android.text.TextUtils;
import android.util.Log;

import com.choicemmed.cbp1k1sdkblelibrary.ble.BP2941Ble;

/**
 * @author Name: ZhengZhong on 2018/1/23 15:32
 *         Email: zheng_zhong@163.com
 * @version V1.0.0
 */
public class BP2941ConnectDeviceCommand extends BP2941BaseCommand {

    private static final String TAG = "BP2941ConnectDeviceComm";
    /**
     * 设备mac地址
     */
    private String address = "";

    public BP2941ConnectDeviceCommand(BP2941Ble ble) {
        super(ble);
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
        ble.connectDevice(address);
    }
}
