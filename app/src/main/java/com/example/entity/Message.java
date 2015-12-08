package com.example.entity;

/**
 * Created by kathy on 2015/12/3.
 */
import java.util.Date;
public class Message {
    private int message_id;
    private String message_type;
    private String message_sender_id;
    private String message_sender_name;
    private String message_content;
    private Date message_time;
    private String message_remark;
    public int getMessage_id() {
        return message_id;
    }
    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }
    public String getMessage_type() {
        return message_type;
    }
    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }
    public String getMessage_sender_id() {
        return message_sender_id;
    }
    public void setMessage_sender_id(String message_sender_id) {
        this.message_sender_id = message_sender_id;
    }
    public String getMessage_sender_name() {
        return message_sender_name;
    }
    public void setMessage_sender_name(String message_sender_name) {
        this.message_sender_name = message_sender_name;
    }
    public String getMessage_content() {
        return message_content;
    }
    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }
    public Date getMessage_time() {
        return message_time;
    }
    public void setMessage_time(Date message_time) {
        this.message_time = message_time;
    }
    public String getMessage_remark() {
        return message_remark;
    }
    public void setMessage_remark(String message_remark) {
        this.message_remark = message_remark;
    }

}
