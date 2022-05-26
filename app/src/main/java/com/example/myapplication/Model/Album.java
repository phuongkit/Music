package com.example.myapplication.Model;

import androidx.annotation.NonNull;

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

    public void update(String name, String image, String singer) {
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

    @NonNull
    @Override
    public String toString() {
        return "Album{" +
                ", key='" + key + '\'' +
                ", id='" + super.getId() + '\'' +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", singer='" + singer + '\'' +
                '}';
    }
}
