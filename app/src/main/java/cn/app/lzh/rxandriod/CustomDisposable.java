package cn.app.lzh.rxandriod;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import cn.app.lzh.activity.LoginActivity;
import cn.lzh.base.R;
import cn.lzh.base.activity.BaseActivity;
import cn.lzh.base.bean.ResponseMessage;
import cn.lzh.base.widget.ConsumerDialog;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.HttpException;

/**
 * Created by LZH On 2019/3/19
 */
public abstract class CustomDisposable<T> extends DisposableSubscriber<T> {

    private static final String TAG = "Request Error";
    private BaseActivity mContext;
    private Gson mGson;
    private ConsumerDialog dialog;

    public CustomDisposable(BaseActivity mContext) {
        this.mContext = mContext;
        this.mGson = new Gson();
    }

    public CustomDisposable(BaseActivity mContext, ConsumerDialog dialog) {
        if (dialog != null) {
            this.dialog = dialog;
        }
        this.mGson = new Gson();
        this.mContext = mContext;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onComplete() {
        this.dispose();
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError: ", e);
        if (!isNetworkAvailable()) {
            mContext.showToast(R.string.network_exception);
        } else {
            if (e instanceof ConnectException || e instanceof UnknownHostException) {
                mContext.showToast(R.string.network_busyness);
            } else if (e instanceof HttpException) {
                try {
                    String errorText = ((HttpException) e).response().errorBody().string();
                    ResponseMessage msg = mGson.fromJson(errorText, ResponseMessage.class);
                    if (((HttpException) e).response().code() == 401 || !(mContext instanceof LoginActivity)) {// 重新登录
                        mContext.startActivity(new Intent(mContext, LoginActivity.class));
                    }
                    if (msg != null) {
                        mContext.showToast(msg.getMessage());
                    }
                } catch (JsonSyntaxException | IOException e1) {
                    if (e1 instanceof JsonSyntaxException) {
                        mContext.showToast(R.string.network_gson_exception);
                    } else {
                        mContext.showToast(R.string.network_exception);
                    }
                }
            } else if (e instanceof SocketTimeoutException || e instanceof SocketException) {
                mContext.showToast(R.string.network_connect_time_out);
            }
        }
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) return false;
        NetworkInfo.State network = info.getState();
        return (network == NetworkInfo.State.CONNECTED || network == NetworkInfo.State.CONNECTING);
    }

}
