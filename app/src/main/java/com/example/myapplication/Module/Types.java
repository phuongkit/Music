package com.example.myapplication.Module;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Types extends MusicObject implements Serializable {
    private String name;
    private String image;

    public Types() {
    }

    public Types(String id, String name, String image) {
        super(id);
        this.name = name;
        this.image = image;
    }

    public void update(String id, String name, String image) {
        super.setId(id);
        this.name = name;
        this.image = image;
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

    @Override
    public String toString() {
        return "Types{" +
                "id='" + id + '\'' +
                ", id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

