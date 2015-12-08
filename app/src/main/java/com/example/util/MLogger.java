package com.example.util;

/**
 * Created by kathy on 2015/12/8.
 */
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.util.Log;

public class MLogger {
    private static final String TAG = "WhuGMS";

    private static String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:ms", Locale.CHINA)
                .format(new java.util.Date(System.currentTimeMillis()));
    }

    public static void v(String message, boolean isWithTime) {
        if (isWithTime)
            Log.v(TAG, message + ", " + getTime());
        else
            Log.v(TAG, message);
    }

    public static void d(String message, boolean isWithTime) {
        if (isWithTime)
            Log.d(TAG, message + ", " + getTime());
        else
            Log.d(TAG, message);
    }

    public static void i(String message, boolean isWithTime) {
        if (isWithTime)
            Log.i(TAG, message + ", " + getTime());
        else
            Log.i(TAG, message);
    }

    public static void w(String message, boolean isWithTime) {
        if (isWithTime)
            Log.w(TAG, message + ", " + getTime());
        else
            Log.w(TAG, message);
    }

    public static void e(String message, boolean isWithTime) {
        if (isWithTime)
            Log.e(TAG, message + ", " + getTime());
        else
            Log.e(TAG, message);
    }
}
