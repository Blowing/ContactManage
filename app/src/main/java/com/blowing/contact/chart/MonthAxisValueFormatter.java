package com.blowing.contact.chart;

import com.blowing.contact.manager.CallManager;
import com.blowing.contact.manager.NetworkManager;
import com.blowing.contact.manager.SMSManager;
import com.blowing.contact.model.Constant;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by wujie
 * on 2019/4/5/005.
 */
public class MonthAxisValueFormatter implements IAxisValueFormatter {
    private String action ;

    public MonthAxisValueFormatter(String action) {
        this.action = action;
    }

    @Override
    public String getFormattedValue(float v, AxisBase axisBase) {

        Iterator iterator = null;
        if (action.equals(Constant.MESSAGE)) {
            iterator = SMSManager.mounthMap.keySet().iterator();
            ArrayList<String> strings = new ArrayList<>();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                strings.add(key);
            }
            return strings.get((int)v);
        } else if(action.equals(Constant.CALL)){
            iterator = CallManager.mounthMap.keySet().iterator();
            ArrayList<String> strings = new ArrayList<>();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                strings.add(key);
            }
            return strings.get((int)v);
        }  else {
            return NetworkManager.list.get((int)v).name;
        }



    }
}
