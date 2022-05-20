package com.example.myapplication.Activity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Fragment.MusicFragment;
import com.example.myapplication.Module.Song;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;

public class SongOnDeviceActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> activityResultLauncher;
    String[] permission = {READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};
    ContentResolver contentResolver;
    Cursor cursor;
    Context context;
    String[] array = new String[]{};
    ArrayList<String> arrayList;
    Toolbar toolbar;
    Uri uri;
    ArrayList<Song> songs = new ArrayList<Song>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_on_device);
//        listView = findViewById(R.id.listView);
        context = getApplicationContext();
        arrayList = new ArrayList<>(Arrays.asList(array));
//        songOnDeviceAdapter = new SongOnDeviceAdapter(SongOnDeviceActivity.this, android.R.layout.simple_list_item_1, songs);
        getPermission();
        getMp3();
        initData();
        addControls();
        addEvents();
    }

    private void addEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addControls() {
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        //Toobar đã như ActionBar
        actionBar.setDisplayHomeAsUpEnabled(true);
    }



    private void initData() {
        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        MusicFragment musicFragment = new MusicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("TypeMusic", 2);
        bundle.putSerializable("songs", songs);
        musicFragment.setArguments(bundle);
        ft.replace(R.id.fragmentBaihat, musicFragment);
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
    }

    private void getMp3() {
        contentResolver = context.getContentResolver();
        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String o = MediaStore.Audio.Media.DATA;

        cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null) {
            Toast.makeText(context, "Sai", Toast.LENGTH_SHORT).show();
        } else if (!cursor.moveToFirst()) {
            Toast.makeText(context, "Không tìm thấy", Toast.LENGTH_SHORT).show();
        } else {
            int title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int data = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int key = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int id = 1;
            do {
                Song song = new Song();
                String st = cursor.getString(title);
                String st1 = cursor.getString(data);
                String st2 = cursor.getString(key);
                song.key = st2;
                song.setId(id + "");
                song.setName(st);
                song.setLinkSong(st1);
                songs.add(song);
                id++;
            } while (cursor.moveToNext());

        }
    }

    private void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(SongOnDeviceActivity.this);
                    alert.setTitle("Warning");
                    alert.setMessage("Cho phép quyền truy cập");
                    alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(SongOnDeviceActivity.this, new String[]{READ_EXTERNAL_STORAGE}, 30);
                        }
                    });
                    alert.setNegativeButton("cancel", null);
                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                } else {
                    ActivityCompat.requestPermissions(SongOnDeviceActivity.this, new String[]{READ_EXTERNAL_STORAGE}, 30);
                }
            }
        }
    }
}