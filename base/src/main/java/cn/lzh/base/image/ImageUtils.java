package cn.lzh.base.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.concurrent.ExecutionException;

import cn.lzh.base.R;

/**
 * Created by LZH On 2019/3/20
 */
public class ImageUtils {

    private final static RequestOptions normalOptions = new RequestOptions()
            .placeholder(R.drawable.ic_launcher)
            .error(R.drawable.ic_launcher);

    private final static RequestOptions cropOptions = new RequestOptions()
            .placeholder(R.drawable.ic_launcher)
            .error(R.drawable.ic_launcher)
            .centerCrop();

    private final static RequestOptions circleOptions = new RequestOptions()
            .placeholder(R.drawable.ic_launcher)
            .error(R.drawable.ic_launcher)
            .circleCrop();

    private final static RequestOptions rgb565Options = new RequestOptions()
            .placeholder(R.drawable.ic_launcher)
            .error(R.drawable.ic_launcher)
            .format(DecodeFormat.PREFER_RGB_565);

    private final static RequestOptions bigImageOptions = new RequestOptions()
            .placeholder(R.drawable.ic_launcher)
            .error(R.drawable.ic_launcher)
            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);


    private static GlideRequests initGlide(ImageView view) {
        return GlideApp.with(view);
    }

    private static void displayImage(ImageView view, String path, RequestOptions options) {
        initGlide(view).load(path).apply(options).into(view);
    }






    public static void displayNormalImage(ImageView view, String path) {
        initGlide(view).load(path).apply(normalOptions).into(view);
    }

    public static Bitmap getBitmap(Context mContext, String path) throws ExecutionException, InterruptedException {
        return GlideApp.with(mContext).asBitmap().load(path).submit().get();
    }

    public static File downloadImage(Context mContext, String path) throws ExecutionException, InterruptedException {
        return GlideApp.with(mContext).download(path).submit().get();
    }
}
