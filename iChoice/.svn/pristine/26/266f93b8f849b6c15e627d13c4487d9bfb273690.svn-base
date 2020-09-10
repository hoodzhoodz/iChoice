package com.choicemmed.cbp1k1sdkblelibrary.cmd.command;

import com.choicemmed.cbp1k1sdkblelibrary.ble.BP2941Ble;

/**
 * @author Name: ZhengZhong on 2018/1/23 15:32
 *         Email: zheng_zhong@163.com
 * @version V1.0.0
 */
public class BP2941PowerOffCommand extends BP2941BaseCommand {

    /**
     * 关机指令
     */
    private static final String CMD = "000105";

    public BP2941PowerOffCommand(BP2941Ble ble) {
        super(ble);
    }

    @Override
    public void execute() {
        ble.sendCmd(CMD);
    }
}
