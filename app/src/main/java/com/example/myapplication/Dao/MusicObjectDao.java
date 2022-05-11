package com.example.myapplication.Dao;

import android.util.Log;

import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Module.MusicObject;
import com.google.firebase.database.DataSnapshot;

import java.util.concurrent.CountDownLatch;

public class MusicObjectDao extends FirebaseDao<MusicObject> {

    CountDownLatch latch = new CountDownLatch(1);
    String newKey = "";
    boolean ready = false;

    public MusicObjectDao(String firebaseName) {
        super(firebaseName);
    }

    @Override
    protected void parseDataSnapshot(DataSnapshot dataSnapshot, RetrieValEventListener<MusicObject> retrievalEventListener) {
        Log.d("Test", "Music Object: ");
        // Create a new banner object to populate data
        final MusicObject musicObject = new MusicObject();
        musicObject.key = dataSnapshot.getKey();
        //  ----------------------------------------------------------------------------------------
        // | IMPORTANT NOTE: make sure that the variable name is EXACTLY the same as the node name. |
        //  ----------------------------------------------------------------------------------------
        //       ↓                           ↓
        musicObject.setId(dataSnapshot.child("id").getValue().toString());

        Log.d("Test", "Music Object: " + musicObject.toString());

        // Now we have parsed all of the attributes of the banner object. We will feed it to the callback
        retrievalEventListener.OnDataRetrieved(musicObject);
    }
}
