package cn.lzh.base.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.File;

import cn.lzh.base.activity.BaseActivity;
import cn.lzh.base.app.ApplicationContext;

import static cn.lzh.base.app.ApplicationContext.REQUEST_CODE_INSTALL;

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

    public static void installApk(Context mContext, String path) {
        //8.0 安装APK权限申请
        if (!TextUtils.isEmpty(path)) {
            ApplicationContext.upDatePath = path;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean b = mContext.getPackageManager().canRequestPackageInstalls();
            if (b) {
                install(mContext);
            } else {
                //请求安装未知应用来源的权限
                ActivityCompat.requestPermissions((BaseActivity) mContext, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, REQUEST_CODE_INSTALL);
            }
        } else {
            install(mContext);
        }
    }

    private static void install(Context mContext) {
        String path = ApplicationContext.upDatePath;
        ApplicationContext.upDatePath = "";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //判断版本是否在7.0以上
            Uri apkUri = getUriForFile(mContext, new File(path));
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
        }
        mContext.startActivity(intent);
    }

    public static Uri getUriForFile(Context mContext, File toFile) {
        return FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".file_provider", toFile);
    }

}
