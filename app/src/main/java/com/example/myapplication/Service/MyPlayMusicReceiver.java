package com.example.myapplication.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.Model.Song;

public class MyPlayMusicReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        int actionMusic = bundle.getInt("action_music");
        Song song = (Song) bundle.get("song");
        Intent intentService = new Intent(context, MyPlayMusicService.class);
        Bundle bundle1 = new Bundle();
        boolean isPlaying = false;
        if(actionMusic == 1){
            isPlaying = false;
        }else if(actionMusic == 2){
            isPlaying= true;
        }
        bundle1.putInt("action_music", actionMusic);
        bundle1.putBoolean("isplaying", isPlaying);
        bundle1.putSerializable("song",song);
        intentService.putExtras(bundle1);
        context.startService(intentService);
    }
}
