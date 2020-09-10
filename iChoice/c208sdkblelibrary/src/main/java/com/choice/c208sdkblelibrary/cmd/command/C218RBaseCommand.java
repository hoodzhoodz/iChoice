package com.choice.c208sdkblelibrary.cmd.command;

import com.choice.c208sdkblelibrary.ble.C218RBle;

public abstract class C218RBaseCommand {
    protected C218RBle c218RBle;
    protected String cmd;

    public C218RBaseCommand(C218RBle c208Ble, String cmd) {
        this.c218RBle = c208Ble;
        this.cmd = cmd;
    }

    public C218RBaseCommand(C218RBle c208Ble) {
        this.c218RBle = c208Ble;
    }

    /**
     * 执行命令的方法
     */
    public abstract void execute();
}
