package com.example.myapplication.Dao;

import com.example.myapplication.Dao.Listeners.RetrievalEventListener;
import com.example.myapplication.module.Baihat;
import com.example.myapplication.module.Banner;
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

        Baihat baihat = new Baihat();
        baihat.id = dataSnapshot.child("baihat").getKey();
        //  ----------------------------------------------------------------------------------------
        // | IMPORTANT NOTE: make sure that the variable name is EXACTLY the same as the node name. |
        //  ----------------------------------------------------------------------------------------
        //       ↓                           ↓
        baihat.setTenBaihat(dataSnapshot.child("baihat").child("tenBaihat").getValue().toString());
        //       ↓                           ↓
        baihat.setHinhBaihat(dataSnapshot.child("baihat").child("hinhBaihat").getValue().toString());
        //       ↓                           ↓
        baihat.setIdAlbum(dataSnapshot.child("baihat").child("idAlbum").getValue().toString());
        //       ↓                           ↓
        baihat.setIdBaihat(dataSnapshot.child("baihat").child("idBaihat").getValue().toString());
        //       ↓                           ↓
        baihat.setIdPlaylist(dataSnapshot.child("baihat").child("idPlaylist").getValue().toString());
        //       ↓                           ↓
        baihat.setCaSi(dataSnapshot.child("baihat").child("caSi").getValue().toString());
        //       ↓                           ↓
        baihat.setLinkBaihat(dataSnapshot.child("baihat").child("linkBaihat").getValue().toString());
        //       ↓                           ↓
        banner.setBaihat(baihat);

        // Now we have parsed all of the attributes of the banner object. We will feed it to the callback
        retrievalEventListener.OnDataRetrieved(banner);
    }
}
