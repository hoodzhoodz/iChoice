package com.choice.c208sdkblelibrary.cmd.command;

import com.choice.c208sdkblelibrary.ble.C208Ble;
import com.choice.c208sdkblelibrary.ble.C208sBle;

public class C208sSendPasswordCommand extends C208sBaseCommand {
    /**
     * 发送密码指令
     */
    private static final String CMD = "AA5504B10000";

    public C208sSendPasswordCommand(C208sBle c208Ble) {
        super(c208Ble);
    }

    @Override
    public void execute() {
        c208Ble.sendCmd(CMD);
    }
}
