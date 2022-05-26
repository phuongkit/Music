package com.example.myapplication.Dao;

import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Model.Album;

import java.util.List;

public  interface IAlbumDAO {
    String getNewKey();
    void get(String id, final RetrieValEventListener<Album> retrievalEventListener);
    void getAll(final RetrieValEventListener<List<Album>> retrievalEventListener);
    void save(Album album, String id, final TaskListener taskListener);
    void delete(String id, TaskListener taskListener);
}
