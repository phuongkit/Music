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

import com.example.myapplication.Adapter.AlbumItemAdapter;
import com.example.myapplication.Dao.AlbumDao;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Model.Album;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class AlbumFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    TextView tvTitle;
    RecyclerView.LayoutManager layoutManager;
    AlbumItemAdapter albumItemAdapter;

    public ListView lvPlayList;
    ArrayList<Album> albums = new ArrayList<>();

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
        tvTitle.setText(getString(R.string.strHeaderAlbum));
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            AlbumDao baiHatDao = new AlbumDao();
            baiHatDao.getAll(new RetrieValEventListener<List<Album>>() {
                @Override
                public void OnDataRetrieved(List<Album> Albums) {
                    albums = (ArrayList<Album>) Albums;
                    albumItemAdapter = new AlbumItemAdapter(getActivity(), albums);
                    recyclerView.setAdapter(albumItemAdapter);
                }
            });
        }, 100);
    }
}
