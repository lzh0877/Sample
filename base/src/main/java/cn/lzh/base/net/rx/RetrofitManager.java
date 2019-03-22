package cn.lzh.base.net.rx;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

import cn.lzh.base.net.interceptor.CacheInterceptor;
import cn.lzh.base.net.interceptor.DownloadInterceptor;
import cn.lzh.base.net.interceptor.UpLoadInterceptor;
import cn.lzh.base.widget.DownNotification;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator_LZH on 2017/5/22.
 */
public class RetrofitManager {
    private Retrofit.Builder retrofitBuilder;
    private OkHttpClient.Builder builder;
    private Gson mGson;

    public RetrofitManager() {
        mGson = new GsonBuilder().serializeNulls().create();
        retrofitBuilder = RetrofitBuilder.getInstance().getBuilder();
        builder = OkHttpClientManager.getInstance().getBuilder();
    }

    public <T> T getDefaultClient(Class<T> serviceClass) {
        OkHttpClient client = builder.build();
        return retrofitBuilder
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build().create(serviceClass);
    }

    public <T> T getNotificationClient(Class<T> serviceClass, DownNotification downNotification, final String completeTitle) {
        OkHttpClient client = builder.addInterceptor(new DownloadInterceptor((bytesRead, contentLength, done) -> {
            if (done) {
                downNotification.getBuilder().setContentTitle(completeTitle);
                downNotification.setProgressComplete("下载完成");
            } else {
                downNotification.setProgress((Long.valueOf(contentLength)).intValue(), (Long.valueOf(bytesRead)).intValue(), false);
            }
        }))
                .build();
        return retrofitBuilder.client(client)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build().create(serviceClass);
    }

    public <T> T getProgressDialogClient(Class<T> serviceClass, final NumberProgressBar dialog) {
        OkHttpClient client = builder.addInterceptor(new UpLoadInterceptor((bytesWritten, contentLength) ->
                Observable.create((ObservableOnSubscribe<Integer>) emitter ->
                        dialog.setProgress((int) ((Long.valueOf(bytesWritten).floatValue() / Long.valueOf(contentLength).floatValue()) * 100)))
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()))
                .build();
        return retrofitBuilder.client(client)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build().create(serviceClass);
    }

    public <T> T getCacheClient(Class<T> serviceClass, File cacheFile) {
        OkHttpClient client = builder
                .cache(new Cache(cacheFile, 10 * 1024 * 1024))
                .addNetworkInterceptor(new CacheInterceptor())
                .build();
        return retrofitBuilder.client(client)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build().create(serviceClass);
    }

}
