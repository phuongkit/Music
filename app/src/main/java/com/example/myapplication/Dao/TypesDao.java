package com.example.myapplication.Dao;

import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Module.Types;
import com.google.firebase.database.DataSnapshot;

public class TypesDao extends FirebaseDao<Types> {
    public TypesDao(){
        // Specify the table name for the class
        super("types");
    }

    @Override
    protected void parseDataSnapshot(DataSnapshot dataSnapshot, RetrieValEventListener<Types> retrievalEventListener) {
        // Create a new Types object to populate data
        final Types types = new Types();
        types.key = dataSnapshot.getKey();
        //  ----------------------------------------------------------------------------------------
        // | IMPORTANT NOTE: make sure that the variable name is EXACTLY the same as the node name. |
        //  ----------------------------------------------------------------------------------------
        //       ↓                           ↓
        types.setId(dataSnapshot.child("id").getValue().toString());
        //       ↓                           ↓
        types.setImage(dataSnapshot.child("image").getValue().toString());
        //       ↓                           ↓
        types.setName(dataSnapshot.child("name").getValue().toString());
        //       ↓                           ↓
        types.setIdTheme(dataSnapshot.child("idTheme").getValue().toString());

        // Now we have parsed all of the attributes of the types object. We will feed it to the callback
        retrievalEventListener.OnDataRetrieved(types);
    }
}

