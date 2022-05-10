package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.Module.Song;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomBaihatAdapter extends ArrayAdapter<Song> {

    public CustomBaihatAdapter(Context context, int resource,List<Song> objects) {
        super(context, resource, objects);
    }
    class ViewHolder{
    TextView tvMusicListIndex,tvTenBaiHatMusicList,tvTenCaSiMusicList;
    ImageView imgTopsong;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.baihat_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvMusicListIndex = convertView.findViewById(R.id.tvMusicListIndex);
            viewHolder.tvTenBaiHatMusicList = convertView.findViewById(R.id.tvTenBaiHatMusicList);
            viewHolder.tvTenCaSiMusicList = convertView.findViewById(R.id.tvTenCaSiMusicList);
            viewHolder.imgTopsong = convertView.findViewById(R.id.imageViewtop);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Song song = getItem(position);
        Picasso.with(getContext()).load(song.getImage()).into(viewHolder.imgTopsong);

        viewHolder.tvMusicListIndex.setText(String.valueOf(song.getId()));
        viewHolder.tvTenBaiHatMusicList.setText(song.getName());
        viewHolder.tvTenCaSiMusicList.setText(song.getSinger());

        return convertView;
    }
}
