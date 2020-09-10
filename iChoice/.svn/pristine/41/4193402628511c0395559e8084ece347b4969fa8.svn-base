package com.choice.c208sdkblelibrary.cmd.command;

import com.choice.c208sdkblelibrary.ble.C208sBle;

public abstract class C208sBaseCommand {
    protected C208sBle c208Ble;
    protected String cmd;

    public C208sBaseCommand(C208sBle c208Ble, String cmd) {
        this.c208Ble = c208Ble;
        this.cmd = cmd;
    }

    public C208sBaseCommand(C208sBle c208Ble) {
        this.c208Ble = c208Ble;
    }

    /**
     * 执行命令的方法
     */
    public abstract void execute();
}
