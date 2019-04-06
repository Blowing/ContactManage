package com.blowing.contact.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by wujie
 * on 2019/4/5/005.
 */
public class CallAxisValueFormatter implements IAxisValueFormatter
{

    private DecimalFormat mFormat;

    public CallAxisValueFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0.0");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mFormat.format(value) + " 分";
    }


}
