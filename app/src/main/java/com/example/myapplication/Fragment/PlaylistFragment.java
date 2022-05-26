package com.example.myapplication.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.PlaylistItemAdapter;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.PlaylistDao;
import com.example.myapplication.Model.Playlist;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class PlaylistFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    TextView tvTitle;
    RecyclerView.LayoutManager layoutManager;
    PlaylistItemAdapter playlistItemAdapter;

    public ListView lvPlayList;
    ArrayList<Playlist> playlists=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fame_theme_type_album_playlist, container, false);
        bindingView();
        GetDetail();
        return view;
    }


    private void bindingView() {
        recyclerView = view.findViewById(R.id.recyclerView);
        tvTitle = view.findViewById(R.id.tvTitle);
    }
    @SuppressLint("SetTextI18n")
    private void GetDetail() {
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        tvTitle.setText(getString(R.string.strHeaderPlaylist));
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
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
        }, 100);


    }

    }
