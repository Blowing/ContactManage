package com.blowing.contact.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by wujie
 * on 2019/4/6/006.
 */
public class NetworkAxisValueFormatter implements IAxisValueFormatter
{

    private DecimalFormat mFormat;

    public NetworkAxisValueFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0.0");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mFormat.format(value) + " B";
    }


}
