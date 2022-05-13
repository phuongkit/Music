package com.example.myapplication.Module;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Playlist extends MusicObject implements Serializable {

    private boolean isAdmin;
    private String dateCreated;
    private String name;
    private String uid;
    private String image;

    public Playlist() {
    }

    public Playlist(String id, boolean isAdmin, String dateCreated, String name, String uid, String image) {
        super(id);
        this.isAdmin = isAdmin;
        this.dateCreated = dateCreated;
        this.name = name;
        this.uid = uid;
        this.image = image;
    }

    public void update(String id, boolean isAdmin, String dateCreated, String name, String uid, String image) {
        super.setId(id);
        this.isAdmin = isAdmin;
        this.dateCreated = dateCreated;
        this.name = name;
        this.uid = uid;
        this.image = image;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "key='" + key + '\'' +
                ", id='" + id + '\'' +
                ", isAdmin=" + isAdmin +
                ", dateCreated='" + dateCreated + '\'' +
                ", name='" + name + '\'' +
                ", uid='" + uid + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public List<MusicObject> upCastList(List<Playlist> playlists) {
        List<MusicObject> musicObjects = new ArrayList<>();
        for (Playlist playlist : playlists) {
            musicObjects.add(playlist);
        }
        return musicObjects;
    }
}
