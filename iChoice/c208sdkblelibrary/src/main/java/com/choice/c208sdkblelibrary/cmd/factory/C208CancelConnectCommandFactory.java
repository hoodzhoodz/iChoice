package com.choice.c208sdkblelibrary.cmd.factory;

import com.choice.c208sdkblelibrary.ble.C208Ble;
import com.choice.c208sdkblelibrary.cmd.command.C208BaseCommand;
import com.choice.c208sdkblelibrary.cmd.command.C208CancelConnectCommand;

public class C208CancelConnectCommandFactory implements C208CreatCommandListener {
    @Override
    public C208BaseCommand createCommand(C208Ble ble) {
        return new C208CancelConnectCommand(ble);
    }
}
