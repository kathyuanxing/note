package com.example.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;

import java.io.File;
import java.io.FileNotFoundException;
import android.graphics.Bitmap.Config;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kathy on 2015/12/17.
 */
public class MImageUtil {
    /**
     * 生成缩略图
     *
     * @param originUri
     *            原图路径
     * @param thumbnailUri
     *            缩略图路径
     */
    public static void getThumbnail(String originUri, String thumbnailUri) {
        if (originUri == null || originUri.equals(""))
            return;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            // 不返回实际的bitmap不给其分配内存空间而只包括一些解码边界信息即图片大小信息
            options.inJustDecodeBounds = true;
            // 获取这个图片的宽和高，注意，此时返回bitmap为空
            Bitmap bitmap = BitmapFactory.decodeFile(originUri, options);
            // 重新设置为false，下一次返回实际的bitmap
            options.inJustDecodeBounds = false;

            // 计算缩放比
            int widthRatio = (int) Math.ceil(options.outWidth / (float) 1200);
            int heightRatio = (int) Math.ceil(options.outHeight / (float) 1600);
            if (heightRatio > 1 || widthRatio > 1) {
                if (heightRatio > widthRatio) {
                    options.inSampleSize = heightRatio;
                } else {
                    options.inSampleSize = widthRatio;
                }
            } else {
                options.inSampleSize = 1;
            }

            // 重新读入图片
            bitmap = BitmapFactory.decodeFile(originUri, options);
            // 保存缩略图
            saveBitmap(bitmap, thumbnailUri);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存bitmap
     *
     * @param bitmap
     * @param uri
     */
    private static void saveBitmap(Bitmap bitmap, String uri) {
        File outFile = new File(uri);
        try {
            outFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream mFileOutputStream = null;
        try {
            mFileOutputStream = new FileOutputStream(outFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, mFileOutputStream);
        try {
            mFileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mFileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据图片路径获取缩略图
     *
     * @param path
     *            图片路径
     * @param context
     *            应用上下文
     * @return
     */
    public static Bitmap getThumbnailFromPath(String path, Context context) {
        // 获取图片Uri
        Uri uri = Uri.fromFile(new File(path));

        ContentResolver cr = context.getContentResolver();
        // 图片输入流
        InputStream input = null;
        try {
            input = cr.openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 图片缩略选项
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 4;
        // 获取图片缩略图并返回
        return BitmapFactory.decodeStream(input, null, opts);
    }

    /**
     * 根据图片路径获取指定长宽的缩略图
     *
     * @param path
     *            图片路径
     * @param width
     *            指定的图片长度
     * @param height
     *            指定的图片高度
     * @param context
     *            应用上下文
     * @return
     */
    public static Bitmap getThumbnail(String path, int width, int height,
                                      Context context) {
        if (path == null || path.equals(""))
            return null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            // 不返回实际的bitmap不给其分配内存空间而只包括一些解码边界信息即图片大小信息
            options.inJustDecodeBounds = true;
            // 获取这个图片的宽和高，注意，此时返回bitmap为空
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            // 重新设置为false，下一次返回实际的bitmap
            options.inJustDecodeBounds = false;

            // 计算缩放比
            int widthRatio = (int) Math.ceil(options.outWidth / (float) width);
            int heightRatio = (int) Math.ceil(options.outHeight
                    / (float) height);
            if (heightRatio > 1 || widthRatio > 1) {
                if (heightRatio > widthRatio) {
                    options.inSampleSize = heightRatio;
                } else {
                    options.inSampleSize = widthRatio;
                }
            } else {
                options.inSampleSize = 1;
            }

            // 重新读入图片
            bitmap = BitmapFactory.decodeFile(path, options);

            return bitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 为图片添加水印
     *
     * @param origin
     *            原图
     * @param watermark
     *            水印
     * @return
     *         添加了水印后的图片
     */
    public static Bitmap drawWatermark(Bitmap origin, Bitmap watermark) {
        int originWidth = origin.getWidth();
        int originHeight = origin.getHeight();
        // 新建一张和原图同样大小的图片
        Bitmap bitmapWithWatermark = Bitmap.createBitmap(originWidth,
                originHeight, Config.ARGB_8888);
        // 进行绘图工作
        Canvas canvas = new Canvas(bitmapWithWatermark);
        // 在 0，0坐标开始画入原图
        canvas.drawBitmap(origin, 0, 0, null);
        // 将水印填入原图中间
        canvas.drawBitmap(watermark, (originWidth - watermark.getWidth()) / 2,
                (originHeight - watermark.getHeight()) / 2, null);
        // 保存
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        // 销毁原图
        origin.recycle();
        watermark.recycle();

        return bitmapWithWatermark;
    }

    /**
     * 为图片添加水印
     *
     * @param origin
     *            原图
     * @param watermarkId
     *            水印在项目中的ID
     * @param context
     *            上下文
     * @return
     *         添加了水印后的图片
     */
    public static Bitmap drawWatermark(Bitmap origin, int watermarkId,
                                       Context context) {
        // 读取水印图
        Resources mResources = context.getResources();
        InputStream mInputStream = mResources.openRawResource(watermarkId);
        BitmapDrawable mBitmapDrawable = new BitmapDrawable(mInputStream);
        Bitmap watermark = mBitmapDrawable.getBitmap();
        // 添加水印
        return drawWatermark(origin, watermark);
    }
}
