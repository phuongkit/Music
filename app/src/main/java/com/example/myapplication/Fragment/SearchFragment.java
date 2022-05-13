package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Activity.PlaybaihatActivity;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.SongDao;
import com.example.myapplication.R;
import com.example.myapplication.Adapter.CustomSongAdapter;
import com.example.myapplication.Module.Song;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    CustomSongAdapter customSongAdapter;
    ListView lvSearch;
    View view;
    ArrayList<Song> songs = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        lvSearch = view.findViewById(R.id.lvSearch);
        customSongAdapter = new CustomSongAdapter(getActivity(), android.R.layout.simple_list_item_1, songs);
        lvSearch.setAdapter(customSongAdapter);
        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), PlaybaihatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("songs", songs);
                bundle.putInt("index", i);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });
        searchFilter("");
        return view;
    }

    public void setBundle() {
        Log.d("Test", "Node G");
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            if (bundle.getString("query") != null) {
                String query = bundle.getString("query");
                Log.d("Info", "Search: " + query);
                query = query.toLowerCase();
                search(query);
            }
            else {
                String filter = bundle.getString("filter");
                Log.d("Info", "Search Filter: " + filter);
                filter = filter.toLowerCase();
                searchFilter(filter);
            }
        }
        else {
            Log.d("Test", "Node K");
        }
    }

    private void search(String query) {
        String textSearch = query;
        Log.d("Test", query);
//        baihats = new ArrayList<>();
        SongDao songDao = new SongDao();
        songDao.getAll(new RetrieValEventListener<List<Song>>() {
            @Override
            public void OnDataRetrieved(List<Song> baihats) {
                songs = new ArrayList<>();
                if (textSearch != null) {
                    for (Song song : baihats) {
                        if (song.getName().toLowerCase().contains(textSearch)
                                || song.getSinger().toLowerCase().contains(textSearch)) {
                            songs.add(song);
                        }
                    }
                    customSongAdapter.clear();
                    customSongAdapter.addAll(songs);
                }
            }
        });
    }

    private void searchFilter(String filter) {
        Log.d("Test", "Node A");
        SongDao songDao = new SongDao();
        songDao.getAll(new RetrieValEventListener<List<Song>>() {
            @Override
            public void OnDataRetrieved(List<Song> Songs) {
                customSongAdapter.clear();
                customSongAdapter.addAll(Songs);
                customSongAdapter.getFilter().filter(filter);
            }
        });
    }
}
