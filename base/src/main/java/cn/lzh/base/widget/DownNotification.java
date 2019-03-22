package cn.lzh.base.widget;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;


/**
 * Created by Administrator_LZH on 2016/8/23.
 */
public class DownNotification {

    private static final String TAG = "DownNotification";
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotifyManager;
    private Context mContext;
    private int DOWNLOAD_NOTIFICATION_ID = 100000;

    public DownNotification(Context mContext) {
        this.mContext = mContext;
    }

    public DownNotification init(String title, String contentText, int iconId) {
        mNotifyManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setContentTitle(title)
                .setContentText(contentText)
                .setSmallIcon(iconId);
        return this;
    }

    public DownNotification setProgress(int max, int progress, boolean indeterminate) {
        mBuilder.setProgress(max, progress, indeterminate);
        mNotifyManager.notify(DOWNLOAD_NOTIFICATION_ID, mBuilder.build());
        return this;
    }

    public DownNotification setProgressComplete(String completeText) {
        mBuilder.setContentText(completeText).setProgress(0, 0, false);
        mNotifyManager.notify(DOWNLOAD_NOTIFICATION_ID, mBuilder.build());
        return this;
    }

    public void cancel() {
        mNotifyManager.cancel(DOWNLOAD_NOTIFICATION_ID);
    }

    public NotificationCompat.Builder getBuilder() {
        return mBuilder;
    }
}
