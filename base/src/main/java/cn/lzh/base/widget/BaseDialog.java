package cn.lzh.base.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;


/**
 * Created by LZH On 2019/3/15
 */
public class BaseDialog extends AlertDialog {

    private int layoutResID;
    private View parentView;
    private Context mContext;
    private Window mWindow;

    /**
     * @param context     Context
     * @param layoutResID dialog layout
     */
    public BaseDialog(@NonNull Context context, int layoutResID) {
        super(context);
        mContext = context;
        this.layoutResID = layoutResID;
    }

    /**
     * @param context     Context
     * @param layoutResID dialog layout
     * @param theme       theme id
     */
    public BaseDialog(@NonNull Context context, int layoutResID, int theme) {
        super(context, theme);
        mContext = context;
        this.layoutResID = layoutResID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentView = getLayoutInflater().inflate(layoutResID, null);
        setContentView(parentView);
        mWindow = getWindow();
        mWindow.setBackgroundDrawableResource(android.R.color.transparent);
        setCancelable(false);
    }

    public void setDialogWH(int width, int height) {
        if (mWindow == null) mWindow = getWindow();
        mWindow.setLayout(width, height);
    }

    public void setGravity(int gravity) {
        if (mWindow == null) mWindow = getWindow();
        mWindow.setGravity(gravity);
    }

    public <T extends View> T findViewById(int id) {
        return parentView.findViewById(id);
    }

    public void setViewOnClickListener(int id, View.OnClickListener listener) {
        parentView.findViewById(id).setOnClickListener(listener);
    }
}
