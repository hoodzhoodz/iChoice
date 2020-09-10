package com.choice.c208sdkblelibrary.cmd.command;

import com.choice.c208sdkblelibrary.ble.C208Ble;

public class C208BindCommand extends C208BaseCommand {
    public C208BindCommand(C208Ble c208Ble) {
        super(c208Ble);
    }

    @Override
    public void execute() {
        c208Ble.startLeScan();
    }
}
