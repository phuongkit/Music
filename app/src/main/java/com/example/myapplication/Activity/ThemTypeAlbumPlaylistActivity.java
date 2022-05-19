package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.Adapter.ThemTypeAdapter;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.SongDao;
import com.example.myapplication.Module.Album;
import com.example.myapplication.Module.Playlist;
import com.example.myapplication.Module.Song;
import com.example.myapplication.Module.Theme;
import com.example.myapplication.Module.Types;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;



public class ThemTypeAlbumPlaylistActivity extends AppCompatActivity {
    Theme theme;
    Types types;
    Album album;
    Playlist playlist;
    String check="";
    RecyclerView recyclerViewsong;
    Toolbar toolbarsong;
    ThemTypeAdapter songAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_song_of_theme_type_album_playlist);
        Intent intent = getIntent();
        if (intent.hasExtra("theme")){
            theme = (Theme) intent.getSerializableExtra("theme");
        }
        if (intent.hasExtra("type")){
            types = (Types) intent.getSerializableExtra("type");
        }
        if (intent.hasExtra("album")){
            album = (Album) intent.getSerializableExtra("album");
        }
        if (intent.hasExtra("playlist")){
            playlist = (Playlist) intent.getSerializableExtra("playlist");
        }
        check = intent.getStringExtra("check");
        viewBinding();
        init();
        getData();
    }

    private void getData() {
        if(check.equals("theme")){
            ArrayList<Song> songsByTheme = new ArrayList<>();
            SongDao songDao = new SongDao();
            songDao.getAll(new RetrieValEventListener<List<Song>>() {
                @Override
                public void OnDataRetrieved(List<Song> songs) {
                    for (Song song:songs) {
                        if(song.getIdTheme().equals(theme.id)){
                            songsByTheme.add((song));
                        }
                    }
                    songAdapter = new ThemTypeAdapter(ThemTypeAlbumPlaylistActivity.this, songsByTheme);
                    recyclerViewsong.setLayoutManager(new GridLayoutManager(ThemTypeAlbumPlaylistActivity.this, 2));
                    recyclerViewsong.setAdapter(songAdapter);
                }
            });
        }
        if(check.equals("type")) {
            ArrayList<Song> songsByType = new ArrayList<>();
            SongDao songDao = new SongDao();
            songDao.getAll(new RetrieValEventListener<List<Song>>() {
                @Override
                public void OnDataRetrieved(List<Song> songs) {
                    for (Song song : songs) {
                        if (song.getIdTypes().equals(types.id)) {
                            songsByType.add((song));
                        }
                    }
                    songAdapter = new ThemTypeAdapter(ThemTypeAlbumPlaylistActivity.this, songsByType);
                    recyclerViewsong.setLayoutManager(new GridLayoutManager(ThemTypeAlbumPlaylistActivity.this, 2));
                    recyclerViewsong.setAdapter(songAdapter);
                }
            });
        }
        if(check.equals("album")) {
            ArrayList<Song> songsByAlbum = new ArrayList<>();
            SongDao songDao = new SongDao();
            songDao.getAll(new RetrieValEventListener<List<Song>>() {
                @Override
                public void OnDataRetrieved(List<Song> songs) {
                    for (Song song : songs) {
                        if (song.getIdAlbum().equals(album.id)) {
                            songsByAlbum.add((song));
                        }
                    }
                    songAdapter = new ThemTypeAdapter(ThemTypeAlbumPlaylistActivity.this, songsByAlbum);
                    recyclerViewsong.setLayoutManager(new GridLayoutManager(ThemTypeAlbumPlaylistActivity.this, 2));
                    recyclerViewsong.setAdapter(songAdapter);
                }
            });
        }
        if(check.equals("playlist")) {
            ArrayList<Song> songsByPlaylist = new ArrayList<>();
            SongDao songDao = new SongDao();
            songDao.getAll(new RetrieValEventListener<List<Song>>() {
                @Override
                public void OnDataRetrieved(List<Song> songs) {
                    for (Song song : songs) {
                        if (song.getIdPlaylist().equals(playlist.id)) {
                            songsByPlaylist.add((song));
                        }
                    }
                    songAdapter = new ThemTypeAdapter(ThemTypeAlbumPlaylistActivity.this, songsByPlaylist);
                    recyclerViewsong.setLayoutManager(new GridLayoutManager(ThemTypeAlbumPlaylistActivity.this, 2));
                    recyclerViewsong.setAdapter(songAdapter);
                }
            });
        }
    }


    private void init() {
        if(check.equals("theme")) {
            setSupportActionBar(toolbarsong);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(theme.getName());
            toolbarsong.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } if(check.equals("type")) {
            setSupportActionBar(toolbarsong);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(types.getName());
            toolbarsong.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        if(check.equals("album")) {
            setSupportActionBar(toolbarsong);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(album.getName());
            toolbarsong.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        if(check.equals("playlist")) {
            setSupportActionBar(toolbarsong);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(playlist.getName());
            toolbarsong.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
    private void viewBinding() {
        recyclerViewsong= findViewById(R.id.recyclerViewSong);
        toolbarsong = findViewById(R.id.toolBarSong);
    }

}
