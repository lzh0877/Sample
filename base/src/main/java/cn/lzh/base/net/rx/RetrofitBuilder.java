package cn.lzh.base.net.rx;

import retrofit2.Retrofit;

/**
 * Created by LZH On 2019/3/18
 */
public class RetrofitBuilder {
    private static final RetrofitBuilder ourInstance = new RetrofitBuilder();
    private static Retrofit.Builder builder;

    public static RetrofitBuilder getInstance() {
        return ourInstance;
    }

    private RetrofitBuilder() {
        builder = new Retrofit.Builder();
    }

    Retrofit.Builder getBuilder() {
        return builder;
    }

    public void setBaseUrl(String baseUrl) {
        builder.baseUrl(baseUrl);
    }
}
