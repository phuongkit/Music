package com.example.myapplication.Dao;

import com.example.myapplication.Dao.Listeners.RetrievalEventListener;
import com.example.myapplication.Module.Album;
import com.google.firebase.database.DataSnapshot;

public class AlbumDao extends FirebaseDao<Album> {
    public AlbumDao(){
        // Specify the table name for the class
        super("album");
    }

    @Override
    protected void parseDataSnapshot(DataSnapshot dataSnapshot, RetrievalEventListener<Album> retrievalEventListener) {
        // Create a new Album object to populate data
        final Album album = new Album();
        album.key = dataSnapshot.getKey();
        //  ----------------------------------------------------------------------------------------
        // | IMPORTANT NOTE: make sure that the variable name is EXACTLY the same as the node name. |
        //  ----------------------------------------------------------------------------------------
        //       ↓                           ↓
        album.setId(dataSnapshot.child("id").getValue().toString());
        //       ↓                           ↓
        album.setName(dataSnapshot.child("name").getValue().toString());
        //       ↓                           ↓
        album.setSinger(dataSnapshot.child("singer").getValue().toString());
        //       ↓                           ↓
        album.setImage(dataSnapshot.child("image").getValue().toString());

        // Now we have parsed all of the attributes of the Album object. We will feed it to the callback
        retrievalEventListener.OnDataRetrieved(album);
    }
}
