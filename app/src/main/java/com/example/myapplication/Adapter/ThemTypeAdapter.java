package com.example.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.myapplication.Activity.PlayMusicActivity;
import com.example.myapplication.Model.Song;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ThemTypeAdapter extends RecyclerView.Adapter<ThemTypeAdapter.ViewHolder> {
    Context context;
    ArrayList<Song> songs;

    public ThemTypeAdapter(Context context, ArrayList<Song> songs) {
        this.context = context;
        this.songs = songs;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewSongtheme;
        TextView tvSongTheme,tvSingerTheme;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewSongtheme = itemView.findViewById(R.id.imageViewSongthem);
            tvSongTheme = itemView.findViewById(R.id.tvSongThem);
            tvSingerTheme =itemView.findViewById(R.id.tvSingerThem);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_songthems, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Song songThem = songs.get(position);
        holder.tvSongTheme.setText(songThem.getName());
        holder.tvSingerTheme.setText(songThem.getSinger());
        Glide.with(context).load(songThem.getImage()).error(R.drawable.ic_launcher_background).into(holder.imageViewSongtheme);
        holder.imageViewSongtheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlayMusicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("songs", songs);
                bundle.putInt("index", position);
                intent.putExtra("bundle", bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
}
