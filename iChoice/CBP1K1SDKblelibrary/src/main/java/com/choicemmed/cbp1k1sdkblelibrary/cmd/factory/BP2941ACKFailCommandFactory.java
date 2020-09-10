package com.choicemmed.cbp1k1sdkblelibrary.cmd.factory;

import com.choicemmed.cbp1k1sdkblelibrary.ble.BP2941Ble;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.command.BP2941ACKFailCommand;
import com.choicemmed.cbp1k1sdkblelibrary.cmd.command.BP2941BaseCommand;

/**
 * @author Name: ZhengZhong on 2018/1/23 16:43
 *         Email: zheng_zhong@163.com
 * @version V1.0.0
 */
public class BP2941ACKFailCommandFactory implements BP2941CreateCommandListener{
    @Override
    public BP2941BaseCommand createCommand(BP2941Ble ble) {
        return new BP2941ACKFailCommand(ble);
    }
}
