package com.analytics.bjj;

import com.github.mikephil.charting.utils.ValueFormatter;
//this class formats the pie chart values are integers

public class IntValueFormatter implements ValueFormatter {

    public IntValueFormatter() {
    }

    @Override
    public String getFormattedValue(float value) {
        int tempInt;
        tempInt = Math.round(value);
        return Integer.toString(tempInt);
    }
}