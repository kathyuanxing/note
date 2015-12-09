package com.example.util;

/**
 * Created by kathy on 2015/12/8.
 */
import java.util.ArrayList;

import android.content.Context;

import com.example.entity.Constants;
import com.example.entity.MMessage;
import com.example.handler.MessageHandlerManager;
import com.example.thread.ThreadManager;

/**
 * 对服务器传来的数据进行本地存储，获取, 先采用缓存的方式，预留数据库的接口
 */
public class MDataOperation {
    /**
     * 存储用户名，密码列表
     *
     * @param list
     * @param dataStore
     *            数据存储方式
     */
    // public static void writeUserPasswordList(List<UserPassword> list,
    // int dataStore) {
    // if (dataStore == MConstants.DATA_STORE_CACHE)
    // MCacheUtil.writeUserPasswordList(list);
    // else if (dataStore == MConstants.DATA_STORE_DATABASE)
    // ;
    // }

    /**
     * 读取用户名、密码列表
     *
     * @param dataStore
     *            数据存储方式
     * @return
     */
    // public static List<UserPassword> readUserPasswordList(int dataStore) {
    // if (dataStore == MConstants.DATA_STORE_CACHE)
    // return MCacheUtil.readUserPasswordList();
    // else if (dataStore == MConstants.DATA_STORE_DATABASE)
    // return null;
    // return null;
    // }

    /**
     * 根据用户请求从本地获取数据
     *
     * @param url
     *            请求的URL地址
     * @param keys
     *            get请求时键（key）
     * @param values
     *            get请求时值（value）
     * @param dataStore
     *            数据存储方式
     * @return
     */
    public static String readDataByRequest(String url, ArrayList<String> keys,
                                           ArrayList<String> values, int dataStore) {
        if (dataStore == Constants.DATA_STORE_CACHE) {
            // 获取缓存文件名
            String cacheFileName = MCacheUtil.getCacheName(url, keys, values);
            // 读取缓存
            return MCacheUtil.readCache(Constants.USER_NAME, cacheFileName);
        } else if (dataStore == Constants.DATA_STORE_DATABASE)
            return null;
        return null;
    }

    /**
     * 根据用户请求将获取到的数据存入本地
     *
     * @param data
     *            存入本地的数据
     * @param url
     *            请求的URL地址
     * @param keys
     *            get请求时键（key）
     * @param values
     *            get请求时值（value）
     * @param dataStore
     *            数据存储方式
     * @return
     */
    public static void writeDataByRequest(String data, String url,
                                          ArrayList<String> keys, ArrayList<String> values, int dataStore) {
        if (dataStore == Constants.DATA_STORE_CACHE) {
            // 获取缓存文件名
            String cacheFileName = MCacheUtil.getCacheName(url, keys, values);
            // 写缓存
            MCacheUtil.writeCache(Constants.USER_NAME, data, cacheFileName);
        } else if (dataStore == Constants.DATA_STORE_DATABASE)
            ;
    }

    /**
     * 从指定条目数起读取本地警报
     *
     * @param start
     *            读数据起始位置
     * @param dataStore
     *            数据存储方式
     * @param userName
     *            当前用户名
     * @param context
     *            上下文
     * @return
     */
    // public static ArrayList<AlarmBasic> readAlarms(int start, int dataStore,
    // String userName, Context context) {
    // ArrayList<AlarmBasic> alarmBasics = null;
    // if (dataStore == MConstants.DATA_STORE_CACHE) {
    //
    // } else if (dataStore == MConstants.DATA_STORE_DATABASE) {
    // alarmBasics = AlarmBasic.getAlarmBasics(start, userName, context);
    // }
    // return alarmBasics;
    // }

    /**
     * 将服务器返回的警报列表存入本地
     *
     * @param alarmBasicList
     *            服务器返回的警报列表
     * @param dataStore
     *            数据存储方式
     * @param context
     *            上下文
     */
    // public static void writeAlarms(ArrayList<AlarmBasic> alarmBasicList,
    // int dataStore, Context context) {
    // if (dataStore == MConstants.DATA_STORE_CACHE) {
    //
    // } else if (dataStore == MConstants.DATA_STORE_DATABASE) {
    // if (alarmBasicList != null && alarmBasicList.size() != 0) {
    // // 开启将警报写入数据库的线程
    // ThreadManager.getInstance().startWriteAlarmThread(
    // alarmBasicList, context);
    // } else {
    // // 通知UI线程刷新
    // MessageHandlerManager.getInstance().sendMessage(
    // MConstants.WRITE_ALARM, "AlarmFragment");
    // }
    // }
    // }

    /**
     * 从指定条目数起读取本地消息
     *
     * @param start
     *            读数据起始位置
     * @param dataStore
     *            数据存储方式
     * @param userID
     *            当前用户ID
     * @param talkerID
     *            对话者ID
     * @param context
     *            上下文
     * @return
     */
    public static ArrayList<MMessage> readMessages(int start, int dataStore,
                                                   String userID, String talkerID, Context context) {
        ArrayList<MMessage> messages = null;
        if (dataStore == Constants.DATA_STORE_CACHE) {

        } else if (dataStore == Constants.DATA_STORE_DATABASE) {
            messages = MMessage.getMessages(start, userID, talkerID, context);
        }
        return messages;
    }

    /**
     * 将消息列表存入本地
     *
     * @param messageList
     *            消息
     * @param dataStore
     *            数据存储方式
     * @param isSend
     *            是否本机发送
     * @param context
     *            上下文
     */
    public static void writeMessages(ArrayList<MMessage> messageList,
                                     int dataStore, boolean isSend, Context context) {
        if (dataStore == Constants.DATA_STORE_CACHE) {

        } else if (dataStore == Constants.DATA_STORE_DATABASE) {
            if (messageList != null && messageList.size() != 0) {
                // 开启将消息写入数据库的线程
                ThreadManager.getInstance().startWriteMessageThread(
                        messageList, isSend, context);
            } else {
                // 通知UI线程刷新
                MessageHandlerManager.getInstance().sendMessage(
                        Constants.WRITE_MESSAGE, "RegisterSuccessActivity");
            }
        }
    }
}