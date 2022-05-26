package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Activity.PlayMusicActivity;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.SongDao;
import com.example.myapplication.Dao.ThemeDao;
import com.example.myapplication.Dao.TypesDao;
import com.example.myapplication.Generic.GeneralHandling;
import com.example.myapplication.Model.Theme;
import com.example.myapplication.Model.Types;
import com.example.myapplication.R;
import com.example.myapplication.Adapter.CustomSongAdapter;
import com.example.myapplication.Model.Song;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    GeneralHandling generalHandling = new GeneralHandling();

    CustomSongAdapter customSongAdapter;
    ListView lvSearch;
    View view;
    ArrayList<Song> songs = new ArrayList<>();
    ArrayList<Theme> themes = new ArrayList<>();
    ArrayList<Types> typess = new ArrayList<>();

    public SearchFragment() {
        ThemeDao themeDao = new ThemeDao();
        themeDao.getAll(new RetrieValEventListener<List<Theme>>() {
            @Override
            public void OnDataRetrieved(List<Theme> mThemes) {
                themes = (ArrayList<Theme>) mThemes;
            }
        });
        TypesDao typesDao = new TypesDao();
        typesDao.getAll(new RetrieValEventListener<List<Types>>() {
            @Override
            public void OnDataRetrieved(List<Types> mTypess) {
                typess = (ArrayList<Types>) mTypess;
            }
        });
    }

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
                Intent intent = new Intent(getActivity(), PlayMusicActivity.class);
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
        }
    }

    private void search(String query) {
        SongDao songDao = new SongDao();
        songDao.getAll(new RetrieValEventListener<List<Song>>() {
            @Override
            public void OnDataRetrieved(List<Song> mSongs) {
                songs = filterSong((ArrayList<Song>) mSongs, query);
                customSongAdapter.clear();
                customSongAdapter.addAll(songs);
            }
        });
    }

    private ArrayList<Song> filterSong(ArrayList<Song> mSongs, String filterString) {

        final List<Song> list = mSongs;

        int count = list.size(), countTheme = themes.size(), countTypes = typess.size();
        final ArrayList<Song> nlist = new ArrayList<>(count);

        Song filterableSong;
        Theme filterableTheme;
        Types filterableTypes;

        int i, j;

        ArrayList<Song> notList = new ArrayList<>();

        for (i = 0; i < count; i++) {
            filterableSong = list.get(i);
            if (filterableSong.getName().toLowerCase().contains(filterString) || filterableSong.getSinger().toLowerCase().contains(filterString)) {
                nlist.add(filterableSong);
            } else {
                notList.add(filterableSong);
            }
        }

        ArrayList<Theme> filterableThemes = new ArrayList<>();
        ArrayList<Types> filterableTypess = new ArrayList<>();

        for (i = 0; i < countTheme; i++) {
            filterableTheme = themes.get(i);
            if (filterableTheme.getName().toLowerCase().contains(filterString)) {
                filterableThemes.add(filterableTheme);
            }
        }
        for (i = 0; i < countTypes; i++) {
            filterableTypes = typess.get(i);
            if (filterableTypes.getName().toLowerCase().contains(filterString)) {
                filterableTypess.add(filterableTypes);
            }
        }

        int countFilterTheme = filterableThemes.size();
        int countFilterType = filterableTypess.size();

        int maxCount = Math.max(countFilterTheme, countFilterType);

        for (i = 0; i < notList.size(); i++) {
            Song song = notList.get(i);
            for (j = 0; j < maxCount; j++) {
                if ((j < countFilterTheme && song.getIdTheme().equals(filterableThemes.get(j).getId())) ||
                        (j < countFilterType && song.getIdTypes().equals(filterableTypess.get(j).getId()))) {
                    nlist.add(song);
                    break;
                }
            }
        }

        return nlist;
    }
}
