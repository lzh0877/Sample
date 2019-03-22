package cn.app.lzh.sys;

import com.squareup.leakcanary.LeakCanary;

import cn.lzh.base.Util.SharedPreferencesUtils;
import cn.lzh.base.app.BaseApplication;

/**
 * Created by LZH On 2019/3/14
 */
public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        init(Constant.URL.BASE, Constant.APP_SECRET, Constant.APP_KEY);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
