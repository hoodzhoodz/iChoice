package com.choicemmed.ichoice.healthcheck.custom;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class MouthFormatter extends ValueFormatter
{
    private final DecimalFormat mFormat;
    private String suffix;

    public MouthFormatter(String suffix) {
        mFormat = new DecimalFormat("#");
        this.suffix = suffix;
    }

    @Override
    public String getFormattedValue(float value) {
        return (Integer.parseInt(mFormat.format(value % 5)) + 1) + suffix;
    }

}
