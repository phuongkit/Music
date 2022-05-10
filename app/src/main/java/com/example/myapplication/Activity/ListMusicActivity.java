package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.myapplication.Fragment.MusicFragment;
import com.example.myapplication.Module.Playlist;
import com.example.myapplication.R;

public class ListMusicActivity extends AppCompatActivity {

    Toolbar toolbar;

    Playlist playlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_music);
        initData();
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
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(playlist.getName());

        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        MusicFragment musicFragment = new MusicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("TypeMusic", 1);
        bundle.putSerializable("playlist", playlist);
        musicFragment.setArguments(bundle);
        ft.replace(R.id.fragmentBaihat, musicFragment);
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        playlist = (Playlist) bundle.getSerializable("playlist");
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

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}