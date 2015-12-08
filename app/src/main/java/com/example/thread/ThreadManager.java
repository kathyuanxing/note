package com.example.thread;

/**
 * Created by kathy on 2015/12/8.
 */
import java.util.ArrayList;
import android.content.Context;
import com.example.entity.MMessage;

public class ThreadManager {
    // 单例
    private static ThreadManager instance = null;
    // 将警报写入数据库的线程
    // private WriteAlarmThread writeAlarmThread;
    // 将消息写入数据库的线程
    private WriteMessageThread writeMessageThread;
    // 将消息发送给服务器的线程
    private SendMessageThread sendMessageThread;
    // 从服务器获取消息的线程
    private GetMessageThread getMessageThread;
    // 从服务器获取警报的线程
    //private GetAlarmThread getAlarmThread;

    private ThreadManager() {
    }

    // 获取单例
    public static ThreadManager getInstance() {
        if (instance == null) {
            synchronized (ThreadManager.class) {
                // check twice
                if (instance == null) {
                    instance = new ThreadManager();
                }
            }
        }
        return instance;
    }

    /**
     * 开启警报写入线程
     *
     * @param alarmBasicList
     * @param context
     */
    // public void startWriteAlarmThread(ArrayList<AlarmBasic> alarmBasicList,
    // Context context) {
    // if (writeAlarmThread == null) {
    // // 新建线程
    // writeAlarmThread = new WriteAlarmThread(alarmBasicList, context);
    // writeAlarmThread.start();
    // } else {
    // synchronized (writeAlarmThread) {
    // // 添加要写入的数据
    // writeAlarmThread.addAlarmBasicList(alarmBasicList);
    // // 唤醒线程
    // writeAlarmThread.notify();
    // }
    // }
    // }

    /**
     * 开启消息写入线程
     *
     * @param message
     * @param isSend
     * @param context
     */
    public void startWriteMessageThread(ArrayList<MMessage> messages,
                                        boolean isSend, Context context) {
        if (writeMessageThread == null) {
            // 新建线程
            writeMessageThread = new WriteMessageThread(messages, context);
            writeMessageThread.setIsSend(isSend);
            writeMessageThread.start();
        } else {
            synchronized (writeMessageThread) {
                // 添加要写入的数据
                writeMessageThread.addMessage(messages);
                writeMessageThread.setIsSend(isSend);
                // 唤醒线程
                writeMessageThread.notify();
            }
        }
    }

    /**
     * 开启消息发送线程
     *
     * @param message
     * @param context
     */
    public void startSendMessageThread(MMessage message) {
        if (sendMessageThread == null) {
            // 新建线程
            sendMessageThread = new SendMessageThread(message);
            sendMessageThread.start();
        } else {
            synchronized (sendMessageThread) {
                // 添加要写入的数据
                sendMessageThread.addMessage(message);
                // 唤醒线程
                sendMessageThread.notify();
            }
        }
    }

    /**
     * 开启从服务器获取消息的线程
     *
     * @param context
     */
    public void startGetMessageThread(Context context) {
        if (getMessageThread == null) {
            getMessageThread = new GetMessageThread(context);
            getMessageThread.start();
        }
    }

    public void stopGetMessageThread() {
        if (getMessageThread != null && getMessageThread.isAlive()) {
            getMessageThread.interrupt();
            getMessageThread = null;
        }
    }

    /**
     * 开启从服务器获取警报的线程
     *
     * @param context
     */
    // public void startGetAlarmThread(Context context) {
    // if (getAlarmThread == null) {
    // getAlarmThread = new GetAlarmThread(context);
    // getAlarmThread.start();
    // }
    // }
}
