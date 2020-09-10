package com.choice.c208sdkblelibrary.cmd.factory;

import com.choice.c208sdkblelibrary.ble.C208sBle;
import com.choice.c208sdkblelibrary.cmd.command.C208sBaseCommand;
import com.choice.c208sdkblelibrary.cmd.command.C208sSendRACPCommand;

public class C208sSendRACPCommandFactory implements C208sCreatCommandListener {
    @Override
    public C208sBaseCommand createCommand(C208sBle ble, String cmd) {
        return new C208sSendRACPCommand(ble, cmd);
    }
}
