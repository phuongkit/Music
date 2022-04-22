package com.example.myapplication.Dao;

import com.example.myapplication.Dao.Listeners.RetrievalEventListener;
import com.example.myapplication.Module.Baihat;
import com.example.myapplication.Module.Banner;
import com.google.firebase.database.DataSnapshot;

public class BannerDao  extends FirebaseDao<Banner>{
    public BannerDao(){
        // Specify the table name for the class
        super("banner");
    }

    @Override
    protected void parseDataSnapshot(DataSnapshot dataSnapshot, RetrievalEventListener<Banner> retrievalEventListener) {
        // Create a new banner object to populate data
        final Banner banner = new Banner();
        banner.id = dataSnapshot.getKey();
        //  ----------------------------------------------------------------------------------------
        // | IMPORTANT NOTE: make sure that the variable name is EXACTLY the same as the node name. |
        //  ----------------------------------------------------------------------------------------
        //       ↓                           ↓
        banner.setIdBanner(dataSnapshot.child("idBanner").getValue().toString());
        //       ↓                           ↓
        banner.setHinhAnh(dataSnapshot.child("hinhAnh").getValue().toString());
        //       ↓                           ↓
        banner.setNoiDung(dataSnapshot.child("noiDung").getValue().toString());
        //       ↓                           ↓
        banner.setIdBaihat(dataSnapshot.child("idBaihat").child("linkBaihat").getValue().toString());

        // Now we have parsed all of the attributes of the banner object. We will feed it to the callback
        retrievalEventListener.OnDataRetrieved(banner);
    }
}
