package com.choicemmed.wristpulselibrary.cmd.factory;

import com.choicemmed.wristpulselibrary.base.BaseBle;
import com.choicemmed.wristpulselibrary.cmd.command.BaseCommand;
import com.choicemmed.wristpulselibrary.cmd.command.DisconnectDeviceCommand;

/**
 * @author Created by Jiang nan on 2020/1/11 12:59.
 * @description
 **/
public class IDisconnectDeviceCommandFactory implements ICommandCreator {
    @Override
    public BaseCommand createCommand(BaseBle baseBle) {
        return new DisconnectDeviceCommand(baseBle);
    }
}
