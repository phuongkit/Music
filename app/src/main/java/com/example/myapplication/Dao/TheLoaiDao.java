package com.example.myapplication.Dao;

import com.example.myapplication.Dao.Listeners.RetrievalEventListener;
import com.example.myapplication.Module.Chude;
import com.example.myapplication.Module.Theloai;
import com.google.firebase.database.DataSnapshot;

public class TheLoaiDao extends FirebaseDao<Theloai> {
    public TheLoaiDao(){
        // Specify the table name for the class
        super("theloai");
    }

    @Override
    protected void parseDataSnapshot(DataSnapshot dataSnapshot, RetrievalEventListener<Theloai> retrievalEventListener) {
        // Create a new Theloai object to populate data
        final Theloai theloai = new Theloai();
        theloai.id = dataSnapshot.getKey();
        //  ----------------------------------------------------------------------------------------
        // | IMPORTANT NOTE: make sure that the variable name is EXACTLY the same as the node name. |
        //  ----------------------------------------------------------------------------------------
        //       ↓                           ↓
        theloai.setIdTheloai(dataSnapshot.child("idTheloai").getValue().toString());
        //       ↓                           ↓
        theloai.setHinh(dataSnapshot.child("hinh").getValue().toString());
        //       ↓                           ↓
        theloai.setTen(dataSnapshot.child("ten").getValue().toString());

        Chude chude = new Chude();
        chude.id = dataSnapshot.child("chude").getKey();
        //  ----------------------------------------------------------------------------------------
        // | IMPORTANT NOTE: make sure that the variable name is EXACTLY the same as the node name. |
        //  ----------------------------------------------------------------------------------------
        //       ↓                           ↓
        chude.setIdChude(dataSnapshot.child("chude").child("idChude").getValue().toString());
        //       ↓                           ↓
        chude.setHinh(dataSnapshot.child("chude").child("hinh").getValue().toString());
        //       ↓                           ↓
        chude.setTen(dataSnapshot.child("chude").child("ten").getValue().toString());
        //       ↓                           ↓
        theloai.setChude(chude);

        // Now we have parsed all of the attributes of the Theloai object. We will feed it to the callback
        retrievalEventListener.OnDataRetrieved(theloai);
    }
}

