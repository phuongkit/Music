package com.example.myapplication.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.myapplication.Adapter.AddPlaylistAdapter;
import com.example.myapplication.Module.Playlist;
import com.example.myapplication.Module.Song;
import com.example.myapplication.R;
import com.example.myapplication.Module.Impls.AddPlaylistImpl;

import java.util.ArrayList;

public class Add_Playlist_Dialog extends AlertDialog {

    Activity activity;
    View view;
    ArrayList<Playlist> playlists;
    ArrayList<Playlist> playlists_by_song;
    Song song;
    ArrayList<AddPlaylistImpl> addPlaylistImpls;

    ListView lvPlaylist;
    AddPlaylistAdapter addPlaylistAdapter;
    CheckBox cbSelectAll;
    ImageButton imgBtnSave, imgBtnClose;


    public Add_Playlist_Dialog(Activity activity, ArrayList<Playlist> playlists, ArrayList<Playlist> playlists_by_song, Song song) {
        super(activity);
        this.activity = activity;
        this.playlists = playlists;
        this.playlists_by_song = playlists_by_song;
        this.song = song;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = inflater.inflate(R.layout.add_playlist_dialog, null);
//        view = this;
        setContentView(R.layout.add_playlist_dialog);
        setTitle(activity.getString(R.string.strHeaderPlaylist));
//        int paddingDpWidth = 400;
//        int paddingDpHeight = 600;
//        float density = activity.getResources().getDisplayMetrics().density;
//        int paddingWidth = (int) (paddingDpWidth * density);
//        int paddingHeight = (int) (paddingDpHeight * density);
//        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.90);
//        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.90);
//
//        getWindow().setLayout(width, height);
        addControls();
        addEvents();
        initData();
    }

    private void initData() {
    }

    private void addEvents() {
        cbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                addPlaylistImpls = addPlaylistAdapter.addPlaylistImpls;
                int countImpl = addPlaylistImpls.size();
                for (int i = 0; i < countImpl; i++) {
                    addPlaylistImpls.get(i).setSelectAll(b);
                }
            }
        });
        imgBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPlaylistAdapter.saveChange();
            }
        });

        imgBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void addControls() {
        lvPlaylist = findViewById(R.id.lvPlaylist);
        addPlaylistAdapter = new AddPlaylistAdapter(activity, R.layout.add_playlist_item, playlists, playlists_by_song, song);
        lvPlaylist.setAdapter(addPlaylistAdapter);
        addPlaylistAdapter.addAll(playlists);
        cbSelectAll = findViewById(R.id.cbSelectAll);
        imgBtnSave = findViewById(R.id.imgBtnSave);
        imgBtnClose = findViewById(R.id.imgBtnClose);
    }
}
