package com.choicemmed.cbp1k1sdkblelibrary.cmd.command;

import com.choicemmed.cbp1k1sdkblelibrary.ble.BP2941Ble;

/**
 * @author Name: ZhengZhong on 2018/1/23 15:32
 *         Email: zheng_zhong@163.com
 * @version V1.0.0
 */
public class BP2941StartMeasureCommand extends BP2941BaseCommand {

    /**
     * 开始测量指令
     */
    private static final String CMD = "00020340";

    public BP2941StartMeasureCommand(BP2941Ble ble) {
        super(ble);
    }

    @Override
    public void execute() {
        ble.sendCmd(CMD);
    }
}
