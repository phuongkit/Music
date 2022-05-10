package com.example.myapplication.Dao;

import android.util.Log;

import com.example.myapplication.Dao.Listeners.RetrievalEventListener;
import com.example.myapplication.Module.User;
import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;

public class UserDao extends FirebaseDao<User> {
    public UserDao(){
        // Specify the table name for the class
        super("user");
    }

    @Override
    protected void parseDataSnapshot(DataSnapshot dataSnapshot, RetrievalEventListener<User> retrievalEventListener) {
        // Create a new user object to populate data
        final User user = new User();
        user.key = dataSnapshot.getKey();
        //  ----------------------------------------------------------------------------------------
        // | IMPORTANT NOTE: make sure that the variable name is EXACTLY the same as the node name. |
        //  ----------------------------------------------------------------------------------------
        //       ↓                           ↓
        user.setId(dataSnapshot.child("id").getValue().toString());
        //       ↓                           ↓
        user.setDisplayName(dataSnapshot.child("displayName").getValue().toString());
        //       ↓                           ↓
        user.setPassword(dataSnapshot.child("password").getValue().toString());
        //       ↓                           ↓
        user.setEmail(dataSnapshot.child("email").getValue().toString());
        //       ↓                           ↓
        user.setPhone(dataSnapshot.child("phone").getValue().toString());
        //       ↓                           ↓
        user.setAvatar(dataSnapshot.child("avatar").getValue().toString());
        //       ↓                           ↓
        user.setRole(Integer.valueOf(dataSnapshot.child("role").getValue().toString()));

        // Now we have parsed all of the attributes of the user object. We will feed it to the callback
        retrievalEventListener.OnDataRetrieved(user);
    }
}
