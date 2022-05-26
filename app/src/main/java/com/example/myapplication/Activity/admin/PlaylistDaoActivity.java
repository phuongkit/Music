package com.example.myapplication.Activity.admin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Adapter.admin.CustomPlaylistDaoAdapter;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.PlaylistDao;
import com.example.myapplication.Model.Playlist;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class PlaylistDaoActivity extends AppCompatActivity {
    CustomPlaylistDaoAdapter customPlaylistDaoAdapter;
    Toolbar toolbar;
    ListView lvPlayList;
    TextView tvTitlePlayList;
    ArrayList<Playlist> playlists;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list_item_dao);
        activity = this;
        mapping();
        GetDetail();

        playlists = new ArrayList<>();
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
                String control = "add";
                Intent intent = new Intent(this, CRUDDaoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("control", control);
                bundle.putString("module", customPlaylistDaoAdapter.getCheck());
                intent.putExtra("bundle", bundle);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void load() {
        lvPlayList.getAdapter().notify();
    }

    private void GetDetail() {
        playlists = new ArrayList<>();
        PlaylistDao playlistDao = new PlaylistDao();
        playlistDao.getAll(new RetrieValEventListener<List<Playlist>>() {
            @Override
            public void OnDataRetrieved(List<Playlist> playlists1) {
                playlists = new ArrayList<>();
                playlists = (ArrayList<Playlist>) playlists1;
                customPlaylistDaoAdapter = new CustomPlaylistDaoAdapter(activity, android.R.layout.simple_list_item_1, playlists);
                customPlaylistDaoAdapter.setCheck(Playlist.class.getName());
                lvPlayList.setAdapter(customPlaylistDaoAdapter);
            }
        });

    }

    private void mapping() {
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        //Toobar đã như ActionBar
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.strHeaderPlaylist);

        lvPlayList = findViewById(R.id.listViewPlayListBaihatDao);
        tvTitlePlayList = findViewById(R.id.tvTitlePlayListBaihatDao);
    }
}
