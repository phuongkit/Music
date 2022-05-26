package com.example.myapplication.Dao;

import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Model.Playlist_Song;
import com.example.myapplication.Model.Song;
import com.example.myapplication.Model.Theme;
import com.example.myapplication.Model.Types;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SongDao extends FirebaseDao<Song> {
    public SongDao() {
        // Specify the table name for the class
        super("song");
    }

    @Override
    protected void parseDataSnapshot(DataSnapshot dataSnapshot, RetrieValEventListener<Song> retrievalEventListener) {
        // Create a new Song object to populate data
        final Song song = new Song();
        song.key = dataSnapshot.getKey();
        //  ----------------------------------------------------------------------------------------
        // | IMPORTANT NOTE: make sure that the variable name is EXACTLY the same as the node name. |
        //  ----------------------------------------------------------------------------------------
        //       ↓                           ↓
        song.setId(dataSnapshot.child("id").getValue().toString());
        //       ↓                           ↓
        song.setName(dataSnapshot.child("name").getValue().toString());
        //       ↓                           ↓
        song.setImage(dataSnapshot.child("image").getValue().toString());
        //       ↓                           ↓
        song.setSinger(dataSnapshot.child("singer").getValue().toString());
        //       ↓                           ↓
        song.setIdTheme(dataSnapshot.child("idTheme").getValue().toString());
        //       ↓                           ↓
        song.setIdTypes(dataSnapshot.child("idTypes").getValue().toString());
        //       ↓                           ↓
        song.setIdAlbum(dataSnapshot.child("idAlbum").getValue().toString());
        //       ↓                           ↓
        song.setIdPlaylist(dataSnapshot.child("idPlaylist").getValue().toString());
        //       ↓                           ↓
        song.setLinkSong(dataSnapshot.child("linkSong").getValue().toString());

        // Now we have parsed all of the attributes of the song object. We will feed it to the callback
        retrievalEventListener.OnDataRetrieved(song);
    }

    public void getSongByAlbum(String idAlbum, RetrieValEventListener<List<Song>> retrieValEventListener) {
        this.getAll(new RetrieValEventListener<List<Song>>() {
            @Override
            public void OnDataRetrieved(List<Song> songs) {
                ArrayList<Song> songByAlbums = new ArrayList<>();
                for (Song song : songs) {
                    if (song.getIdAlbum().equals(idAlbum)) {
                        songByAlbums.add((song));
                    }
                }
                retrieValEventListener.OnDataRetrieved(songByAlbums);
            }
        });
    }

    public void getSongByTheme(String idTheme, RetrieValEventListener<List<Song>> retrieValEventListener) {
        this.getAll(new RetrieValEventListener<List<Song>>() {
            @Override
            public void OnDataRetrieved(List<Song> songs) {
                ArrayList<Song> songByThemes = new ArrayList<>();
                for (Song song : songs) {
                    if (song.getIdTheme().equals(idTheme)) {
                        songByThemes.add((song));
                    }
                }
                retrieValEventListener.OnDataRetrieved(songByThemes);
            }
        });
    }

    public void getSongByListTheme(ArrayList<Theme> themes, RetrieValEventListener<List<Song>> retrieValEventListener) {
        this.getAll(new RetrieValEventListener<List<Song>>() {
            @Override
            public void OnDataRetrieved(List<Song> songs) {
                ArrayList<Song> songByListTheme = new ArrayList<>();
                for (Song song : songs) {
                    for (Theme theme : themes) {
                        if (song.getIdTheme().equals(theme.getId())) {
                            songByListTheme.add((song));
                        }
                        break;
                    }
                }
                retrieValEventListener.OnDataRetrieved(songByListTheme);
            }
        });
    }

    public void getSongByTypes(String idTypes, RetrieValEventListener<List<Song>> retrieValEventListener) {
        this.getAll(new RetrieValEventListener<List<Song>>() {
            @Override
            public void OnDataRetrieved(List<Song> songs) {
                ArrayList<Song> songByTypess = new ArrayList<>();
                for (Song song : songs) {
                    if (song.getIdTypes().equals(idTypes)) {
                        songByTypess.add((song));
                    }
                }
                retrieValEventListener.OnDataRetrieved(songByTypess);
            }
        });
    }

    public void getSongByListTypes(ArrayList<Types> typess, RetrieValEventListener<List<Song>> retrieValEventListener) {
        this.getAll(new RetrieValEventListener<List<Song>>() {
            @Override
            public void OnDataRetrieved(List<Song> songs) {
                ArrayList<Song> songByListTypes = new ArrayList<>();
                for (Song song : songs) {
                    for (Types types : typess)
                    if (song.getIdTypes().equals(types.getId())) {
                        songByListTypes.add((song));
                        break;
                    }
                }
                retrieValEventListener.OnDataRetrieved(songByListTypes);
            }
        });
    }

    public void getSongByPlaylist(String idPlaylist, RetrieValEventListener<List<Song>> retrieValEventListener) {
        Playlist_SongDao playlist_songDao = new Playlist_SongDao();
        playlist_songDao.getAll(new RetrieValEventListener<List<Playlist_Song>>() {
            @Override
            public void OnDataRetrieved(List<Playlist_Song> playlist_songs) {
                ArrayList<String> song_ids = new ArrayList<>();
                for (Playlist_Song playlist_song : playlist_songs) {
                    if (playlist_song.getIdPlaylist().equals(idPlaylist) && !song_ids.contains(playlist_song.getIdSong())) {
                        song_ids.add(playlist_song.getIdSong());
                    }
                }
                SongDao songDao = new SongDao();
                songDao.getAll(new RetrieValEventListener<List<Song>>() {
                    @Override
                    public void OnDataRetrieved(List<Song> mSongs) {
                        ArrayList<Song> songByPlaylists = new ArrayList<>();
                        for (String song_id : song_ids) {
                            for (Song song : mSongs) {
                                if (song.getId().equals(song_id)) {
                                    songByPlaylists.add(song);
                                    break;
                                }
                            }
                        }
                        retrieValEventListener.OnDataRetrieved(songByPlaylists);
                    }
                });
            }
        });
    }
}
