package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Adapter.DSViewpagerSongAdapter;
import com.example.myapplication.Fragment.Fragment_DiaNhac;
import com.example.myapplication.Fragment.Fragment_PlaySongList;
import com.example.myapplication.Module.Hinhdianhac;
import com.example.myapplication.Module.Song;
import com.example.myapplication.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlaybaihatActivity extends AppCompatActivity {
    Context context;
    ConstraintLayout layout_controlMusic;

    ArrayList<Hinhdianhac> hinhbaihats;
    public static ArrayList<Song> songs = new ArrayList<>();
    int index;
    public MediaPlayer mediaPlayer = new MediaPlayer();
    View view;
    Toolbar toolbarPlayMusic;

    //    CircleImageView imageViewDiaNhac;;
    SeekBar seekBarTime;

    DSViewpagerSongAdapter viewPagerPlayListSong;
    Fragment_DiaNhac fragmentDiaNhac;
    Fragment_PlaySongList fragmentPlayMusicList;
    TextView tvTimeSong, tvTotalTimeSong;
    ImageButton btnPause, btnNext, btnPrevious, imgRepeat, imgRandom;
    String url, urlImage;
    ViewPager viewPagerPlayMusic;
    public static final int BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT = 1;
    String checkIndex;

    public String getCheckIndex() {
        return checkIndex;
    }

    public void setCheckIndex(String checkIndex) {
        this.checkIndex = checkIndex;
    }

    boolean repeat = false;
    boolean checkRandom = false;
    boolean next = false;
    int position = 0;
    int count = 0;
    AnimatedVectorDrawableCompat avd;
    AnimatedVectorDrawable avd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hinhbaihats = new ArrayList<>();
        setContentView(R.layout.controlmucsic_item);
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
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Log.d("Test", "Node XBC");
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();
                    }
                });
                mediaPlayer.setDataSource(Song);
                mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                        return false;
                    }
                });
                mediaPlayer.prepare();
            } catch (FileNotFoundException e) {
//                Log.d("Test", "Node 1");
                e.printStackTrace();
                Toast.makeText(PlaybaihatActivity.this, getString(R.string.strMessageFailPlayMusic), Toast.LENGTH_LONG).show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nextMusic();
                    }
                }, 4500);
                return;
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            TimeSong();
            updateTime();
        }
    }

    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        tvTotalTimeSong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        seekBarTime.setMax(mediaPlayer.getDuration());
    }

    @SuppressLint("ResourceType")
    private void eventClick() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (viewPagerPlayListSong.getItem(1) != null) {
                    if (songs.size() > 0) {
                        fragmentDiaNhac.playMusic(songs.get(position).getImage());
                        handler.removeCallbacks(this);
                    } else {
                        handler.postDelayed(this, 300);
                    }
                }
            }
        }, 100);
        btnPause.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View view) {
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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        fragmentDiaNhac.objectAnimator.pause();
                    }

//                    Fragment_dianhac fragment_dianhac = new Fragment_dianhac();
//                    Bundle bundle=new Bundle();
//                    bundle.putString("imageDianhac", getCheckIndex());
//                    bundle.putString("animator","pause");
//                    fragment_dianhac.setArguments(bundle);
//                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.viewPagerPlayMusic2,fragment_dianhac).commit();
                } else {
                    mediaPlayer.start();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        fragmentDiaNhac.objectAnimator.resume();
                    }

//                    Fragment_dianhac fragment_dianhac = new Fragment_dianhac();
//                    Bundle bundle=new Bundle();
//                    bundle.putString("imageDianhac",getCheckIndex());
//                    bundle.putString("animator","resume");
//                    fragment_dianhac.setArguments(bundle);
//                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.viewPagerPlayMusic2,fragment_dianhac).commit();
                }
            }
        });
        imgRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repeat == false) {
                    if (checkRandom == true) {
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
            }
        });
        imgRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRandom == false) {
                    if (repeat == true) {
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
            }
        });
        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
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
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMusic();
            }

        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousMusic();
            }
        });

    }

    private void nextMusic() {
        if (songs.size() > 0) {
            if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            if (checkRandom) {
                Random random = new Random();
                int vitri = random.nextInt(songs.size());
                position = vitri;
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
//                    Fragment_dianhac fragment_dianhac = new Fragment_dianhac();
//                    Bundle bundle=new Bundle();
//                    setCheckIndex(songs.get(position).getImage());
//                    bundle.putString("imageDianhac",getCheckIndex());
//                    bundle.putString("animator","resume");
//                    fragment_dianhac.setArguments(bundle);
//                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.viewPagerPlayMusic2,fragment_dianhac).commit();
            getSupportActionBar().setTitle(songs.get(position).getName());

            updateTime();
        }
        btnPrevious.setClickable(false);
        btnNext.setClickable(false);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                btnNext.setClickable(true);
                btnPrevious.setClickable(true);
            }
        }, 2000);
        viewPagerPlayMusic.getAdapter().notifyDataSetChanged();
    }

    private void previousMusic() {
        if (songs.size() > 0) {
            if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            if (checkRandom == true) {
                Random random = new Random();
                int vitri = random.nextInt(songs.size());
                position = vitri;
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

//                    Fragment_dianhac fragment_dianhac = new Fragment_dianhac();
//                    Bundle bundle=new Bundle();
//                    setCheckIndex(songs.get(position).getImage());
//                    bundle.putString("imageDianhac",getCheckIndex());
//                    bundle.putString("animator","resume");
//                    fragment_dianhac.setArguments(bundle);
//                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.viewPagerPlayMusic2,fragment_dianhac).commit();
            getSupportActionBar().setTitle(songs.get(position).getName());
            updateTime();
        }
        btnPrevious.setClickable(false);
        btnNext.setClickable(false);
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                btnPrevious.setClickable(true);
                btnNext.setClickable(true);
            }
        }, 2000);
        viewPagerPlayMusic.getAdapter().notifyDataSetChanged();
    }

    private void updateTime() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    seekBarTime.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    tvTimeSong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this, 600);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            next = true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
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
                if (next == true) {
                    if (songs.size() > 0) {
                        if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            mediaPlayer = null;
                        }
                        if (checkRandom == true) {
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
//                        Fragment_dianhac fragment_dianhac = new Fragment_dianhac();
//                        Bundle bundle=new Bundle();
//                        bundle.putString("imageDianhac",songs.get(position).getImage());
//                        fragment_dianhac.setArguments(bundle);
//                        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.replace(R.id.viewPagerPlayMusic2,fragment_dianhac).commit();
                        fragmentDiaNhac.playMusic(songs.get(position).getImage());
                        fragmentPlayMusicList.loaddata(position);
                        getSupportActionBar().setTitle(songs.get(position).getName());
                        viewPagerPlayMusic.getAdapter().notifyDataSetChanged();
                    }
                    btnPrevious.setClickable(false);
                    btnNext.setClickable(false);
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btnPrevious.setClickable(true);
                            btnNext.setClickable(true);
                        }
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
    }

    private void init() {
        setSupportActionBar(toolbarPlayMusic);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarPlayMusic.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mediaPlayer.stop();
                songs.clear();
            }
        });
        hinhbaihats.add(new Hinhdianhac(songs.get(index).getImage()));
        fragmentDiaNhac = new Fragment_DiaNhac();
        fragmentPlayMusicList = new Fragment_PlaySongList(index);
        viewPagerPlayListSong = new DSViewpagerSongAdapter(getSupportFragmentManager());
        viewPagerPlayListSong.addFragment(fragmentPlayMusicList);
        viewPagerPlayListSong.addFragment(fragmentDiaNhac);
        viewPagerPlayMusic.setAdapter(viewPagerPlayListSong);
        viewPagerPlayMusic.setCurrentItem(1);
        fragmentPlayMusicList = (Fragment_PlaySongList) viewPagerPlayListSong.getItem(0);
        fragmentDiaNhac = (Fragment_DiaNhac) viewPagerPlayListSong.getItem(1);
        if (songs.size() > 0) {
            position = index;
            getSupportActionBar().setTitle(songs.get(index).getName());
            new PlayMp3().execute(songs.get(index).getLinkSong());
            btnPause.setImageResource(R.drawable.avd_pause_to_play);

        }
    }

    private void setBackgroud(String url) {
        URL ur = null;

        try {
            ur = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(ur.openConnection().getInputStream());
            BitmapDrawable background = new BitmapDrawable(getResources(), bitmap);
            layout_controlMusic.setBackground(background);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                mediaPlayer.stop();
                songs.clear();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        finish();
//        mediaPlayer.stop();
//        songs.clear();
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        mediaPlayer.stop();
        songs.clear();
    }
}
