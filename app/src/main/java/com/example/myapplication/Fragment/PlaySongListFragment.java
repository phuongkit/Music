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

import com.example.myapplication.Activity.PlayMusicActivity;
import com.example.myapplication.Adapter.SongsListAdapter;
import com.example.myapplication.R;

public class PlaySongListFragment extends Fragment {
    View view;
    public RecyclerView recyclerViewPlayMusicList;
    SongsListAdapter playMusicListAdapter;
    int position;

    public PlaySongListFragment(int position) {
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_music_list, container, false);
        recyclerViewPlayMusicList = view.findViewById(R.id.recyclerViewPlayMusicList);

        loaddata(position);
        return view;
    }
    public void loaddata(int position){
        if(PlayMusicActivity.songs.size() > 0){
            playMusicListAdapter = new SongsListAdapter(getActivity(), PlayMusicActivity.songs,position);
            recyclerViewPlayMusicList.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewPlayMusicList.setAdapter(playMusicListAdapter);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
