package com.example.myapplication.Dao;

import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Model.Playlist_Song;
import com.google.firebase.database.DataSnapshot;

import java.util.List;

public class Playlist_SongDao extends FirebaseDao<Playlist_Song> {
    public Playlist_SongDao(){
        // Specify the table name for the class
        super("playlist_song");
    }

    @Override
    protected void parseDataSnapshot(DataSnapshot dataSnapshot, RetrieValEventListener<Playlist_Song> retrievalEventListener) {
        // Create a new Chude object to populate data
        final Playlist_Song playlist_Song = new Playlist_Song();
        playlist_Song.key = dataSnapshot.getKey();
        //  ----------------------------------------------------------------------------------------
        // | IMPORTANT NOTE: make sure that the variable name is EXACTLY the same as the node name. |
        //  ----------------------------------------------------------------------------------------
        //       ↓                           ↓
        playlist_Song.setId(dataSnapshot.child("id").getValue().toString());
        //       ↓                           ↓
        playlist_Song.setIdSong(dataSnapshot.child("idSong").getValue().toString());
        //       ↓                           ↓
        playlist_Song.setIdPlaylist(dataSnapshot.child("idPlaylist").getValue().toString());

        // Now we have parsed all of the attributes of the Playlist_Song object. We will feed it to the callback
        retrievalEventListener.OnDataRetrieved(playlist_Song);
    }

    public void deletePlaylistSongByIdPlaylistAndIdSong(String idPlaylist, String idSong, TaskListener taskListener) {
        this.getAll(new RetrieValEventListener<List<Playlist_Song>>() {
            @Override
            public void OnDataRetrieved(List<Playlist_Song> playlist_songs) {
                for (Playlist_Song playlist_song : playlist_songs) {
                    if (playlist_song.getIdPlaylist().equals(idPlaylist) && playlist_song.getIdSong().equals(idSong)) {
                        delete(playlist_song.key, taskListener);
                    }
                }
            }
        });
    }
}
