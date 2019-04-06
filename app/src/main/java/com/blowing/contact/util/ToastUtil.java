package com.blowing.contact.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wujie
 * on 2019/4/3/003.
 */
public class ToastUtil {

    public static void showToast(Context context, String content) {
        showToast(context, content, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, String content, int duration) {
        Toast.makeText(context, content, duration).show();
    }
}
