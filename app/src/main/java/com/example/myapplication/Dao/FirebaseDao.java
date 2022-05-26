package com.example.myapplication.Dao;

import androidx.annotation.NonNull;

import com.example.myapplication.Dao.Impl.GenericDao;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public abstract class FirebaseDao<T> implements GenericDao<T> {
    protected static final DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
    protected String tableName;

    public FirebaseDao(String tableName) {
        this.tableName = tableName;
    }

    public void get(String key, final RetrieValEventListener<T> retrievalEventListener) {
        DatabaseReference rowReference = dbReference.child(tableName).child(key);
        Query query = rowReference;
        rowReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                parseDataSnapshot(dataSnapshot, new RetrieValEventListener<T>() {
                    @Override
                    public void OnDataRetrieved(T t) {
                        retrievalEventListener.OnDataRetrieved(t);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public String getNewKey() {
        return dbReference.child(tableName).push().getKey();
    }

    protected abstract void parseDataSnapshot(DataSnapshot dataSnapshot, RetrieValEventListener<T> retrievalEventListener);

    public void getAll(final RetrieValEventListener<List<T>> retrievalEventListener) {
        DatabaseReference rowReference = dbReference.child(tableName);
        rowReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<T> list = new ArrayList<T>();
                final long len = dataSnapshot.getChildrenCount();
                if (len == 0) {
                    retrievalEventListener.OnDataRetrieved(list);
                    return;
                }
                RetrieValEventListener<T> listRetrieValEventListener = new RetrieValEventListener<T>() {
                    @Override
                    public void OnDataRetrieved(T t) {
                        list.add(t);
                        if (list.size() == len) {
                            retrievalEventListener.OnDataRetrieved(list);
                        }
                    }
                };
                for (DataSnapshot currentDataSnapshot : dataSnapshot.getChildren())
                    parseDataSnapshot(currentDataSnapshot, listRetrieValEventListener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void save(T t, String key, final TaskListener taskListener) {
        Task<Void> task = dbReference.child(tableName).child(key).setValue(t);
        task.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                taskListener.OnSuccess();
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                taskListener.OnFail();
            }
        });
    }

    public void delete(String key, TaskListener taskListener) {
        save(null, key, taskListener);
    }
}
