package cn.app.lzh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.app.lzh.R;
import cn.lzh.base.activity.BaseActivity;

/**
 * Created by LZH On 2019/3/25
 */
public class TestActivity extends BaseActivity {

    @BindView(R.id.tv_hello)
    TextView tvHello;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test, TYPE.BKF);

    }

    @OnClick(R.id.tv_hello)
    public void onViewClicked() {
        startActivity(new Intent(mContext, LoginActivity.class));
    }
}
