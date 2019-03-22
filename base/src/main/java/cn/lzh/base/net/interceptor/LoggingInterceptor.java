package cn.lzh.base.net.interceptor;

import android.util.Log;

import java.io.IOException;

import cn.lzh.base.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by Administrator_LZH on 2016/9/1.
 */
public class LoggingInterceptor implements Interceptor {

    private static final String TAG = "LoggingInterceptor";
    private static final LoggingInterceptor ourInstance = new LoggingInterceptor();

    public static LoggingInterceptor getInstance() {
        return ourInstance;
    }

    private LoggingInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (BuildConfig.DEBUG) {
            Request request = chain.request();
            long t1 = System.nanoTime();
            Log.d(TAG, String.format("Sending %s request %s on %s%n%s", request.method(), request.url(), chain.connection(), request.headers()));
            if (request.method().equals("POST")) {
                Request copy = request.newBuilder().build();
                if (copy.body() != null) {
                    Buffer buffer = new Buffer();
                    copy.body().writeTo(buffer);
                    Log.d(TAG, String.format("%n Request Body%n %s", buffer.readUtf8()));
                }
            }
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            Log.d(TAG, String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        } else {
            return chain.proceed(chain.request());
        }
    }
}
