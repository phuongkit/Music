package com.example.myapplication.Dao;

import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Module.Album;
import com.google.firebase.database.DataSnapshot;

import java.util.List;

public class AlbumDao extends FirebaseDao<Album> implements IAlbumDAO{
    public AlbumDao(){
        // Specify the table name for the class
        super("album");
    }

    @Override
    public String getNewKey() {
        return super.getNewKey();
    }

    @Override
    public void getAll(RetrieValEventListener<List<Album>> retrievalEventListener) {
        super.getAll(retrievalEventListener);
    }

    @Override
    public void get(String id, RetrieValEventListener<Album> retrievalEventListener) {
        super.get(id, retrievalEventListener);
    }

    @Override
    public void save(Album album, String id, TaskListener taskListener) {
        super.save(album, id, taskListener);
    }

    @Override
    public void delete(String id, TaskListener taskListener) {
        super.delete(id, taskListener);
    }

    @Override
    protected void parseDataSnapshot(DataSnapshot dataSnapshot, RetrieValEventListener<Album> retrievalEventListener) {
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
