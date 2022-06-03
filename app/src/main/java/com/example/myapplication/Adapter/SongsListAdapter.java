package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Fragment.PlaySongListFragment;
import com.example.myapplication.Model.Song;
import com.example.myapplication.R;

import java.util.ArrayList;

public class SongsListAdapter extends RecyclerView.Adapter<SongsListAdapter.ViewHolder> {
    Context context;
    ArrayList<Song> baiHatArrayList;
    int position2;

    public SongsListAdapter(Context context, ArrayList<Song> baiHatArrayList, int position2) {
        this.context = context;
        this.baiHatArrayList = baiHatArrayList;
        this.position2 = position2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_play_music_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song baiHat = baiHatArrayList.get(position);
        holder.tvSinger.setText(baiHat.getSinger());
        holder.tvSongName.setText(baiHat.getName());
        holder.tvPlayMusicListIndex.setText(position + 1 + "");
        holder.select_song2.setVisibility(View.INVISIBLE);
        if(position==position2){
            holder.select_song2.setVisibility(View.VISIBLE);
        }
//        getItem(position);
//        holder.row_linearlayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    row_index=position;
//                    notifyDataSetChanged();
//                }
//            });
//            if(row_index==position){
//                holder.row_linearlayout.setBackgroundColor(Color.parseColor("#567845"));
//                holder.tvSongName.setTextColor(Color.parseColor("#ffffff"));
//            }
//            else
//            {
//                holder.row_linearlayout.setBackgroundColor(Color.parseColor("#ffffff"));
//                holder.tvSongName.setTextColor(Color.parseColor("#000000"));
//            }
    }
    public Fragment getItem(int position) {
        return new PlaySongListFragment(position);
    }
    @Override
    public int getItemCount() {
        return baiHatArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvPlayMusicListIndex, tvSongName, tvSinger,select_song2;
        ImageView select_song;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlayMusicListIndex = itemView.findViewById(R.id.tvPlayMusicListIndex);
            tvSongName = itemView.findViewById(R.id.tvPlayMusicList_SongName);
            tvSinger = itemView.findViewById(R.id.tvPlayMusicList_Singer);
            relativeLayout = itemView.findViewById(R.id.select_background);
            select_song2 = itemView.findViewById(R.id.select_song2);
        }
    }
}
