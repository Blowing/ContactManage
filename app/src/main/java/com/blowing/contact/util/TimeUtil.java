package com.blowing.contact.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wujie
 * on 2019/4/4/004.
 * 时间工具类
 */
public class TimeUtil {

    public static String formatTime(long date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date(date));
    }

    public static String formatTimeByM(long date) {
        DateFormat dateFormat = new SimpleDateFormat("yy-MM", Locale.getDefault());
        return dateFormat.format(new Date(date));
    }

    public static String formatDuration(long time) {
        long s = time % 60;
        long m = time / 60;
        long h = time / 60 / 60;
        StringBuilder sb = new StringBuilder();
        if (h > 0) {
            sb.append(h).append("小时");
        }
        if (m > 0) {
            sb.append(m).append("分");
        }
        sb.append(s).append("秒");
        return sb.toString();
    }

}
