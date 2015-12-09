package com.example.entity;

/**
 * Created by kathy on 2015/12/3.
 */

import android.content.Context;
import com.example.dao.DAOFactory;
import com.example.dao.MessageDao;
import java.util.ArrayList;


/**
 * 消息模型，本模型为移动端本地数据库模型，
 * 与服务器传来的消息模型的差别在于本模型一个字段filePath对应的服务器传来的消息模型的fileData字段，
 * 还有就是本模型的ID为消息在本地数据库中的ID
 *
 * @author double
 *
 */

public class MMessage {
    private String ID;
    private String senderID;
    private String senderName;
    private String receiverID;
    private String receiverName;
    private String messageText;
    private String filePath;
    private Long fileSize;
    private int fileDuration;
    private int type;  // 消息类型：1——文本；2——音频；3——图片；4——视频；5——二维码
    private String time;
    private String sendPosition;

    private static DAOFactory daoFactory = DAOFactory.getInstance();

    /**
     * 保存消息
     *
     * @param context
     * @return
     */
    public boolean save(Context context) {
        // get dao
        MessageDao dao = daoFactory.getMessageDAO(context);
        // save
        return dao.saveMessage(this);
    }

    /**
     * 从指定位置开始读取指定数目的数据
     *
     * @param start
     * @param userID
     *            * @param talkerID
     * @param context
     * @return
     */
    public static ArrayList<MMessage> getMessages(int start, String userID,
                                                  String talkerID, Context context) {
        // get dao
        MessageDao dao = daoFactory.getMessageDAO(context);
        // query
        return dao.getMessages(start, userID, talkerID, context);
    }

    public static DAOFactory getDaoFactory() {
        return daoFactory;
    }

    public static void setDaoFactory(DAOFactory daoFactory) {
        MMessage.daoFactory = daoFactory;
    }

    public MMessage(String iD, String senderID, String senderName,
                    String receiverID, String receiverName, String messageText,
                    String filePath, Long fileSize, int fileDuration, int type,
                    String time, String sendPosition) {
        super();
        ID = iD;
        this.senderID = senderID;
        this.senderName = senderName;
        this.receiverID = receiverID;
        this.receiverName = receiverName;
        this.messageText = messageText;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.fileDuration = fileDuration;
        this.type = type;
        this.time = time;
        this.sendPosition = sendPosition;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public int getFileDuration() {
        return fileDuration;
    }

    public void setFileDuration(int fileDuration) {
        this.fileDuration = fileDuration;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSendPosition() {
        return sendPosition;
    }

    public void setSendPosition(String sendPosition) {
        this.sendPosition = sendPosition;
    }

    @Override
    public String toString() {
        return "MMessage{" +
                "ID='" + ID + '\'' +
                ", senderID='" + senderID + '\'' +
                ", senderName='" + senderName + '\'' +
                ", receiverID='" + receiverID + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", messageText='" + messageText + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", fileDuration=" + fileDuration +
                ", type=" + type +
                ", time='" + time + '\'' +
                ", sendPosition='" + sendPosition + '\'' +
                '}';
    }
}
