package com.choicemmed.ichoice.healthcheck.custom;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * @anthor by jiangnan
 * @Date on 2020/1/21.
 */
public class TimeXFormatter extends ValueFormatter {
    Calendar calendar;

    public TimeXFormatter(Calendar calendar) {
        this.calendar = calendar;
    }
    @Override
    public String getFormattedValue(float value) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(calendar.getTimeInMillis() + (long) (value * 120 * 1000));
        int hour = (calendar1.get(Calendar.HOUR_OF_DAY));
        int minute = (calendar1.get(Calendar.MINUTE));
        int second = calendar1.get(Calendar.SECOND);
        String dateString = String.format("%02d", hour) + ":" + String.format("%02d", minute)
                + ":" + String.format("%02d", second);
        return dateString;
    }
}
