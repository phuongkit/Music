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

import com.example.myapplication.Adapter.admin.CustomUserDaoAdapter;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.UserDao;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class UserDaoActivity extends AppCompatActivity {
    CustomUserDaoAdapter customUserDaoAdapter;
    View view;
    Toolbar toolbar;
    public ListView lvPlayList;
    TextView tvTitlePlayList, tvXemThem;
    ArrayList<User> users;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list_item_dao);
        activity = this;
        mapping();
        GetDetail();

        users = new ArrayList<>();
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
                bundle.putString("module", customUserDaoAdapter.getCheck());
                intent.putExtra("bundle", bundle);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void GetDetail() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                users = new ArrayList<>();
                UserDao userDao = new UserDao();
                userDao.getAll(new RetrieValEventListener<List<User>>() {
                    @Override
                    public void OnDataRetrieved(List<User> userss) {
                        users = new ArrayList<>();
                        users = (ArrayList<User>) userss;
                        customUserDaoAdapter = new CustomUserDaoAdapter(activity, android.R.layout.simple_list_item_1, users);
                        customUserDaoAdapter.setCheck(User.class.getName());
                        lvPlayList.setAdapter(customUserDaoAdapter);
                    }
                });
            }
        }, 100);

    }

    private void mapping() {
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        //Toobar đã như ActionBar
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.strHeaderUser);
        lvPlayList = findViewById(R.id.listViewPlayListBaihatDao);
        tvTitlePlayList = findViewById(R.id.tvTitlePlayListBaihatDao);
//        tvXemThem = findViewById(R.id.tvMorePlayListBaihatDao);
    }

}
