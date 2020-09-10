package com.choice.c208sdkblelibrary.cmd.factory;

import com.choice.c208sdkblelibrary.ble.C208sBle;
import com.choice.c208sdkblelibrary.ble.C218RBle;
import com.choice.c208sdkblelibrary.cmd.command.C208sBaseCommand;
import com.choice.c208sdkblelibrary.cmd.command.C208sConnectCommand;
import com.choice.c208sdkblelibrary.cmd.command.C218RBaseCommand;
import com.choice.c208sdkblelibrary.cmd.command.C218RConnectCommand;

public class C218RConnectCommandFactory implements C218RCreatCommandListener {
    @Override
    public C218RBaseCommand createCommand(C218RBle ble, String reportNumberOfStoredRecordsCmd) {
        return new C218RConnectCommand(ble);
    }
}
