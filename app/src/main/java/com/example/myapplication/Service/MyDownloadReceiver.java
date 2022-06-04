package com.example.myapplication.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyDownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int actionNotify = intent.getIntExtra("action_notify",0);

        Intent intentService = new Intent(context, MyDownloadService.class);
        intentService.putExtra("action_notify_service",actionNotify);
        Log.i("Info", MyDownloadService.getNameAction(actionNotify) + " MyPlayMusicReceiver");
        context.startService(intentService);
    }
}
