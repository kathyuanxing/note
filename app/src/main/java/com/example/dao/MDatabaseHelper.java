package com.example.dao;

/**
 * Created by kathy on 2015/12/3.
 */

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;

public class MDatabaseHelper extends SQLiteOpenHelper {
    // 单例
    private static MDatabaseHelper instance;
    // 新建警报表的SQL语句
    private final String CREATE_TABLE_ALARM_SQL = "CREATE TABLE "
            + MDatabaseConstants.TABLE_ALARM + "(id integer primary key , "
            + "user_name text not null, " + "title text, " + "time text, "
            + "is_read integer not null" + ");";
    // 新建消息表的SQL语句
    private final String CREATE_TABLE_MESSAGE_SQL = "CREATE TABLE "
            + MDatabaseConstants.TABLE_MESSAGE
            + "(id text primary key , "
            + "sender_id text not null, " + "sender_name text not null, "
            + "receiver_id text not null, " + "receiver_name text not null, "
            + "message_text text, " + "file_path text, "
            + "file_size integer, " + "file_duration integer, "
            + "type integer not null, " + "time text not null, "
            + "sendPosition text" + ");";

    MDatabaseHelper(Context context) {
        super(context, MDatabaseConstants.DATABASE_NAME, null,
                MDatabaseConstants.DATABASE_VERSION);
    }

    // 获取单例
    public static MDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (MDatabaseHelper.class) {
                if (instance == null)
                    instance = new MDatabaseHelper(context);
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            // 新建警报表
            db.execSQL(CREATE_TABLE_ALARM_SQL);
            // 新建消息表
            db.execSQL(CREATE_TABLE_MESSAGE_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            // 删除旧表
            db.execSQL("DROP TABLE IF EXISTS " + MDatabaseConstants.TABLE_ALARM);
            db.execSQL("DROP TABLE IF EXISTS "
                    + MDatabaseConstants.TABLE_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 重建
        onCreate(db);
    }
}
