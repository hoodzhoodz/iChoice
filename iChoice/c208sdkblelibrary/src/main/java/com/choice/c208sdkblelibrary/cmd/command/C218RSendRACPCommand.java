package com.choice.c208sdkblelibrary.cmd.command;

import com.choice.c208sdkblelibrary.ble.C218RBle;

public class C218RSendRACPCommand extends C218RBaseCommand {

    public C218RSendRACPCommand(C218RBle c218RBle, String cmd) {
        super(c218RBle, cmd);
    }

    @Override
    public void execute() {
        c218RBle.sendCmd(cmd);
    }
}
