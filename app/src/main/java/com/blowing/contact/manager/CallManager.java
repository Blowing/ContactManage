package com.blowing.contact.manager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.Log;
import com.blowing.contact.model.CallRecord;
import com.blowing.contact.util.TimeUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by wujie
 * on 2019/4/4/004.
 * 通话记录
 */
public class CallManager {

    public static ArrayList<CallRecord> callRecords = new ArrayList<>();
    public static LinkedHashMap<String, Integer> mounthMap = new LinkedHashMap<>();
    /**
     * CallLog.Calls.CACHED_FORMATTED_NUMBER      通话记录格式化号码
     * CallLog.Calls.CACHED_MATCHED_NUMBER     通话记录为格式化号码
     * CallLog.Calls.CACHED_NAME     联系人名称
     * CallLog.Calls.TYPE    通话类型
     * CallLog.Calls.DATE    通话时间(long型)
     * CallLog.Calls.DURATION     通话时长(秒为单位)
     * CallLog.Calls.GEOCODED_LOCATION    运营商地址(如：浙江杭州)
     *
     * 通话类型
     * CallLog.Calls.INCOMING_TYPE      呼入
     * CallLog.Calls.OUTGOING_TYPE      呼出
     * CallLog.Calls.MISSED_TYPE       未接
     *
     * @param activity
     * @return
     */
    public static ArrayList<CallRecord> getCallRecods(Activity activity) {
//        ArrayList<CallRecord> callRecords = new ArrayList<>();
        String date = "";
        int count = 0;

        ContentResolver resolver = activity.getContentResolver();
        Log.i(TAG, "query call log " + resolver);
        //获取cursor对象
        @SuppressLint("MissingPermission") Cursor cursor = resolver.query(CallLog.Calls.CONTENT_URI, new String[]{
                CallLog.Calls.CACHED_FORMATTED_NUMBER,
                CallLog.Calls.CACHED_MATCHED_NUMBER,
                CallLog.Calls.CACHED_NAME,
                CallLog.Calls.TYPE,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION,
                CallLog.Calls.GEOCODED_LOCATION,
        }, null, null, "date DESC");

        /**
         *  "date DESC limit 2"
         *  按时间排序查询2条记录
         */

        if (cursor != null) {
            Log.i(TAG, "cursor length is " + cursor.getCount());
            try {
                CallRecord record;
                while (cursor.moveToNext()) {
                    record = new CallRecord();
                    record.formatted_number = cursor.getString(0);
                    record.matched_number = cursor.getString(1);
                    record.name = cursor.getString(2);
                    record.type = getCallType(cursor.getInt(3));
                    record.date = cursor.getLong(4);
                    record.duration = cursor.getLong(5);
                    record.location = cursor.getString(6);
                    Log.i("wujie", record.toString());
                    callRecords.add(record);
                    if (date.equals(TimeUtil.formatTimeByM(record.date))) {
                        count = count+ (int) record.duration;
                        mounthMap.put(date, count);
                    } else {
                        date = TimeUtil.formatTimeByM(record.date);
                        count = 0;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();  //关闭cursor，避免内存泄露
            }
        }
        return callRecords;

    }


    private static String getCallType(int anInt) {
        switch (anInt) {
            case CallLog.Calls.INCOMING_TYPE:
                return "呼入";
            case CallLog.Calls.OUTGOING_TYPE:
                return "呼出";
            case CallLog.Calls.MISSED_TYPE:
                return "未接";
            default:
                break;
        }
        return null;
    }
}
