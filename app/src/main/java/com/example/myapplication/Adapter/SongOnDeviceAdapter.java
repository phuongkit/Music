package com.example.myapplication.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Module.Song;
import com.example.myapplication.R;

import java.util.ArrayList;

public class SongOnDeviceAdapter extends ArrayAdapter<Song> {
    TextView tvMusicListIndex, tvTenBaiHatMusicList;
    ImageView imageViewtop;
    ArrayList<Song> objects;
    public SongOnDeviceAdapter(Activity activity, int resource, ArrayList<Song> objects) {
        super(activity, resource, objects);
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.song_on_device_item, null);
        tvTenBaiHatMusicList = convertView.findViewById(R.id.tvTenBaiHatMusicList);
        Song song = getItem(position);
        tvTenBaiHatMusicList.setText(song.getName());
        return convertView;
    }
}
