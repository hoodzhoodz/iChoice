package com.choice.c208sdkblelibrary.cmd.factory;

import com.choice.c208sdkblelibrary.ble.C208sBle;
import com.choice.c208sdkblelibrary.cmd.command.C208sBaseCommand;
import com.choice.c208sdkblelibrary.cmd.command.C208sBindCommand;

public class C208sBindCommandFactory implements C208sCreatCommandListener {
    @Override
    public C208sBaseCommand createCommand(C208sBle ble, String reportNumberOfStoredRecordsCmd) {
        return new C208sBindCommand(ble);
    }


}
