package com.example.myapplication.Module;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Theme  implements Serializable {
    @Exclude
    public String key;

    private String id;
    private String name;
    private String image;

    public Theme() {
    }

    public Theme(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public void update(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return "Theme{" +
                "id='" + id + '\'' +
                ", id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
