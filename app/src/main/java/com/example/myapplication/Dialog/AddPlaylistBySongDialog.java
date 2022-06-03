package com.example.myapplication.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.myapplication.Adapter.AddPlaylistBySongAdapter;
import com.example.myapplication.Generic.Impls.AddPlaylistOrSongImpl;
import com.example.myapplication.Model.Playlist;
import com.example.myapplication.Model.Song;
import com.example.myapplication.R;

import java.util.ArrayList;

public class AddPlaylistBySongDialog extends AlertDialog {

    Activity activity;
    ArrayList<Playlist> playlists;
    ArrayList<Playlist> playlists_by_song;
    Song song;
    ArrayList<AddPlaylistOrSongImpl> addPlaylistOrSongImpls;

    ListView lvPlaylist;
    AddPlaylistBySongAdapter addPlaylistBySongAdapter;
    CheckBox cbSelectAll;
    Button btnSave, btnCancel;


    public AddPlaylistBySongDialog(Activity activity, ArrayList<Playlist> playlists, ArrayList<Playlist> playlists_by_song, Song song) {
        super(activity);
        this.activity = activity;
        this.playlists = playlists;
        this.playlists_by_song = playlists_by_song;
        this.song = song;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_playlist);
        setTitle(activity.getString(R.string.strHeaderPlaylist));
        addControls();
        addEvents();
        initData();
    }

    private void initData() {
    }

    private void addEvents() {
        cbSelectAll.setOnCheckedChangeListener((compoundButton, b) -> {
            addPlaylistOrSongImpls = addPlaylistBySongAdapter.getAddPlaylistImpls();
            int countImpl = addPlaylistOrSongImpls.size();
            for (int i = 0; i < countImpl; i++) {
                addPlaylistOrSongImpls.get(i).setSelectAll(b);
            }
        });
        btnSave.setOnClickListener(view -> addPlaylistBySongAdapter.saveChange());

        btnCancel.setOnClickListener(view -> dismiss());
    }

    private void addControls() {
        lvPlaylist = findViewById(R.id.lvPlaylist);
        addPlaylistBySongAdapter = new AddPlaylistBySongAdapter(activity, R.layout.item_add_playlist, playlists, playlists_by_song, song);
        lvPlaylist.setAdapter(addPlaylistBySongAdapter);
        addPlaylistBySongAdapter.addAll(playlists);
        cbSelectAll = findViewById(R.id.cbSelectAll);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }
}
