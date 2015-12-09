package com.example.thread;

/**
 * Created by kathy on 2015/12/8.
 */
import java.util.ArrayList;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.dao.MDatabaseConstants;
import com.example.entity.Constants;
import com.example.entity.MMessage;
import com.example.entity.MMessageFromServer;
import com.example.handler.MessageHandlerManager;
import com.example.util.AsyncHttp;
import com.example.util.MDataOperation;
import com.example.util.MFileUtil;
import com.example.util.MJsonParseUtil;
import com.example.util.MLogger;
import com.example.util.MMicroUtil;
import com.example.util.MSharedPreference;

public class GetMessageThread extends Thread {
    // 上下文
    private Context context;
    // 线程运行的标志
    private boolean flag = true;
    // post请求参数
    ArrayList<String> keys, values;
    // 接收请求数据的handler
    private Handler mHandler;
    Constants constants = new Constants();

    public GetMessageThread(Context context) {
        this.context = context;

        // 初始化handler
        initHandler();

		/* 添加post请求参数，因为线程运行中，参数始终不变，所以在构造函数中初始化 */
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        while (flag) {
            // 等待
            synchronized (this) {
                try {
                    wait(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 再次判断线程运行标志，防止ThreadManager里面停止线程后，线程继续运行
                if (!flag)
                    break;
                MLogger.i("get message", true);
				/* 向服务器请求消息 */
                // 获取用户ID
                String userID = MSharedPreference.get(context,
                        MSharedPreference.USER_ID, "");
                String mGetMessageJson = "{\"userID\":\"" + userID + "\"}";
                // post请求参数
                keys = new ArrayList<String>();
                values = new ArrayList<String>();
                // 添加post请求参数
                keys.add("param");
                values.add(mGetMessageJson);
                System.out.println("GETMESSAGE------"+mGetMessageJson);
                //System.out.println("GetMessageThread:    "+values.get(0));
                AsyncHttp.post(constants.SERVER_BASE_URL + Constants.GET_MESSAGE, keys, values,"GetMessageThread");
            }
        }
    }

    private void initHandler() {
        // 初始化handler
        // Looper.prepare();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Constants.REQUEST_SUCCESS:
                        String result = (String) msg.obj;
                        // 解析服务器返回数据
                        ArrayList<MMessageFromServer> messageList = MJsonParseUtil
                                .getMessageList(result);
                        if (messageList != null && messageList.size() != 0) {
                            // 通知服务器已经收到消息
                            sendResponse(messageList);
                            // 将消息写入本地
                            writeMessage(messageList);
                        }
                        break;
                    case Constants.REQUEST_FAIL:
                        break;
                }
            }
        };
        // Looper.loop();
        // 注册handler
        MessageHandlerManager.getInstance().register(mHandler,
                Constants.REQUEST_SUCCESS, "GetMessageThread");
        MessageHandlerManager.getInstance().register(mHandler,
                Constants.REQUEST_FAIL, "GetMessageThread");
    }

    /**
     * 将消息写入本地
     *
     * @param messageList
     *            服务器返回的消息列表
     */
    private void writeMessage(ArrayList<MMessageFromServer> messageList) {
        // 写入本地的消息列表
        ArrayList<MMessage> localMessageList = new ArrayList<MMessage>(
                messageList.size());
        // 本地数据库中的消息ID
        String id;
        long gap = 10;
        for (MMessageFromServer message : messageList) {
            // 构造消息ID，注意要添加时间间隔，否则会造成ID冲突
            id = MMicroUtil.getCurrentTimeMillis(gap);
            gap += 10;

            // 文字类型，以及二维码的文本信息
            if (message.getType() == MDatabaseConstants.MESSAGE_TYPE_TEXT
                    || message.getType() == MDatabaseConstants.MESSAGE_TYPE_QRCODE
                    || message.getType() == MDatabaseConstants.MESSAGE_TYPE_LOCATION
                    || message.getType() == MDatabaseConstants.MESSAGE_TYPE_TRACK) {
                // 将消息存入数据库
                localMessageList.add(new MMessage(id, message.getSenderID(),
                        message.getSenderName(), message.getReceiverID(),
                        message.getReceiverName(), message.getMessageText(),
                        null, message.getFileSize(), message.getFileDuration(),
                        message.getType(), message.getTime(), null));
            } else {
                // 存储文件
                String path = MFileUtil.saveBase64File(message.getFileData(),
                        message.getType());
                // 将消息存入数据库
                localMessageList.add(new MMessage(id, message.getSenderID(),
                        message.getSenderName(), message.getReceiverID(),
                        message.getReceiverName(), null, path, message
                        .getFileSize(), message.getFileDuration(),
                        message.getType(), message.getTime(), null));
            }
        }
        // 将消息写入数据库
        MDataOperation.writeMessages(localMessageList,
                Constants.DATA_STORE_DATABASE, false, context);
    }

    /**
     * 通知服务器已经收到消息列表
     *
     * @param messageList
     */
    private void sendResponse(ArrayList<MMessageFromServer> messageList) {
        // 添加get请求参数
        ArrayList<String> keys = new ArrayList<String>();
        ArrayList<String> values = new ArrayList<String>();
        // 构造消息ID组成的字符串
        StringBuilder ids = new StringBuilder();
        for (MMessageFromServer message : messageList) {
            ids.append(message.getID() + ",");
        }
        // 去掉最后一个逗号
        ids.deleteCharAt(ids.length() - 1);

        // 向服务器发送请求
        keys.add("param");
        values.add("{  \"ids\":\"" + ids.toString() + "\" }");
        // 发送post请求
        AsyncHttp.post(
                constants.SERVER_BASE_URL
                        + Constants.MESSAGE_RESPONSE, keys, values, "null");
    }
}
