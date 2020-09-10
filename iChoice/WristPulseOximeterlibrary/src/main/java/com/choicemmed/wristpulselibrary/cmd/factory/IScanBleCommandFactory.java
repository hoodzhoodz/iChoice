package com.choicemmed.wristpulselibrary.cmd.factory;

import com.choicemmed.wristpulselibrary.base.BaseBle;
import com.choicemmed.wristpulselibrary.cmd.command.BaseCommand;
import com.choicemmed.wristpulselibrary.cmd.command.ScanBleCommand;

/**
 * @author Created by Jiang nan on 2020/1/11 13:14.
 * @description
 **/
public class IScanBleCommandFactory implements ICommandCreator {
    @Override
    public BaseCommand createCommand(BaseBle baseBle) {
        return new ScanBleCommand(baseBle);
    }
}
