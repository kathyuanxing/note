package com.example.entity;

/**
 * Created by kathy on 2015/12/3.
 */

public class Constants
{
//    ScreenNetwork network = new ScreenNetwork();
    // 应用的名称
    public static final String APP_NAME = "IMSDroid";
    // 用户名
    public static String USER_NAME;
    // 服务器数据请求的URl基址
    public  String SERVER_BASE_URL ="http://172.16.134.14:8080/";
    //public static String SERVER_BASE_URL = "http://techserver.oicp.net:9988/";
    // public static String SERVER_BASE_URL = "http://192.168.1.100:8020/";
    public  String CONTACT_URL ="http://172.16.134.5:8080"+"/department.xml";
    //"http://119.97.246.78:9090/plugins/unifiedcom/department";
    //public static final String CONTACT_URL ="http://192.168.1.119:9090/plugins/unifiedcom/department";
    // public static final String CONTACT_URL =
    // "http://192.168.4.204:9090/plugins/unifiedcom/department";
    // 请求成功
    public static final int REQUEST_SUCCESS = 0;
    // 请求失败
    public static final int REQUEST_FAIL  = 1;
    // 将警报写入数据库完成
    public static final int WRITE_ALARM   = 2;
    // 将消息写入数据库完成
    public static final int WRITE_MESSAGE = 3;

    public static final String FREE_CONFERENCE_NUM = "freeConferenceNum";

    /*对服务器进行数据请求的方法*/
    public static final String GET_FREE_CONFERENCE = "conference.do";
    public static final String MAKE_CALL = "call.do";
    public static final String NEW_MESSAGE = "HttpService/NewMessage";
    public static final String GET_MESSAGE = "HttpService/GetMessage";
    public static final String MESSAGE_RESPONSE = "HttpService/MessageResponse";
    public static final String UPLOAD_POSITION = "HttpService/UploadPosition";
    public static final String UPDATE_ONLINE_STATE = "HttpService/UpdateOnlineState";

    /*数据存储方式*/
    public static final int DATA_STORE_CACHE = 1;// 缓存
    public static final int DATA_STORE_DATABASE = 2;// 数据库

    /*HTTP传输类型*/
    public static final String CONTENT_TYPE_JPEG = "image/jpeg";
    public static final String CONTENT_TYPE_MP4 = "video/mp4";
    public static final String CONTENT_TYPE_MP3 = "audio/mpeg";

    /*附件类型*/
    public static final int ATTACHMENT_TYPE_AUDIO = 2;
    public static final int ATTACHMENT_TYPE_IMAGE = 3;
    public static final int ATTACHMENT_TYPE_VIDEO = 4;

    // 滑动退出的x轴最小像素间距
    public static final int MIN_GAP_X = 150;
    // 滑动退出的y轴最小像素间距
    public static final int MAX_GAP_Y = 100;

    // 向服务器请求警报信息的间隔（毫秒）
    public static int GET_ALARM_GAP;
    public static final int GET_ALARM_GAP_5_MIN = 300000;
    public static final int GET_ALARM_GAP_15_MIN = 900000;
    public static final int GET_ALARM_GAP_30_MIN = 1800000;
    // 地图显示中心位置
//    public static final LatLng WUHAN = new LatLng(30.51667, 114.31667);// 武汉市经纬度

}
