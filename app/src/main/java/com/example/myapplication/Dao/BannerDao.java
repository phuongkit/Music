package com.example.myapplication.Dao;

import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Model.Banner;
import com.google.firebase.database.DataSnapshot;

public class BannerDao extends FirebaseDao<Banner>{
    public BannerDao(){
        // Specify the table name for the class
        super("banner");
    }

    @Override
    protected void parseDataSnapshot(DataSnapshot dataSnapshot, RetrieValEventListener<Banner> retrievalEventListener) {
        // Create a new banner object to populate data
        final Banner banner = new Banner();
        banner.key = dataSnapshot.getKey();
        //  ----------------------------------------------------------------------------------------
        // | IMPORTANT NOTE: make sure that the variable name is EXACTLY the same as the node name. |
        //  ----------------------------------------------------------------------------------------
        //       ↓                           ↓
        banner.setId(dataSnapshot.child("id").getValue().toString());
        //       ↓                           ↓
        banner.setName(dataSnapshot.child("name").getValue().toString());
        //       ↓                           ↓
        banner.setImage(dataSnapshot.child("image").getValue().toString());
        //       ↓                           ↓
        banner.setIdSong(dataSnapshot.child("idSong").getValue().toString());

        // Now we have parsed all of the attributes of the banner object. We will feed it to the callback
        retrievalEventListener.OnDataRetrieved(banner);
    }
}
