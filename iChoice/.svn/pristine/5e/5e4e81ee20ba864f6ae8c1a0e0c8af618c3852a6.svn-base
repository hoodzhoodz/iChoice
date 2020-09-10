package com.choicemmed.ichoice.healthcheck.custom;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;
import java.util.Calendar;

public class DayOfMouthFormatter extends ValueFormatter {
    private Calendar beginCalendar;

    public DayOfMouthFormatter(Calendar beginCalendar) {
        this.beginCalendar = beginCalendar;
    }

    @Override
    public String getFormattedValue(float value) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(beginCalendar.getTimeInMillis() + ((long) value) * 24 * 60 * 60 * 1000);
        if (calendar1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return (calendar1.get(Calendar.DAY_OF_MONTH)) + "日";
        } else {
            return "";
        }

    }

}
