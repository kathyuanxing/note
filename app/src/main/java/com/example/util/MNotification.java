package com.example.util;

/**
 * Created by kathy on 2015/12/8.
 */
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.testandroid.R;
import com.example.testandroid.RegisterSuccessActivity;

/**
 * 通知栏显示工具类
 *
 * @author double
 *
 */
public class MNotification {
    public static final int ALARM_NOTIFICATION_ID = 123321;
    public static final int MESSAGE_NOTIFICATION_ID = 123322;

    /**
     * 显示新消息通知
     *
     * @param context
     */
    public static void showMessageNotification(Context context) {
        String notificationText = "新消息";
        Intent mIntent = new Intent(context, RegisterSuccessActivity.class);
        int notificationDrawable = R.drawable.message;
        // 显示通知
        showNotification(mIntent, notificationText, notificationDrawable,
                MESSAGE_NOTIFICATION_ID, context);
    }

    /**
     * 显示新消息通知
     *
     * @param context
     */
    public static void showMessageNotification(String talkerName,
                                               String talkerID, Context context) {
        String notificationText = "来自" + talkerName + "的消息";
        Intent mIntent = new Intent(context, RegisterSuccessActivity.class);
        mIntent.putExtra("TalkerName", talkerName);
        mIntent.putExtra("TalkerID", talkerID);
        int notificationDrawable = R.drawable.message;
        // 显示通知
        showNotification(mIntent, notificationText, notificationDrawable,
                ALARM_NOTIFICATION_ID, context);
    }

    /**
     * 在通知栏显示通知
     *
     * @param intent
     *            点击通知后跳转的Intent
     * @param text
     *            通知文字
     * @param drawable
     *            通知图标
     * @param id
     *            通知ID
     * @param context
     */

    private static void showNotification(Intent intent, String text,
                                         int drawable, int id, Context context) {
        // 点击通知栏后进入相应界面
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(android.content.Context.NOTIFICATION_SERVICE);
//        Notification mNotification = new Notification(drawable, text,
//                System.currentTimeMillis());
        PendingIntent mPendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setAutoCancel(true)
                        .setContentIntent(mPendingIntent)
                        .setSmallIcon(drawable)
                        .setWhen(System.currentTimeMillis())
                        .setContentTitle(text)
                        .setContentText(text);
//        mNotification.defaults = Notification.DEFAULT_ALL;
//        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
//        mNotification.setLatestEventInfo(context, text, text, mPendingIntent);
        mNotificationManager.notify(id, builder.build());
    }

    /**
     * 擦除通知
     *
     * @param context
     */
    public static void cancelNotification(int id, Context context) {
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(android.content.Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(id);
    }
}
