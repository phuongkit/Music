package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.SongDao;
import com.example.myapplication.Dialog.AddSongByPlaylistDialog;
import com.example.myapplication.Fragment.MusicFragment;
import com.example.myapplication.Model.Playlist;
import com.example.myapplication.Model.Song;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ListMusicActivity extends AppCompatActivity {
    Activity activity;

    Toolbar toolbar;
    ImageView imgPlaylistAvatar;

    Playlist playlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_list_music);
        initData();
        addControls();
        addEvents();
        initControl();
    }

    private void initControl() {
        Glide.with(ListMusicActivity.this).load(playlist.getImage()).error(R.drawable.ic_playlist).into(imgPlaylistAvatar);
    }

    private void addEvents() {
    }

    private void addControls() {
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        //Toobar đã như ActionBar
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(playlist.getName());

        imgPlaylistAvatar = findViewById(R.id.imgPlaylistAvatar);
        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        MusicFragment musicFragment = new MusicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("TypeMusic", 1);
        bundle.putSerializable("playlist", playlist);
        musicFragment.setArguments(bundle);
        ft.replace(R.id.fragmentBaihat, musicFragment);
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        playlist = (Playlist) bundle.getSerializable("playlist");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.playlist_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menuInsert:
                SongDao songDao = new SongDao();
                songDao.getAll(new RetrieValEventListener<List<Song>>() {
                    @Override
                    public void OnDataRetrieved(List<Song> songs1) {
                        songDao.getSongByPlaylist(playlist.getId(), new RetrieValEventListener<List<Song>>() {
                            @Override
                            public void OnDataRetrieved(List<Song> songs2) {

                                AddSongByPlaylistDialog dialog = new AddSongByPlaylistDialog(activity, (ArrayList<Song>) songs1, (ArrayList<Song>) songs2, playlist);
                                dialog.show();
                            }
                        });
                    }
                });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}