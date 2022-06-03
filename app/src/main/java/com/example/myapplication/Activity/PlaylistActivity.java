package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Adapter.PlaylistAdapter;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.PlaylistDao;
import com.example.myapplication.Dialog.InputDialog;
import com.example.myapplication.Model.Playlist;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtTitleLibary;
    ListView lvDataPlaylist;
    PlaylistAdapter playlistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        addControls();
        addEvents();
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
        actionBar.setTitle(R.string.strHeaderPlaylist);

        txtTitleLibary = findViewById(R.id.txtTitleLibary);
        lvDataPlaylist = findViewById(R.id.lvDataPlaylist);
        playlistAdapter = new PlaylistAdapter(PlaylistActivity.this, R.layout.item_playlist);
        lvDataPlaylist.setAdapter(playlistAdapter);
//        FirebaseAuth auth = FirebaseAuth.getInstance();
        initData();
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
                InputDialog inputDialog = new InputDialog();
                Bundle bundle = new Bundle();
                bundle.putBoolean("isUpdate", false);
                inputDialog.setArguments(bundle);
                inputDialog.show(getSupportFragmentManager(), "input dialog");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            PlaylistDao playlistDao = new PlaylistDao();
            playlistDao.getAll(new RetrieValEventListener<List<Playlist>>() {
                @Override
                public void OnDataRetrieved(List<Playlist> playlists) {
                    ArrayList<Playlist> playlists1 = new ArrayList<>();
                    for (Playlist playlist : playlists) {
                        if (playlist.getUid().equals(uid)) {
                            playlists1.add(playlist);
                        }
                    }
                    playlistAdapter.clear();
                    playlistAdapter.addAll(playlists1);
                }
            });
        } else {
            Toast.makeText(this, "No user", Toast.LENGTH_SHORT).show();
        }
    }
}