package cn.lzh.base.Util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import cn.lzh.base.app.ApplicationContext;

/**
 * Created by LZH On 2019/3/15
 */
public class SysUtils {

    public static void getScreenWH(int[] wh) {
        if (wh.length < 2) return;
        WindowManager manager = (WindowManager) ApplicationContext.application.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels; //屏幕宽
        int height = dm.heightPixels;
        wh[0] = width;
        wh[1] = height;
    }

    public static int getScreenWidth() {
        WindowManager manager = (WindowManager) ApplicationContext.application.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels; //屏幕宽
    }

    public static int getScreenHeight() {
        WindowManager manager = (WindowManager) ApplicationContext.application.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels; //屏幕宽
    }

    public static int dp2px(float dpValue) {
        final float scale = ApplicationContext.application.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public int px2dp(float pxValue) {
        final float scale = ApplicationContext.application.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
