package com.example.dao;

/**
 * Created by kathy on 2015/12/3.
 */

import com.example.entity.MMessage;
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MessageDao {
    private MDatabaseHelper dbHelper;

    public MessageDao(Context context) {
        dbHelper = MDatabaseHelper.getInstance(context);
    }

    /**
     * 将消息条目保存如数据库
     *
     * @param message
     * @return
     */
    public boolean saveMessage(MMessage message) {
        // get content values
        ContentValues values = createContentValues(message);
        // get writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // insert
        long rowID = db.insert(MDatabaseConstants.TABLE_MESSAGE, null, values);
        if (rowID == -1)
            return false;
        else {
            return true;
        }
    }

    /**
     * 从指定位置开始读取指定数目的数据
     *
     * @param start
     * @param userID
     * @param talkerID
     * @param context
     * @return
     */
    public ArrayList<MMessage> getMessages(int start, String userID,
                                           String talkerID, Context context) {
        ArrayList<MMessage> messages = new ArrayList<MMessage>();
        Cursor cursor = null;
        try {
            // get readable database
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            // query
            cursor = db
                    .query(MDatabaseConstants.TABLE_MESSAGE,
                            MDatabaseConstants.MESSAGE_ALL_COLUMNS,
                            "sender_id = ? AND receiver_id = ? OR sender_id = ? AND receiver_id = ?",
                            new String[] { userID, talkerID, talkerID, userID },
                            null, null, "time desc", start + " , "
                                    + MDatabaseConstants.QUERY_MESSAGE_LIMIT);

            while (cursor.moveToNext())
                messages.add(createFromCursor(cursor));
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return messages;
    }

    private ContentValues createContentValues(MMessage message) {
        ContentValues values = new ContentValues();
        values.put("id", message.getID());
        values.put("sender_id", message.getSenderID());
        values.put("sender_name", message.getSenderName());
        values.put("receiver_id", message.getReceiverID());
        values.put("receiver_name", message.getReceiverName());
        values.put("message_text", message.getMessageText());
        values.put("file_path", message.getFilePath());
        values.put("file_size", message.getFileSize());
        values.put("file_duration", message.getFileDuration());
        values.put("type", message.getType());
        values.put("time", message.getTime());
        values.put("sendPosition", message.getSendPosition());
        return values;
    }

    private MMessage createFromCursor(Cursor cursor) {
        String ID = cursor.getString(0);
        String senderID = cursor.getString(1);
        String senderName = cursor.getString(2);
        String receiverID = cursor.getString(3);
        String receiverName = cursor.getString(4);
        String messageText = cursor.getString(5);
        String filePath = cursor.getString(6);
        Long fileSize = cursor.getLong(7);
        int fileDuration = cursor.getInt(8);
        int type = cursor.getInt(9);
        String time = cursor.getString(10);
        String sendPosition = cursor.getString(11);
        return new MMessage(ID, senderID, senderName, receiverID, receiverName,
                messageText, filePath, fileSize, fileDuration, type, time,
                sendPosition);
    }
}
