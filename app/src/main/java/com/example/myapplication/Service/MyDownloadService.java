package com.example.myapplication.Service;

import static com.example.myapplication.Service.MyApplication.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.Activity.MainActivity;
import com.example.myapplication.Generic.Beans.NotifyObject;
import com.example.myapplication.R;

public class MyDownloadService extends Service {

    public static final int ACTION_START = -2;
    public static final int ACTION_PAUSE = -1;
    public static final int ACTION_RESUME = 1;
    public static final int ACTION_COMPLETE = 2;
    public static final int ACTION_IN_PROGRESS = 0;
    public static final int ACTION_CANCEL = 3;
    public static final int NOTIFICATION_ID = 1;

    RemoteViews notificationLayout = null;
    RemoteViews notificationLayoutExpanded = null;
    Notification notification;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Info", "MyDownloadService onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int actionNotify = -1;
        try {
            actionNotify = intent.getIntExtra("action_notify_service", 0);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Info", "Error: " + e.toString());
        }

        if (actionNotify == ACTION_PAUSE || actionNotify == ACTION_RESUME || actionNotify == ACTION_CANCEL) {
            Log.i("Info", getNameAction(actionNotify) + " MyDownloadService");
            handleActionNotify(actionNotify);
        } else {
            try {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    NotifyObject notifyObject = (NotifyObject) intent.getSerializableExtra("notifyObject");

                    if (notificationLayout == null || notificationLayoutExpanded == null) {
                        initNotification(notifyObject);
                    }
                    sendNotification(notifyObject);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Info", e.toString());
            }
        }

        return START_NOT_STICKY;
    }

    private void handleActionNotify(int action) {
        Log.i("Info", "handleActionNotify");
        switch (action) {
            case ACTION_PAUSE:
            case ACTION_RESUME:
                pauseOrResumeDownload();
                break;

            case ACTION_CANCEL:
                cancelDownload();
                break;
        }
    }

    private void pauseOrResumeDownload() {
//        Log.i("Info", "pauseDownload");
//        // getting the static instance of activity
//        MainActivity activity = MainActivity.instance;
//        if (activity != null) {
//            // we are calling here activity's method
//            activity.pauseOrResumeDownload();
//        }

    }

    private void cancelDownload() {
//        Log.i("Info", "cancelDownload");
//        // getting the static instance of activity
//        MainActivity activity = MainActivity.instance;
//        if (activity != null) {
//            // we are calling here activity's method
//            activity.cancelDownload();
//        }
    }

    public void initNotification(NotifyObject notifyObject) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Get the layouts to use in the custom notification
        notificationLayout = new RemoteViews(getPackageName(), R.layout.layout_download);
        notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.layout_download_expand);

        notificationLayoutExpanded.setTextViewText(R.id.txtLinkDownload, "Link: " + notifyObject.getLink());

        notificationLayoutExpanded.setOnClickPendingIntent(R.id.btnPause, getPendingIntent(this, ACTION_PAUSE));

        notificationLayoutExpanded.setOnClickPendingIntent(R.id.btnPause, getPendingIntent(this, ACTION_RESUME));

        notificationLayoutExpanded.setOnClickPendingIntent(R.id.btnCancel, getPendingIntent(this, ACTION_CANCEL));

        notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.download)
                .setContentTitle("File Download")
                .setContentText("Download in progress")
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .setCustomBigContentView(notificationLayoutExpanded)
                .setOnlyAlertOnce(true)
                .build();
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

//        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void sendNotification(NotifyObject notifyObject) {

        switch (notifyObject.getStatus()) {
            case ACTION_START:
                notificationLayout.setTextViewText(R.id.txtDownloadProgress, "Start");
                notificationLayoutExpanded.setTextViewText(R.id.txtDownloadProgress, "Start");
                break;
            case ACTION_PAUSE:
                notificationLayout.setTextViewText(R.id.txtDownloadProgress, "Pause");
                notificationLayoutExpanded.setTextViewText(R.id.txtDownloadProgress, "Pause");
                break;
            case ACTION_IN_PROGRESS:
                notificationLayout.setTextViewText(R.id.txtDownloadProgress, "Complete: " + notifyObject.getProgress() + "%");
                notificationLayoutExpanded.setTextViewText(R.id.txtDownloadProgress, "Complete: " + notifyObject.getProgress() + "%");
                break;
            case ACTION_RESUME:
                notificationLayout.setTextViewText(R.id.txtDownloadProgress, "Resume");
                notificationLayoutExpanded.setTextViewText(R.id.txtDownloadProgress, "Resume");
                break;
            case ACTION_COMPLETE:
                notificationLayout.setTextViewText(R.id.txtDownloadProgress, "Complete");
                notificationLayoutExpanded.setTextViewText(R.id.txtDownloadProgress, "Complete");
                break;
            case ACTION_CANCEL:
                notificationLayout.setTextViewText(R.id.txtDownloadProgress, "Cancel");
                notificationLayoutExpanded.setTextViewText(R.id.txtDownloadProgress, "Cancel");
                break;
        }
        startForeground(NOTIFICATION_ID, notification);
    }

    private PendingIntent getPendingIntent(Context context, int action) {
        Log.i("Info", getNameAction(action) + " clicked");
        Intent intent = new Intent(this, MyDownloadReceiver.class);
        intent.putExtra("action_notify", action);

        return PendingIntent.getBroadcast(context.getApplicationContext(), action, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static String getNameAction(int action) {
        switch (action) {
            case ACTION_PAUSE:
                return "Pause";
            case ACTION_RESUME:
                return "Resume";
            case ACTION_CANCEL:
                return "Cancel";
            default:
                return "No Action";
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Info", "MyDownloadService onDestroy");
    }
}
