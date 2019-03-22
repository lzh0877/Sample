package cn.lzh.base.net.interceptor;

import java.io.IOException;

import cn.lzh.base.net.utils.CountingRequestBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator_LZH on 2016/8/24.
 */
public class UpLoadInterceptor implements Interceptor {

    private CountingRequestBody.Listener progressListener;

    public UpLoadInterceptor(CountingRequestBody.Listener progressListener) {
        this.progressListener = progressListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        if (originalRequest.body() == null) {
            return chain.proceed(originalRequest);
        }

        Request progressRequest = originalRequest.newBuilder()
                .method(originalRequest.method(), new CountingRequestBody(originalRequest.body(), progressListener))
                .build();

        return chain.proceed(progressRequest);
    }
}
