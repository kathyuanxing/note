package com.example.thread;

/**
 * Created by kathy on 2015/12/3.
 */

import java.util.ArrayList;

import com.example.dao.MDatabaseConstants;
import com.example.entity.Constants;
import com.example.entity.MMessage;
import com.example.util.AsyncHttp;
import com.example.util.MFileUtil;

public class SendMessageThread extends Thread {
    // 需要发送给服务器的消息列表
    private ArrayList<MMessage> messageList;
    // 线程运行标志
    private boolean flag = true;
    Constants constants = new Constants();

    public SendMessageThread(MMessage message) {
        messageList = new ArrayList<MMessage>();
        messageList.add(message);
    }

    public void addMessage(MMessage message) {
        messageList.add(message);
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        while (flag) {
            MMessage message;
            while (messageList.size() != 0) {
                // 获取消息序列的头
                message = messageList.get(0);
				/*发送消息*/
                // 发送给服务器的json字符串
                StringBuilder mMessageJson = new StringBuilder();
                // post请求参数
                ArrayList<String> keys = new ArrayList<String>();
                ArrayList<String> values = new ArrayList<String>();
                // 添加post请求参数
                keys.add("param");

                // 添加json内容
                mMessageJson.append("{\"ID\":\"" + message.getID() + "\",");
                System.out.println("ID-----"+message.getID());

                mMessageJson.append("\"senderID\":\"" + message.getSenderID()
                        + "\",");
                System.out.println("senderID-----"+message.getSenderID());
                mMessageJson.append("\"senderName\":\""
                        + message.getSenderName() + "\",");
                System.out.println("senderName-----"+message.getSenderName());
                mMessageJson.append("\"receiverID\":\"kathy"
                        + "\",");
                System.out.println("receiverID-----");
                mMessageJson.append("\"receiverName\":\"kathy"
                        + "\",");
                System.out.println("receiverName-----"+message.getReceiverName());
                // 根据不同的消息类型添加不同的内容
                //文本类型，以及二维码的文本信息
                if (message.getType() == MDatabaseConstants.MESSAGE_TYPE_TEXT
                        || message.getType() == MDatabaseConstants.MESSAGE_TYPE_QRCODE
                        || message.getType() == MDatabaseConstants.MESSAGE_TYPE_LOCATION
                        || message.getType() == MDatabaseConstants.MESSAGE_TYPE_TRACK) {
                    mMessageJson.append("\"msgContent\":\""
                            + message.getMessageText() + "\",");
                    mMessageJson.append("\"fileSize\":\"0\",");
                    mMessageJson.append("\"fileDuration\":\"0\",");
                    mMessageJson.append("\"fileData\":\"\",");
                    System.out.println("msgContent----"+message.getMessageText());
                } else {
                    mMessageJson.append("\"msgContent\":\"\",");
                    mMessageJson.append("\"fileSize\":\""
                            + message.getFileSize() + "\",");
                    mMessageJson.append("\"fileDuration\":\""
                            + message.getFileDuration() + "\",");
                    mMessageJson.append("\"fileData\":\""
                            + MFileUtil.getBase64String(message.getFilePath())
                            + "\",");
                }
                mMessageJson.append("\"type\":\"" + message.getType() + "\",");
                mMessageJson.append("\"sendTime\":\"" + message.getTime()
                        + "\",");
                mMessageJson.append("\"sendPosition\":\""
                        + message.getSendPosition() + "\",");
                // mMessageJson.append("\"sendPosition\":"
                // + message.getSendPosition() + ",");
                mMessageJson.append("\"remark\":\"\"}");

                values.clear();
                values.add(mMessageJson.toString());
                System.out.println("NEWMESSAGE-------"+mMessageJson.toString());
                // 发送post请求，注意，此处除了传递发送给服务器的请求，另外附带了消息的ID，这样，如果发送失败，消息ID可以传回
                AsyncHttp.post(constants.SERVER_BASE_URL
                                + Constants.NEW_MESSAGE, keys, values,
                        message.getID(), "MessageActivity");

                //System.out.println("SendMessageThread =  "+values.get(0));
                // 发送消息后删除消息序列的头
                messageList.remove(0);
            }

            // 发送完毕后等待
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
