package com.choice.c208sdkblelibrary.cmd.command;

import com.choice.c208sdkblelibrary.ble.C208sBle;

public class C208sSendRACPCommand extends C208sBaseCommand {

    public C208sSendRACPCommand(C208sBle ble, String cmd) {
        super(ble, cmd);
    }

    @Override
    public void execute() {
        c208Ble.sendCmd(cmd);
    }
}
