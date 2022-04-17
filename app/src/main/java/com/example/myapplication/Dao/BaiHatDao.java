package com.example.myapplication.Dao;

import android.util.Log;

import com.example.myapplication.Dao.Listeners.RetrievalEventListener;
import com.example.myapplication.module.Baihat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BaiHatDao extends FirebaseDao<Baihat>{ 
    public BaiHatDao(){
    // Specify the table name for the class
    super("baihat");
}

    @Override
    protected void parseDataSnapshot(DataSnapshot dataSnapshot, RetrievalEventListener<Baihat> retrievalEventListener) {
        // Create a new Baihat object to populate data
        final Baihat baihat = new Baihat();
        baihat.id = dataSnapshot.getKey();
        //  ----------------------------------------------------------------------------------------
        // | IMPORTANT NOTE: make sure that the variable name is EXACTLY the same as the node name. |
        //  ----------------------------------------------------------------------------------------
        //       ↓                           ↓
        baihat.setTenBaihat(dataSnapshot.child("tenBaihat").getValue().toString());
        //       ↓                           ↓
        baihat.setHinhBaihat(dataSnapshot.child("hinhBaihat").getValue().toString());
        //       ↓                           ↓
        baihat.setIdAlbum(dataSnapshot.child("idAlbum").getValue().toString());
        //       ↓                           ↓
        baihat.setIdBaihat(dataSnapshot.child("idBaihat").getValue().toString());
        //       ↓                           ↓
        baihat.setIdPlaylist(dataSnapshot.child("idPlaylist").getValue().toString());
        //       ↓                           ↓
        baihat.setCaSi(dataSnapshot.child("caSi").getValue().toString());
        //       ↓                           ↓
        baihat.setLinkBaihat(dataSnapshot.child("linkBaihat").getValue().toString());

        // Now we have parsed all of the attributes of the Baihat object. We will feed it to the callback
        retrievalEventListener.OnDataRetrieved(baihat);
    }
}
