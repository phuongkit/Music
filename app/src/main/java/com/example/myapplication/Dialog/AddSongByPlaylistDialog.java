package com.example.myapplication.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.myapplication.Adapter.AddSongByPlaylistAdapter;
import com.example.myapplication.Generic.Impls.AddPlaylistOrSongImpl;
import com.example.myapplication.Model.Playlist;
import com.example.myapplication.Model.Song;
import com.example.myapplication.R;

import java.util.ArrayList;

public class AddSongByPlaylistDialog extends AlertDialog {

    Activity activity;
    ArrayList<Song> songs;
    ArrayList<Song> songByPlaylists;
    Playlist playlist;
    ArrayList<AddPlaylistOrSongImpl> addPlaylistOrSongImpls;

    ListView lvPlaylist;
    AddSongByPlaylistAdapter addSongByPlaylistAdapter;
    CheckBox cbSelectAll;
    Button btnSave, btnCancel;
    Boolean isAdd = false;


    public AddSongByPlaylistDialog(Activity activity, ArrayList<Song> songs, ArrayList<Song> songByPlaylists, Playlist playlist) {
        super(activity);
        this.activity = activity;
        this.songs = songs;
        this.songByPlaylists = songByPlaylists;
        this.playlist = playlist;
    }

    public AddSongByPlaylistDialog(Activity activity, ArrayList<Song> songs, ArrayList<Song> songByPlaylists) {
        super(activity);
        this.activity = activity;
        this.songs = songs;
        this.songByPlaylists = songByPlaylists;
        isAdd = true;
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
            addPlaylistOrSongImpls = addSongByPlaylistAdapter.getAddPlaylistImpls();
            int countImpl = addPlaylistOrSongImpls.size();
            for (int i = 0; i < countImpl; i++) {
                addPlaylistOrSongImpls.get(i).setSelectAll(b);
            }
        });
        btnSave.setOnClickListener(view -> addSongByPlaylistAdapter.saveChange());

        btnCancel.setOnClickListener(view -> dismiss());
    }

    private void addControls() {
        lvPlaylist = findViewById(R.id.lvPlaylist);
        if (isAdd) {
            addSongByPlaylistAdapter = new AddSongByPlaylistAdapter(activity, R.layout.item_add_playlist, songs, songByPlaylists);
        }
        else {
            addSongByPlaylistAdapter = new AddSongByPlaylistAdapter(activity, R.layout.item_add_playlist, songs, songByPlaylists, playlist);
        }
        lvPlaylist.setAdapter(addSongByPlaylistAdapter);
        addSongByPlaylistAdapter.addAll(songs);
        cbSelectAll = findViewById(R.id.cbSelectAll);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }
}