package com.example.myapplication.Dao;

import com.example.myapplication.Dao.Listeners.RetrievalEventListener;
import com.example.myapplication.Module.Playlist_Song;
import com.google.firebase.database.DataSnapshot;

public class Playlist_SongDao extends FirebaseDao<Playlist_Song> {
    public Playlist_SongDao(){
        // Specify the table name for the class
        super("playlist_song");
    }

    @Override
    protected void parseDataSnapshot(DataSnapshot dataSnapshot, RetrievalEventListener<Playlist_Song> retrievalEventListener) {
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
}
