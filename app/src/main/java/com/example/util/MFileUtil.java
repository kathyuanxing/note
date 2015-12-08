package com.example.util;

/**
 * Created by kathy on 2015/12/3.
 */

import java.io.File;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.os.Environment;
import android.util.Base64;
import com.example.entity.Constants;

/**
 * 文件操作的工具类，包括文件的读写，复制，删除
 *
 */
public class MFileUtil {
    // 缓冲区大小
    private static final int BUFFER_SIZE = 8192;

    /**
     * 创建缓存文件夹
     *
     * @return
     */
    public static String getSDCardDataCacheDir() {
        String mSDCardDataCacheDir = null;
        if (Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            File file = new File(Environment.getExternalStorageDirectory()
                    .getPath()
                    + File.separator
                    + Constants.APP_NAME
                    + File.separator + "data" + File.separator + "cache");
            if (!file.exists()) {
                if (file.mkdirs()) {
                    mSDCardDataCacheDir = file.getAbsolutePath();
                }
            } else {
                mSDCardDataCacheDir = file.getAbsolutePath();
            }
        }
        return mSDCardDataCacheDir;
    }

    /**
     * 根据用户名创建缓存文件夹
     *
     * @param userName
     * @return
     */
    public static String getSDCardDataCacheDir(String userName) {
        String mSDCardDataCacheDir = null;
        if (Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            File file = new File(Environment.getExternalStorageDirectory()
                    .getPath()
                    + File.separator
                    + Constants.APP_NAME
                    + File.separator
                    + "data"
                    + File.separator
                    + "cache"
                    + File.separator + userName);
            if (!file.exists()) {
                if (file.mkdirs()) {
                    mSDCardDataCacheDir = file.getAbsolutePath();
                }
            } else {
                mSDCardDataCacheDir = file.getAbsolutePath();
            }
        }
        return mSDCardDataCacheDir;
    }

    /**
     * 创建图片缓存文件夹
     *
     * @return
     */
    public static File getSDCardImageCacheDir() {
        File file = null;
        if (Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            file = new File(Environment.getExternalStorageDirectory().getPath()
                    + File.separator + Constants.APP_NAME + File.separator
                    + "data" + File.separator + "cache" + File.separator
                    + "progressImage");
            if (!file.exists()) {
                if (file.mkdirs()) {
                    return file;
                }
            } else {
                return file;
            }
        }
        return file;
    }

    /**
     * 读取文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String readTextFile(File file) throws IOException {
        String text = null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            text = readTextInputStream(is);
            ;
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return text;
    }

    /**
     * 从流中读取文件
     *
     * @param is
     * @return
     * @throws IOException
     */
    private static String readTextInputStream(InputStream is)
            throws IOException {
        StringBuffer strbuffer = new StringBuffer();
        String line;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null) {
                // 回车换行符是否需要？
                // strbuffer.append(line).append("\r\n");
                strbuffer.append(line);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return strbuffer.toString();
    }

    /**
     * 将文本内容写入文件
     *
     * @param file
     * @param str
     * @throws IOException
     */
    public static void writeTextFile(File file, String str) throws IOException {
        DataOutputStream out = null;
        try {
            out = new DataOutputStream(new FileOutputStream(file));
            out.write(str.getBytes());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 复制文件
     *
     * @param sourceFile
     * @param targetFile
     * @throws IOException
     */
    public static void copyFile(File sourceFile, File targetFile)
            throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = inBuff.read(buffer)) != -1) {
                outBuff.write(buffer, 0, length);
            }
            outBuff.flush();
        } finally {
            if (inBuff != null) {
                inBuff.close();
            }
            if (outBuff != null) {
                outBuff.close();
            }
        }
    }

    /**
     * 生成MD5摘要
     *
     * @param string
     * @return
     */
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 删除文件夹
     *
     * @param folderPath
     */
    public static void delFolder(String folderPath) {
        try {
            // 删除完里面所有内容
            delAllFile(folderPath);
            // 删除空文件夹
            new File(folderPath).delete();
        } catch (Exception e) {
            System.out.println("删除文件夹操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 删除文件夹里面的所有文件
     *
     * @param path
     */
    private static void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                // 先删除文件夹里面的文件
                delAllFile(path + "/" + tempList[i]);
                // 再删除空文件夹
                delFolder(path + "/" + tempList[i]);
            }
        }
    }

    /**
     * 删除文件
     *
     * @param path
     */
    public static void deleteFile(String path) {
        try {
            File file = new File(path);
            if (file.exists())
                file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据媒体文件类型生成存储路径
     *
     * @param type
     *            媒体文件类型
     * @return
     */
    public static String getMediaUri(int type) {
        String mediaFolderName = null;
        File file = null;
        // 根据文件类型构建相关媒体文件夹
        if (Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            switch (type) {
                case Constants.ATTACHMENT_TYPE_IMAGE:
                    file = new File(Environment.getExternalStorageDirectory()
                            .getPath()
                            + File.separator
                            + Constants.APP_NAME
                            + File.separator + "data" + File.separator + "image");
                    break;
                case Constants.ATTACHMENT_TYPE_VIDEO:
                    file = new File(Environment.getExternalStorageDirectory()
                            .getPath()
                            + File.separator
                            + Constants.APP_NAME
                            + File.separator + "data" + File.separator + "video");
                    break;
                case Constants.ATTACHMENT_TYPE_AUDIO:
                    file = new File(Environment.getExternalStorageDirectory()
                            .getPath()
                            + File.separator
                            + Constants.APP_NAME
                            + File.separator + "data" + File.separator + "audio");
                    break;
            }
            if (!file.exists()) {
                if (file.mkdirs()) {
                    mediaFolderName = file.getAbsolutePath();
                }
            } else {
                mediaFolderName = file.getAbsolutePath();
            }
        }
        // 生成当前时间作为文件名一部分
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)
                .format(new Date(System.currentTimeMillis()));
        // 生成完整的媒体文件地址
        switch (type) {
            case Constants.ATTACHMENT_TYPE_IMAGE:
                return mediaFolderName + File.separator + timeStamp + ".jpg";
            case Constants.ATTACHMENT_TYPE_VIDEO:
                return mediaFolderName + File.separator + timeStamp + ".mp4";
            case Constants.ATTACHMENT_TYPE_AUDIO:
                return mediaFolderName + File.separator + timeStamp + ".mp3";
            default:
                return "";
        }
    }

    /**
     * 获取文件大小
     *
     * @param path
     * @return
     */
    public static long getFileSize(String path) {
        try {
            File file = new File(path);
            if (file.exists())
                return file.length();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取文件二进制流的Base64编码字符串
     *
     * @param path
     *            文件路径
     * @return 文件的Base64编码
     */
    public static String getBase64String(String path) {
        File file = null;
        FileInputStream in = null;
        try {
            file = new File(path);
            in = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length() + 100];
            int length = in.read(buffer);
            String data = Base64.encodeToString(buffer, 0, length,
                    Base64.DEFAULT);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将Base64编码字符串存储为文件
     *
     * @param base64String
     *            Base64编码字符串
     * @param type
     *            文件类型
     * @return 存储的文件路径
     */
    public static String saveBase64File(String base64String, int type) {
        // 生成文件存储路径
        String path = getMediaUri(type);
        // Base64解码
        File file = null;
        FileOutputStream out = null;
        try {
            file = new File(path);
            out = new FileOutputStream(file);
            byte[] buffer = Base64.decode(base64String, Base64.DEFAULT);
            out.write(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return path;
    }
}
