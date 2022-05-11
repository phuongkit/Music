package com.example.myapplication.Module;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Banner extends MusicObject implements Serializable {
    private String name;
    private String image;
    private String idSong;

    public Banner(){

    }

    public Banner(String id, String name, String image, String idSong) {
        super(id);
        this.name = name;
        this.image = image;
        this.idSong = idSong;
    }

    public void update(String id, String name, String image, String idSong) {
        super.setId(id);
        this.name = name;
        this.image = image;
        this.idSong = idSong;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIdSong() {
        return idSong;
    }

    public void setIdSong(String idSong) {
        this.idSong = idSong;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "id='" + id + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", idSong='" + idSong + '\'' +
                '}';
    }
}
