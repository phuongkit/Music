package com.example.myapplication.adapter;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.example.myapplication.R;
import com.example.myapplication.module.Baihat;
import com.example.myapplication.module.Hinhdianhac;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.myapplication.R.*;
import static com.example.myapplication.R.drawable.*;


public class PlaybaihatAdapter extends PagerAdapter {
    public static  Context context;
    public static  ArrayList<Baihat> baihats = new ArrayList<Baihat>();
    int index;
    ObjectAnimator objectAnimator;
    MediaPlayer mediaPlayer = new MediaPlayer();
    View view;
    CircleImageView imageViewDiaNhac;;
    SeekBar seekBarTime;
    TextView tvTimeSong, tvTotalTimeSong;
    ImageButton btnPause, btnNext, btnPrevious;
    String url, urlImage;

    boolean repeat = false;
    boolean checkRandom = false;
    boolean next = false;

    public PlaybaihatAdapter(Context context, ArrayList<Baihat> baihats, int index) {
        this.context = context;
        this.baihats = baihats;
        this.index = index;
    }

    @Override
    public int getCount() {
        return baihats.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(layout.dianhac_item, null);
        mapping();
        control();
        PlayNhacMp3(url, urlImage);
        container.addView(view);
        return view;
    }

    private void control() {
//        btnPause.setBackgroundResource(R.drawable.iconpause);
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageButton btn = (ImageButton) view;
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
//                    objectAnimator.pause();
                    btn.setBackgroundResource(R.drawable.iconplay);
                    Log.d("PPP", "Pause Music");
                }
                else {
                    mediaPlayer.start();
//                    objectAnimator.start();
                    btn.setBackgroundResource(R.drawable.iconpause);
                    Log.d("PPP", "Play Music");
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index + 1 < baihats.size()) {
                    index = index + 1;
                    url = baihats.get(index).getLinkBaihat();
                    urlImage = baihats.get(index).getHinhBaihat();
                    Log.d("PPP", "Next Music:" + index);
//                    Glide.with(context).load(baihats.get(index).getHinhBaihat())
//                            .error(ic_launcher_background)
//                            .apply(new RequestOptions()
//                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                                    .skipMemoryCache(true))
//                            .into(imageViewDiaNhac);
//            imageViewDiaNhac.setImageBitmap(null);
//            Picasso.with(context).load(baihats.get(index).getHinhBaihat()).into(imageViewDiaNhac);
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
                    url = baihats.get(index).getLinkBaihat();
                    urlImage = baihats.get(index).getHinhBaihat();
                    Log.d("KKK", "Previous Music:"+baihats.get(index).getHinhBaihat());
//                    Glide.with(context).load(baihats.get(index).getHinhBaihat())
//                            .error(ic_launcher_background)
//                            .apply(new RequestOptions()
//                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                                    .skipMemoryCache(true))
//                            .into(imageViewDiaNhac);
//                    Picasso.with(context).load(baihats.get(index).getHinhBaihat()).into(imageViewDiaNhac);
                    PlayNhacMp3(url, urlImage);
                }
                else {
                    Toast.makeText(context, "Not Available Previous Music", Toast.LENGTH_SHORT).show();
                    Log.d("KKK", "Not Available Previous Music");
                }
            }
        });
//        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
//                tvTimeSong.setText(simpleDateFormat.format(progress));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                mediaPlayer.seekTo(seekBar.getProgress());
//            }
//        });
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
        Glide.with(context).load(baihats.get(index).getHinhBaihat())
                .error(ic_launcher_background)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(imageViewDiaNhac);
//        Picasso.with(context).load(baihats.get(index).getHinhBaihat()).into(imageViewDiaNhac);
//        Glide.with(context).load(baihats.get(this.index).getHinhBaihat()).error(ic_launcher_background).into(imageViewDiaNhac);
//        Picasso.with(context).load("").into(imageViewDiaNhac);
        objectAnimator = ObjectAnimator.ofFloat(imageViewDiaNhac, "rotation", 0f, 360f);
        objectAnimator.setDuration(10000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
        url = baihats.get(this.index).getLinkBaihat();
        urlImage = baihats.get(this.index).getHinhBaihat();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void PlayNhacMp3(String url, String urlImage) {
        Glide.with(context)
                .load(urlImage)
//                .signature(new ObjectKey(String.valueOf(System.currentTimeMillis())))
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d("PPP", dataSource.toString());
                        imageViewDiaNhac.setImageDrawable(resource);
                        return false;
                    }
                })
                .placeholder(R.drawable.icondianhac)
                .error(R.drawable.ic_launcher_background);
//                .into(imageViewDiaNhac);
//        imageViewDiaNhac.setImageBitmap(getBitmapFromURL(url));
//        if (mediaPlayer != null) {
//            mediaPlayer.stop();
//            mediaPlayer.reset();
//            mediaPlayer.release();
//        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        mediaPlayer.setAudioAttributes(
//                new AudioAttributes
//                        .Builder()
//                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                        .build());

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
        PlaybaihatAdapter.this.notifyDataSetChanged();
//        Log.d("PPP", "Time:" + mediaPlayer.getDuration());
//        TimeSong();
//        updateTim e();
    }

//    private void TimeSong() {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
//        tvTotalTimeSong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
//        seekBarTime.setMax(mediaPlayer.getDuration());
//    }

//    private void updateTime() {
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (mediaPlayer != null) {
//                    seekBarTime.setProgress(mediaPlayer.getCurrentPosition());
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
//                    tvTimeSong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
//                    handler.postDelayed(this, 300);
//                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                        @Override
//                        public void onCompletion(MediaPlayer mp) {
//                            next = true;
//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                }
//            }
//        }, 300);
//        final Handler handlerChangeSong = new Handler();
//        handlerChangeSong.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (next == true) {
//                    if (baihats.size() > 0) {
//                        if (mediaPlayer.isPlaying() || mediaPlayer != null) {
//                            mediaPlayer.stop();
//                            mediaPlayer.release();
//                            mediaPlayer = null;
//                        }
//                        if (checkRandom == true) {
//                            Random random = new Random();
//                            int position = index;
//                            while(index == position){
//                                position = random.nextInt(baihats.size());
//                            }
//                            position = index;
//                        } else {
//                            index = (index + 1) % index.size();
//                        }
//                        PlayNhacMp3();
//                    }
//                    imgPre.setClickable(false);
//                    imgNext.setClickable(false);
//                    Handler handler1 = new Handler();
//                    handler1.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            imgPre.setClickable(true);
//                            imgNext.setClickable(true);
//                        }
//                    }, 2000);
//                    next = false;
//                    handlerChangeSong.removeCallbacks(this);
//                } else {
//                    handler.postDelayed(this, 1000);
//                }
//            }
//        }, 1000);
//    }
//    public static Bitmap getBitmapFromURL(String src) {
//        try {
//            URL url = new URL(src);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            return myBitmap;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}
