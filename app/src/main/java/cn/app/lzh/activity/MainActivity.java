package cn.app.lzh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.app.lzh.R;
import cn.app.lzh.databinding.ActivityMainDbingBinding;
import cn.app.lzh.rxandriod.CustomDisposable;
import cn.app.lzh.service.UserService;
import cn.lzh.base.activity.BaseActivity;
import cn.lzh.base.net.rx.RetrofitManager;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_hello)
    TextView tvHello;
    @BindView(R.id.network)
    TextView network;
    private UserService userService;
    private ActivityMainDbingBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tvHello.setText("adsad");

//        setContentView(R.layout.activity_main_dbing, TYPE.DBING);
//        binding = getBinding();
//        binding.tvHello.setText("asdasd");

        //显示空白页
//        showBlankView(id, "提示");
//        setBlankViewVisible(View.VISIBLE);

        // 实例Retrofit
        userService = new RetrofitManager().getDefaultClient(UserService.class);

//        disableToolbar(); // 不显示toolbar
//        setToolbar("Hello World", true);// 设置toolbar
//
//        showToast("Hello World");

        // 设置下拉刷新事件
//        setRefreshListener(() -> {
//            refreshLayout.setEnabled(false);
//        });

        // 设置显示空白页时刷新事件
//        setOnRetryRequestListener(v -> {
//
//        });
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
                startActivity(new Intent(mContext, TestActivity.class));
                break;
        }
    }

}
