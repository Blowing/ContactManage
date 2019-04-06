package com.blowing.contact.model;

import com.blowing.contact.util.TimeUtil;

/**
 * Created by wujie
 * on 2019/4/4/004.
 * 通话记录
 */
public class CallRecord {
    public long date;
    public String formatted_number;
    public String matched_number;
    public String name;
    public String type;
    public String location;
    public long duration;

    @Override
    public String toString() {
        return "CallRecord{" +
                "date=" + TimeUtil.formatTime(date) +
                ", formatted_number='" + formatted_number + '\'' +
                ", matched_number='" + matched_number + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", location='" + location + '\'' +
                ", duration=" + TimeUtil.formatDuration(duration) +
                '}';
    }


}
