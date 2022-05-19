package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.PlaybaihatActivity;
import com.example.myapplication.Adapter.SongsListAdapter;
import com.example.myapplication.R;

public class Fragment_PlaySongList extends Fragment {
    View view;
    int index;
    public RecyclerView recyclerViewPlayMusicList;
    SongsListAdapter playMusicListAdapter;
    int position;

    public Fragment_PlaySongList(int position) {
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_music_list, container, false);
        recyclerViewPlayMusicList = view.findViewById(R.id.recyclerViewPlayMusicList);

//        recyclerViewPlayMusicList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                index=position;
//                recyclerViewPlayMusicList.setBackgroundColor(Color.parseColor("#567845"));
//            }
//        });

//        if(index==position){
//            recyclerViewPlayMusicList.setBackgroundColor(Color.parseColor("#567845"));
////            recyclerViewPlayMusicList.tvSongName.setTextColor(Color.parseColor("#ffffff"));
//        }
//        else
//        {
//            recyclerViewPlayMusicList.setBackgroundColor(Color.parseColor("#ffffff"));
////            holder.tvSongName.setTextColor(Color.parseColor("#000000"));
//        }
        loaddata(position);
        return view;
    }
    public void loaddata(int position){
        if(PlaybaihatActivity.songs.size() > 0){
            playMusicListAdapter = new SongsListAdapter(getActivity(), PlaybaihatActivity.songs,position);
            recyclerViewPlayMusicList.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewPlayMusicList.setAdapter(playMusicListAdapter);
        }
//        recyclerViewPlayMusicList.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
