package cn.lzh.base.net.interceptor;

import java.io.IOException;
import java.util.Map;

import cn.lzh.base.bean.ReqParams;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator_LZH on 2016/6/23.
 */
public class ParamsInterceptor implements Interceptor {

    private static final ParamsInterceptor ourInstance = new ParamsInterceptor();

    public static ParamsInterceptor getInstance() {
        return ourInstance;
    }

    private ParamsInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        ReqParams reqParams = ReqParams.getInstance();
        Request original = chain.request();
        Request.Builder builder = original.newBuilder();
        for (Map.Entry<String, Object> entry : reqParams.refreshParamsMap().entrySet()) {
            builder.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
        }
        builder.method(original.method(), original.body());
        return chain.proceed(builder.build());
    }

}
