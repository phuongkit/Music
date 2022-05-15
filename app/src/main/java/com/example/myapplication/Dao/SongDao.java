package com.example.myapplication.Dao;

import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Module.Song;
import com.google.firebase.database.DataSnapshot;

public class SongDao extends FirebaseDao<Song>{
    public SongDao(){
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
}
