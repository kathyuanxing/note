package com.example.entity;

/**
 * Created by kathy on 2015/12/2.
 */
public class ChatMsgEntity {
    private String name;//消息来自
    private String date;//消息日期
    private String message;//消息内容
    private boolean isComMeg=true;//是否收到消息
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
    public ChatMsgEntity(String name,String date,String text,boolean isComMeg){
        super();
        this.name=name;
        this.date=date;
        this.message=text;
        this.isComMeg=isComMeg;
    }
}
