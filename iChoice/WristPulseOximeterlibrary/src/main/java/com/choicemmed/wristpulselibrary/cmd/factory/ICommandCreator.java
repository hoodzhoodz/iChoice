package com.choicemmed.wristpulselibrary.cmd.factory;

import com.choicemmed.wristpulselibrary.base.BaseBle;
import com.choicemmed.wristpulselibrary.cmd.command.BaseCommand;

/**
 * @author Created by Jiang nan on 2020/1/11 12:56.
 * @description
 **/
public interface ICommandCreator {
    BaseCommand createCommand(BaseBle baseBle);
}
