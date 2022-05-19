package com.example.myapplication.Dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.Dao.Impl.GenericDao;
import com.example.myapplication.Dao.Listeners.RetrieNewKeyEventListener;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Module.MusicObject;
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

    public void get(String id, final RetrieValEventListener<T> retrievalEventListener) {
        DatabaseReference rowReference = dbReference.child(tableName).child(id);
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

//    public void getNewKey(RetrieNewKeyEventListener retrieNewKeyEventListener) {
//
//        this.getAll(new RetrieValEventListener<List<T>>() {
//            @Override
//            public void OnDataRetrieved(List<T> ts) {
//                long index = 0;
//                for (int i = 0; i < ts.size(); i++) {
//                    MusicObject musicObject = (MusicObject) ts.get(i);
//                    try {
//                        long Key = Integer.valueOf(musicObject.key);
//                        if (Key > index) {
//                            index = Key;
//                        }
//                    } catch (NumberFormatException ex) {
//                        Log.e("Error", ex.getMessage());
//                    }
//                }
//                index++;
//                String newKey = String.valueOf(index);
//                dbReference.child(newKey).push();
//                retrieNewKeyEventListener.OnNewKeyRetrieved(newKey);
//            }
//        });
//    }

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

    public void save(T t, String id, final TaskListener taskListener) {
        Task<Void> task = dbReference.child(tableName).child(id).setValue(t);
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

    public void delete(String id, TaskListener taskListener) {
        save(null, id, taskListener);
    }
}
