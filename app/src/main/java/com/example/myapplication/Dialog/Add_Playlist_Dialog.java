package com.example.myapplication.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.example.myapplication.Adapter.AddPlaylistAdapter;
import com.example.myapplication.Module.Playlist;
import com.example.myapplication.Module.Song;
import com.example.myapplication.R;

import java.util.ArrayList;

public class Add_Playlist_Dialog extends Dialog {

    ArrayList<Playlist> playlists;
    ArrayList<Playlist> playlists_by_song;
    Song song;

    ListView lvPlaylist;
    AddPlaylistAdapter addPlaylistAdapter;
    ImageButton imgBtnSave, imgBtnClose;

    public Add_Playlist_Dialog(@NonNull Activity activity, ArrayList<Playlist> playlists, ArrayList<Playlist> playlists_by_song, Song song) {
        super(activity);
        this.playlists = playlists;
        this.playlists_by_song = playlists_by_song;
        this.song = song;
        addControls();
        addEvents();
        initData();
    }

    private void initData() {
    }

    private void addEvents() {
        imgBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imgBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
    }

    private void addControls() {
        lvPlaylist = findViewById(R.id.lvPlaylist);
        addPlaylistAdapter = new AddPlaylistAdapter(getContext(), android.R.layout.simple_list_item_1, playlists, playlists_by_song, song);
        lvPlaylist.setAdapter(addPlaylistAdapter);
        addPlaylistAdapter.addAll(playlists);
        imgBtnSave = findViewById(R.id.imgBtnSave);
        imgBtnClose = findViewById(R.id.imgBtnClose);
    }
}
