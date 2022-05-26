package com.example.myapplication.Model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Playlist extends MusicObject implements Serializable {

    private String name;
    private String image;
    private boolean isAdmin;
    private String uid;
    private String dateCreated;

    public Playlist() {
    }

    public Playlist(String id, String name, String image, boolean isAdmin, String uid, String dateCreated) {
        super(id);
        this.name = name;
        this.image = image;
        this.isAdmin = isAdmin;
        this.uid = uid;
        this.dateCreated = dateCreated;
    }

    public void update(String name, String image, boolean isAdmin, String uid, String dateCreated) {
        this.name = name;
        this.image = image;
        this.isAdmin = isAdmin;
        this.uid = uid;
        this.dateCreated = dateCreated;
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

    @NonNull
    @Override
    public String toString() {
        return "Playlist{" +
                "key='" + key + '\'' +
                ", id='" + super.getId() + '\'' +
                ", isAdmin=" + isAdmin +
                ", dateCreated='" + dateCreated + '\'' +
                ", name='" + name + '\'' +
                ", uid='" + uid + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
