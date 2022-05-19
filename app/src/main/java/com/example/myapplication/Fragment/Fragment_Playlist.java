package com.example.myapplication.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.PlaylistItemAdapter;
import com.example.myapplication.Adapter.admin.CustomTypesDaoAdapter;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.PlaylistDao;
import com.example.myapplication.Module.Playlist;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Playlist extends Fragment {
    View view;
    RecyclerView recyclerView;
    TextView tvTitle;
    RecyclerView.LayoutManager layoutManager;
    PlaylistItemAdapter playlistItemAdapter;
    CustomTypesDaoAdapter customTypesDaoAdapter;
    Toolbar toolbar;
    RecyclerView adapter;

    public ListView lvPlayList;
    ArrayList<Playlist> playlists=new ArrayList<>();
    Activity activity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fame_theme_type_album_playlist, container, false);
        bindingView();
        GetDetail();
        return view;
    }


    private void bindingView() {
        recyclerView = view.findViewById(R.id.recyclerView);
        tvTitle = view.findViewById(R.id.tvTitle);
    }
    private void GetDetail() {
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
//        horizontalScrollView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        tvTitle.setText("Playlist");
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<Playlist> playlistsTrue = new ArrayList<>();
                PlaylistDao baiHatDao = new PlaylistDao();
                baiHatDao.getAll(new RetrieValEventListener<List<Playlist>>() {
                    @Override
                    public void OnDataRetrieved(List<Playlist> Playlists) {
                        for (Playlist playlist : Playlists) {
                            if (playlist.isAdmin()) {
                                playlistsTrue.add((playlist));
                            }
                        }
                        playlists = (ArrayList<Playlist>) Playlists;
                        playlistItemAdapter = new PlaylistItemAdapter(getActivity(),playlistsTrue);
                        recyclerView.setAdapter(playlistItemAdapter);
                    }
                });
            }
        }, 100);


    }

    }
