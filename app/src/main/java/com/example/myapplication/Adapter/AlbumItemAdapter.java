package com.example.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Activity.ExploreActivity;
import com.example.myapplication.Module.Album;
import com.example.myapplication.R;

import java.util.ArrayList;

public class AlbumItemAdapter extends RecyclerView.Adapter<AlbumItemAdapter.ViewHolder> {
    Context context;
    ArrayList<Album> albums;

    public AlbumItemAdapter(Context context, ArrayList<Album> albums) {
        this.context = context;
        this.albums = albums;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewSongtheme;
        TextView tvSongTheme;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewSongtheme = itemView.findViewById(R.id.imageTypes);
            tvSongTheme = itemView.findViewById(R.id.nameTypes);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_theme_type_album_playlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Album album = albums.get(position);
        holder.tvSongTheme.setText(album.getName());
        Glide.with(context).load(album.getImage()).error(R.drawable.ic_launcher_background).into(holder.imageViewSongtheme);
        holder.imageViewSongtheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ExploreActivity.class);
                intent.putExtra("album", album);
                intent.putExtra("check", "album");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }
}
