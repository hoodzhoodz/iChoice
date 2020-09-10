package com.choice.c208sdkblelibrary.cmd.command;

import com.choice.c208sdkblelibrary.ble.C208sBle;
import com.choice.c208sdkblelibrary.ble.C218RBle;

public class C218RDisconnectCommand extends C218RBaseCommand {
    public C218RDisconnectCommand(C218RBle c218RBle) {
        super(c218RBle);
    }

    @Override
    public void execute() {
        c218RBle.disconnect();
    }
}
