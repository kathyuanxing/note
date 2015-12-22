package com.example.util;

import com.amap.api.maps.model.LatLng;

/**
 * Created by kathy on 2015/12/22.
 */
public class Constants
{
    // Ӧ�õ�����
    public static final String APP_NAME = "IMSDroid";
    // �û���
    public static String USER_NAME;
    // ���������������URl��ַ
    public  String SERVER_BASE_URL ="http://172.16.134.5:8080/";
    //public static String SERVER_BASE_URL = "http://techserver.oicp.net:9988/";
    // public static String SERVER_BASE_URL = "http://192.168.1.100:8020/";
    public  String CONTACT_URL ="http://172.16.134.5:8080"+"/department.xml";
    //"http://119.97.246.78:9090/plugins/unifiedcom/department";
    //public static final String CONTACT_URL ="http://192.168.1.119:9090/plugins/unifiedcom/department";
    // public static final String CONTACT_URL =
    // "http://192.168.4.204:9090/plugins/unifiedcom/department";
    // ����ɹ�
    public static final int REQUEST_SUCCESS = 0;
    // ����ʧ��
    public static final int REQUEST_FAIL  = 1;
    // ������д�����ݿ����
    public static final int WRITE_ALARM   = 2;
    // ����Ϣд�����ݿ����
    public static final int WRITE_MESSAGE = 3;

    public static final String FREE_CONFERENCE_NUM = "freeConferenceNum";

    /*�Է�����������������ķ���*/
    public static final String GET_FREE_CONFERENCE = "conference.do";
    public static final String MAKE_CALL = "call.do";
    public static final String NEW_MESSAGE = "HttpService/NewMessage";
    public static final String GET_MESSAGE = "HttpService/GetMessage";
    public static final String MESSAGE_RESPONSE = "HttpService/MessageResponse";
    public static final String UPLOAD_POSITION = "HttpService/UploadPosition";
    public static final String UPDATE_ONLINE_STATE = "HttpService/UpdateOnlineState";

    /*���ݴ洢��ʽ*/
    public static final int DATA_STORE_CACHE = 1;// ����
    public static final int DATA_STORE_DATABASE = 2;// ���ݿ�

    /*HTTP��������*/
    public static final String CONTENT_TYPE_JPEG = "image/jpeg";
    public static final String CONTENT_TYPE_MP4 = "video/mp4";
    public static final String CONTENT_TYPE_MP3 = "audio/mpeg";

    /*��������*/
    public static final int ATTACHMENT_TYPE_AUDIO = 2;
    public static final int ATTACHMENT_TYPE_IMAGE = 3;
    public static final int ATTACHMENT_TYPE_VIDEO = 4;

    // �����˳���x����С���ؼ��
    public static final int MIN_GAP_X = 150;
    // �����˳���y����С���ؼ��
    public static final int MAX_GAP_Y = 100;

    // ����������󾯱���Ϣ�ļ�������룩
    public static int GET_ALARM_GAP;
    public static final int GET_ALARM_GAP_5_MIN = 300000;
    public static final int GET_ALARM_GAP_15_MIN = 900000;
    public static final int GET_ALARM_GAP_30_MIN = 1800000;
    // ��ͼ��ʾ����λ��
    public static final LatLng WUHAN = new LatLng(30.51667, 114.31667);// �人�о�γ��

}
