package com.example.myapplication.Fragment;

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

import com.example.myapplication.Adapter.TypeItemAdapter;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.TypesDao;
import com.example.myapplication.Model.Types;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class TypesFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    TextView tvTitle;
    RecyclerView.LayoutManager layoutManager;
    TypeItemAdapter themTypeItemAdapter;

    public ListView lvPlayList;
    ArrayList<Types> typess=new ArrayList<>();
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
    private void GetDetail() {
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
//        horizontalScrollView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        tvTitle.setText(getString(R.string.strHeaderTypes));
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            TypesDao baiHatDao = new TypesDao();
            baiHatDao.getAll(new RetrieValEventListener<List<Types>>() {
                @Override
                public void OnDataRetrieved(List<Types> Typess) {
                    typess = (ArrayList<Types>) Typess;
                    themTypeItemAdapter = new TypeItemAdapter(getActivity(),typess);
                    recyclerView.setAdapter(themTypeItemAdapter);
                }
            });
        }, 100);


    }

    }
