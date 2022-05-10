package com.example.myapplication.Module;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MusicObject implements Serializable {
    @Exclude
    public String key;

    public String id;

    public MusicObject() {
    }

    public MusicObject(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String GetNewId(List<MusicObject> musicObjects) {
        int id = 0;
        for (MusicObject musicObject : musicObjects) {
            int old_id = Integer.parseInt(musicObject.getId());
            if (old_id > id) {
                id = old_id;
            }
        }
        id++;
        return String.valueOf(id);
    }
}
