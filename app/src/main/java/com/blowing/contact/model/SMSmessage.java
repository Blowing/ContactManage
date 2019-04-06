package com.blowing.contact.model;

import com.blowing.contact.util.TimeUtil;

/**
 * Created by wujie
 * on 2019/4/4/004.
 */
public class SMSmessage {

    public long date;
    public String address;
    public String body;
    public String person;
    public String read;
    public String status;
    public String type;

    @Override
    public String toString() {
        return "SMSMessage{" +
                "date=" + TimeUtil.formatTime(date) +
                ", address='" + address + '\'' +
                ", body='" + body + '\'' +
                ", person='" + person + '\'' +
                ", read='" + read + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                '}';
    }



}
