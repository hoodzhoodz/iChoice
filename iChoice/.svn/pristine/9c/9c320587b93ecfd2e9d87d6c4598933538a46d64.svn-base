package com.choicemmed.ichoice.healthcheck.custom;

import com.github.mikephil.charting.formatter.ValueFormatter;


/**
 * @author Created by Jiang nan on 2019/12/5 17:42.
 * @description
 **/
public class WeekFormatter extends ValueFormatter {
    String[] weeks = new String[7];

    public String[] getWeeks() {
        return weeks;
    }

    public void setWeeks(String[] weeks) {
        for (int i = 0; i < weeks.length; i++) {
            this.weeks[i] = weeks[i];
        }
    }

    @Override
    public String getFormattedValue(float value) {
        return weeks[(int)value%7];
    }
}
