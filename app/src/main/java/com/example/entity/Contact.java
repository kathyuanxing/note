package com.example.entity;
import java.util.Date;
/**
 * Created by kathy on 2015/12/27.
 */

public class Contact {
    private int contact_id;
    private String contact_userid;
    private String contact_name;
    private String contact_siptel1;
    private String contact_siptel2;
    private String contact_nickname;
    private String contact_image_path;
    private String contact_province;
    private String contact_position;//title
    private String contact_post;
    private long contact_telephone;
    private String contact_chushi;
    private String contact_workvoice;
    private String contact_ord;
    private String contact_fax;
    private String contact_message;
    private String contact_department;
    private String contact_department_id;
    private int contact_work_number = 0;
    private int contact_work_id = 0;
    private int contact_short_phone_num = 0;
    private String contact_email = "";
    private String contact_state = "";
    private Date contact_update_time = new Date();
    private String contact_remark = "";
    private String sortLetters;  //显示数据拼音的首字母 2013-12-25 WH

    public Contact() {
        super();
    }

    public Contact(int contact_id, String contact_userid, String contact_name,
                   String contact_siptel1, String contact_siptel2,
                   String contact_nickname, String contact_image_path,
                   String contact_province, String contact_position,
                   String contact_post, long contact_telephone, String contact_chushi,
                   String contact_workvoice, String contact_ord, String contact_fax,
                   String contact_message, String contact_department,
                   String contact_department_id, int contact_work_number,
                   int contact_work_id, int contact_short_phone_num,
                   String contact_email, String contact_state,
                   Date contact_update_time, String contact_remark, String sortLetters) {
        super();
        this.contact_id = contact_id;
        this.contact_userid = contact_userid;
        this.contact_name = contact_name;
        this.contact_siptel1 = contact_siptel1;
        this.contact_siptel2 = contact_siptel2;
        this.contact_nickname = contact_nickname;
        this.contact_image_path = contact_image_path;
        this.contact_province = contact_province;
        this.contact_position = contact_position;
        this.contact_post = contact_post;
        this.contact_telephone = contact_telephone;
        this.contact_chushi = contact_chushi;
        this.contact_workvoice = contact_workvoice;
        this.contact_ord = contact_ord;
        this.contact_fax = contact_fax;
        this.contact_message = contact_message;
        this.contact_department = contact_department;
        this.contact_department_id = contact_department_id;
        this.contact_work_number = contact_work_number;
        this.contact_work_id = contact_work_id;
        this.contact_short_phone_num = contact_short_phone_num;
        this.contact_email = contact_email;
        this.contact_state = contact_state;
        this.contact_update_time = contact_update_time;
        this.contact_remark = contact_remark;
        this.sortLetters = sortLetters;
    }
    @Override
    public String toString() {
        return "Contact{" +
                "contact_id=" + contact_id +
                ", contact_userid='" + contact_userid + '\'' +
                ", contact_name='" + contact_name + '\'' +
                ", contact_siptel1='" + contact_siptel1 + '\'' +
                ", contact_siptel2='" + contact_siptel2 + '\'' +
                ", contact_nickname='" + contact_nickname + '\'' +
                ", contact_image_path='" + contact_image_path + '\'' +
                ", contact_province='" + contact_province + '\'' +
                ", contact_position='" + contact_position + '\'' +
                ", contact_post='" + contact_post + '\'' +
                ", contact_telephone=" + contact_telephone +
                ", contact_chushi='" + contact_chushi + '\'' +
                ", contact_workvoice='" + contact_workvoice + '\'' +
                ", contact_ord='" + contact_ord + '\'' +
                ", contact_fax='" + contact_fax + '\'' +
                ", contact_message='" + contact_message + '\'' +
                ", contact_department='" + contact_department + '\'' +
                ", contact_department_id='" + contact_department_id + '\'' +
                ", contact_work_number=" + contact_work_number +
                ", contact_work_id=" + contact_work_id +
                ", contact_short_phone_num=" + contact_short_phone_num +
                ", contact_email='" + contact_email + '\'' +
                ", contact_state='" + contact_state + '\'' +
                ", contact_update_time=" + contact_update_time +
                ", contact_remark='" + contact_remark + '\'' +
                ", sortLetters='" + sortLetters + '\'' +
                '}';
    }

    public int getContact_id() {
        return contact_id;
    }
    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }
    public String getContact_userid() {
        return contact_userid;
    }
    public void setContact_userid(String contact_userid) {
        this.contact_userid = contact_userid;
    }
    public String getContact_name() {
        return contact_name;
    }
    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }
    public String getContact_siptel1() {
        return contact_siptel1;
    }
    public void setContact_siptel1(String contact_siptel1) {
        this.contact_siptel1 = contact_siptel1;
    }
    public String getContact_siptel2() {
        return contact_siptel2;
    }
    public void setContact_siptel2(String contact_siptel2) {
        this.contact_siptel2 = contact_siptel2;
    }
    public String getContact_nickname() {
        return contact_nickname;
    }
    public void setContact_nickname(String contact_nickname) {
        this.contact_nickname = contact_nickname;
    }
    public String getContact_image_path() {
        return contact_image_path;
    }
    public void setContact_image_path(String contact_image_path) {
        this.contact_image_path = contact_image_path;
    }
    public String getContact_province() {
        return contact_province;
    }
    public void setContact_province(String contact_province) {
        this.contact_province = contact_province;
    }
    public String getContact_position() {
        return contact_position;
    }
    public void setContact_position(String contact_position) {
        this.contact_position = contact_position;
    }
    public String getContact_post() {
        return contact_post;
    }
    public void setContact_post(String contact_post) {
        this.contact_post = contact_post;
    }
    public long getContact_telephone() {
        return contact_telephone;
    }
    public void setContact_telephone(long contact_telephone) {
        this.contact_telephone = contact_telephone;
    }
    public String getContact_chushi() {
        return contact_chushi;
    }
    public void setContact_chushi(String contact_chushi) {
        this.contact_chushi = contact_chushi;
    }
    public String getContact_workvoice() {
        return contact_workvoice;
    }
    public void setContact_workvoice(String contact_workvoice) {
        this.contact_workvoice = contact_workvoice;
    }
    public String getContact_ord() {
        return contact_ord;
    }
    public void setContact_ord(String contact_ord) {
        this.contact_ord = contact_ord;
    }
    public String getContact_fax() {
        return contact_fax;
    }
    public void setContact_fax(String contact_fax) {
        this.contact_fax = contact_fax;
    }
    public String getContact_message() {
        return contact_message;
    }
    public void setContact_message(String contact_message) {
        this.contact_message = contact_message;
    }
    public String getContact_department() {
        return contact_department;
    }
    public void setContact_department(String contact_department) {
        this.contact_department = contact_department;
    }
    public String getContact_department_id() {
        return contact_department_id;
    }
    public void setContact_department_id(String contact_department_id) {
        this.contact_department_id = contact_department_id;
    }
    public int getContact_work_number() {
        return contact_work_number;
    }
    public void setContact_work_number(int contact_work_number) {
        this.contact_work_number = contact_work_number;
    }
    public int getContact_work_id() {
        return contact_work_id;
    }
    public void setContact_work_id(int contact_work_id) {
        this.contact_work_id = contact_work_id;
    }
    public int getContact_short_phone_num() {
        return contact_short_phone_num;
    }
    public void setContact_short_phone_num(int contact_short_phone_num) {
        this.contact_short_phone_num = contact_short_phone_num;
    }
    public String getContact_email() {
        return contact_email;
    }
    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }
    public String getContact_state() {
        return contact_state;
    }
    public void setContact_state(String contact_state) {
        this.contact_state = contact_state;
    }
    public Date getContact_update_time() {
        return contact_update_time;
    }
    public void setContact_update_time(Date contact_update_time) {
        this.contact_update_time = contact_update_time;
    }
    public String getContact_remark() {
        return contact_remark;
    }
    public void setContact_remark(String contact_remark) {
        this.contact_remark = contact_remark;
    }
    //-------------2013-12-25 WH--------------------
    public String getSortLetters() {
        return sortLetters;
    }
    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

}
