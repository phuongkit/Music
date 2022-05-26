package com.example.myapplication.Activity.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import com.example.myapplication.Adapter.admin.CustomAlbumDaoAdapter;
import com.example.myapplication.Dao.AlbumDao;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Model.Album;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class AlbumDaoActivity extends AppCompatActivity {
    CustomAlbumDaoAdapter customAlbumDaoAdapter;
    Toolbar toolbar;
    View view;
    public ListView lvPlayList;
    TextView tvTitlePlayList, tvXemThem;
    ArrayList<Album> baihats;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list_item_dao);
        activity=this;
        mapping();
        GetDetail();

        baihats =new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.playlist_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Handler handler = new Handler();
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menuInsert:
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String control="add";
                        Intent intent = new Intent(AlbumDaoActivity.this, CRUDDaoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("control", control);
                        bundle.putString("module",customAlbumDaoAdapter.getCheck());
                        intent.putExtra("bundle", bundle);
                        startActivity(intent);
                    }
                }, 500);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void GetDetail() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                baihats =new ArrayList<>();
                AlbumDao baiHatDao = new AlbumDao();
                baiHatDao.getAll(new RetrieValEventListener<List<Album>>() {
                    @Override
                    public void OnDataRetrieved(List<Album> baiHats) {
                        baihats = new ArrayList<>();
                        baihats = (ArrayList<Album>) baiHats;
                        System.out.println(baiHats.toString());
                        customAlbumDaoAdapter = new CustomAlbumDaoAdapter(activity,android.R.layout.simple_list_item_1, baihats);
                        customAlbumDaoAdapter.setCheck(Album.class.getName());
                        lvPlayList.setAdapter(customAlbumDaoAdapter);
                    }
                });
            }
        }, 1000);

    }

    private void mapping() {
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        //Toobar đã như ActionBar
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.strHeaderAlbum);
        lvPlayList = findViewById(R.id.listViewPlayListBaihatDao);
        tvTitlePlayList = findViewById(R.id.tvTitlePlayListBaihatDao);
//        tvXemThem = findViewById(R.id.tvMorePlayListBaihatDao);
    }
}
