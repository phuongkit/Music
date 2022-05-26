package com.example.myapplication.Dao;

import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Model.Playlist;
import com.example.myapplication.Model.Playlist_Song;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class PlaylistDao extends FirebaseDao<Playlist> {
    public PlaylistDao() {
        // Specify the table name for the class
        super("playlist");
    }

    @Override
    protected void parseDataSnapshot(DataSnapshot dataSnapshot, RetrieValEventListener<Playlist> retrievalEventListener) {
        // Create a new Chude object to populate data
        final Playlist playlist = new Playlist();
        playlist.key = dataSnapshot.getKey();
        //  ----------------------------------------------------------------------------------------
        // | IMPORTANT NOTE: make sure that the variable name is EXACTLY the same as the node name. |
        //  ----------------------------------------------------------------------------------------
        //       ↓                           ↓
        playlist.setId(dataSnapshot.child("id").getValue().toString());
        //       ↓                           ↓
        playlist.setAdmin(Boolean.parseBoolean(dataSnapshot.child("admin").getValue().toString()));
        //       ↓                           ↓
        playlist.setDateCreated(dataSnapshot.child("dateCreated").getValue().toString());
        //       ↓                           ↓
        playlist.setName(dataSnapshot.child("name").getValue().toString());
        //       ↓                           ↓
        playlist.setImage(dataSnapshot.child("image").getValue().toString());
        //       ↓                           ↓
        playlist.setUid(dataSnapshot.child("uid").getValue().toString());

        // Now we have parsed all of the attributes of the Playlist object. We will feed it to the callback
        retrievalEventListener.OnDataRetrieved(playlist);
    }

    public void getPlaylistById(String idPlaylist,RetrieValEventListener<Playlist> retrieValEventListener) {
        this.getAll(new RetrieValEventListener<List<Playlist>>() {
            @Override
            public void OnDataRetrieved(List<Playlist> playlists) {
                for (Playlist playlist : playlists) {
                    if (playlist.getId().equals(idPlaylist)) {
                        retrieValEventListener.OnDataRetrieved(playlist);
                        break;
                    }
                }
            }
        });
    }

    public void getPlaylistByUser(String idUser, RetrieValEventListener<List<Playlist>> retrieValEventListener) {
        this.getAll(new RetrieValEventListener<List<Playlist>>() {
            @Override
            public void OnDataRetrieved(List<Playlist> playlists) {
                ArrayList<Playlist> playlistByUsers = new ArrayList<>();
                for (Playlist playlist : playlists) {
                    if (playlist.getUid().equals(idUser)) {
                        playlistByUsers.add(playlist);
                    }
                }
                retrieValEventListener.OnDataRetrieved(playlistByUsers);
            }
        });
    }

    public void getPlaylistBySong(String idSong, RetrieValEventListener<List<Playlist>> retrieValEventListener) {
        Playlist_SongDao playlist_songDao = new Playlist_SongDao();
        playlist_songDao.getAll(new RetrieValEventListener<List<Playlist_Song>>() {
            @Override
            public void OnDataRetrieved(List<Playlist_Song> playlist_songs) {
                ArrayList<String> playlistIdBySongs = new ArrayList<>();
                for (Playlist_Song playlist_song : playlist_songs) {
                    if (playlist_song.getIdSong().equals(idSong)) {
                        playlistIdBySongs.add(playlist_song.getIdPlaylist());
                    }
                }
                PlaylistDao playlistDao = new PlaylistDao();
                playlistDao.getAll(new RetrieValEventListener<List<Playlist>>() {
                    @Override
                    public void OnDataRetrieved(List<Playlist> playlists) {
                        ArrayList<Playlist> playlistBySongs = new ArrayList<>();
                        for (String playlistIdBySong : playlistIdBySongs) {
                            for (Playlist playlist : playlists) {
                                if (playlist.getId().equals(playlistIdBySong)) {
                                    playlistBySongs.add(playlist);
                                }
                            }
                        }
                        retrieValEventListener.OnDataRetrieved(playlistBySongs);
                    }
                });
            }
        });
    }

    public void getPlaylistByAdmin(RetrieValEventListener<List<Playlist>> retrieValEventListener) {
        this.getAll(new RetrieValEventListener<List<Playlist>>() {
            @Override
            public void OnDataRetrieved(List<Playlist> playlists) {
                ArrayList<Playlist> playlistByAdmins = new ArrayList<>();
                for (Playlist playlist : playlists) {
                    if (playlist.isAdmin()) {
                        playlistByAdmins.add(playlist);
                    }
                }
                retrieValEventListener.OnDataRetrieved(playlistByAdmins);
            }
        });
    }
}
