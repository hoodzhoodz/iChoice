package com.choicemmed.cbp1k1sdkblelibrary.cmd.command;

import com.choicemmed.cbp1k1sdkblelibrary.ble.BP2941Ble;

import java.util.Calendar;
import java.util.Locale;

/**
 * @author Name: ZhengZhong on 2018/1/23 15:32
 *         Email: zheng_zhong@163.com
 * @version V1.0.0
 */
public class BP2941CheckDateCommand extends BP2941BaseCommand {

    /**
     * 检验时间指令前三字节
     */
    private static final String CMD_HEADER = "000704";

    public BP2941CheckDateCommand(BP2941Ble ble) {
        super(ble);
    }

    @Override
    public void execute() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR) - 2000;
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        String cmd = String.format(Locale.ENGLISH, "%s%02x%02x%02x%02x%02x%02x", CMD_HEADER, year, month, day, hour, minute, second);

        ble.sendCmd(cmd);
    }
}
