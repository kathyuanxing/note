package com.example.util;

/**
 * Created by kathy on 2015/12/22.
 */

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


public class ToastUtils {

    public static void show(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, String text, int duration) {
        Toast.makeText(context, text, duration).show();
    }

    public static void show(Context context, int resId, int duration) {
        Toast.makeText(context, resId, duration).show();
    }

    public static void show(Context context, String text, int xOffset,
                            int yOffset) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.NO_GRAVITY, xOffset, yOffset);
        toast.show();
    }

    public static void show(Context context, int resId,
                            int xOffset, int yOffset) {
        Toast toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.NO_GRAVITY, xOffset, yOffset);
        toast.show();
    }

    public static void show(Context context, String text, int xOffset,
                            int yOffset, int duration) {
        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.NO_GRAVITY, xOffset, yOffset);
        toast.show();
    }

    public static void show(Context context, int resId, int xOffset,
                            int yOffset, int duration) {
        Toast toast = Toast.makeText(context, resId, duration);
        toast.setGravity(Gravity.NO_GRAVITY, xOffset, yOffset);
        toast.show();
    }

}
