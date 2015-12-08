package com.example.dao;

/**
 * Created by kathy on 2015/12/3.
 */

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    //database name and version
    private static final String DB_NAME = "IMS";
    private static final int VERSION = 2;

    //table contact and column name
    public static final String TABLE_CONTACT_NAME = "contact";
    public static final String CONTACT_ID = "contact_id";
    public static final String CONTACT_USERID = "contact_userid";
    public static final String CONTACT_NAME = "contact_name";
    public static final String CONTACT_SIPTEL1 = "contact_siptel1";
    public static final String CONTACT_SIPTEL2 = "contact_siptel2";
    public static final String CONTACT_NICKNAME = "contact_nickname";
    public static final String CONTACT_IMAGE_PATH = "contact_image_path";
    public static final String CONTACT_PROVINCE = "contact_province";
    public static final String CONTACT_POSITION = "contact_position";
    public static final String CONTACT_POST = "contact_post";
    public static final String CONTACT_TELEPHONE = "contact_telephone";
    public static final String CONTACT_CHUSHI= "contact_chushi";
    public static final String CONTACT_WORKVOICE= "contact_workvoice";
    public static final String CONTACT_ORD= "contact_ord";
    public static final String CONTACT_FAX= "contact_fax";
    public static final String CONTACT_MESSAGE= "contact_message";
    public static final String CONTACT_DEPARTMENT = "contact_department";
    public static final String CONTACT_DEPARTMENT_ID = "contact_department_id";

    public static final String CONTACT_WORK_NUMBER = "contact_work_number";
    public static final String CONTACT_WORK_ID = "contact_work_id";
    public static final String CONTACT_SHORT_PHONE_NUM = "contact_short_phone_num";
    public static final String CONTACT_EMAIL = "contact_email";
    public static final String CONTACT_STATE = "contact_state";
    public static final String CONTACT_UPDATE_TIME = "contact_update_time";
    public static final String CONTACT_REMARK = "contact_remark";

    //table work_content and column name
    public static final String TABLE_WORK_CONTENT_NAME = "work_content";
    public static final String WORK_CONTENT_ID = "work_content_id";
    public static final String WORK_CONTENT_CONTACT_ID = "work_content_contact_id";
    public static final String WORK_CONTENT_CONTENT = "work_content_content";
    public static final String WORK_CONTENT_TIME = "work_content_time";
    public static final String WORK_CONTENT_STATE = "work_content_state";
    public static final String WORK_CONTENT_REMARK = "work_content_remark";

    //table meeting_topology and column name
    public static final String TABLE_MEETING_TOPOLOGY_NAME = "meeting_topology";
    public static final String MEETING_ID = "meeting_id";
    public static final String MEETING_CONFID = "meeting_confid";
    public static final String MEETING_NAME = "meeting_name";
    public static final String MEETING_HOST_ID = "meeting_host_id";
    public static final String MEETING_BEGIN_TIME = "meeting_begin_time";
    public static final String MEETING_END_TIME = "meeting_end_time";
    public static final String MEETING_STATE = "meeting_state";
    public static final String MEETING_REMARK = "meeting_remark";

    //table participants and column name
    public static final String TABLE_PARTICIPANTS_NAME = "participants";
    public static final String PARTICIPANTS_ID = "participants_id";
    public static final String PARTICIPANTS_MEETING_CONFID = "participants_meeting_confid";
    public static final String PARTICIPANTS_HOSTER_ID = "participants_hoster_id";
    public static final String PARTICIPANTS_PARTICIPANT_ID = "participants_participant_id";
    public static final String PARTICIPANTS_PARTICIPANT_JOIN_TIME = "participants_participant_join_time";
    public static final String PARTICIPANTS_PARTICIPANT_EXIT_TIME = "participants_participant_exit_time";
    public static final String PARTICIPANTS_STATE = "participants_state";
    public static final String PARTICIPANTS_REMARK = "participants_remark";


    //table system_log and column name
    public static final String TABLE_SYSTEM_LOG_NAME = "system_log";
    public static final String SYSTEM_LOG_id = "log_id";
    public static final String SYSTEM_LOG_CONTENT = "log_content";
    public static final String SYSTEM_LOG_TYPE = "log_type";
    public static final String SYSTEM_LOG_TIME = "log_time";
    public static final String SYSTEM_LOG_REMARK= "log_remark";

    //table message and column name
    public static final String TABLE_MESSAGE_NAME = "message";
    public static final String MESSAGE_ID = "message_id";
    public static final String MESSAGE_TYPE = "message_type";
    public static final String MESSAGE_SENDER_ID = "message_sender_id";
    public static final String MESSAGE_SENDER_NAME = "message_sender_name";
    public static final String MESSAGE_CONTENT = "message_content";
    public static final String MESSAGE_TIME = "message_time";
    public static final String MESSAGE_REMARK = "message_remark";

    private static final String CREAT_TABLE_CONTACT = "create table contact("
            + "contact_id integer primary key, "//AUTOINCREMENT
            + "contact_userid text, "
            + "contact_name text, "
            + "contact_siptel1 text, "
            + "contact_siptel2 text, "
            + "contact_nickname text, "
            + "contact_image_path text, "
            + "contact_province text, "
            + "contact_position text, "
            + "contact_post text, "
            + "contact_telephone integer, "
            + "contact_chushi text, "
            + "contact_workvoice text, "
            + "contact_ord text, "
            + "contact_fax text, "
            + "contact_message text, "
            + "contact_department text, "
            + "contact_department_id text, "
            + "contact_work_number integer, "
            + "contact_work_id integer, "
            + "contact_short_phone_num integer, "
            + "contact_email text, "
            + "contact_state text, "
            + "contact_update_time text, "//date
            + "contact_remark text)";

    private static final String CREAT_TABLE_WORK_CONTENT = "create table work_content("
            + "work_content_id integer not null primary key, "
            + "work_content_contact_id text, "
            + "work_content_content text , "
            + "work_content_time text, "//date
            + "work_content_state text, "
            + "work_content_remark text)";

    private static final String CREAT_TABLE_MEETING_TOPOLOGY = "create table meeting_topology("
            + "meeting_id integer not null primary key, "
            + "meeting_confid text, "
            + "meeting_name text, "
            + "meeting_host_id text, "
            + "meeting_begin_time text  , "//date
            + "meeting_end_time text, "//date
            + "meeting_state text, "
            + "meeting_remark text)";

    private static final String CREAT_TABLE_PARTICIPANTS = "create table participants("
            + "participants_id integer not null primary key, "
            + "participants_meeting_confid text, "
            + "participants_hoster_id text, "
            + "participants_participant_id text, "
            + "participants_participant_join_time text, "//date
            + "participants_participant_exit_time text, "//date
            + "participants_state text, "
            + "participants_remark text)";

    private static final String CREAT_TABLE_SYSTEM_LOG = "create table system_log("
            + "log_id integer not null primary key, "
            + "log_content text, "
            + "log_type text, "
            + "log_time text, " //date
            + "log_remark text)";

    private static final String CREAT_TABLE_MESSAGE = "create table message("
            + "message_id integer not null primary key, "
            + "message_type text, "
            + "message_sender_id text, "
            + "message_sender_name text, "
            + "message_content text, "
            + "message_time text, "//date
            + "message_remark text)";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public DbHelper(Context context, String name, CursorFactory factory,
                    int version) {
        super(context, DB_NAME, null, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREAT_TABLE_CONTACT);
        db.execSQL(CREAT_TABLE_WORK_CONTENT);
        db.execSQL(CREAT_TABLE_MEETING_TOPOLOGY);
        db.execSQL(CREAT_TABLE_PARTICIPANTS);
        db.execSQL(CREAT_TABLE_SYSTEM_LOG);
        db.execSQL(CREAT_TABLE_MESSAGE);
    }

    //--更新数据库版本
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP　TABLE　IF　EXISTS　contact");
        db.execSQL("DROP　TABLE　IF　EXISTS　work_content");
        db.execSQL("DROP　TABLE　IF　EXISTS　meeting_topology");
        db.execSQL("DROP　TABLE　IF　EXISTS　participants");
        db.execSQL("DROP　TABLE　IF　EXISTS　message");
        db.execSQL("DROP　TABLE　IF　EXISTS　system_log");
        onCreate(db);
    }

}
