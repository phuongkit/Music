package com.example.myapplication.Dao;

import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Module.Theme;
import com.google.firebase.database.DataSnapshot;

public class ThemeDao extends FirebaseDao<Theme> {
    public ThemeDao(){
        // Specify the table name for the class
        super("theme");
    }

    @Override
    protected void parseDataSnapshot(DataSnapshot dataSnapshot, RetrieValEventListener<Theme> retrievalEventListener) {
        // Create a new Theme object to populate data
        final Theme theme = new Theme();
        theme.key = dataSnapshot.getKey();
        //  ----------------------------------------------------------------------------------------
        // | IMPORTANT NOTE: make sure that the variable name is EXACTLY the same as the node name. |
        //  ----------------------------------------------------------------------------------------
        //       ↓                           ↓
        theme.setId(dataSnapshot.child("id").getValue().toString());
        //       ↓                           ↓
        theme.setImage(dataSnapshot.child("image").getValue().toString());
        //       ↓                           ↓
        theme.setName(dataSnapshot.child("name").getValue().toString());

        // Now we have parsed all of the attributes of the theme object. We will feed it to the callback
        retrievalEventListener.OnDataRetrieved(theme);
    }
}
