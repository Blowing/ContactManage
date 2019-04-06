package com.blowing.contact.manager;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.util.Log;
import com.blowing.contact.model.SMSmessage;
import com.blowing.contact.util.TimeUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by wujie
 * on 2019/4/4/004.
 */
public class SMSManager {

    public static ArrayList<SMSmessage> smSmessages = new ArrayList<>();
    public static LinkedHashMap<String, Integer> mounthMap = new LinkedHashMap<>();
    /**
     * Telephony.Sms.ADDRESS     发件人地址，即手机号
     * Telephony.Sms.BODY      短信具体内容
     * Telephony.Sms.DATE      日期，long型
     * Telephony.Sms.READ      是否阅读0未读，1已读
     * Telephony.Sms.STATUS     短信状态-1 接收，0 complete,64 pending,128 failed
     * Telephony.Sms.TYPE      短信类型1是接收到的，2是已发出
     *
     * @param activity
     * @return
     */
    public static ArrayList<SMSmessage> getSMS(Activity activity) {
        String date = "";
        int count = 0;
        if (smSmessages.size() == 0) {
            ContentResolver resolver = activity.getContentResolver();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                Cursor cursor = resolver.query(Telephony.Sms.CONTENT_URI, new String[]{
                        Telephony.Sms.ADDRESS,   //
                        Telephony.Sms.BODY,
                        Telephony.Sms.DATE,
                        Telephony.Sms.READ,
                        Telephony.Sms.STATUS,
                        Telephony.Sms.TYPE,
                }, null, null, "date DESC");
                if (cursor != null) {
                    SMSmessage message;
                    while (cursor.moveToNext()) {
                        message = new SMSmessage();
                        message.address = cursor.getString(0);
                        message.body = cursor.getString(1);
                        message.date = cursor.getLong(2);
                        message.read = getMessageRead(cursor.getInt(3));
                        message.status = getMessageStatus(cursor.getInt(4));
                        message.type = getMessageType(cursor.getInt(5));
                        message.person = getPerson(message.address, activity);
                        smSmessages.add(message);
                        if (date.equals(TimeUtil.formatTimeByM(message.date))) {
                            count++;
                            mounthMap.put(date, count);
                        } else {
                            date = TimeUtil.formatTimeByM(message.date);
                            count = 0;
                        }
                        Log.i("wujie", "message : " + message.toString());
                    }
                    cursor.close();
                }

            }
        }
        return smSmessages;

    }


    private static String getPerson(String address, Activity activity) {
        try {
            ContentResolver resolver = activity.getContentResolver();
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, address);
            Cursor cursor;
            cursor = resolver.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
            if (cursor != null) {
                try {
                    if (cursor.getCount() != 0) {
                        cursor.moveToFirst();
                        String name = cursor.getString(0);
                        return name;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cursor.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;

    }

    private static String getMessageType(int anInt) {
        if (1 == anInt) {
            return "收到的";
        }
        if (2 == anInt) {
            return "已发出";
        }
        return null;
    }

    private static String getMessageStatus(int anInt) {
        switch (anInt) {
            case -1:
                return "接收";
            case 0:
                return "complete";
            case 64:
                return "pending";
            case 128:
                return "failed";
            default:
                break;
        }
        return null;

    }

    private static String getMessageRead(int anInt) {
        if (1 == anInt) {
            return "已读";
        }
        if (0 == anInt) {
            return "未读";
        }
        return null;
    }
}
