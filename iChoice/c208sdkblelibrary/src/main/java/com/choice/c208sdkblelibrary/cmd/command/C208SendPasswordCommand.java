package com.choice.c208sdkblelibrary.cmd.command;

import com.choice.c208sdkblelibrary.ble.C208Ble;

public class C208SendPasswordCommand extends C208BaseCommand {
    /**
     * 发送密码指令
     */
    private static final String CMD = "AA5504B10000";

    public C208SendPasswordCommand(C208Ble c208Ble) {
        super(c208Ble);
    }

    @Override
    public void execute() {
        c208Ble.sendCmd(CMD);
    }
}
