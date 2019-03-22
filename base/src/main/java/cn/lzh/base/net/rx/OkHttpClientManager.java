package cn.lzh.base.net.rx;

import java.util.concurrent.TimeUnit;

import cn.lzh.base.net.interceptor.LoggingInterceptor;
import cn.lzh.base.net.interceptor.ParamsInterceptor;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator_LZH on 2016/6/23.
 */
public class OkHttpClientManager {
    private static final String TAG = "OkHttpClientManager";
    private OkHttpClient.Builder builder;
    private static OkHttpClientManager instance = null;

    private OkHttpClientManager() {
        builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(ParamsInterceptor.getInstance())
                .addInterceptor(LoggingInterceptor.getInstance());
    }

    public static OkHttpClientManager getInstance() {
        if (instance == null) {
            synchronized (OkHttpClientManager.class) {
                if (instance == null) {
                    instance = new OkHttpClientManager();
                }
            }
        }
        return instance;
    }

    public OkHttpClient.Builder getBuilder() {
        return builder;
    }

}
