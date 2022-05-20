package com.example.myapplication.Activity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
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
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Adapter.SongOnDeviceAdapter;
import com.example.myapplication.Fragment.MusicFragment;
import com.example.myapplication.Module.Song;
import com.example.myapplication.R;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;

public class SongOnDeviceActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> activityResultLauncher;
    String[] permission ={READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};
    ArrayList<String> mp3File = new ArrayList<String>();
    ListView listView;
    ArrayAdapter<String> adapter;
    ContentResolver contentResolver;
    Cursor cursor;
    Context context;
    String[] array = new String[]{};
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    SongOnDeviceAdapter songOnDeviceAdapter;
    Uri uri;
    ArrayList<Song> songs = new ArrayList<Song>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_on_device);
        listView = findViewById(R.id.listView);
        context = getApplicationContext();
        arrayList = new ArrayList<>(Arrays.asList(array));
        songOnDeviceAdapter = new SongOnDeviceAdapter(SongOnDeviceActivity.this, android.R.layout.simple_list_item_1,songs);
        getPermission();
        getMp3();
//        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult result) {
//                if(result.getResultCode() == Activity.RESULT_OK){
//                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
//                        if (Environment.isExternalStorageManager()){
//                            Toast.makeText(SongOnDeviceActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(SongOnDeviceActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//            }
//        });
//        if(checkPermission()){
//            File directory = new File(String.valueOf(Environment.getExternalStoragePublicDirectory("Download")));
//            File[] mp3Files = directory.listFiles(new FileFilter() {
//                @Override
//                public boolean accept(File file) {
//                    return file.getName().endsWith(".mp3");
//                }
//            });
//            for(File f: mp3Files) {
//                mp3File.add(f.getAbsolutePath());
//            }
//            adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,mp3File);
//            listView.setAdapter(adapter);
//        }else {
//            requestPermission();
//        }

        listView.setAdapter(songOnDeviceAdapter);
//        listView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                MusicFragment musicFragment = new MusicFragment();
//                Bundle bundle = new Bundle();
//                bundle.putInt("TypeMusic", 2);
//                bundle.putSerializable("songs", songs);
//                musicFragment.setArguments(bundle);
//                ft.replace(R.id.framentBaihat, musicFragment);
//                ft.commit();
//            }
//        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SongOnDeviceActivity.this, PlaybaihatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("songs", songs);
                bundle.putInt("index", i);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });
    }

    private void getMp3() {
        contentResolver= context.getContentResolver();
        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String o = MediaStore.Audio.Media.DATA;

        cursor = contentResolver.query(uri, null,null,null,null);
        if(cursor == null){
            Toast.makeText(context, "Sai", Toast.LENGTH_SHORT).show();
        }else if(!cursor.moveToFirst()){
            Toast.makeText(context, "Không tìm thấy", Toast.LENGTH_SHORT).show();
        }else {
            int title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int data = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int id = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
            do {
                Song song = new Song();
                String st = cursor.getString(title);
                String st1 = cursor.getString(data);
                String st2 = cursor.getString(id);
                song.setId(st2);
                song.setName(st);
                song.setLinkSong(st1);
                songs.add(song);
            }while (cursor.moveToNext());

        }
    }

    private void getPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)){
                    AlertDialog.Builder alert = new AlertDialog.Builder(SongOnDeviceActivity.this);
                    alert.setTitle("Warning");
                    alert.setMessage("Cho phép quyền truy cập");
                    alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(SongOnDeviceActivity.this, new String[]{READ_EXTERNAL_STORAGE},30);
                        }
                    });
                    alert.setNegativeButton("cancel", null);
                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                }else {
                    ActivityCompat.requestPermissions(SongOnDeviceActivity.this, new String[]{READ_EXTERNAL_STORAGE},30);
                }
            }
        }
    }

//    private void requestPermission(){
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
//            try {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//                intent.addCategory("android.intent.category.DEFAULT");
//                intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
//                activityResultLauncher.launch(intent);
//            }catch (Exception e){
//                Intent intent = new Intent();
//                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//                activityResultLauncher.launch(intent);
//            }
//        }else{
//            ActivityCompat.requestPermissions(SongOnDeviceActivity.this,permission,30);
//        }
//    }
//    private boolean checkPermission(){
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
//            return Environment.isExternalStorageManager();
//        }
//        else {
//            int readCheck = ContextCompat.checkSelfPermission(getApplicationContext(),READ_EXTERNAL_STORAGE);
//            int writeCheck = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
//            return readCheck == PackageManager.PERMISSION_GRANTED && writeCheck == PackageManager.PERMISSION_GRANTED;
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode){
//            case 30:
//                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED);
//        }
//    }
}