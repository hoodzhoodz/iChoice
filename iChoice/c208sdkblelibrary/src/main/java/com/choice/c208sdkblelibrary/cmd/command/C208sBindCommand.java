package com.choice.c208sdkblelibrary.cmd.command;

import com.choice.c208sdkblelibrary.ble.C208sBle;

public class C208sBindCommand extends C208sBaseCommand {
    public C208sBindCommand(C208sBle c208Ble) {
        super(c208Ble);
    }

    @Override
    public void execute() {
        c208Ble.startLeScan();
    }
}
