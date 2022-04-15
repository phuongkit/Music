package com.example.myapplication.adapter;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.module.Hinhdianhac;

import java.io.IOException;
import java.util.ArrayList;


public class PlaybaihatAdapter extends PagerAdapter {
    Context context;
    ArrayList<Hinhdianhac> hinhdianhac;
    ObjectAnimator objectAnimator;
    MediaPlayer mediaPlayer;
    Button btnPause;

    public PlaybaihatAdapter(Context context, ArrayList<Hinhdianhac> hinhdianhac) {
        this.context = context;
        this.hinhdianhac = hinhdianhac;
    }

    @Override
    public int getCount() {
        return hinhdianhac.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dianhac_item, null);

        ImageView imageViewDiaNhac = view.findViewById(R.id.imageViewDiaNhac);

        Glide.with(context).load(hinhdianhac.get(position).getHinhdianhac()).error(R.drawable.ic_launcher_background).into(imageViewDiaNhac);
//        Picasso.with(context).load("").into(circleImageView);
        objectAnimator=ObjectAnimator.ofFloat(imageViewDiaNhac,"rotation",2f,360f);
        objectAnimator.setDuration(10000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
        btnPause = view.findViewById(R.id.btnPause);

        PlayNhacMp3(hinhdianhac.get(position).getLinkbaihat());
        container.addView(view);



        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
    public void PlayNhacMp3(String url){

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        mediaPlayer.setAudioAttributes(
//                new AudioAttributes
//                        .Builder()
//                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                        .build());

        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    btnPause.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    mp.pause();
                                }
                            });
                        }
                    });
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public String toString() {
        return "PlaybaihatAdapter{" +
                "context=" + context +
                ", hinhdianhac=" + hinhdianhac.get(0).getLinkbaihat() +
                ", objectAnimator=" + objectAnimator +
                '}';
    }
}
