package com.choicemmed.ichoice.healthcheck.custom;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by Jiang nan on 2019/12/10 15:18.
 * @description
 **/
public class DayFormatter extends ValueFormatter {

    @Override
    public String getFormattedValue(float value) {
        return (int)(value%24)+"";
    }
}
