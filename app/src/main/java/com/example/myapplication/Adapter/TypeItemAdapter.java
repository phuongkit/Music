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
import com.example.myapplication.Model.Types;
import com.example.myapplication.R;

import java.util.ArrayList;

public class TypeItemAdapter extends RecyclerView.Adapter<TypeItemAdapter.ViewHolder> {
    Context context;
    ArrayList<Types> typess;

    public TypeItemAdapter(Context context, ArrayList<Types> typess) {
        this.context = context;
        this.typess = typess;
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
        Types types = typess.get(position);
        holder.tvSongTheme.setText(types.getName());
        Glide.with(context).load(types.getImage()).error(R.drawable.ic_launcher_background).into(holder.imageViewSongtheme);
        holder.imageViewSongtheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ExploreActivity.class);
                intent.putExtra("type", types);
                intent.putExtra("check", "type");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return typess.size();
    }
}
