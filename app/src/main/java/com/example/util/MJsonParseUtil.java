package com.example.util;

/**
 * Created by kathy on 2015/12/8.
 */
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;

import com.example.entity.MMessageFromServer;

/**
 * json解析工具类
 *
 * @author double
 *
 */
public class MJsonParseUtil {

    /**
     * 解析json字符串，获取巡视数据ID
     *
     * @param result
     * @return 成功返回巡视数据ID，失败“”
     */
    public static HashMap<String, String> getMessageResult(String result) {
        HashMap<String, String> messageResult = new HashMap<String, String>();
        // 解析json
        try {
            // 构造json对象
            JSONObject resultJSONObject = new JSONObject(result);
            // 提取服务器的返回信息
            messageResult.put("success", resultJSONObject.getString("success")
                    .trim());
            messageResult.put("messageID",
                    resultJSONObject.getString("messageID").trim());
            return messageResult;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析json字符串中的消息
     *
     * @param result
     * @return 消息的list
     */
    public static ArrayList<MMessageFromServer> getMessageList(String result) {
        ArrayList<MMessageFromServer> messageList;
        // 解析json
        try {
            JSONObject resultJSONObject = new JSONObject(result);
            JSONArray messageArray = resultJSONObject.optJSONArray("messages");

            // 根据返回信息中的消息数构造列表
            int size = messageArray.length();
            messageList = new ArrayList<MMessageFromServer>(size);

            // 解析返回信息中的消息
            String id, senderID, senderName, receiverID, receiverName, messageText, fileData, fileSize, fileDuration, type, time;
            for (int i = 0; i < size; i++) {
                JSONObject messageJSONObject = messageArray.getJSONObject(i);
                id = messageJSONObject.getString("ID");
                senderID = messageJSONObject.getString("senderID");
                senderName = messageJSONObject.getString("senderName");
                receiverID = messageJSONObject.getString("receiverID");
                receiverName = messageJSONObject.getString("receiverName");
                messageText = messageJSONObject.getString("msgContent");
                fileData = messageJSONObject.getString("fileData");
                fileSize = messageJSONObject.getString("fileSize");
                fileDuration = messageJSONObject.getString("fileDuration");
                type = messageJSONObject.getString("type");
                time = messageJSONObject.getString("sendTime");
                messageList.add(new MMessageFromServer(id, senderID,
                        senderName, receiverID, receiverName,
                        messageText, fileData, Long.parseLong(fileSize),
                        Integer.parseInt(fileDuration), Integer.parseInt(type),
                        time));
            }
            return messageList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get "result" form response
     *
     * @param response
     * @return
     */
    public static boolean getResult(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getBoolean("result");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    /**
     * get "msg" from response
     *
     * @param response
     * @return
     */
    public static String getMsg(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getString("msg");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取发送消息的位置json字符串
     *
     * @param location
     * @param userID
     * @param userName
     * @param time
     * @return
     */
    public static String getSendPositionJson(Location location, String userID,
                                             String userName, String time) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID", userID);
            jsonObject.put("userName", userName);
            jsonObject.put("collectionTime", time);
            jsonObject.put("accuracy", location.getAccuracy());
            jsonObject.put("longitude", location.getLongitude());
            jsonObject.put("latitude", location.getLatitude());
            jsonObject.put("height", location.getAltitude());
            jsonObject.put("speed", location.getSpeed());
            jsonObject.put("gpsType", 1);
            jsonObject.put("state", 1);
            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 获取更新在线状态json
     *
     * @param userID
     * @param state
     * @return
     */
    public static String getOnlineStateJson(String userID, int state) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID", userID);
            jsonObject.put("state", state);
            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "";
    }

}
