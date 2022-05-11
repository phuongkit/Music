package com.example.myapplication.Module;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Album extends MusicObject implements Serializable {
    private String name;
    private String image;
    private String singer;

    public Album() {
    }

    public Album(String id, String name, String image, String singer) {
        super(id);
        this.name = name;
        this.image = image;
        this.singer = singer;
    }

    public void update(String id, String name, String image, String singer) {
        super.setId(id);
        this.name = name;
        this.image = image;
        this.singer = singer;
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

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id='" + key + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", singer='" + singer + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
