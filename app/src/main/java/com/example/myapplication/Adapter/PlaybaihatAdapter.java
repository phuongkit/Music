package com.example.myapplication.Adapter;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.Model.Song;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.myapplication.R.*;
import static com.example.myapplication.R.drawable.*;


public class PlaybaihatAdapter extends PagerAdapter {
    public static  Context context;
    public static  ArrayList<Song> songs = new ArrayList<Song>();
    private int index;
    private ObjectAnimator objectAnimator;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private View view;
    private CircleImageView imageViewDiaNhac;;
    private SeekBar seekBarTime;
    private TextView tvTimeSong, tvTotalTimeSong;
    private ImageButton btnPause, btnNext, btnPrevious;
    private String url, urlImage;

    boolean repeat = false;
    boolean checkRandom = false;
    boolean next = false;

    public PlaybaihatAdapter(Context context, ArrayList<Song> songs, int index) {
        this.context = context;
        this.songs = songs;
        this.index = index;
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(layout.item_music_disc, null);
        mapping();
        control();
        PlayNhacMp3(url, urlImage);
        container.addView(view);
        return view;
    }

    private void control() {
        btnPause.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                ImageButton btn = (ImageButton) view;
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    objectAnimator.pause();
                    btn.setBackgroundResource(avd_pause_to_play);
                    btn.setBackgroundResource(R.drawable.iconplay);
                    Log.d("PPP", "Pause Music");
                }
                else {
                    mediaPlayer.start();
                    objectAnimator.pause();
                    btn.setBackgroundResource(R.drawable.iconpause);
                    btn.setBackgroundResource(avd_play_to_pause);
                    Log.d("PPP", "Play Music");
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index + 1 < songs.size()) {
                    index = index + 1;
                    url = songs.get(index).getLinkSong();
                    urlImage = songs.get(index).getImage();
                    Log.d("PPP", "Next Music:" + index);
                    PlayNhacMp3(url, urlImage);
                }
                else {
                    Toast.makeText(context, "Not Available Next Music", Toast.LENGTH_SHORT).show();
                    Log.d("PPP", "Not Available Next Music");
                }
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index > 0) {
                    index = index - 1;
                    url = songs.get(index).getLinkSong();
                    urlImage = songs.get(index).getImage();
                    Log.d("KKK", "Previous Music:"+songs.get(index).getImage());
                    PlayNhacMp3(url, urlImage);
                }
                else {
                    Toast.makeText(context, "Not Available Previous Music", Toast.LENGTH_SHORT).show();
                    Log.d("KKK", "Not Available Previous Music");
                }
            }
        });
    }

    private void mapping() {
        seekBarTime = view.findViewById(R.id.seekBarTime);
        tvTimeSong = view.findViewById(R.id.tvTimeSong);
        tvTotalTimeSong = view.findViewById(R.id.tvTotalTimeSong);
        imageViewDiaNhac = view.findViewById(R.id.imageViewDiaNhac);
        btnPause = view.findViewById(R.id.btnPause);
        btnNext = view.findViewById(R.id.btnNext);
        btnPrevious = view.findViewById(R.id.btnPrevious);
        imageViewDiaNhac = view.findViewById(R.id.imageViewDiaNhac);
//        Picasso.with(context).load(songs.get(index).getImage()).into(imageViewDiaNhac);
        objectAnimator = ObjectAnimator.ofFloat(imageViewDiaNhac, "rotation", 0f, 360f);
        objectAnimator.setDuration(10000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
        url = songs.get(this.index).getLinkSong();
        urlImage = songs.get(this.index).getImage();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void PlayNhacMp3(String url, String urlImage) {
        btnPause.setBackgroundResource(avd_play_to_pause);
        Glide.with(context).load(songs.get(index).getImage())
                .error(ic_launcher_background)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(imageViewDiaNhac);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}