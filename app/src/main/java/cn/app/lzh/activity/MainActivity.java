package cn.app.lzh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.app.lzh.R;
import cn.app.lzh.rxandriod.CustomDisposable;
import cn.app.lzh.service.UserService;
import cn.lzh.base.activity.BaseActivity;
import cn.lzh.base.net.rx.RetrofitManager;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity {

    private UserService userService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main, TYPE.BKF);
        ButterKnife.bind(this);

        userService = new RetrofitManager().getDefaultClient(UserService.class);
        setToolbar("Hello World", true);
        showToast("Hello World");

    }

    @OnClick({R.id.network, R.id.tv_hello})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.network:
                userService.queryUserInfo()
                        .doOnSubscribe(dialog)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CustomDisposable<Map<String, Object>>(mContext, dialog) {
                            @Override
                            public void onNext(Map<String, Object> map) {
                            }
                        });
                break;
            case R.id.tv_hello:
                startActivity(new Intent(mContext, LoginActivity.class));
                finish();
                break;
        }
    }

}
