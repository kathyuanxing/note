package com.example.util;

/**
 * Created by kathy on 2015/12/8.
 */
import android.content.Context;

import com.example.entity.Constants;

/**
 * 程序永久保存变量控制类
 *
 * @author double
 *
 */
public class MSharedPreference {

    private static final String PREFERENCE_NAME = Constants.APP_NAME;
    // 用户名
    public static final String USER_NAME = "userName";
    // 用户ID
    public static final String USER_ID = "userID";
    public static final String Siptel = "siptel";
    // 密码
    public static final String PASSWORD = "password";
    // IP地址
    public static final String DEFAULT_IP = "ip";
    // 用户当前项目ID
    public static final String DEFAULT_PROJECT_ID = "currentProjectID";
    // 当前项目的服务器最近更改时间
    public static final String DEFAULT_PROJECT_UPDATE_TIME = "currentProjectUpdateTime";
    //获取警报的时间间隔
    public static final String GET_ALARM_GAP = "getAlarmGap";

    public static final String CALLEE = "callee";

    /** 保存布尔值 */
    public static void save(Context context, String key, boolean value) {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .edit().putBoolean(key, value).commit();
    }

    /** 获取布尔值 */
    public static boolean get(Context context, String key, boolean defaultValue) {
        return context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE).getBoolean(key, defaultValue);
    }

    /** 保存整型值 */
    public static void save(Context context, String key, int value) {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .edit().putInt(key, value).commit();
    }

    /** 获取整型值 */
    public static int get(Context context, String key, int defaultValue) {
        return context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE).getInt(key, defaultValue);
    }

    /** 保存长整型值 */
    public static void save(Context context, String key, long value) {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .edit().putLong(key, value).commit();
    }

    /** 获取长整型值 */
    public static long get(Context context, String key, long defaultValue) {
        return context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE).getLong(key, defaultValue);
    }

    /** 保存浮点数 */
    public static void save(Context context, String key, float value) {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .edit().putFloat(key, value).commit();
    }

    /** 获取浮点数 */
    public static float get(Context context, String key, float defaultValue) {
        return context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE).getFloat(key, defaultValue);
    }

    /** 保存字符串值 */
    public static void save(Context context, String key, String value) {
        //--得到名为PREFERENCE_NAME的偏好文件
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .edit().putString(key, value).commit();
    }

    /** 获取字符串值 */
    public static String get(Context context, String key, String defaultValue) {
        return context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE).getString(key, defaultValue);
    }
}