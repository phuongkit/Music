package com.example.myapplication.Dao;

import com.example.myapplication.Dao.Listeners.RetrievalEventListener;
import com.example.myapplication.module.Chude;
import com.google.firebase.database.DataSnapshot;

public class ChuDeDao extends FirebaseDao<Chude> {
    public ChuDeDao(){
        // Specify the table name for the class
        super("chude");
    }

    @Override
    protected void parseDataSnapshot(DataSnapshot dataSnapshot, RetrievalEventListener<Chude> retrievalEventListener) {
        // Create a new Chude object to populate data
        final Chude chude = new Chude();
        chude.id = dataSnapshot.getKey();
        //  ----------------------------------------------------------------------------------------
        // | IMPORTANT NOTE: make sure that the variable name is EXACTLY the same as the node name. |
        //  ----------------------------------------------------------------------------------------
        //       ↓                           ↓
        chude.setIdChude(dataSnapshot.child("idChude").getValue().toString());
        //       ↓                           ↓
        chude.setHinh(dataSnapshot.child("hinh").getValue().toString());
        //       ↓                           ↓
        chude.setTen(dataSnapshot.child("ten").getValue().toString());

        // Now we have parsed all of the attributes of the Chude object. We will feed it to the callback
        retrievalEventListener.OnDataRetrieved(chude);
    }
}
