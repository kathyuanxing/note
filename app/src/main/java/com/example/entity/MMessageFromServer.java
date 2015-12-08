package com.example.entity;

/**
 * Created by kathy on 2015/12/8.
 */

/**
 * 消息模型，本模型为服务器传来的消息模型，
 * 本模型与本地数据库消息模型的差别在于本模型的fileData字段对应的本地数据库消息模型字段为filePath，
 * 还有就是本模型的ID为消息在服务器中的ID
 *
 * @author double
 *
 */
public class MMessageFromServer {
    private String ID;
    private String senderID;
    private String senderName;
    private String receiverID;
    private String receiverName;
    private String messageText;
    private String fileData;
    private Long fileSize;
    private int fileDuration;
    private int type;  // 消息类型：1——文本；2——音频；3——图片；4——视频；5--二维码
    private String time;

    public MMessageFromServer(String iD, String senderID, String senderName,
                              String receiverID, String receiverName, String messageText,
                              String fileData, Long fileSize, int fileDuration, int type,
                              String time) {
        super();
        ID = iD;
        this.senderID = senderID;
        this.senderName = senderName;
        this.receiverID = receiverID;
        this.receiverName = receiverName;
        this.messageText = messageText;
        this.fileData = fileData;
        this.fileSize = fileSize;
        this.fileDuration = fileDuration;
        this.type = type;
        this.time = time;
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

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
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

}
