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
import com.example.myapplication.Module.Baihat;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.myapplication.R.*;
import static com.example.myapplication.R.drawable.*;


public class PlaybaihatAdapter extends PagerAdapter {
    public static  Context context;
    public static  ArrayList<Baihat> baihats = new ArrayList<Baihat>();
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
        btnPause.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                ImageButton btn = (ImageButton) view;
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    objectAnimator.pause();
                    btn.setBackgroundResource(R.drawable.iconplay);
                    Log.d("PPP", "Pause Music");
                }
                else {
                    mediaPlayer.start();
                    objectAnimator.pause();
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
//        Picasso.with(context).load(baihats.get(index).getHinhBaihat()).into(imageViewDiaNhac);
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
        btnPause.setBackgroundResource(iconpause);
        Glide.with(context).load(baihats.get(index).getHinhBaihat())
                .error(ic_launcher_background)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(imageViewDiaNhac);
//                .into(imageViewDiaNhac);
//        imageViewDiaNhac.setImageBitmap(getBitmapFromURL(url));
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
        }
        mediaPlayer = new MediaPlayer();
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