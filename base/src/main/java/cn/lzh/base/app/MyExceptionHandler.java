package cn.lzh.base.app;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by LZH On 2019/3/25
 */
public class MyExceptionHandler implements Thread.UncaughtExceptionHandler {

    private AppCompatActivity activity;

    public MyExceptionHandler(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        activity.finish();
        System.exit(2);
    }
}
