package com.example.myapplication.Service;

import static com.example.myapplication.Service.MyApplication.CHANNEL_ID;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadata;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.myapplication.Model.Song;
import com.example.myapplication.R;

import org.jetbrains.annotations.Nullable;

public class MyPlayMusicService extends Service {
    public static final int ACTION_PAUSE = 1;
    public static final int ACTION_PLAY = 2;
    public static final int ACTION_NEXT = 3;
    public static final int ACTION_PREVIOUS = 4;
    private boolean isPlaying = true;
    Song mSong;
    private int actionMusic = ACTION_PLAY;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            Song song = (Song) bundle.get("song");
            isPlaying = bundle.getBoolean("isplaying");
            if(song!=null){
                mSong=song;
                sendNotification(song);
            }else {
                Log.d("NNNN", "Song null");

            }
            actionMusic = bundle.getInt("action_music");
            Log.e("NNNN", actionMusic+" Receiver");
            handleActionMusic(actionMusic);

        }
        return START_NOT_STICKY;
    }

    private void sendNotification(Song song) {

        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(this, "tag");
        mediaSessionCompat.setMetadata(
                new MediaMetadataCompat.Builder()
                        .putString(MediaMetadata.METADATA_KEY_TITLE, song.getName())
                        .putString(MediaMetadata.METADATA_KEY_ARTIST, song.getSinger())
                        .build()
        );
        androidx.media.app.NotificationCompat.MediaStyle mediaStyle = new androidx.media.app.NotificationCompat.MediaStyle();
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(song.getName())
                .setContentText(song.getSinger())
                .setSmallIcon(R.drawable.ic_notification)
                .setStyle(mediaStyle.setShowActionsInCompactView(0,1,2).setMediaSession(mediaSessionCompat.getSessionToken()));
        if(isPlaying){
            notificationBuilder
                    .addAction(R.drawable.iconpreview,"Preview",getPendingIntent(this, ACTION_PREVIOUS))
                    .addAction(R.drawable.ic_pause,"Pause", getPendingIntent(this, ACTION_PAUSE))
                    .addAction(R.drawable.iconnext,"Next", getPendingIntent(this,ACTION_NEXT));
        }else{
            notificationBuilder
                    .addAction(R.drawable.iconpreview,"Preview", getPendingIntent(this,ACTION_PREVIOUS))
                    .addAction(R.drawable.iconplay,"Play", getPendingIntent(this, ACTION_PLAY))
                    .addAction(R.drawable.iconnext,"Next", getPendingIntent(this,ACTION_NEXT));
        }
        Notification notification = notificationBuilder.build();
        startForeground(1, notification);
    }

    private void handleActionMusic(int action){
        switch (action) {
            case ACTION_PAUSE:
                pauseMusic();
                break;

            case ACTION_PLAY:
                playMusic();
                break;

            case ACTION_PREVIOUS:
                sendActionToPlaySong(ACTION_PREVIOUS);
                break;

            case ACTION_NEXT:
                sendActionToPlaySong(ACTION_NEXT);
                break;
        }
    }

    private void playMusic() {
        sendNotification(mSong);
        sendActionToPlaySong(ACTION_PLAY);
    }

    private void pauseMusic() {
        sendNotification(mSong);
        sendActionToPlaySong(ACTION_PAUSE);
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private PendingIntent getPendingIntent(Context context, int action){
        Intent intent = new Intent(this, MyPlayMusicReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putInt("action_music", action);
        bundle.putSerializable("song", mSong);
        intent.putExtras(bundle);
        return PendingIntent.getBroadcast(context.getApplicationContext(), action, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void sendActionToPlaySong(int action){
        Intent intent = new Intent("send_data");
        Bundle bundle = new Bundle();
        bundle.putInt("action", actionMusic);
        intent.putExtras(bundle);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
