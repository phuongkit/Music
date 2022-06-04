package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Adapter.DSViewpagerSongAdapter;
import com.example.myapplication.Fragment.MusicDiscFragment;
import com.example.myapplication.Fragment.PlaySongListFragment;
import com.example.myapplication.Generic.Beans.Hinhdianhac;
import com.example.myapplication.Model.Song;
import com.example.myapplication.R;
import com.example.myapplication.Service.MyPlayMusicService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class PlayMusicActivity extends AppCompatActivity {
    ConstraintLayout layout_controlMusic;

    ArrayList<Hinhdianhac> hinhbaihats;
    public static ArrayList<Song> songs = new ArrayList<>();
    int index;
    public MediaPlayer mediaPlayer = new MediaPlayer();
    Toolbar toolbarPlayMusic;

    SeekBar seekBarTime;

    DSViewpagerSongAdapter viewPagerPlayListSong;
    MusicDiscFragment fragmentDiaNhac;
    PlaySongListFragment fragmentPlayMusicList;
    TextView tvTimeSong, tvTotalTimeSong;
    ImageButton btnPause, btnNext, btnPrevious, imgRepeat, imgRandom;
    ViewPager viewPagerPlayMusic;

    boolean repeat = false;
    boolean checkRandom = false;
    boolean next = false;
    int position = 0;
    int count = 0;
    boolean isPlaying = true;
    AnimatedVectorDrawableCompat avd;
    AnimatedVectorDrawable avd2;

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle == null) {
                return;
            }
            int actionMusic = bundle.getInt("action");
            Log.d("NNNN", actionMusic + "");
            switch (actionMusic) {
                case MyPlayMusicService.ACTION_PAUSE:
                    btnPause.setImageDrawable(getResources().getDrawable(R.drawable.iconplay));
                    mediaPlayer.pause();
                    fragmentDiaNhac.objectAnimator.pause();
                    break;
                case MyPlayMusicService.ACTION_PLAY:
                    btnPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                    mediaPlayer.start();
                    fragmentDiaNhac.objectAnimator.resume();
                    break;
                case MyPlayMusicService.ACTION_NEXT:
                    nextMusic();
                    break;
                case MyPlayMusicService.ACTION_PREVIOUS:
                    previousMusic();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hinhbaihats = new ArrayList<>();
        setContentView(R.layout.item_controlmucsic);
        mapping();
        UNI();
        init();
        eventClick();
    }

    public void UNI() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        songs = (ArrayList<Song>) bundle.getSerializable("songs");
        index = bundle.getInt("index", -1);
    }


    @SuppressLint("StaticFieldLeak")
    class PlayMp3 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String Song) {
            super.onPostExecute(Song);
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                mediaPlayer.setOnCompletionListener(mp -> {
                    Log.d("Test", "Node XBC");
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                });
                if (Song.length() > 4 && (Song.startsWith("http"))) {
                    // Dùng cho URL
                    mediaPlayer.setDataSource(Song);
                } else {
                    mediaPlayer.setDataSource(PlayMusicActivity.this, Uri.parse(Song));
                }
                mediaPlayer.setOnErrorListener((mediaPlayer, i, i1) -> false);
                mediaPlayer.prepare();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(PlayMusicActivity.this, getString(R.string.strNotifyFailPlayMusic), Toast.LENGTH_LONG).show();
                Handler handler = new Handler();
                handler.postDelayed(PlayMusicActivity.this::nextMusic, 4500);
                return;
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            TimeSong();
            updateTime();
        }
    }

    private void TimeSong() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        tvTotalTimeSong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        seekBarTime.setMax(mediaPlayer.getDuration());
    }

    @SuppressLint({"ResourceType", "UseCompatLoadingForDrawables"})
    private void eventClick() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewPagerPlayListSong.getItem(1);
                if (songs.size() > 0) {
                    fragmentDiaNhac.playMusic(songs.get(position).getImage());
                    handler.removeCallbacks(this);
                } else {
                    handler.postDelayed(this, 300);
                }
            }
        }, 100);
        btnPause.setOnClickListener(view -> {
            if (count == 0) {
                btnPause.setImageDrawable(getResources().getDrawable(R.drawable.avd_pause_to_play));
                Drawable drawable = btnPause.getDrawable();
                if (drawable instanceof AnimatedVectorDrawableCompat) {
                    avd = (AnimatedVectorDrawableCompat) drawable;
                    avd.start();
                } else if (drawable instanceof AnimatedVectorDrawable) {
                    avd2 = (AnimatedVectorDrawable) drawable;
                    avd2.start();
                }

                count++;

            } else {
                btnPause.setImageDrawable(getResources().getDrawable(R.drawable.avd_play_to_pause));
                Drawable drawable = btnPause.getDrawable();
                if (drawable instanceof AnimatedVectorDrawableCompat) {
                    avd = (AnimatedVectorDrawableCompat) drawable;
                    avd.start();
                } else if (drawable instanceof AnimatedVectorDrawable) {
                    avd2 = (AnimatedVectorDrawable) drawable;
                    avd2.start();
                }
                count--;

            }
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                fragmentDiaNhac.objectAnimator.pause();
                isPlaying = false;
            } else {
                mediaPlayer.start();
                fragmentDiaNhac.objectAnimator.resume();
                isPlaying = true;
            }
        });
        imgRepeat.setOnClickListener(v -> {
            if (!repeat) {
                if (checkRandom) {
                    checkRandom = false;
                    imgRepeat.setImageResource(R.drawable.iconsyned);
                    imgRandom.setImageResource(R.drawable.iconsuffle);
                }
                imgRepeat.setImageResource(R.drawable.iconsyned);
                repeat = true;
            } else {
                imgRepeat.setImageResource(R.drawable.iconrepeat);
                repeat = false;
            }
        });
        imgRandom.setOnClickListener(v -> {
            if (!checkRandom) {
                if (repeat) {
                    repeat = false;
                    imgRandom.setImageResource(R.drawable.iconshuffled);
                    imgRepeat.setImageResource(R.drawable.iconrepeat);
                }
                imgRandom.setImageResource(R.drawable.iconshuffled);
                checkRandom = true;
            } else {
                imgRandom.setImageResource(R.drawable.iconsuffle);
                checkRandom = false;
            }
        });
        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                tvTimeSong.setText(simpleDateFormat.format(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        btnNext.setOnClickListener(v -> nextMusic());

        btnPrevious.setOnClickListener(v -> previousMusic());

    }

    private void nextMusic() {
        if (songs.size() > 0) {
            try {
                if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
            }
            catch (IllegalStateException e) {
                e.printStackTrace();
            }
            if (checkRandom) {
                Random random = new Random();
                position = random.nextInt(songs.size());
            } else {
                if (repeat) {
                    Log.d("OOO", String.valueOf(position));
                    position = (position) % songs.size();
                    Log.d("OOO", String.valueOf(position));
                } else {
                    position = (position + 1) % songs.size();
                }
            }
            tvTotalTimeSong.setText("");
            new PlayMp3().execute(songs.get(position).getLinkSong());
            hinhbaihats.add(new Hinhdianhac(songs.get(position).getImage()));
            fragmentDiaNhac.playMusic(songs.get(position).getImage());
            fragmentPlayMusicList.loaddata(position);
            fragmentDiaNhac.objectAnimator.start();
            isPlaying = true;
            clickStartService(songs.get(position),MyPlayMusicService.ACTION_PLAY);
            Objects.requireNonNull(getSupportActionBar()).setTitle(songs.get(position).getName());

            updateTime();
        }
        btnPrevious.setClickable(false);
        btnNext.setClickable(false);
        Handler handler1 = new Handler();
        handler1.postDelayed(() -> {
            btnNext.setClickable(true);
            btnPrevious.setClickable(true);
        }, 2000);
        Objects.requireNonNull(viewPagerPlayMusic.getAdapter()).notifyDataSetChanged();
    }

    private void previousMusic() {
        if (songs.size() > 0) {
            if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            if (checkRandom) {
                Random random = new Random();
                position = random.nextInt(songs.size());
            } else {
                if (repeat) {
                    position = (position) % songs.size();
                } else {
                    position = position - 1 < 0 ? songs.size() - 1 : position - 1;
                }
            }
            fragmentDiaNhac.objectAnimator.start();
            new PlayMp3().execute(songs.get(position).getLinkSong());
            hinhbaihats.add(new Hinhdianhac(songs.get(position).getImage()));
            fragmentDiaNhac.playMusic(songs.get(position).getImage());
            fragmentPlayMusicList.loaddata(position);
            isPlaying = true;
            clickStartService(songs.get(position),MyPlayMusicService.ACTION_PLAY);
            Objects.requireNonNull(getSupportActionBar()).setTitle(songs.get(position).getName());
            updateTime();
        }
        btnPrevious.setClickable(false);
        btnNext.setClickable(false);
        Handler handler1 = new Handler();
        handler1.postDelayed(() -> {
            btnPrevious.setClickable(true);
            btnNext.setClickable(true);
        }, 2000);
        Objects.requireNonNull(viewPagerPlayMusic.getAdapter()).notifyDataSetChanged();
    }

    private void updateTime() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    try {
                        seekBarTime.setProgress(mediaPlayer.getCurrentPosition());
                    }
                    catch (IllegalStateException e) {
                        e.printStackTrace();
                        Toast.makeText(PlayMusicActivity.this, getString(R.string.strNotifyFailPlayMusic), Toast.LENGTH_LONG).show();
                        Handler handler = new Handler();
                        handler.postDelayed(() -> nextMusic(), 4500);
                        return;
                    }
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    tvTimeSong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this, 600);
                    mediaPlayer.setOnCompletionListener(mp -> {
                        next = true;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        }, 300);
        final Handler handlerChangeSong = new Handler();
        handlerChangeSong.postDelayed(new Runnable() {
            @SuppressLint("ResourceType")
            @Override
            public void run() {
                if (next) {
                    if (songs.size() > 0) {
                        if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            mediaPlayer = null;
                        }
                        if (checkRandom) {
                            Random random = new Random();
                            int index = position;
                            while (index == position) {
                                index = random.nextInt(songs.size());
                            }
                            position = index;
                        } else {
                            if (repeat) {
                                position = (position) % songs.size();
                            } else {
                                position = (position + 1) % songs.size();
                            }

                        }
                        fragmentDiaNhac.objectAnimator.start();
                        new PlayMp3().execute(songs.get(position).getLinkSong());
                        hinhbaihats.add(new Hinhdianhac(songs.get(position).getImage()));
                        fragmentDiaNhac.playMusic(songs.get(position).getImage());
                        fragmentPlayMusicList.loaddata(position);
                        Objects.requireNonNull(getSupportActionBar()).setTitle(songs.get(position).getName());
                        Objects.requireNonNull(viewPagerPlayMusic.getAdapter()).notifyDataSetChanged();
                    }
                    btnPrevious.setClickable(false);
                    btnNext.setClickable(false);
                    Handler handler1 = new Handler();
                    handler1.postDelayed(() -> {
                        btnPrevious.setClickable(true);
                        btnNext.setClickable(true);
                    }, 2000);
                    next = false;
                    handlerChangeSong.removeCallbacks(this);
                } else {
                    handler.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }

    private void mapping() {
        toolbarPlayMusic = findViewById(R.id.toolbarPlayMusic);
        seekBarTime = findViewById(R.id.seekBarSong);
        imgRepeat = findViewById(R.id.imageViewButtonLoop);
        imgRandom = findViewById(R.id.imageviewButtonShuffle);
        viewPagerPlayMusic = findViewById(R.id.viewPagerPlayMusic2);
        tvTimeSong = findViewById(R.id.tvTimeSong);
        tvTotalTimeSong = findViewById(R.id.tvTotalTimeSong);
        btnPause = findViewById(R.id.btnPause2);
        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);
        layout_controlMusic = findViewById(R.id.layout_controlmusic);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("send_data"));
    }

    private void init() {
        setSupportActionBar(toolbarPlayMusic);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbarPlayMusic.setNavigationOnClickListener(v -> {
            finish();
            mediaPlayer.stop();
            songs.clear();
        });
        hinhbaihats.add(new Hinhdianhac(songs.get(index).getImage()));
        fragmentDiaNhac = new MusicDiscFragment();
        fragmentPlayMusicList = new PlaySongListFragment(index);
        viewPagerPlayListSong = new DSViewpagerSongAdapter(getSupportFragmentManager());
        viewPagerPlayListSong.addFragment(fragmentPlayMusicList);
        viewPagerPlayListSong.addFragment(fragmentDiaNhac);
        viewPagerPlayMusic.setAdapter(viewPagerPlayListSong);
        viewPagerPlayMusic.setCurrentItem(1);
        fragmentPlayMusicList = (PlaySongListFragment) viewPagerPlayListSong.getItem(0);
        fragmentDiaNhac = (MusicDiscFragment) viewPagerPlayListSong.getItem(1);
        if (songs.size() > 0) {
            position = index;
            Objects.requireNonNull(getSupportActionBar()).setTitle(songs.get(index).getName());
            new PlayMp3().execute(songs.get(index).getLinkSong());
            btnPause.setImageResource(R.drawable.avd_pause_to_play);
            clickStartService(songs.get(index), MyPlayMusicService.ACTION_PLAY);
        }
    }

    private void clickStartService(Song song, int action) {
        Intent intent = new Intent(this, MyPlayMusicService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("song", song);
        bundle.putInt("action_music", action);
        bundle.putBoolean("isplaying", isPlaying);
        intent.putExtras(bundle);
        startService(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            mediaPlayer.stop();
            songs.clear();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        mediaPlayer.stop();
        songs.clear();
    }
}
