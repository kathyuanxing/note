package com.example.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.example.handler.MessageHandlerManager;
import org.apache.http.entity.InputStreamEntity;

import android.content.Context;

import com.example.entity.Constants;
import com.example.handler.MessageHandlerManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 异步Http，基于http://loopj.com/android-async-http/
 *
 * @author liuh
 *
 */
public class AsyncHttp {

    private static AsyncHttpClient client = new AsyncHttpClient();

    /**
     * 异步的get请求
     *
     * @param url
     *            请求的URL地址
     * @param keys
     *            get请求时键（key）
     * @param values
     *            get请求时值（value）
     * @param activityName
     *            消息发往的activity
     * @param context
     *            应用上下文
     */
    public static void get(final String url, final List<String> keys,
                           final List<String> values, final String activityName,
                           Context context) {
        // 添加get请求参数
        RequestParams params = new RequestParams();
        for (int i = 0; i < keys.size(); ++i) {
            params.put(keys.get(i), values.get(i));
        }
        // get请求
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                // 发送内容
                MessageHandlerManager.getInstance().sendMessage(
                        Constants.REQUEST_SUCCESS, response, activityName);
            }

            @Override
            public void onFailure(Throwable error, String content) {
                // 发送失败消息
                MessageHandlerManager.getInstance().sendMessage(
                        Constants.REQUEST_FAIL, activityName);
            }
        });
    }

    /**
     * 异步的get请求
     *
     * @param url
     * @param paramMap
     * @param activityName
     * @param context
     */
    public static void get(final String url,
                           final Map<String, String> paramMap, final String activityName,
                           Context context) {
        // 添加get请求参数
        RequestParams params = new RequestParams(paramMap);
        // get请求
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                // 发送内容
                MessageHandlerManager.getInstance().sendMessage(
                        Constants.REQUEST_SUCCESS, response, activityName);
            }

            @Override
            public void onFailure(Throwable error, String content) {
                // 发送失败消息
                MessageHandlerManager.getInstance().sendMessage(
                        Constants.REQUEST_FAIL, activityName);
            }
        });
    }

    /**
     * 异步的post请求
     *
     * @param url
     *            请求的URL地址
     * @param keys
     *            get请求时键（key）
     * @param values
     *            get请求时值（value）
     * @param activityName
     *            消息发往的activity
     */
    public static void post(String url, List<String> keys, List<String> values,
                            final String activityName) {
        //System.out.println(url);
        //if(url.equals("http://172.16.134.2:8081/HttpService/NewMessage"))
        //{
        //	System.out.println("MessageType:   NewMessage");
        //}
        //else if(url.equals("http://172.16.134.2:8081/HttpService/GetMessage"))
        //{
        //	System.out.println("MessageType:   GetMessage");
        //}
        // 添加post请求参数
        RequestParams params = new RequestParams();
        for (int i = 0; i < keys.size(); ++i) {
            params.put(keys.get(i), values.get(i));
            //System.out.println(i+"    keys:  "+keys.get(i).toString()+"   value: "+values.get(i).toString());
        }
        //System.out.println("参数:   "+params.toString());
        // post请求
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                // 发送内容
                MessageHandlerManager.getInstance().sendMessage(
                        Constants.REQUEST_SUCCESS, response, activityName);
            }

            @Override
            public void onFailure(Throwable error, String content) {
                // 发送失败消息
                MessageHandlerManager.getInstance().sendMessage(
                        Constants.REQUEST_FAIL, activityName);
            }
        });
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param paramMap
     * @param activityName
     */
    public static void post(String url, Map<String, String> paramMap,
                            final String activityName) {
        // 添加post请求参数
        RequestParams params = new RequestParams(paramMap);
        //System.out.println(url+"00000000000000000");
        //System.out.println("**************upPosition     "+params.toString());
        // post请求
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                // 发送内容
                MessageHandlerManager.getInstance().sendMessage(
                        Constants.REQUEST_SUCCESS, response, activityName);
            }

            @Override
            public void onFailure(Throwable error, String content) {
                // 发送失败消息
                MessageHandlerManager.getInstance().sendMessage(
                        Constants.REQUEST_FAIL, activityName);
            }
        });
    }


    /**
     * 异步的post请求
     *
     * @param url
     *            请求的URL地址
     * @param keys
     *            get请求时键（key）
     * @param values
     *            get请求时值（value）
     * @param obj
     *            需要传递的消息内容
     * @param activityName
     *            消息发往的activity
     */
    public static void post(String url, List<String> keys, List<String> values,
                            final Object obj, final String activityName) {
        // 添加post请求参数
        System.out.println("url是:   "+url);
        RequestParams params = new RequestParams();
        for (int i = 0; i < keys.size(); ++i) {
            params.put(keys.get(i), values.get(i));
        }
        System.out.println("参数值是:  "+params.toString());
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                System.out.println("response的值为: "+response);
                // 发送内容
                MessageHandlerManager.getInstance().sendMessage(
                        Constants.REQUEST_SUCCESS, response, activityName);
            }


            @Override
            public void onFailure(Throwable error, String content) {
                // 发送失败消息
                MessageHandlerManager.getInstance().sendMessage(
                        Constants.REQUEST_FAIL, obj, activityName);
            }
        });
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param paramMap
     * @param obj
     * @param activityName
     */
    public static void post(String url, Map<String, String> paramMap,
                            final Object obj, final String activityName) {
        // 添加post请求参数
        RequestParams params = new RequestParams(paramMap);
        // post请求
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                // 发送内容
                MessageHandlerManager.getInstance().sendMessage(
                        Constants.REQUEST_SUCCESS, response, activityName);
            }

            @Override
            public void onFailure(Throwable error, String content) {
                // 发送失败消息
                MessageHandlerManager.getInstance().sendMessage(
                        Constants.REQUEST_FAIL, obj, activityName);
            }
        });
    }

    /**
     * 通过异步post请求传输文件
     *
     * @param url
     *            请求的URL地址
     * @param filePath
     *            文件路径
     * @param contentType
     *            文件传输类型
     * @param activityName
     *            消息发往的activity
     * @param context
     *            应用上下文
     */
    public static void postFile(String url, String filePath,
                                String contentType, final String activityName, Context context) {
        // 添加post请求参数
        File mFile;
        InputStream mInputStream;
        InputStreamEntity mInputStreamEntity = null;
        try {
            mFile = new File(filePath);
            mInputStream = new FileInputStream(mFile);
            mInputStreamEntity = new InputStreamEntity(mInputStream,
                    mFile.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // post请求
        client.post(context, url, mInputStreamEntity, contentType,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        // 发送内容
                        MessageHandlerManager.getInstance().sendMessage(
                                Constants.REQUEST_SUCCESS, response,
                                activityName);
                    }

                    @Override
                    public void onFailure(Throwable error, String content) {
                        // 发送失败消息
                        MessageHandlerManager.getInstance().sendMessage(
                                Constants.REQUEST_FAIL, activityName);
                    }
                });
    }

}
