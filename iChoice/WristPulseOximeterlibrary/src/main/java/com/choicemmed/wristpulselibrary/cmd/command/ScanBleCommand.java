package com.choicemmed.wristpulselibrary.cmd.command;


import com.choicemmed.wristpulselibrary.base.BaseBle;

/**
 * @author Created by Jiang nan on 2020/1/11 13:13.
 * @description
 **/
public class ScanBleCommand extends BaseCommand {
    public ScanBleCommand(BaseBle baseBle) {
        super(baseBle);
    }


    @Override
    public void execute() {
        baseBle.startLeScan();
    }
}
