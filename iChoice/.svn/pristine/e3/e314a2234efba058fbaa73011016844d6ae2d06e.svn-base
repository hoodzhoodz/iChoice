package com.choice.c208sdkblelibrary.cmd.factory;


import com.choice.c208sdkblelibrary.ble.C218RBle;
import com.choice.c208sdkblelibrary.cmd.command.C218RBaseCommand;
import com.choice.c208sdkblelibrary.cmd.command.C218RSendRACPCommand;

public class C218RSendRACPCommandFactory implements C218RCreatCommandListener {
    @Override
    public C218RBaseCommand createCommand(C218RBle ble, String cmd) {
        return new C218RSendRACPCommand(ble, cmd);
    }
}
