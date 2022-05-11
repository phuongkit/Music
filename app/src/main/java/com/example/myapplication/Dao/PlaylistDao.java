package com.example.myapplication.Dao;

import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Module.Playlist;
import com.google.firebase.database.DataSnapshot;

public class PlaylistDao  extends FirebaseDao<Playlist> {
    public PlaylistDao(){
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
}
