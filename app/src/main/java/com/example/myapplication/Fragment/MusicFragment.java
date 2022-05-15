package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.SongDao;
import com.example.myapplication.Activity.PlaybaihatActivity;
import com.example.myapplication.Dao.Playlist_SongDao;
import com.example.myapplication.Module.Playlist;
import com.example.myapplication.Module.Playlist_Song;
import com.example.myapplication.Module.Song;
import com.example.myapplication.R;
import com.example.myapplication.Adapter.CustomSongAdapter;

import java.util.ArrayList;
import java.util.List;

public class MusicFragment extends Fragment {
    CustomSongAdapter customSongAdapter;
    View view;
    ListView lvPlayList;
    TextView tvTitlePlayList, tvXemThem;
    ArrayList<Song> songs;

    Playlist playlist = new Playlist();
    Bundle bundle;
    private static final int HOME = 0;
    private static final int PLAYLIST = 1;

    public MusicFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_music, container, false);
        mapping();
        GetDetail();
        songs = new ArrayList<>();
        lvPlayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    private void GetDetail() {
        songs = new ArrayList<>();
        int typeListMusic = bundle.getInt("TypeMusic", HOME);
        switch (typeListMusic) {
            case HOME:
                getListMusicByHome();
                break;
            case PLAYLIST:
                playlist = (Playlist) bundle.getSerializable("playlist");
                getListMusicByPlaylist(playlist);
                break;
            default:
                break;
        }
    }

    private void getListMusicByHome() {
        SongDao songDao = new SongDao();
        songDao.getAll(new RetrieValEventListener<List<Song>>() {
            @Override
            public void OnDataRetrieved(List<Song> Songs) {
                Log.d("DAO", Songs.toString());
                songs = new ArrayList<>();
                songs = (ArrayList<Song>) Songs;
                customSongAdapter = new CustomSongAdapter(getActivity(), android.R.layout.simple_list_item_1, songs);
                lvPlayList.setAdapter(customSongAdapter);
            }
        });
    }

    public void getListMusicByPlaylist(Playlist Playlist) {
        Playlist_SongDao playlist_songDao = new Playlist_SongDao();
        playlist_songDao.getAll(new RetrieValEventListener<List<Playlist_Song>>() {
            @Override
            public void OnDataRetrieved(List<Playlist_Song> Playlist_songs) {
                ArrayList<String> song_ids = new ArrayList<>();
                for (Playlist_Song playlist_song : Playlist_songs) {
                    if (playlist_song.getIdPlaylist().equals(Playlist.getId())) {
                        song_ids.add(playlist_song.getIdSong());
                    }
                }
                if (song_ids.size() > 0) {
                    getListMusicByPlaylistt(Playlist, song_ids);
                }
            }
        });
    }

    private void getListMusicByPlaylistt(Playlist Playlist, final ArrayList<String> song_ids) {
        SongDao songDao = new SongDao();
        songDao.getAll(new RetrieValEventListener<List<Song>>() {
            @Override
            public void OnDataRetrieved(List<Song> Songs) {
                songs = new ArrayList<>();
                for (String id : song_ids) {
                    for (Song song : Songs) {
                        if (song.getId().equals(id)) {
                            songs.add(song);
                            break;
                        }
                    }
                }
                Log.d("DAO", "get Playlist " + Playlist.getName() + ": " + songs.toString());
                customSongAdapter = new CustomSongAdapter(getActivity(), android.R.layout.simple_list_item_1, songs, Playlist, MusicFragment.this);
                lvPlayList.setAdapter(customSongAdapter);
            }
        });
    }

    private void mapping() {
        lvPlayList = view.findViewById(R.id.listViewPlayList);
//        tvTitlePlayList = view.findViewById(R.id.tvTitlePlayList);
//        tvXemThem = view.findViewById(R.id.tvMorePlayList);

        bundle = getArguments();
    }
}