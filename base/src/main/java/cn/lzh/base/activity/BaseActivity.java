package cn.lzh.base.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import cn.lzh.base.R;
import cn.lzh.base.app.MyExceptionHandler;
import cn.lzh.base.listener.OnRetryRequestListener;
import cn.lzh.base.util.SysUtils;
import cn.lzh.base.widget.ConsumerDialog;

import static cn.lzh.base.app.ApplicationContext.REQUEST_CODE_INSTALL;
import static cn.lzh.base.util.SysUtils.dp2px;

/**
 * Created by LZH On 2019/3/14
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final String TAG = BaseActivity.class.getName();
    private Toast mToast;
    private TextView toolbarTitle;
    private OnRetryRequestListener onRetryRequestListener;

    protected BaseActivity mContext;
    protected SwipeRefreshLayout refreshLayout = null;
    protected FrameLayout mainLayout = null;
    protected Toolbar toolbar = null;
    protected View contentView = null;
    protected View blankView = null;
    protected ConsumerDialog dialog;
    private ViewDataBinding binding;
    private TYPE type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        dialog = new ConsumerDialog(mContext);
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(false);
            refreshLayout.setEnabled(false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_INSTALL) {// 适配获取8.0安装权限
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SysUtils.installApk(this, "");
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                startActivityForResult(intent, REQUEST_CODE_INSTALL);
            }
        }
    }

    /**
     * @param layoutResID activity布局文件
     * @param type        加载方式{@link TYPE.BKF} 使用ButterKnife加载View
     *                    {@link TYPE.DBING} 使用DataBinding加载View
     *                    {@link TYPE.DEFAULT} 默认加载View。
     *                    除默认加载View外，均设置Toolbar、SwipeRefreshLayout、空白页{@link BaseActivity.blankView}
     */
    protected void setContentView(int layoutResID, TYPE type) {
        this.type = type;
        switch (type) {
            case BKF:
                setContentView(R.layout.activity_base);
                mainLayout = findViewById(R.id.fl_main);
                contentView = LayoutInflater.from(this).inflate(layoutResID, mainLayout, true);
                ButterKnife.bind(this);
                break;
            case DBING:
                setContentView(R.layout.activity_base);
                mainLayout = findViewById(R.id.fl_main);
                binding = DataBindingUtil.inflate(LayoutInflater.from(this), layoutResID, mainLayout, true);
                break;
            case DEFAULT:
            default:
                setContentView(layoutResID);
                break;
        }
        initView();
    }

    private void initView() {
        if (type == TYPE.BKF || type == TYPE.DBING) {
            refreshLayout = findViewById(R.id.srl_main);
            toolbar = findViewById(R.id.toolbar);
            toolbarTitle = findViewById(R.id.tv_toolbar_title);
            refreshLayout.setEnabled(false);
            toolbar.setNavigationOnClickListener(v -> finish());
        }
    }

    protected void showBlankView(int resource, String tip) {
        if (mainLayout == null) return;
        if (blankView == null) {
            blankView = LayoutInflater.from(this).inflate(R.layout.layout_blank, mainLayout, false);
        }
        blankView.setVisibility(View.VISIBLE);
        mainLayout.addView(blankView);
        blankView.<TextView>findViewById(R.id.tv_tip).setText(tip);
        blankView.<ImageView>findViewById(R.id.iv_bad_network).setImageDrawable(ContextCompat.getDrawable(mContext, resource));
        blankView.setOnClickListener(v -> {
            if (onRetryRequestListener != null) {
                onRetryRequestListener.onRetryRequest(v);
            }
        });
    }

    protected void setBlankViewVisible(int visible) {
        if (blankView != null) {
            blankView.setVisibility(visible);
        }
    }

    protected void setRefreshLayout(boolean enable) {
        if (refreshLayout != null) {
            refreshLayout.setEnabled(enable);
        }
    }

    public <T extends ViewDataBinding> T getBinding() {
        if (binding == null) {
            return null;
        } else {
            return (T) binding;
        }
    }

    public void disableToolbar() {
        if (toolbar == null) return;
        toolbar.setVisibility(View.GONE);
    }

    public void setToolbar(String title, boolean showBackIcon) {
        if (toolbar == null) return;
        toolbarTitle.setText(title);
        if (!showBackIcon) {
            toolbar.setNavigationIcon(null);
        } else {
            toolbar.setNavigationIcon(R.drawable.ic_back);
        }
    }

    public void setNavigationOnClickListener(View.OnClickListener listener) {
        toolbar.setNavigationOnClickListener(listener);
    }

    public View setToolbarRight(View v) {
        if (toolbar == null) return v;
        Toolbar.LayoutParams lp = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
        lp.setMargins(0, 0, dp2px(16), 0);
        toolbar.addView(v, lp);
        return v;
    }

    public void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    public void showToast(int msgId) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, msgId, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msgId);
        }
        mToast.show();
    }

    public void setRefreshListener(SwipeRefreshLayout.OnRefreshListener refreshListener) {
        if (refreshLayout == null) return;
        setRefreshLayout(true);
        refreshLayout.setOnRefreshListener(refreshListener);
    }

    public void setOnRetryRequestListener(OnRetryRequestListener onRetryRequestListener) {
        this.onRetryRequestListener = onRetryRequestListener;
    }

    public enum TYPE {
        BKF, DBING, DEFAULT
    }

    public void exit() {
        finishAffinity();
    }

}
