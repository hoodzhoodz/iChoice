package com.choice.c208sdkblelibrary.cmd.command;

import com.choice.c208sdkblelibrary.ble.C208Ble;
import com.choice.c208sdkblelibrary.ble.C208sBle;

public class C208sCancelConnectCommand extends C208sBaseCommand {
    public C208sCancelConnectCommand(C208sBle c208sBle) {
        super(c208sBle);
    }

    @Override
    public void execute() {
        c208Ble.cancelConnect();
    }
}
