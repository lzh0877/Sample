package cn.lzh.base.widget;


import android.content.Context;

import org.reactivestreams.Subscription;

import cn.lzh.base.R;
import io.reactivex.functions.Consumer;


/**
 * Created by LZH On 2019/3/19
 */
public class ConsumerDialog implements Consumer<Subscription> {
    private BaseDialog dialog;

    public ConsumerDialog(Context mContext) {
        dialog = new BaseDialog(mContext, R.layout.layout_loading, R.style.Dialog_FullScreen);
    }

    @Override
    public void accept(Subscription subscription) throws Exception {
        if (dialog != null) {
            dialog.show();
        }
    }

    public boolean isShowing() {
        if (dialog != null) {
            return dialog.isShowing();
        } else {
            return false;
        }
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
