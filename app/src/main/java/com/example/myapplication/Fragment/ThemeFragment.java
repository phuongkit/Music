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


import com.example.myapplication.Adapter.ThemeItemAdapter;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.ThemeDao;
import com.example.myapplication.Model.Theme;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class ThemeFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    TextView tvTitle;
    RecyclerView.LayoutManager layoutManager;
    ThemeItemAdapter themTypeItemAdapter;
    public ListView lvPlayList;
    ArrayList<Theme> themes=new ArrayList<>();

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
        final Handler handler = new Handler();
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        tvTitle.setText(getString(R.string.strHeaderTheme));
        handler.postDelayed(() -> {
            ThemeDao baiHatDao = new ThemeDao();
            baiHatDao.getAll(new RetrieValEventListener<List<Theme>>() {
                @Override
                public void OnDataRetrieved(List<Theme> Themes) {
                    themes = new ArrayList<>();
                    themes = (ArrayList<Theme>) Themes;
                    themTypeItemAdapter = new ThemeItemAdapter(getActivity(), themes);
                    recyclerView.setAdapter(themTypeItemAdapter);
                }
            });
        }, 100);
    }
}


