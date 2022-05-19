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


import com.example.myapplication.Adapter.ThemeItemAdapter;
import com.example.myapplication.Adapter.admin.CustomTypesDaoAdapter;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.ThemeDao;
import com.example.myapplication.Module.Theme;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Them extends Fragment {
    View view;
    RecyclerView recyclerView;
    TextView tvTitle;
    RecyclerView.LayoutManager layoutManager;
    CustomTypesDaoAdapter customTypesDaoAdapter;
    Toolbar toolbar;
    ThemeItemAdapter themTypeItemAdapter;
    public ListView lvPlayList;
    ArrayList<Theme> themes=new ArrayList<>();
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
        final Handler handler = new Handler();
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        tvTitle.setText("Theme");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
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
            }
        }, 100);
    }
}


