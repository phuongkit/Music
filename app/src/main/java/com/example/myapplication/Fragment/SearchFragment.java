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
import com.example.myapplication.Dao.SongDao;
import com.example.myapplication.Dao.Listeners.RetrievalEventListener;
import com.example.myapplication.R;
import com.example.myapplication.Adapter.CustomBaihatAdapter;
import com.example.myapplication.Module.Song;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    CustomBaihatAdapter customBaihatAdapter;
    ListView lvSearch;
    View view;
    ArrayList<Song> songs = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        lvSearch = view.findViewById(R.id.lvSearch);
        customBaihatAdapter = new CustomBaihatAdapter(getActivity(), android.R.layout.simple_list_item_1, songs);
        lvSearch.setAdapter(customBaihatAdapter);
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
        return view;
    }

    public void setBundle() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            if (bundle.getString("query") != null) {
                String query = bundle.getString("query");
                Log.d("Info", "Search: " + query);
                query = query.toLowerCase();
                search(query);
            }
            else {
                Log.d("Test", "nodeB");
                String filter = bundle.getString("filter");
                Log.d("Info", "Search Filter: " + filter);
                filter = filter.toLowerCase();
                searchFilter(filter);
            }
        }
    }

    private void search(String query) {
        String textSearch = query;
//        baihats = new ArrayList<>();
        SongDao songDao = new SongDao();
        songDao.getAll(new RetrievalEventListener<List<Song>>() {
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
                    customBaihatAdapter.clear();
                    customBaihatAdapter.addAll(songs);
                }
            }
        });
    }

    private void searchFilter(String filter) {
        String textSearch = filter;
//        baihats = new ArrayList<>();
        SongDao songDao = new SongDao();
        songDao.getAll(new RetrievalEventListener<List<Song>>() {
            @Override
            public void OnDataRetrieved(List<Song> Songs) {
                customBaihatAdapter.clear();
                customBaihatAdapter.addAll(Songs);
                customBaihatAdapter.getFilter().filter(filter);

            }
        });
    }
}
