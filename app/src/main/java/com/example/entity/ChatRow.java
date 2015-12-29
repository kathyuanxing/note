package com.example.entity;

/**
 * Created by kathy on 2015/12/29.
 */
public class ChatRow {
    public ChatRow(){
    }
    private String chatName;
    private String chatMessage;
    private int chatHead;
    private String chatTime;
    private int chatState;

    @Override
    public String toString() {
        return "ChatRow{" +
                "chatName='" + chatName + '\'' +
                ", chatMessage='" + chatMessage + '\'' +
                ", chatHead=" + chatHead +
                ", chatTime='" + chatTime + '\'' +
                ", chatState=" + chatState +
                '}';
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public String getChatTime() {
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }

    public int getChatHead() {
        return chatHead;
    }

    public void setChatHead(int chatHead) {
        this.chatHead = chatHead;
    }

    public int getChatState() {
        return chatState;
    }

    public void setChatState(int chatState) {
        this.chatState = chatState;
    }


}
