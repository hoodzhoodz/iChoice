package com.choicemmed.wristpulselibrary.cmd.command;
import com.choicemmed.wristpulselibrary.base.BaseBle;


/**
 * @author Created by Jiang nan on 2020/1/11 12:16.
 * @description
 **/
public abstract class BaseCommand {
    protected BaseBle baseBle;

    public BaseCommand(BaseBle baseBle) {
        this.baseBle = baseBle;
    }

    public abstract void execute();
}
