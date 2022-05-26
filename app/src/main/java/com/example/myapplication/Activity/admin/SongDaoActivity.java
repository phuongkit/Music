package com.example.myapplication.Activity.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Adapter.admin.CustomSongDaoAdapter;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.SongDao;
import com.example.myapplication.Model.Song;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class SongDaoActivity extends AppCompatActivity {
    CustomSongDaoAdapter customSongDaoAdapter;
    Toolbar toolbar;
    View view;
    ListView lvPlayList;
    TextView tvTitlePlayList, tvXemThem;
    ArrayList<Song> songs;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list_item_dao);
        activity = this;
        mapping();
        GetDetail();

        songs = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.playlist_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

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
                bundle.putString("module", customSongDaoAdapter.getCheck());
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
        songs = new ArrayList<>();
        SongDao songDao = new SongDao();
        songDao.getAll(new RetrieValEventListener<List<Song>>() {
            @Override
            public void OnDataRetrieved(List<Song> Songs) {
                songs = new ArrayList<>();
                songs = (ArrayList<Song>) Songs;
                customSongDaoAdapter = new CustomSongDaoAdapter(activity, android.R.layout.simple_list_item_1, songs);
                customSongDaoAdapter.setCheck(Song.class.getName());
                lvPlayList.setAdapter(customSongDaoAdapter);
            }
        });

    }

    private void mapping() {
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        //Toobar đã như ActionBar
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.strHeaderSong);

        lvPlayList = findViewById(R.id.listViewPlayListBaihatDao);
        tvTitlePlayList = findViewById(R.id.tvTitlePlayListBaihatDao);
//        tvXemThem = findViewById(R.id.tvMorePlayListBaihatDao);
    }
}
