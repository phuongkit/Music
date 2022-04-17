package com.example.myapplication.Dao;

import com.example.myapplication.Dao.FirebaseDao;
import com.example.myapplication.Dao.Listeners.RetrievalEventListener;
import com.example.myapplication.module.Album;
import com.example.myapplication.module.Baihat;
import com.example.myapplication.module.Album;
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
        album.id = dataSnapshot.getKey();
        //  ----------------------------------------------------------------------------------------
        // | IMPORTANT NOTE: make sure that the variable name is EXACTLY the same as the node name. |
        //  ----------------------------------------------------------------------------------------
        //       ↓                           ↓
        album.setIdAlbum(dataSnapshot.child("idAlbum").getValue().toString());
        //       ↓                           ↓
        album.setTenAlbum(dataSnapshot.child("tenAlbum").getValue().toString());
        //       ↓                           ↓
        album.setTenCasiAlbum(dataSnapshot.child("tenCaSiAlbum").getValue().toString());

        // Now we have parsed all of the attributes of the Album object. We will feed it to the callback
        retrievalEventListener.OnDataRetrieved(album);
    }
}
