package cn.lzh.base.app;

import android.app.Application;
import android.support.annotation.NonNull;

import cn.lzh.base.bean.ReqParams;
import cn.lzh.base.net.rx.RetrofitBuilder;

/**
 * Created by LZH On 2019/3/18
 */
public abstract class BaseApplication extends Application {

    /**
     * 设置网络请求头相关参数
     * @param baseUrl   设置接口地址
     * @param appKey    设置加密请求的appKey
     * @param appSecret 设置加密请求的appSecret
     */
    public void init(@NonNull String baseUrl, String appKey, String appSecret) {
        RetrofitBuilder.getInstance().setBaseUrl(baseUrl);
        ReqParams params = ReqParams.getInstance();
        params.initReqMap(appKey, appSecret);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationContext.application = this;
    }

}
