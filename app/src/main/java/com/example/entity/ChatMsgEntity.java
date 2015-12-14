package com.example.entity;

/**
 * Created by kathy on 2015/12/2.
 */
public class ChatMsgEntity {
    private String name;//消息来自
    private String date;//消息日期
    private String message;//消息内容
    private String path;//消息路径
    private int type;//消息类型
    private boolean isComMeg=true;//是否收到消息
    @Override
    public String toString() {
        return "ChatMsgEntity{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", message='" + message + '\'' +
                ", isComMeg=" + isComMeg +
                '}';
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getMsgType() {
        return isComMeg;
    }

    public void setMsgType(boolean isComMeg) {
        this.isComMeg = isComMeg;
    }
    public ChatMsgEntity(){
    }
}
