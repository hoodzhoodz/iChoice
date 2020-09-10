package com.choice.c208sdkblelibrary.cmd.command;

import com.choice.c208sdkblelibrary.ble.C208Ble;
import com.choice.c208sdkblelibrary.ble.C218RBle;

public class C218RSendPasswordCommand extends C218RBaseCommand {
    /**
     * 发送密码指令
     */
    private static final String CMD = "AA5504B10000";

    public C218RSendPasswordCommand(C218RBle c218RBle) {
        super(c218RBle);
    }

    @Override
    public void execute() {
        c218RBle.sendCmd(CMD);
    }
}
