package com.example.myapplication.Dao.Impl;

import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.google.firebase.database.DataSnapshot;

import java.util.List;

public interface GenericDao<T> {
    String getNewKey();
    void get(String id, final RetrieValEventListener<T> retrievalEventListener);
    void getAll(final RetrieValEventListener<List<T>> retrievalEventListener);
    void save(T t, String id, final TaskListener taskListener);
    void delete(String id, TaskListener taskListener);
}
