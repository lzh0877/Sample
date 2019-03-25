package cn.app.lzh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.app.lzh.R;
import cn.app.lzh.bean.LoginResp;
import cn.app.lzh.rxandriod.CustomDisposable;
import cn.app.lzh.service.UserService;
import cn.lzh.base.util.Md5Util;
import cn.lzh.base.util.SharedPreferencesUtils;
import cn.lzh.base.activity.BaseActivity;
import cn.lzh.base.net.rx.RetrofitManager;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static cn.lzh.base.bean.ReqParams.LOGIN_DATA;
import static cn.lzh.base.bean.ReqParams.TOKEN;

/**
 * Created by LZH On 2019/3/19
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.edit_account)
    EditText editAccount;
    @BindView(R.id.edit_psw)
    EditText editPsw;
    @BindView(R.id.textView)
    TextView textView;

    private UserService userService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login, TYPE.BKF);
        ButterKnife.bind(this);
        disableToolbar();

        userService = new RetrofitManager().getDefaultClient(UserService.class);
    }

    private void login() {
        Map<String, Object> map = new HashMap<>();
        map.put("account", editAccount.getText().toString());
        map.put("password", Md5Util.md5(editPsw.getText().toString()));
        userService.login(map)
                .doOnSubscribe(dialog)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomDisposable<LoginResp>(mContext, dialog) {
                    @Override
                    public void onNext(LoginResp resp) {
                        if (resp != null) {
                            showToast(resp.getMessage());
                            if (resp.getCode() == 1) {
                                SharedPreferencesUtils.putString(LOGIN_DATA, TOKEN, resp.getToken());
                                startActivity(new Intent(mContext, MainActivity.class));
                            }
                        } else {
                            showToast("登录失败");
                        }
                    }
    });
    }

    @OnClick({R.id.textView})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.textView:
//                login();
                throw new NullPointerException();
//                break;
        }
    }
}
