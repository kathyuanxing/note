package com.example.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.Date;
import android.database.sqlite.SQLiteDatabase;
import java.text.ParseException;
import com.example.entity.Contact;
import com.example.util.CharacterParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by kathy on 2015/12/27.
 */
public class ContactDao {

    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private CharacterParser characterParser;

    public ContactDao(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ContactDao open() {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    // insert into tale contact
    public long insertIntoTableContact(Contact contact) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.CONTACT_ID, contact.getContact_id());
        contentValues.put(DbHelper.CONTACT_USERID, contact.getContact_userid());
        contentValues.put(DbHelper.CONTACT_NAME, contact.getContact_name());
        contentValues.put(DbHelper.CONTACT_SIPTEL1,
                contact.getContact_siptel1());
        contentValues.put(DbHelper.CONTACT_SIPTEL2,
                contact.getContact_siptel2());
        contentValues.put(DbHelper.CONTACT_NICKNAME,
                contact.getContact_nickname());
        contentValues.put(DbHelper.CONTACT_IMAGE_PATH,
                contact.getContact_image_path());
        contentValues.put(DbHelper.CONTACT_PROVINCE,
                contact.getContact_province());
        contentValues.put(DbHelper.CONTACT_POSITION,
                contact.getContact_position());
        contentValues.put(DbHelper.CONTACT_POST, contact.getContact_post());
        contentValues.put(DbHelper.CONTACT_TELEPHONE,
                contact.getContact_telephone());
        contentValues.put(DbHelper.CONTACT_CHUSHI, contact.getContact_chushi());
        contentValues.put(DbHelper.CONTACT_WORKVOICE,
                contact.getContact_workvoice());
        contentValues.put(DbHelper.CONTACT_ORD, contact.getContact_ord());
        contentValues.put(DbHelper.CONTACT_FAX, contact.getContact_fax());
        contentValues.put(DbHelper.CONTACT_MESSAGE,
                contact.getContact_message());
        contentValues.put(DbHelper.CONTACT_DEPARTMENT,
                contact.getContact_department());
        contentValues.put(DbHelper.CONTACT_DEPARTMENT_ID,
                contact.getContact_department_id());
        contentValues.put(DbHelper.CONTACT_WORK_NUMBER,
                contact.getContact_work_number());
        contentValues.put(DbHelper.CONTACT_WORK_ID,
                contact.getContact_work_id());
        contentValues.put(DbHelper.CONTACT_SHORT_PHONE_NUM,
                contact.getContact_short_phone_num());
        contentValues.put(DbHelper.CONTACT_EMAIL, contact.getContact_email());
        contentValues.put(DbHelper.CONTACT_STATE, contact.getContact_state());
        contentValues.put(DbHelper.CONTACT_UPDATE_TIME, contact
                .getContact_update_time().toString());
        contentValues.put(DbHelper.CONTACT_REMARK, contact.getContact_remark());
        return db.insert(DbHelper.TABLE_CONTACT_NAME, null, contentValues);
    }

    private ArrayList<Contact> getListContacts(Cursor cursor) {
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        boolean judgeType=cursor.moveToFirst();
        System.out.println(judgeType);
        if (true) {
            do {
                Contact contact = new Contact();
                contact.setContact_id(cursor.getInt(0));
                contact.setContact_userid(cursor.getString(1));
                contact.setContact_name(cursor.getString(2));
                contact.setContact_siptel1(cursor.getString(3));
                contact.setContact_siptel2(cursor.getString(4));
                contact.setContact_nickname(cursor.getString(5));
                contact.setContact_image_path(cursor.getString(6));
                contact.setContact_province(cursor.getString(7));
                contact.setContact_position(cursor.getString(8));
                contact.setContact_post(cursor.getString(9));
                contact.setContact_telephone(cursor.getLong(10));
                contact.setContact_chushi(cursor.getString(11));
                contact.setContact_workvoice(cursor.getString(12));
                contact.setContact_ord(cursor.getString(13));
                contact.setContact_fax(cursor.getString(14));
                contact.setContact_message(cursor.getString(15));
                contact.setContact_department(cursor.getString(16));
                contact.setContact_department_id(cursor.getString(17));
                contact.setContact_work_number(cursor.getInt(18));
                contact.setContact_work_id(cursor.getInt(19));
                contact.setContact_short_phone_num(cursor.getInt(20));
                contact.setContact_email(cursor.getString(21));
                contact.setContact_state(cursor.getString(22));
                //SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sf=new SimpleDateFormat("EEE MMM dd HH:mm:ss 格林尼治标准时间+0800 yyyy", Locale.ENGLISH);
                Date date = null;
                try {
                    date = sf.parse(cursor.getString(23));
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                contact.setContact_update_time(date);
                contact.setContact_remark(cursor.getString(24));

                //汉字转换成拼音
                characterParser = CharacterParser.getInstance();
                String pinyin = characterParser.getSelling(cursor.getString(2));
                //--将转换成拼音的联系人的首字母转换成大写
                String sortString = pinyin.substring(0, 1).toUpperCase();

                // 正则表达式，判断首字母是否是英文字母
                if(sortString.matches( "[A-Z]")){
                    contact.setSortLetters(sortString.toUpperCase());
                } else{
                    contact.setSortLetters( "#");
                }


                contacts.add(contact);

            } while (cursor.moveToNext());
            System.out.println(contacts+"11111111");
            return contacts;
        }
        System.out.println("22222222222");
        return null;
    }

    // get all data
    public ArrayList<Contact> getAllContacts() {
        Cursor cursor = db.query(DbHelper.TABLE_CONTACT_NAME, new String[] {
                DbHelper.CONTACT_ID, DbHelper.CONTACT_USERID,
                DbHelper.CONTACT_NAME, DbHelper.CONTACT_SIPTEL1,
                DbHelper.CONTACT_SIPTEL2, DbHelper.CONTACT_NICKNAME,
                DbHelper.CONTACT_IMAGE_PATH, DbHelper.CONTACT_PROVINCE,
                DbHelper.CONTACT_POSITION, DbHelper.CONTACT_POST,
                DbHelper.CONTACT_TELEPHONE, DbHelper.CONTACT_CHUSHI,
                DbHelper.CONTACT_WORKVOICE, DbHelper.CONTACT_ORD,
                DbHelper.CONTACT_FAX, DbHelper.CONTACT_MESSAGE,
                DbHelper.CONTACT_DEPARTMENT, DbHelper.CONTACT_DEPARTMENT_ID,
                DbHelper.CONTACT_WORK_NUMBER, DbHelper.CONTACT_WORK_ID,
                DbHelper.CONTACT_SHORT_PHONE_NUM, DbHelper.CONTACT_EMAIL,
                DbHelper.CONTACT_STATE, DbHelper.CONTACT_UPDATE_TIME,
                DbHelper.CONTACT_REMARK }, null, null, null, null, null);
        System.out.println("++++++++++++++");
        return getListContacts(cursor);
    }

    // query by id
    public Contact getContactById(String id) {
        Cursor cursor = db
                .query(true, DbHelper.TABLE_CONTACT_NAME,
                        new String[] { DbHelper.CONTACT_ID,
                                DbHelper.CONTACT_USERID, DbHelper.CONTACT_NAME,
                                DbHelper.CONTACT_SIPTEL1,
                                DbHelper.CONTACT_SIPTEL2,
                                DbHelper.CONTACT_NICKNAME,
                                DbHelper.CONTACT_IMAGE_PATH,
                                DbHelper.CONTACT_PROVINCE,
                                DbHelper.CONTACT_POSITION,
                                DbHelper.CONTACT_POST,
                                DbHelper.CONTACT_TELEPHONE,
                                DbHelper.CONTACT_CHUSHI,
                                DbHelper.CONTACT_WORKVOICE,
                                DbHelper.CONTACT_ORD, DbHelper.CONTACT_FAX,
                                DbHelper.CONTACT_MESSAGE,
                                DbHelper.CONTACT_DEPARTMENT,
                                DbHelper.CONTACT_DEPARTMENT_ID,
                                DbHelper.CONTACT_WORK_NUMBER,
                                DbHelper.CONTACT_WORK_ID,
                                DbHelper.CONTACT_SHORT_PHONE_NUM,
                                DbHelper.CONTACT_EMAIL, DbHelper.CONTACT_STATE,
                                DbHelper.CONTACT_UPDATE_TIME,
                                DbHelper.CONTACT_REMARK }, DbHelper.CONTACT_ID
                                + "=" + id, null, null, null, null, null);
        ArrayList<Contact> contacts = getListContacts(cursor);
        if (contacts != null) {
            return contacts.get(0);
        }
        return null;
    }

    public Contact getContactByUserId(String userId) {
        Cursor cursor = db
                .query(true, DbHelper.TABLE_CONTACT_NAME,
                        new String[] { DbHelper.CONTACT_ID,
                                DbHelper.CONTACT_USERID, DbHelper.CONTACT_NAME,
                                DbHelper.CONTACT_SIPTEL1,
                                DbHelper.CONTACT_SIPTEL2,
                                DbHelper.CONTACT_NICKNAME,
                                DbHelper.CONTACT_IMAGE_PATH,
                                DbHelper.CONTACT_PROVINCE,
                                DbHelper.CONTACT_POSITION,
                                DbHelper.CONTACT_POST,
                                DbHelper.CONTACT_TELEPHONE,
                                DbHelper.CONTACT_CHUSHI,
                                DbHelper.CONTACT_WORKVOICE,
                                DbHelper.CONTACT_ORD, DbHelper.CONTACT_FAX,
                                DbHelper.CONTACT_MESSAGE,
                                DbHelper.CONTACT_DEPARTMENT,
                                DbHelper.CONTACT_DEPARTMENT_ID,
                                DbHelper.CONTACT_WORK_NUMBER,
                                DbHelper.CONTACT_WORK_ID,
                                DbHelper.CONTACT_SHORT_PHONE_NUM,
                                DbHelper.CONTACT_EMAIL, DbHelper.CONTACT_STATE,
                                DbHelper.CONTACT_UPDATE_TIME,
                                DbHelper.CONTACT_REMARK },
                        DbHelper.CONTACT_USERID + "=" + userId, null,
                        null,
                        null, null, null);
        ArrayList<Contact> contacts = getListContacts(cursor);
        if (contacts != null) {
            return contacts.get(0);
        }
        return null;
    }

    public Contact getContactByUserName(String userName) {
        Cursor cursor = db
                .query(true, DbHelper.TABLE_CONTACT_NAME,
                        new String[] { DbHelper.CONTACT_ID,
                                DbHelper.CONTACT_USERID, DbHelper.CONTACT_NAME,
                                DbHelper.CONTACT_SIPTEL1,
                                DbHelper.CONTACT_SIPTEL2,
                                DbHelper.CONTACT_NICKNAME,
                                DbHelper.CONTACT_IMAGE_PATH,
                                DbHelper.CONTACT_PROVINCE,
                                DbHelper.CONTACT_POSITION,
                                DbHelper.CONTACT_POST,
                                DbHelper.CONTACT_TELEPHONE,
                                DbHelper.CONTACT_CHUSHI,
                                DbHelper.CONTACT_WORKVOICE,
                                DbHelper.CONTACT_ORD, DbHelper.CONTACT_FAX,
                                DbHelper.CONTACT_MESSAGE,
                                DbHelper.CONTACT_DEPARTMENT,
                                DbHelper.CONTACT_DEPARTMENT_ID,
                                DbHelper.CONTACT_WORK_NUMBER,
                                DbHelper.CONTACT_WORK_ID,
                                DbHelper.CONTACT_SHORT_PHONE_NUM,
                                DbHelper.CONTACT_EMAIL, DbHelper.CONTACT_STATE,
                                DbHelper.CONTACT_UPDATE_TIME,
                                DbHelper.CONTACT_REMARK },
                        DbHelper.CONTACT_NAME + "=" + userName, null, null,
                        null, null, null);
        ArrayList<Contact> contacts = getListContacts(cursor);
        if (contacts != null) {
            return contacts.get(0);
        }
        return null;
    }

    // query by 部门名
    public ArrayList<Contact> getContactByDepartment(String deptName) {
        Cursor cursor = db
                .query(true, DbHelper.TABLE_CONTACT_NAME,
                        new String[] { DbHelper.CONTACT_ID,
                                DbHelper.CONTACT_USERID, DbHelper.CONTACT_NAME,
                                DbHelper.CONTACT_SIPTEL1,
                                DbHelper.CONTACT_SIPTEL2,
                                DbHelper.CONTACT_NICKNAME,
                                DbHelper.CONTACT_IMAGE_PATH,
                                DbHelper.CONTACT_PROVINCE,
                                DbHelper.CONTACT_POSITION,
                                DbHelper.CONTACT_POST,
                                DbHelper.CONTACT_TELEPHONE,
                                DbHelper.CONTACT_CHUSHI,
                                DbHelper.CONTACT_WORKVOICE,
                                DbHelper.CONTACT_ORD, DbHelper.CONTACT_FAX,
                                DbHelper.CONTACT_MESSAGE,
                                DbHelper.CONTACT_DEPARTMENT,
                                DbHelper.CONTACT_DEPARTMENT_ID,
                                DbHelper.CONTACT_WORK_NUMBER,
                                DbHelper.CONTACT_WORK_ID,
                                DbHelper.CONTACT_SHORT_PHONE_NUM,
                                DbHelper.CONTACT_EMAIL, DbHelper.CONTACT_STATE,
                                DbHelper.CONTACT_UPDATE_TIME,
                                DbHelper.CONTACT_REMARK }, DbHelper.CONTACT_DEPARTMENT
                                + "='" + deptName +"'", null, null, null, null, null);
        ArrayList<Contact> contacts = getListContacts(cursor);
        if (contacts != null) {
            return contacts;
        }
        return null;
    }

    public Contact getContactBySiptel(String siptel2) {
        try {
            Cursor cursor = db.query(true, DbHelper.TABLE_CONTACT_NAME,
                    new String[] { DbHelper.CONTACT_ID,
                            DbHelper.CONTACT_USERID, DbHelper.CONTACT_NAME,
                            DbHelper.CONTACT_SIPTEL1, DbHelper.CONTACT_SIPTEL2,
                            DbHelper.CONTACT_NICKNAME,
                            DbHelper.CONTACT_IMAGE_PATH,
                            DbHelper.CONTACT_PROVINCE,
                            DbHelper.CONTACT_POSITION, DbHelper.CONTACT_POST,
                            DbHelper.CONTACT_TELEPHONE,
                            DbHelper.CONTACT_CHUSHI,
                            DbHelper.CONTACT_WORKVOICE, DbHelper.CONTACT_ORD,
                            DbHelper.CONTACT_FAX, DbHelper.CONTACT_MESSAGE,
                            DbHelper.CONTACT_DEPARTMENT,
                            DbHelper.CONTACT_DEPARTMENT_ID,
                            DbHelper.CONTACT_WORK_NUMBER,
                            DbHelper.CONTACT_WORK_ID,
                            DbHelper.CONTACT_SHORT_PHONE_NUM,
                            DbHelper.CONTACT_EMAIL, DbHelper.CONTACT_STATE,
                            DbHelper.CONTACT_UPDATE_TIME,
                            DbHelper.CONTACT_REMARK }, DbHelper.CONTACT_SIPTEL2
                            + "=" + siptel2, null, null, null, null, null);
            ArrayList<Contact> contacts = getListContacts(cursor);
            if (contacts.size() > 0) {
                return contacts.get(0);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    // update by id
    public boolean updateContact(Contact contact) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.CONTACT_USERID, contact.getContact_userid());
        contentValues.put(DbHelper.CONTACT_NAME, contact.getContact_name());
        contentValues.put(DbHelper.CONTACT_SIPTEL1,
                contact.getContact_siptel1());
        contentValues.put(DbHelper.CONTACT_SIPTEL2,
                contact.getContact_siptel2());
        contentValues.put(DbHelper.CONTACT_NICKNAME,
                contact.getContact_nickname());
        contentValues.put(DbHelper.CONTACT_IMAGE_PATH,
                contact.getContact_image_path());
        contentValues.put(DbHelper.CONTACT_PROVINCE,
                contact.getContact_province());
        contentValues.put(DbHelper.CONTACT_POSITION,
                contact.getContact_position());
        contentValues.put(DbHelper.CONTACT_POST, contact.getContact_post());
        contentValues.put(DbHelper.CONTACT_TELEPHONE,
                contact.getContact_telephone());
        contentValues.put(DbHelper.CONTACT_CHUSHI, contact.getContact_chushi());
        contentValues.put(DbHelper.CONTACT_WORKVOICE,
                contact.getContact_workvoice());
        contentValues.put(DbHelper.CONTACT_ORD, contact.getContact_ord());
        contentValues.put(DbHelper.CONTACT_FAX, contact.getContact_fax());
        contentValues.put(DbHelper.CONTACT_MESSAGE,
                contact.getContact_message());
        contentValues.put(DbHelper.CONTACT_DEPARTMENT,
                contact.getContact_department());
        contentValues.put(DbHelper.CONTACT_DEPARTMENT_ID,
                contact.getContact_department_id());
        contentValues.put(DbHelper.CONTACT_WORK_NUMBER,
                contact.getContact_work_number());
        contentValues.put(DbHelper.CONTACT_WORK_ID,
                contact.getContact_work_id());
        contentValues.put(DbHelper.CONTACT_SHORT_PHONE_NUM,
                contact.getContact_short_phone_num());
        contentValues.put(DbHelper.CONTACT_EMAIL, contact.getContact_email());
        contentValues.put(DbHelper.CONTACT_STATE, contact.getContact_state());
        contentValues.put(DbHelper.CONTACT_UPDATE_TIME, contact
                .getContact_update_time().toString());
        contentValues.put(DbHelper.CONTACT_REMARK, contact.getContact_remark());
        return db.update(DbHelper.TABLE_CONTACT_NAME, contentValues,
                DbHelper.CONTACT_ID + "=" + contact.getContact_id(), null) > 0;
    }
}
