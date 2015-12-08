package com.example.dao;

/**
 * Created by kathy on 2015/12/3.
 */
public class MDatabaseConstants {
    // 数据库版本
    public static final int DATABASE_VERSION = 3;
    // 数据库名
    public static final String DATABASE_NAME = "WHUGMS.db";
    // 每次数据库查询的条目数
    public static final int QUERY_LIMIT = 10;
    // 每次数据库查询消息的条目数
    public static final int QUERY_MESSAGE_LIMIT = 10;

    /* 表名*/
    public static final String TABLE_ALARM = "alarmInfo";
    public static final String TABLE_MESSAGE = "messageInfo";
    /*表的所有列*/
    public static final String[] ALARM_ALL_COLUMNS = { "id", "user_name",
            "title", "time", "is_read" };
    public static final String[] MESSAGE_ALL_COLUMNS = { "id", "sender_id",
            "sender_name", "receiver_id", "receiver_name", "message_text",
            "file_path", "file_size", "file_duration", "type", "time",
            "sendPosition" };

    /*警报是否已读*/
    public static final int ALARM_NEVER_READ = 0;// 未读
    public static final int ALARM_HAS_READ = 1;// 已读

    /*消息类型*/
    public static final int MESSAGE_TYPE_TEXT = 1;
    public static final int MESSAGE_TYPE_AUDIO = 2;
    public static final int MESSAGE_TYPE_IMAGE = 3;
    public static final int MESSAGE_TYPE_VIDEO = 4;
    public static final int MESSAGE_TYPE_QRCODE = 5; //二维码
    public static final int MESSAGE_TYPE_LOCATION = 6; // 位置
    public static final int MESSAGE_TYPE_TRACK = 7;// 轨迹

}
