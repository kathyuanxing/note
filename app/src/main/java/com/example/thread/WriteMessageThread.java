package com.example.thread;

/**
 * Created by kathy on 2015/12/8.
 */
import java.util.ArrayList;

import android.content.Context;

import com.example.entity.Constants;
import com.example.entity.MMessage;
import com.example.handler.MessageHandlerManager;
import com.example.util.MLogger;
import com.example.util.MNotification;

public class WriteMessageThread extends Thread {
    // 需要写入数据库的消息列表
    private ArrayList<MMessage> messageList;
    // 上下文
    private Context context;
    // 线程运行的标志
    private boolean flag = true;
    // 消息是否本机发送
    private boolean isSend = true;

    public WriteMessageThread(ArrayList<MMessage> messages, Context context) {
        messageList = new ArrayList<MMessage>();
        messageList.addAll(messages);
        this.context = context;
    }

    public void addMessage(ArrayList<MMessage> messages) {
        messageList.addAll(messages);
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setIsSend(boolean isSend) {
        this.isSend = isSend;
    }

    @Override
    public void run() {
        while (flag) {
            MMessage message;
            while (messageList.size() != 0) {
                // 获取消息序列的头
                message = messageList.get(0);
                // 保存消息
                message.save(context);
                MLogger.i("写消息成功", true);
                // 写完消息后删除消息序列的头
                messageList.remove(0);
                // 通知UI线程刷新
                MessageHandlerManager.getInstance().sendMessage(
                        Constants.WRITE_MESSAGE, "RegisterSuccessActivity");
                if (!isSend)
                    // 通知栏显示新消息通知
                    MNotification.showMessageNotification(
                            message.getSenderName(), message.getSenderID(),
                            context);
            }

            // 默认不在通知栏通知
            setIsSend(true);

            // 写数据完毕后等待
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
