package com.example.util;

/**
 * Created by kathy on 2015/12/8.
 */
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MMicroUtil {
    /**
     * get the formatted current time
     *
     * @return
     */
    public static String getCurrentTimeStamp() {
        // return the formated current time
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
                .format(new java.util.Date(System.currentTimeMillis()));
    }

    /**
     * get count of milliseconds from 1970-1-1-00:00:00
     *
     * @return
     */
    public static String getCurrentTimeMillis() {
        long currentTimeMillis = System.currentTimeMillis();
        return Long.toString(currentTimeMillis);
    }

    /**
     * get count of milliseconds from 1970-1-1-00:00:00 plus gap
     *
     * @return
     */
    public static String getCurrentTimeMillis(long gap) {
        long currentTimeMillis = System.currentTimeMillis() + gap;
        return Long.toString(currentTimeMillis);
    }
}