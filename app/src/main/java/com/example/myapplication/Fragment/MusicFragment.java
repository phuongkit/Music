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

import com.example.myapplication.Dao.SongDao;
import com.example.myapplication.Dao.Listeners.RetrievalEventListener;
import com.example.myapplication.Activity.PlaybaihatActivity;
import com.example.myapplication.Dao.Playlist_SongDao;
import com.example.myapplication.Module.Playlist;
import com.example.myapplication.Module.Playlist_Song;
import com.example.myapplication.Module.Song;
import com.example.myapplication.R;
import com.example.myapplication.Adapter.CustomBaihatAdapter;

import java.util.ArrayList;
import java.util.List;

public class MusicFragment extends Fragment {
    CustomBaihatAdapter customBaihatAdapter;
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
        songs =new ArrayList<>();
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
        songs =new ArrayList<>();
        int typeListMusic = bundle.getInt("TypeMusic", HOME);
        switch (typeListMusic) {
            case HOME:
                getListMusicByHome();
                break;
            case PLAYLIST:
                getListMusicByPlaylist();
                break;
            default:
                break;
        }
    }

    private void getListMusicByHome() {
        SongDao songDao = new SongDao();
        songDao.getAll(new RetrievalEventListener<List<Song>>() {
            @Override
            public void OnDataRetrieved(List<Song> Songs) {
                Log.d("DAO",Songs.toString());
                songs = new ArrayList<>();
                songs = (ArrayList<Song>) Songs;
                customBaihatAdapter = new CustomBaihatAdapter(getActivity(),android.R.layout.simple_list_item_1, songs);
                lvPlayList.setAdapter(customBaihatAdapter);
            }
        });
    }

    private void getListMusicByPlaylist() {
        playlist = (Playlist) bundle.getSerializable("playlist");
        Playlist_SongDao playlist_songDao = new Playlist_SongDao();
        playlist_songDao.getAll(new RetrievalEventListener<List<Playlist_Song>>() {
            @Override
            public void OnDataRetrieved(List<Playlist_Song> Playlist_songs) {
                ArrayList<String> musics = new ArrayList<>();
                for (Playlist_Song playlist_song :  Playlist_songs) {
                    if (playlist_song.getIdPlaylist().equals(playlist.getId())) {
                        musics.add(playlist_song.getIdSong());
                    }
                }
                if (musics.size() > 0) {
                    getListMusicByPlaylistt(musics);
                }
            }
        });
    }

    private void getListMusicByPlaylistt(final ArrayList<String> musics) {
        SongDao songDao = new SongDao();
        songDao.getAll(new RetrievalEventListener<List<Song>>() {
            @Override
            public void OnDataRetrieved(List<Song> Songs) {
                songs = new ArrayList<>();
                for (Song song : Songs) {
                    for (String id : musics) {
                        if (song.getId().equals(id)) {
                            Log.d("Test", "Playlist:" + id);
                            songs.add(song);
                            musics.remove(id);
                            break;
                        }
                    }
                }
                Log.d("DAO","get Playlist " + playlist.getName() + ": "  + songs.toString());
                customBaihatAdapter = new CustomBaihatAdapter(getActivity(),android.R.layout.simple_list_item_1, songs);
                lvPlayList.setAdapter(customBaihatAdapter);
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