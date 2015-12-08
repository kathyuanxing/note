package com.example.util;

/**
 * Created by kathy on 2015/12/8.
 */
import com.example.entity.Constants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;



/**
 * 缓存操作工具类，包括缓存的读和写
 */
public class MCacheUtil {
    // 用户名与密码缓存文件
    private static final String LOGIN_CACHE = Constants.APP_NAME + "_login";

    /**
     * 读缓存，读取缓存文件夹根目录
     *
     * @param fileName
     *            缓存文件名
     * @return
     *         缓存文件的内容
     */
    public static String readCache(String fileName) {
        if (fileName == null) {
            return null;
        }

        String result = null;
        File file = new File(MFileUtil.getSDCardDataCacheDir() + "/"
                + encodeString(fileName));
        if (file.exists() && file.isFile()) {
            try {
                result = MFileUtil.readTextFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 根据用户名读缓存，即读取相应的用户名文件夹下的缓存文件
     *
     * @param userName
     *            用户名
     * @param fileName
     *            缓存文件名
     * @return
     *         缓存文件的内容
     */
    public static String readCache(String userName, String fileName) {
        if (fileName == null) {
            return null;
        }

        String result = null;
        File file = new File(
                MFileUtil.getSDCardDataCacheDir(encodeString(userName)) + "/"
                        + encodeString(fileName));
        if (file.exists() && file.isFile()) {
            try {
                result = MFileUtil.readTextFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 写缓存，写到缓存文件夹根目录
     *
     * @param data
     *            写入缓存的数据
     * @param fileName
     *            缓存文件名
     */
    public static void writeCache(String data, String fileName) {
        File file = new File(MFileUtil.getSDCardDataCacheDir() + "/"
                + encodeString(fileName));
        try {
            // 创建缓存数据到磁盘，就是创建文件
            MFileUtil.writeTextFile(file, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据用户名写缓存，即写到相应的用户名文件夹下
     *
     * @param userName
     *            用户名
     * @param data
     *            写入缓存的数据
     * @param fileName
     *            缓存文件名
     */
    public static void writeCache(String userName, String data, String fileName) {
        File file = new File(
                MFileUtil.getSDCardDataCacheDir(encodeString(userName))
                        + File.separator + encodeString(fileName));
        try {
            // 创建缓存数据到磁盘，就是创建文件
            MFileUtil.writeTextFile(file, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清空指定用户缓存文件
     *
     * @param userName
     */
    public static void clearCache(String userName) {
        MFileUtil.delFolder(MFileUtil
                .getSDCardDataCacheDir(encodeString(userName)));
    }

    /**
     * 构造缓存文件名
     *
     * @param url
     *            请求的URL地址
     * @param keys
     *            get请求的键（key）
     * @param values
     *            get请求的值（value）
     * @return 缓存文件名，格式为：URL+get参数+用户名
     */
    public static String getCacheName(String url, ArrayList<String> keys,
                                      ArrayList<String> values) {
        // 缓存文件名中的参数部分
        StringBuilder paramsString = new StringBuilder();
		/* 构造 get请求的参数String */
        for (int i = 0; i < keys.size(); ++i) {
            paramsString.append(keys.get(i));
            paramsString.append(values.get(i));
        }
        // 返回构造的缓存文件名
        return url + paramsString.toString() + Constants.USER_NAME;
    }

    /**
     * 对字符串生成MD5摘要
     *
     * @param s
     * @return
     */
    private static String encodeString(String s) {
        if (s != null)
            return MFileUtil.md5(s);
        return null;
    }
}
